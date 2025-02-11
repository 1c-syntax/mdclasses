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
    return createConfiguration(path, false);
  }

  /**
   * Создает конфигурацию или расширение по указанному пути
   *
   * @param path        Путь к корню проекта
   * @param skipSupport Флаг управления чтением информации о поддержке
   * @return Конфигурация или расширение
   */
  public MDClass createConfiguration(Path path, boolean skipSupport) {
    return MDOReader.readConfiguration(path, skipSupport);
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
    return createConfigurations(sourcePath, false);
  }

  /**
   * Возвращает список конфигураций\расширений в указанном каталоге исходных файлов
   *
   * @param sourcePath  каталог исходных файлов
   * @param skipSupport Флаг управления чтением информации о поддержке
   * @return Список прочитанных контейнеров конфигураций и расширений
   */
  public List<MDClass> createConfigurations(Path sourcePath, boolean skipSupport) {
    return findFiles(sourcePath, SEARCH_CONFIGURATION).parallelStream()
      .map(path -> createConfiguration(path, skipSupport))
      .toList();
  }

  /**
   * Возвращает список внешних отчетов и обработок в указанном каталоге исходных файлов
   *
   * @param sourcePath каталог исходных файлов
   * @return Список прочитанных контейнеров внешних отчетов и обработок
   */
  public List<MDClass> createExternalSources(Path sourcePath) {
    return findFiles(sourcePath, SEARCH_EX_RES).parallelStream()
      .map(MDOReader::readExternalSource)
      .toList();
  }

  /**
   * Возвращает список контейнеров метаданных в указанном каталоге исходных файлов
   *
   * @param sourcePath каталог исходных файлов
   * @return Список прочитанных контейнеров
   */
  public List<MDClass> create(Path sourcePath) {
    return create(sourcePath, false);
  }

  /**
   * Возвращает список контейнеров метаданных в указанном каталоге исходных файлов
   *
   * @param sourcePath  каталог исходных файлов
   * @param skipSupport Флаг управления чтением информации о поддержке
   * @return Список прочитанных контейнеров
   */
  public List<MDClass> create(Path sourcePath, boolean skipSupport) {
    var result = new ArrayList<>(createConfigurations(sourcePath, skipSupport));
    result.addAll(createExternalSources(sourcePath));
    return result;
  }

  /**
   * Возвращает проект - совокупность конфигурации и расширений в каталоге
   *
   * @param sourcePath  - Каталог проекта
   * @param skipSupport - Признак необходимость пропустить чтение поставки
   * @return - Проект
   */
  public Project createProject(Path sourcePath, boolean skipSupport) {
    var mdclasses = create(sourcePath, skipSupport);

    var cf = mdclasses.stream().filter(Configuration.class::isInstance).findFirst();
    Configuration cfData = Configuration.EMPTY;
    if (cf.isPresent()) {
      cfData = (Configuration) cf.get();
    }
    var projectBuilder = Project.create(cfData);
    mdclasses.stream().filter(ConfigurationExtension.class::isInstance)
      .map(ConfigurationExtension.class::cast)
      .forEach(ext -> Project.addExtension(projectBuilder, ext));
    return projectBuilder.build();
  }

  public Project createProject(Path sourcePath) {
    return createProject(sourcePath, false);
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
      .map(MDOType::getGroupName)
      .collect(Collectors.toSet());
  }
}
