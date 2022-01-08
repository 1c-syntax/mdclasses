/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.supconf;

import com.github._1c_syntax.bsl.support.SupportVariant;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Используется для чтения информации о поддержке из файла ParentConfigurations.bin конфигурации
 */
@Slf4j
@UtilityClass
public class ParseSupportData {

  // взято из https://stackoverflow.com/questions/18144431/regex-to-split-a-csv
  private static final String REGEX = "(?:,|\\n|^)(\"(?:(?:\"\")*[^\"]*)*\"|[^\",\\n]*|(?:\\n|$))";
  private static final Pattern patternSplit = Pattern.compile(REGEX);

  private static final int POINT_COUNT_CONFIGURATION = 2;
  private static final int SHIFT_CONFIGURATION_VERSION = 3;
  private static final int SHIFT_CONFIGURATION_PRODUCER = 4;
  private static final int SHIFT_CONFIGURATION_NAME = 5;
  private static final int SHIFT_CONFIGURATION_COUNT_OBJECT = 6;
  private static final int SHIFT_OBJECT_COUNT = 7;
  private static final int COUNT_ELEMENT_OBJECT = 4;
  private static final int CONFIGURATION_SUPPORT = 1;
  private static final int START_READ_POSITION = 3;
  private static final int SHIFT_SIZE = 2;

  private static final Map<Path, Map<String, Map<SupportConfiguration, SupportVariant>>> SUPPORT_MAPS
    = new ConcurrentHashMap<>();

  private static final Map<Path, Map<String, SupportVariant>> SUPPORT_SIMPLE_MAPS
    = new ConcurrentHashMap<>();

  /**
   * Выполняет чтение информации о поддержке и возвращает упрощенный вариант: UUID->MAX(SupportVariant)
   *
   * @param path Путь к файлу описания поддержки
   * @return Прочитанная информация
   */
  public static Map<String, SupportVariant> readSimple(Path path) {
    var rootPath = getRootPathByParentConfigurations(path);
    var supportMap = SUPPORT_SIMPLE_MAPS.get(rootPath);
    if (supportMap == null) {
      readFile(path, rootPath);
      supportMap = SUPPORT_SIMPLE_MAPS.get(rootPath);
    }

    if (supportMap == null) {
      return Collections.emptyMap();
    }

    return supportMap;
  }

  /**
   * Выполняет чтение информации о поддержке и возвращает весь набор прочитанных данных
   *
   * @param path Путь к файлу описания поддержки
   * @return Прочитанная информация
   */
  public static Map<String, Map<SupportConfiguration, SupportVariant>> readFull(Path path) {
    var rootPath = getRootPathByParentConfigurations(path);
    var supportMap = SUPPORT_MAPS.get(rootPath);
    if (supportMap == null) {
      readFile(path, rootPath);
      supportMap = SUPPORT_MAPS.get(rootPath);
    }

    if (supportMap == null) {
      return Collections.emptyMap();
    }

    return supportMap;
  }

  /**
   * Возвращает максимальный вариант поддержки для объекта с явным указанием пути.
   * Используется обычно для объектов, не имеющих собственного файла
   *
   * @param uuid Строка-идентификатор объекта, для которого определяется вариант поддержки
   * @param path Путь к файлу MDO объекта / родительского объекта
   * @return Вариант поддержки
   */
  public static SupportVariant getSupportVariantByMDO(String uuid, Path path) {
    var key = SUPPORT_SIMPLE_MAPS.keySet().stream().filter(path::startsWith).findFirst();
    if (key.isPresent()) {
      return SUPPORT_SIMPLE_MAPS.get(key.get()).getOrDefault(uuid, SupportVariant.NONE);
    }

    return SupportVariant.NONE;
  }

