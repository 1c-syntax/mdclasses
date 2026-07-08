/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * MDClasses is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * MDClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MDClasses.
 */
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.reader.MDMerger;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
@Slf4j
public class MDClasses {

  private static final Pattern SEARCH_CONFIGURATION = Pattern.compile("Configuration\\.(xml|mdo)$");
  private static final Pattern SEARCH_EX_RES =
    Pattern.compile("[a-zA-Zа-яА-Я0-9_ёЁ]+?(?<!Configuration)\\.(xml|mdo)$");

  /**
   * Создает пустую конфигурацию
   *
   * @return Пустая конфигурация
   */
  public MDClass createConfiguration() {
    return Configuration.EMPTY;
  }

  /**
   * Создает пустой внешний отчет
   *
   * @return Пустой внешний отчет
   */
  public ExternalSource createExternalReport() {
    return ExternalReport.EMPTY;
  }

  /**
   * Создает конфигурацию или расширение по указанному пути
   *
   * @param path Путь к корню проекта
   * @return Конфигурация или расширение
   */
  public MDClass createConfiguration(Path path) {
    return createConfiguration(path, MDCReadSettings.DEFAULT);
  }

  /**
   * Создает конфигурацию или расширение по указанному пути
   *
   * @param path         Путь к корню проекта
   * @param readSettings Настройки чтения
   * @return Конфигурация или расширение
   */
  public MDClass createConfiguration(Path path, MDCReadSettings readSettings) {
    return MDOReader.readConfiguration(path, readSettings);
  }

  /**
   * Создает внешнюю обработку или внешний отчет по указанному пути
   *
   * @param mdoPath Путь к файлу описания обработки или отчета
   * @return Конфигурация или расширение
   */
  public MDClass createExternalSource(Path mdoPath) {
    return MDOReader.readExternalSource(mdoPath);
  }

  /**
   * Возвращает список конфигураций\расширений в указанном каталоге исходных файлов
   *
   * @param sourcePath каталог исходных файлов
   * @return Список прочитанных контейнеров конфигураций и расширений
   */
  public List<MDClass> createConfigurations(Path sourcePath) {
    return createConfigurations(sourcePath, MDCReadSettings.DEFAULT);
  }

  /**
   * Читает каталог проекта и
   * - возвращает объект MDClass, если содержится только один объект MDC
   * - возвращает объединенную конфигурацию с расширениями
   * - возвращает объединение расширений с пустой конфигурацией
   *
   * @param sourcePath Путь к каталогу исходников
   * @return Результат чтения решения
   */
  public Solution createSolution(Path sourcePath) {
    return createSolution(sourcePath, MDCReadSettings.DEFAULT);
  }

  /**
   * Читает каталог проекта и
   * - возвращает Solution с единственной конфигурацией, если содержится только один объект MDC
   * - возвращает Solution с объединенной конфигурацией и расширениями
   * - возвращает Solution с объединением расширений с пустой конфигурацией
   *
   * @param sourcePath   Путь к каталогу исходников
   * @param readSettings Настройки чтения проекта
   * @return Результат чтения решения
   */
  public Solution createSolution(Path sourcePath, MDCReadSettings readSettings) {
    var mdcs = createConfigurations(sourcePath, readSettings);

    if (mdcs.isEmpty()) {
      return Solution.EMPTY;
    }

    if (mdcs.size() == 1) {
      var mdc = mdcs.getFirst();
      if (mdc instanceof Configuration cf) {
        return buildSolution(cf, Collections.emptyList());
      }
      if (mdc instanceof ConfigurationExtension ext) {
        return buildSolution(Configuration.EMPTY, List.of(ext));
      }

      // заглушка, если получилось что-то странное
      return Solution.EMPTY;
    }

    // решение содержит несколько компонент, разделяем
    var base = mdcs.stream()
      .filter(Configuration.class::isInstance)
      .map(Configuration.class::cast)
      .findFirst()
      .orElse(Configuration.EMPTY);

    var extensions = mdcs.stream()
      .filter(ConfigurationExtension.class::isInstance)
      .map(ConfigurationExtension.class::cast)
      .toList();

    // что-то странное прочли
    if (base.isEmpty() && extensions.isEmpty()) {
      return Solution.EMPTY;
    }
    return buildSolution(base, extensions);
  }

  /**
   * Возвращает список конфигураций\расширений в указанном каталоге исходных файлов
   *
   * @param sourcePath   каталог исходных файлов
   * @param readSettings Настройки чтения
   * @return Список прочитанных контейнеров конфигураций и расширений
   */
  public List<MDClass> createConfigurations(Path sourcePath, MDCReadSettings readSettings) {
    return findFiles(sourcePath, SEARCH_CONFIGURATION).parallelStream()
      .map(path -> createConfiguration(path, readSettings))
      .toList();
  }

