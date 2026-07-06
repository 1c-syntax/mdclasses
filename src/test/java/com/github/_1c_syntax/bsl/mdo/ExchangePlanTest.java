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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.support.AutoRecordType;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ExchangePlanTest {
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, ExchangePlans.ПланОбмена1",
    "false, mdclasses, ExchangePlans.ПланОбмена1",
    "true, ssl_3_1, ExchangePlans.ОбновлениеИнформационнойБазы",
    "false, ssl_3_1, ExchangePlans.ОбновлениеИнформационнойБазы",
    "true, ssl_3_2, ExchangePlans.ОбновлениеИнформационнойБазы",
    "false, ssl_3_2, ExchangePlans.ОбновлениеИнформационнойБазы"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ExchangePlan.class);

    var exchangePlan = (ExchangePlan) mdo;
    assertThat(exchangePlan).isNotNull();

    var attributes = exchangePlan.getAttributes();
    var tabularSections = exchangePlan.getTabularSections();
    var forms = exchangePlan.getForms();
    var templates = exchangePlan.getTemplates();
    var commands = exchangePlan.getCommands();
    var predefinedValues = exchangePlan.getPredefinedValues();

    // --- ModuleOwner ---
    Assertions.assertThat(exchangePlan.getAllModules(), true)
      .containsAll(exchangePlan.getModules(), forms, commands);

    // --- PredefinedDataOwner ---
    assertThat(predefinedValues).hasSize(0);

    // --- ReferenceObject logic ---
    if (exchangePlan.getName().equals("ПланОбмена1")) {
      var mdo1 = MdoReference.create("Catalog.Справочник1");
      var mdo2 = MdoReference.create("Document.Документ1");
      var mdo3 = MdoReference.create("Catalog.Документ1");

      assertThat(exchangePlan.contains(mdo1)).isTrue();
      assertThat(exchangePlan.contains(mdo2)).isTrue();
      assertThat(exchangePlan.contains(mdo3)).isFalse();

      assertThat(exchangePlan.autoRecord(mdo1)).isEqualTo(AutoRecordType.ALLOW);
      assertThat(exchangePlan.autoRecord(mdo2)).isEqualTo(AutoRecordType.DENY);
      assertThat(exchangePlan.autoRecord(mdo3)).isEqualTo(AutoRecordType.DENY);
    } else {
      var mdo1 = MdoReference.create("InformationRegister.СостоянияРассылокОтчетов");
      var mdo2 = MdoReference.create("Catalog.Справочник1");

      assertThat(exchangePlan.contains(mdo1)).isTrue();
      assertThat(exchangePlan.contains(mdo2)).isFalse();

      assertThat(exchangePlan.autoRecord(mdo1)).isEqualTo(AutoRecordType.DENY);
      assertThat(exchangePlan.autoRecord(mdo2)).isEqualTo(AutoRecordType.DENY);
    }

    // --- AttributeOwner ---
    Assertions.assertThat(exchangePlan.getAllAttributes(), false)
      .containsAll(attributes);
    Assertions.assertThat(exchangePlan.getStorageFields(), false)
      .containsAll(attributes);
    Assertions.assertThat(exchangePlan.getPlainStorageFields(), true)
      .containsAllPlain(attributes, tabularSections);

    // --- Children ---
    Assertions.assertThat(exchangePlan.getChildren(), true)
      .containsAll(attributes, tabularSections, forms, templates, commands);
    Assertions.assertThat(exchangePlan.getPlainChildren(), true)
      .containsAllPlain(attributes, tabularSections, forms, templates, commands);
  }
}
