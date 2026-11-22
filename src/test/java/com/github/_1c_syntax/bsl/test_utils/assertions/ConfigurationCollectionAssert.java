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
package com.github._1c_syntax.bsl.test_utils.assertions;

import com.github._1c_syntax.bsl.mdclasses.CF;
import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import lombok.SneakyThrows;
import org.assertj.core.api.AbstractAssert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Fluent asserty для проверки дочерних коллекций конфигурации.
 * <p>
 * Проверяет состав:
 * - {@code children} == сумма всех child-коллекций
 * - {@code plainChildren} == сумма всех child + раскрытых таб. секций
 */
public class ConfigurationCollectionAssert
  extends AbstractAssert<ConfigurationCollectionAssert, CF> {

  // Все 49 child-коллекций CF в порядке появления в children.
  private static final String[] CHILDREN_NAMES = {
    "subsystems",
    "commonModules",
    "sessionParameters",
    "roles",
    "commonAttributes",
    "exchangePlans",
    "filterCriteria",
    "eventSubscriptions",
    "scheduledJobs",
    "bots",
    "functionalOptions",
    "functionalOptionsParameters",
    "definedTypes",
    "settingsStorages",
    "commonForms",
    "commonCommands",
    "commandGroups",
    "commonTemplates",
    "commonPictures",
    "interfaces",
    "xDTOPackages",
    "webServices",
    "webSocketClients",
    "httpServices",
    "wsReferences",
    "integrationServices",
    "styleItems",
    "paletteColors",
    "styles",
    "languages",
    "constants",
    "catalogs",
    "documents",
    "documentNumerators",
    "sequences",
    "documentJournals",
    "enums",
    "reports",
    "dataProcessors",
    "chartsOfCharacteristicTypes",
    "chartsOfAccounts",
    "chartsOfCalculationTypes",
    "informationRegisters",
    "accumulationRegisters",
    "accountingRegisters",
    "calculationRegisters",
    "businessProcesses",
    "tasks",
    "externalDataSources"
  };

  private ConfigurationCollectionAssert(CF actual) {
    super(actual, ConfigurationCollectionAssert.class);
  }

  public static ConfigurationCollectionAssert assertThat(CF actual) {
    return new ConfigurationCollectionAssert(actual);
  }

  public static ConfigurationCollectionAssert assertThat(CF actual, boolean ignored) {
    return new ConfigurationCollectionAssert(actual);
  }

  /**
   * Проверяет размеры всех child-коллекций по ожиданиям в {@code fixtures/{packName}/Configuration.md}.
   * Читает md-файл и сверяет размеры каждой коллекции.
   */
  @SuppressWarnings("unchecked")
  @SneakyThrows
  public ConfigurationCollectionAssert hasSize(String packName) {
    var expected = readMdExpectations(packName);
    for (var entry : expected.entrySet()) {
      var fieldName = entry.getKey();
      var expectedSize = entry.getValue();
      var field = actual.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      var value = field.get(actual);
      var realCollection = value instanceof Collection<?> collection ? collection : Collections.emptyList();
      org.assertj.core.api.Assertions.assertThat(realCollection).as(fieldName).hasSize(expectedSize);
    }
    return this;
  }

  /**
   * Проверяет что children == сумма всех child-коллекций.
   * Использует {@link MDCollectionAssert#containsAll(Collection[])} — shallow merge.
   */
  public ConfigurationCollectionAssert containsAllChildren() {
    var collections = collectChildren(actual);
    MDCollectionAssert.assertThat(actual.getChildren(), false).containsAll(collections);
    return this;
  }

  /**
   * Проверяет что plainChildren == sum(child-коллекций с раскрытыми таб. секциями).
   */
  public ConfigurationCollectionAssert containsAllPlainChildren() {
    var collections = collectChildren(actual);
    collections[CHILDREN_NAMES.length] = Arrays.stream(collections)
      .filter(Objects::nonNull)
      .flatMap(Collection::stream)
      .filter(ChildrenOwner.class::isInstance)
      .map(ChildrenOwner.class::cast)
      .map(ChildrenOwner::getPlainChildren)
      .flatMap(Collection::stream)
      .toList();

    MDCollectionAssert.assertThat(actual.getPlainChildren(), false).containsAllPlain(collections);
    return this;
  }

  // =================== Helpers ===================

  private static List<?>[] collectChildren(CF cf) {
    var collections = new List<?>[CHILDREN_NAMES.length + 1];
    for (int i = 0; i < CHILDREN_NAMES.length; i++) {
      collections[i] = getCollection(cf, CHILDREN_NAMES[i]);
    }
    return collections;
  }

  @SneakyThrows
  private static List<?> getCollection(CF cf, String fieldName) {
    var field = cf.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    var value = field.get(cf);
    if (value instanceof List<?> collection) {
      return collection;
    }
    throw new IllegalStateException(fieldName + " is not a Collection: " + value);
  }

  @SneakyThrows
  private Map<String, Integer> readMdExpectations(String packName) {
    var path = Path.of("src/test/resources/fixtures", packName, "Configuration.md");
    var lines = Files.readAllLines(path);
    var result = new LinkedHashMap<String, Integer>();

    for (var line : lines) {
      var trimmed = line.trim();
      if (trimmed.contains("|") && !trimmed.contains("type") && !trimmed.contains("getter") &&
        !trimmed.startsWith("|---") && !trimmed.startsWith("|------") && trimmed.startsWith("|")) {
        var parts = trimmed.split("\\|");
        if (parts.length >= 4) {
          var getterName = parts[2].trim();
          var sizeStr = parts[3].trim();
          if (!sizeStr.isEmpty() && !sizeStr.equals("—") && !getterName.isEmpty()) {
            result.put(getterName, Integer.parseInt(sizeStr));
          }
        }
      }
    }
    return result;
  }
}
