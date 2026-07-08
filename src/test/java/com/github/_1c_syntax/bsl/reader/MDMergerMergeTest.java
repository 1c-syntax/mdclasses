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
package com.github._1c_syntax.bsl.reader;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationExtension;
import com.github._1c_syntax.bsl.mdclasses.MDCReadSettings;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MDMergerMergeTest {

  private static Path basePath(boolean isEDT, String pack) {
    return isEDT
      ? Path.of("src/test/resources/ext/edt/" + pack + "/configuration")
      : Path.of("src/test/resources/ext/designer/" + pack);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "false, mdclasses, mdclasses_ext",
      "true, mdclasses, mdclasses_ext"
    }
  )
  void testMergePreservesChildTypes(ArgumentsAccessor argumentsAccessor) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var basePack = argumentsAccessor.getString(1);
    var extPack = argumentsAccessor.getString(2);

    var settings = MDCReadSettings.DEFAULT;
    var baseCfgs = MDClasses.createConfigurations(basePath(isEDT, basePack), settings);
    var extCfgs = MDClasses.createConfigurations(basePath(isEDT, extPack), settings);

    var base = (Configuration) baseCfgs.stream()
      .filter(Configuration.class::isInstance)
      .findFirst().orElseThrow();
    var ext = (ConfigurationExtension) extCfgs.stream()
      .filter(ConfigurationExtension.class::isInstance)
      .findFirst().orElseThrow();

    var merged = MDMerger.merge(base, ext);

    // Расширение изменяет Справочник1 (уже есть в базе) и добавляет Справочник2
    // В результате должно быть 2 справочника: базовый + новый из расширения
    assertThat(merged.getCatalogs()).hasSize(2);

    // Проверяем, что дочерние объекты сохранились в объединённом Справочник1
    var catalog1 = merged.getCatalogs().stream()
      .filter(c -> "Справочник1".equals(c.getName()))
      .findFirst().orElseThrow();

    // Базовый Справочник1: 3 реквизита, 1 табличная часть, 3 формы, 1 шаблон, 1 команда
    // После слияния все типы дочерних объектов должны быть непусты
    assertThat(catalog1.getAttributes()).as("Реквизиты должны сохраниться").isNotEmpty();
    assertThat(catalog1.getTabularSections()).as("Табличные части должны сохраниться").isNotEmpty();
    assertThat(catalog1.getForms()).as("Формы должны сохраниться").isNotEmpty();
    assertThat(catalog1.getTemplates()).as("Шаблоны должны сохраниться").isNotEmpty();
    assertThat(catalog1.getCommands()).as("Команды должны сохраниться").isNotEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "false, mdclasses_ext",
      "true, mdclasses_ext"
    }
  )
  void testMergeWithEmptyBase(ArgumentsAccessor argumentsAccessor) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var extPack = argumentsAccessor.getString(1);

    var extCfgs = MDClasses.createConfigurations(basePath(isEDT, extPack), MDCReadSettings.DEFAULT);
    var ext = (ConfigurationExtension) extCfgs.stream()
      .filter(ConfigurationExtension.class::isInstance)
      .findFirst().orElseThrow();

    var merged = MDMerger.merge(Configuration.EMPTY, ext);

    // Расширение содержит 2 справочника: Справочник1 (adopted) и Справочник2 (новый)
    assertThat(merged.getCatalogs()).hasSize(2);
    assertThat(merged.getCatalogs().getFirst().getAttributes()).isNotEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "false, mdclasses, mdclasses_ext",
      "true, mdclasses, mdclasses_ext"
    }
  )
  void testMergeAllReturnsProvenance(ArgumentsAccessor argumentsAccessor) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var basePack = argumentsAccessor.getString(1);
    var extPack = argumentsAccessor.getString(2);

    var settings = MDCReadSettings.DEFAULT;
    var baseCfgs = MDClasses.createConfigurations(basePath(isEDT, basePack), settings);
    var extCfgs = MDClasses.createConfigurations(basePath(isEDT, extPack), settings);

    var base = (Configuration) baseCfgs.stream()
      .filter(Configuration.class::isInstance)
      .findFirst().orElseThrow();
    var ext = (ConfigurationExtension) extCfgs.stream()
      .filter(ConfigurationExtension.class::isInstance)
      .findFirst().orElseThrow();

    var result = MDMerger.mergeAll(base, List.of(ext));

    assertThat(result.configuration()).isNotNull();
    assertThat(result.provenance()).isNotEmpty();

    // Проверяем происхождение справочника, который есть и в базе, и в расширении
    var catalogRef = result.configuration().getCatalogs().stream()
      .filter(c -> "Справочник1".equals(c.getName()))
      .findFirst().orElseThrow()
      .getMdoReference();
    var prov = result.provenance().get(catalogRef);
    assertThat(prov).isNotNull();
    assertThat(prov.getOwnerRef())
      .as("Объект из базы должен иметь ownerRef = базовая конфигурация")
      .isEqualTo(base.getMdoReference());
    assertThat(prov.getObjectBelonging()).isNotNull();
    // Расширение модифицирует Справочник1 — проверяем, что ref расширения попал в modifiedByExtensionRefs
    assertThat(prov.getModifiedByExtensionRefs())
      .as("Расширение, модифицирующее объект, должно быть в modifiedByExtensionRefs")
      .contains(ext.getMdoReference());

    // Проверяем происхождение справочника, которого нет в базе (только в расширении)
    var catalog2Ref = result.configuration().getCatalogs().stream()
      .filter(c -> "Справочник2".equals(c.getName()))
      .findFirst().orElseThrow()
      .getMdoReference();
    var prov2 = result.provenance().get(catalog2Ref);
    assertThat(prov2).isNotNull();
    assertThat(prov2.getOwnerRef())
      .as("Новый объект из расширения должен иметь ownerRef = расширение")
      .isEqualTo(ext.getMdoReference());
    assertThat(prov2.getModifiedByExtensionRefs())
      .as("Новый объект не модифицировался расширениями")
      .isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "false, mdclasses, mdclasses_ext",
      "true, mdclasses, mdclasses_ext"
    }
  )
  void testMergeAllPreservesObjects(ArgumentsAccessor argumentsAccessor) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var basePack = argumentsAccessor.getString(1);
    var extPack = argumentsAccessor.getString(2);

    var settings = MDCReadSettings.DEFAULT;
    var baseCfgs = MDClasses.createConfigurations(basePath(isEDT, basePack), settings);
    var extCfgs = MDClasses.createConfigurations(basePath(isEDT, extPack), settings);

    var base = (Configuration) baseCfgs.stream()
      .filter(Configuration.class::isInstance)
      .findFirst().orElseThrow();
    var ext = (ConfigurationExtension) extCfgs.stream()
      .filter(ConfigurationExtension.class::isInstance)
      .findFirst().orElseThrow();

    var result = MDMerger.mergeAll(base, List.of(ext));

    // В базе 1 справочник, расширение добавляет ещё 1 — всего 2
    assertThat(result.configuration().getCatalogs()).hasSize(2);
    // Проверяем, что дочерние объекты не потерялись при слиянии
    var catalog1 = result.configuration().getCatalogs().stream()
      .filter(c -> "Справочник1".equals(c.getName()))
      .findFirst().orElseThrow();
    assertThat(catalog1.getAttributes()).isNotEmpty();
    assertThat(catalog1.getForms()).isNotEmpty();
  }

  @Test
  void mergeFormatsProduceIdenticalResult() {
    var desPath = Path.of("src/test/resources/ext/designer/mdclasses");
    var desExtPath = Path.of("src/test/resources/ext/designer/mdclasses_ext");
    var edtPath = Path.of("src/test/resources/ext/edt/mdclasses/configuration");
    var edtExtPath = Path.of("src/test/resources/ext/edt/mdclasses_ext/configuration");

    var settings = MDCReadSettings.DEFAULT;

    var desBase = (Configuration) MDClasses.createConfigurations(desPath, settings).stream()
      .filter(Configuration.class::isInstance).findFirst().orElseThrow();
    var desExt = (ConfigurationExtension) MDClasses.createConfigurations(desExtPath, settings).stream()
      .filter(ConfigurationExtension.class::isInstance).findFirst().orElseThrow();
    var desMerged = MDMerger.merge(desBase, desExt);

    var edtBase = (Configuration) MDClasses.createConfigurations(edtPath, settings).stream()
      .filter(Configuration.class::isInstance).findFirst().orElseThrow();
    var edtExt = (ConfigurationExtension) MDClasses.createConfigurations(edtExtPath, settings).stream()
      .filter(ConfigurationExtension.class::isInstance).findFirst().orElseThrow();
    var edtMerged = MDMerger.merge(edtBase, edtExt);

    Assertions.assertThat(Fixtures.asJson(desMerged), true)
      .isEqual(Fixtures.asJson(edtMerged));
  }
}