  /**
   * Возвращает список внешних отчетов и обработок в указанном каталоге исходных файлов
   *
   * @param sourcePath каталог исходных файлов
   * @return Список прочитанных контейнеров внешних отчетов и обработок
   */
  public List<MDClass> createExternalSources(Path sourcePath) {
    return createExternalSources(sourcePath, MDCReadSettings.DEFAULT);
  }

  /**
   * Возвращает список внешних отчетов и обработок в указанном каталоге исходных файлов
   *
   * @param sourcePath   каталог исходных файлов
   * @param readSettings Настройки чтения
   * @return Список прочитанных контейнеров внешних отчетов и обработок
   */
  public List<MDClass> createExternalSources(Path sourcePath, MDCReadSettings readSettings) {
    return findFiles(sourcePath, SEARCH_EX_RES).parallelStream()
      .map(mdoPath -> MDOReader.readExternalSource(mdoPath, readSettings))
      .toList();
  }

  /**
   * Возвращает список контейнеров метаданных в указанном каталоге исходных файлов
   *
   * @param sourcePath каталог исходных файлов
   * @return Список прочитанных контейнеров
   */
  public List<MDClass> create(Path sourcePath) {
    return create(sourcePath, MDCReadSettings.DEFAULT);
  }

  /**
   * Возвращает список контейнеров метаданных в указанном каталоге исходных файлов
   *
   * @param sourcePath   каталог исходных файлов
   * @param readSettings Настройки чтения
   * @return Список прочитанных контейнеров
   */
  public List<MDClass> create(Path sourcePath, MDCReadSettings readSettings) {
    var result = new ArrayList<>(createConfigurations(sourcePath, readSettings));
    result.addAll(createExternalSources(sourcePath, readSettings));
    return result;
  }

  private List<Path> findFiles(Path sourcePath, Pattern pattern) {
    var excludeFolders = mdoTypeGroupNames();
    excludeFolders.add("Ext");
    List<Path> listPath = new ArrayList<>();

    try {
      Files.walkFileTree(sourcePath, new SimpleFileVisitor<>() {
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
          if (exc instanceof AccessDeniedException) {
            LOGGER.warn("Skipping directory (access denied): {}", file);
            return FileVisitResult.CONTINUE;
          }
          throw new UncheckedIOException(exc);
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
          if (!attrs.isRegularFile()) {
            return FileVisitResult.CONTINUE;
          }

          var parentName = path.getParent().getFileName().toString();
          var parentParentName = "";
          if (path.getParent().getParent() != null && path.getParent().getParent().getFileName() != null) {
            parentParentName = path.getParent().getParent().getFileName().toString();
          }

          if (excludeFolders.contains(parentName) || excludeFolders.contains(parentParentName)) {
            return FileVisitResult.CONTINUE;
          }
          var fileName = path.getFileName().toString();
          var ext = FilenameUtils.getExtension(fileName);
          if (!("xml".equals(ext) || "mdo".equals(ext))) {
            return FileVisitResult.CONTINUE;
          }

          if (pattern.matcher(fileName).matches()) {
            listPath.add(path);
          }
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException e) {
      LOGGER.error("Error reading files", e);
    }

    return listPath;
  }

  private Set<String> mdoTypeGroupNames() {
    return Arrays.stream(MDOType.values())
      .filter(type -> type != MDOType.EXTERNAL_REPORT
        && type != MDOType.EXTERNAL_DATA_PROCESSOR
        && type != MDOType.UNKNOWN
      )
      .map(MDOType::groupName)
      .collect(Collectors.toSet());
  }

  private static Solution buildSolution(Configuration base, List<ConfigurationExtension> extensions) {
    if (extensions.isEmpty()) {
      var provenance = new HashMap<MdoReference, ObjectProvenance>();
      base.getChildrenByMdoRef().forEach((ref, md) ->
        provenance.put(ref, ObjectProvenance.builder()
          .ownerRef(base.getMdoReference())
          .objectBelonging(md.getObjectBelonging())
          .build()));
      return Solution.builder()
        .mergedConfiguration(base)
        .baseConfiguration(base)
        .provenance(Collections.unmodifiableMap(provenance))
        .build();
    }

    var mergeResult = MDMerger.mergeAll(base, extensions);
    return Solution.builder()
      .mergedConfiguration(mergeResult.configuration())
      .baseConfiguration(base)
      .extensions(extensions)
      .provenance(mergeResult.provenance())
      .build();
  }
}