  private void readFile(Path pathToBinFile, Path rootPath) {
    LOGGER.debug("Чтения файла поставки ParentConfigurations.bin");
    if (!pathToBinFile.toFile().exists()) {
      LOGGER.error(String.format("Файл ParentConfigurations.bin не обнаружен по пути '%s'", pathToBinFile.toFile()));
      return;
    }

    try {
      var supportMap = read(pathToBinFile);
      SUPPORT_MAPS.put(rootPath, supportMap);

      Map<String, SupportVariant> result = new HashMap<>();
      supportMap.forEach((String uuid, Map<SupportConfiguration, SupportVariant> supportVariantMap)
        -> result.put(uuid, SupportVariant.max(supportVariantMap.values())));
      SUPPORT_SIMPLE_MAPS.put(rootPath, result);
    } catch (FileNotFoundException exception) {
      LOGGER.error("При чтении файла ParentConfigurations.bin произошла ошибка", exception);
    } catch (NumberFormatException exception) {
      LOGGER.error("Некорректный файл ParentConfigurations.bin", exception);
    }
  }

  private Map<String, Map<SupportConfiguration, SupportVariant>> read(Path pathToBinFile) throws FileNotFoundException {
    Map<String, Map<SupportConfiguration, SupportVariant>> supportMap = new HashMap<>();
    String[] dataStrings;
    var fileInputStream = new FileInputStream(pathToBinFile.toFile());
    try (var scanner = new Scanner(fileInputStream, StandardCharsets.UTF_8)) {
      dataStrings = scanner.findAll(patternSplit)
        .map(matchResult -> matchResult.group(1))
        .toArray(String[]::new);
    }

    var countConfiguration = Integer.parseInt(dataStrings[POINT_COUNT_CONFIGURATION]);
    LOGGER.debug("Найдено конфигураций: {}", countConfiguration);

    var startPoint = START_READ_POSITION;
    for (var numberConfiguration = 1; numberConfiguration <= countConfiguration; numberConfiguration++) {
      var configurationVersion = dataStrings[startPoint + SHIFT_CONFIGURATION_VERSION];
      var configurationProducer = dataStrings[startPoint + SHIFT_CONFIGURATION_PRODUCER];
      var configurationName = dataStrings[startPoint + SHIFT_CONFIGURATION_NAME];
      var countObjectsConfiguration = Integer.parseInt(dataStrings[startPoint + SHIFT_CONFIGURATION_COUNT_OBJECT]);
      var configurationSupport = Integer.parseInt(dataStrings[CONFIGURATION_SUPPORT]);
      var configurationSupportVariant = GeneralSupportVariant.valueOf(configurationSupport);

      var supportConfiguration
        = new SupportConfiguration(configurationName, configurationProducer, configurationVersion);

      LOGGER.debug(String.format(
        "Конфигурация: %s Версия: %s Поставщик: %s Количество объектов: %s",
        configurationName,
        configurationVersion,
        configurationProducer,
        countObjectsConfiguration));

      var startObjectPoint = startPoint + SHIFT_OBJECT_COUNT;
      for (var numberObject = 0; numberObject < countObjectsConfiguration; numberObject++) {
        var currentObjectPoint = startObjectPoint + numberObject * COUNT_ELEMENT_OBJECT;
        // 0 - не редактируется, 1 - с сохранением поддержки, 2 - снято
        var support = Integer.parseInt(dataStrings[currentObjectPoint]);
        var guidObject = dataStrings[currentObjectPoint + SHIFT_SIZE];
        SupportVariant supportVariant;
        if (configurationSupportVariant == GeneralSupportVariant.LOCKED) {
          supportVariant = SupportVariant.NOT_EDITABLE;
        } else {
          supportVariant = SupportVariant.valueOf(support);
        }

        Map<SupportConfiguration, SupportVariant> map = supportMap.computeIfAbsent(guidObject, k -> new HashMap<>());
        map.put(supportConfiguration, supportVariant);
      }

      startPoint = startObjectPoint + SHIFT_SIZE + countObjectsConfiguration * COUNT_ELEMENT_OBJECT;
    }

    return supportMap;
  }

  /**
   * Получает каталог проекта по файлу поддержки конфигурации
   */
  private Path getRootPathByParentConfigurations(Path mdoPath) {
    return Paths.get(
      FilenameUtils.getFullPathNoEndSeparator(
        FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString())));
  }
}
