/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses.metadata.additional;

import com.github._1c_syntax.mdclasses.metadata.SupportConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

@Slf4j
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

  private final Path pathToBinFile;
  private Map<String, Map<SupportConfiguration, SupportVariant>> supportMap = new HashMap<>();

  public ParseSupportData(Path pathToBinFile) {
    this.pathToBinFile = pathToBinFile;
    LOGGER.debug("Чтения файла поставки ParentConfigurations.bin");
    try {
      read();
    } catch (NumberFormatException exception) {
      LOGGER.error("Некорректный файл ParentConfigurations.bin", exception);
      supportMap.clear();
    } catch (FileNotFoundException exception) {
      LOGGER.error("При чтении файла ParentConfigurations.bin произошла ошибка", exception);
    }
  }

  private void read() throws FileNotFoundException, NumberFormatException {

    String[] dataStrings;
    var fileInputStream = new FileInputStream(pathToBinFile.toFile());
    try (var scanner = new Scanner(fileInputStream, StandardCharsets.UTF_8)) {
      dataStrings = scanner.findAll(patternSplit)
        .map(matchResult -> matchResult.group(1))
        .toArray(String[]::new);
    }

    var countConfiguration = Integer.parseInt(dataStrings[POINT_COUNT_CONFIGURATION]);
    LOGGER.debug("Найдено конфигураций: {}", countConfiguration);

    var startPoint = 3;
    for (var numberConfiguration = 1; numberConfiguration <= countConfiguration; numberConfiguration++) {
      var configurationVersion = dataStrings[startPoint + SHIFT_CONFIGURATION_VERSION];
      var configurationProducer = dataStrings[startPoint + SHIFT_CONFIGURATION_PRODUCER];
      var configurationName = dataStrings[startPoint + SHIFT_CONFIGURATION_NAME];
      var countObjectsConfiguration = Integer.parseInt(dataStrings[startPoint + SHIFT_CONFIGURATION_COUNT_OBJECT]);

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
        var guidObject = dataStrings[currentObjectPoint + 2];
        var supportVariant = getSupportVariantByInt(support);

        Map<SupportConfiguration, SupportVariant> map = supportMap.computeIfAbsent(guidObject, k -> new HashMap<>());
        map.put(supportConfiguration, supportVariant);
      }

      startPoint = startObjectPoint + 2 + countObjectsConfiguration * COUNT_ELEMENT_OBJECT;
    }
  }

  public Map<String, Map<SupportConfiguration, SupportVariant>> getSupportMap() {
    return this.supportMap;
  }

  private static SupportVariant getSupportVariantByInt(int support) {
    switch (support) {
      case 0:
        return SupportVariant.NOT_EDITABLE;
      case 1:
        return SupportVariant.EDITABLE_SUPPORT_ENABLED;
      case 2:
        return SupportVariant.NOT_SUPPORTED;
      default:
        return SupportVariant.NONE;
    }
  }
}
