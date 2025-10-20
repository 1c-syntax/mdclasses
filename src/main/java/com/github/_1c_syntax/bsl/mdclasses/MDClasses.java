/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
   * @param path        Путь к корню проекта
   * @param skipSupport Флаг управления чтением информации о поддержке
   * @return Конфигурация или расширение
   * @deprecated Стоит использовать метод с параметром MDCReadSettings.
   * Создает конфигурацию или расширение по указанному пути
   */
  @Deprecated(since = "0.16.0")
  public MDClass createConfiguration(Path path, boolean skipSupport) {
    return createConfiguration(path, MDCReadSettings.builder().skipSupport(skipSupport).build());
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
   * Возвращает список конфигураций\расширений в указанном каталоге исходных файлов
   *
   * @param sourcePath  каталог исходных файлов
   * @param skipSupport Флаг управления чтением информации о поддержке
   * @return Список прочитанных контейнеров конфигураций и расширений
   * @deprecated Стоит использовать метод с параметром MDCReadSettings.
   */
  @Deprecated(since = "0.16.0")
  public List<MDClass> createConfigurations(Path sourcePath, boolean skipSupport) {
    return createConfigurations(sourcePath, MDCReadSettings.builder().skipSupport(skipSupport).build());
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
  public MDClass createSolution(Path sourcePath) {
    var mdcs = createConfigurations(sourcePath, MDCReadSettings.DEFAULT);

    if (mdcs.isEmpty()) {
      return Configuration.EMPTY;
    } else if (mdcs.size() == 1) {
      return mdcs.get(0);
    } else {
      var mdc = mdcs.stream().filter(Configuration.class::isInstance).map(Configuration.class::cast).findFirst();
      var cf = mdc.orElse(Configuration.EMPTY);
      var extensions = mdcs.stream()
        .filter(ConfigurationExtension.class::isInstance)
        .map(ConfigurationExtension.class::cast)
        .toList();

      if (cf.isEmpty()) {
        if (extensions.isEmpty()) {
          // вернем первое значение, т.к. там нет ни конфы, ни расширений
          return mdcs.get(0);
        } else if (extensions.size() == 1) {
          // есть одно расширение, вернем его
          return extensions.get(0);
        }
      } else if (extensions.isEmpty()) {
        // расширений нет, вернем конфигурацию
        return cf;
      }

      // объединим расширения с конфигурацией в одно целое
      var result = cf;
      for (var extension : extensions) {
        result = MDMerger.merge(result, extension);
      }
      return result;
    }
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
   * @param sourcePath  каталог исходных файлов
   * @param skipSupport Флаг управления чтением информации о поддержке
   * @return Список прочитанных контейнеров
   * @deprecated Стоит использовать метод с параметром MDCReadSettings.
   */
  @Deprecated(since = "0.16.0")
  public List<MDClass> create(Path sourcePath, boolean skipSupport) {
    return create(sourcePath, MDCReadSettings.builder().skipSupport(skipSupport).build());
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
    List<Path> listPath = new ArrayList<>();
    var excludeFolders = mdoTypeGroupNames();
    excludeFolders.add("Ext");
    try (Stream<Path> stream = Files.find(sourcePath, Integer.MAX_VALUE,
      (Path path, BasicFileAttributes basicFileAttributes) -> {
        if (!basicFileAttributes.isRegularFile()) {
          return false;
        }

        var parentName = path.getParent().getFileName().toString();
        var parentParentName = "";
        if (path.getParent().getParent() != null) {
          parentParentName = path.getParent().getParent().getFileName().toString();
        }

        if (excludeFolders.contains(parentName) || excludeFolders.contains(parentParentName)) {
          return false;
        }
        var fileName = path.getFileName().toString();
        var ext = FilenameUtils.getExtension(fileName);
        if (!("xml".equals(ext) || "mdo".equals(ext))) {
          return false;
        }

        return pattern.matcher(fileName).matches();
      }
    )) {
      listPath = stream.toList();
    } catch (IOException e) {
      LOGGER.error("Error read files", e);
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
}
