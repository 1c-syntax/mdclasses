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

import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class StandaloneConfigurationContentTest {

  private static final MdoReference CATALOG = MdoReference.create("Catalog.Справочник1");
  private static final MdoReference FORM = MdoReference.create("Catalog.Справочник1.Form.ФормаЭлемента");
  private static final MdoReference MODULE = MdoReference.create("CommonModule.ОбщийМодуль1");
  private static final MdoReference ROLE = MdoReference.create("Role.Роль1");
  private static final MdoReference DOCUMENT = MdoReference.create("Document.Документ1");
  private static final MdoReference WITHOUT_SYNONYM = MdoReference.create("Catalog.СправочникБезСинонима");
  private static final MdoReference ATTRIBUTE = MdoReference.create("Document.Документ1.Attribute.Реквизит1");
  private static final MdoReference DOCUMENT_FORM = MdoReference.create("Document.Документ1.Form.ФормаДокумента");
  private static final MdoReference PRIORITY_ONLY = MdoReference.create("Document.РеализацияТоваровУслуг");

  private static final StandaloneConfigurationContent EXPECTED = StandaloneConfigurationContent.builder()
    .addUsedItem(CATALOG)
    .addUsedItem(MODULE)
    .addUsedItem(DOCUMENT)
    .addUsedItem(ROLE)
    .addUnusedItem(FORM)
    .addUnusedItem(WITHOUT_SYNONYM)
    .addUnusedItem(ATTRIBUTE)
    .addUnusedItem(DOCUMENT_FORM)
    .addPriorityItem(MODULE, StandaloneServerPriority.LOCAL_SERVER)
    .addPriorityItem(PRIORITY_ONLY, StandaloneServerPriority.LOCAL_SERVER)
    .exchangeOnChangeData(true)
    .exchangePeriod(300)
    .transactionCount(1000)
    .inactiveNodesCleanupTimeout(100)
    .build();

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, Configuration",
      "false, mdclasses, Configuration",
      "true, mdclasses_3_18, Configuration",
      "false, mdclasses_3_18, Configuration",
      "true, mdclasses_3_25, Configuration",
      "false, mdclasses_3_25, Configuration",
      "true, mdclasses_3_27, Configuration",
      "false, mdclasses_3_27, Configuration",
      "true, mdclasses_5_1, Configuration",
      "false, mdclasses_5_1, Configuration"
    }
  )
  void contentIsReadWithConfiguration(ArgumentsAccessor argumentsAccessor) {
    var packName = argumentsAccessor.getString(1);
    var mdc = Fixtures.get(argumentsAccessor);
    assertThat(mdc).isInstanceOf(Configuration.class);
    var cf = (Configuration) mdc;
    assertThat(cf).isNotNull();
    var content = cf.getStandaloneContent();

    if ("mdclasses_5_1".equals(packName)) {
      assertThat(content).isEqualTo(EXPECTED);
      assertThat(content.isEmpty()).isFalse();
      assertThat(content.isIncluded(CATALOG)).isTrue();
      assertThat(content.isIncluded(FORM)).isFalse();
      assertThat(content.getPriority(MODULE)).contains(StandaloneServerPriority.LOCAL_SERVER);
      assertThat(content.isIncluded(PRIORITY_ONLY)).isFalse();
      assertThat(content.getPriority(PRIORITY_ONLY)).contains(StandaloneServerPriority.LOCAL_SERVER);
    } else {
      assertThat(content).isEqualTo(StandaloneConfigurationContent.EMPTY);
      assertThat(content.isEmpty()).isTrue();
      assertThat(content.isIncluded(CATALOG)).isFalse();
    }
  }

  @Test
  void absentFileDoesNotIncludeAnything() {
    var content = StandaloneConfigurationContent.EMPTY;

    assertThat(content.isEmpty()).isTrue();
    assertThat(content.isIncluded(CATALOG)).isFalse();
    assertThat(content.isExplicitlyUnused(CATALOG)).isFalse();
    assertThat(content.getPriority(CATALOG)).isEmpty();
  }

  @Test
  void includedParentDoesNotIncludeExplicitlyUnusedChild() {
    var content = StandaloneConfigurationContent.builder()
      .addUsedItem(CATALOG)
      .addUnusedItem(FORM)
      .addPriorityItem(MODULE, StandaloneServerPriority.LOCAL_SERVER)
      .build();

    assertThat(content.isEmpty()).isFalse();
    assertThat(content.isIncluded(CATALOG)).isTrue();
    assertThat(content.isIncluded(FORM)).isFalse();
    assertThat(content.isExplicitlyUnused(FORM)).isTrue();
    assertThat(content.isIncluded(MODULE)).isFalse();
    assertThat(content.getPriority(MODULE)).contains(StandaloneServerPriority.LOCAL_SERVER);
  }

  @Test
  void priorityOfParentIsAvailableForChild() {
    var content = StandaloneConfigurationContent.builder()
      .addUsedItem(CATALOG)
      .addPriorityItem(CATALOG, StandaloneServerPriority.LOCAL_SERVER)
      .build();

    assertThat(content.getPriority(FORM)).contains(StandaloneServerPriority.LOCAL_SERVER);
  }

  @Test
  void onlyMatchingPrefixIsIncluded() {
    var content = StandaloneConfigurationContent.builder()
      .addUsedItem(CATALOG)
      .build();

    assertThat(content.isIncluded(FORM)).isTrue();
    assertThat(content.isIncluded(MdoReference.create("Catalog.Справочник2"))).isFalse();
  }
}
