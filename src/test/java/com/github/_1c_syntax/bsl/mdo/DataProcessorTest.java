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

import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DataProcessorTest {
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, DataProcessors.Обработка1",
    "false, mdclasses, DataProcessors.Обработка1",
    "true, ssl_3_1, DataProcessors.ЗагрузкаКурсовВалют",
    "false, ssl_3_1, DataProcessors.ЗагрузкаКурсовВалют",
    "true, ssl_3_2, DataProcessors.ЗагрузкаКурсовВалют",
    "false, ssl_3_2, DataProcessors.ЗагрузкаКурсовВалют"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(DataProcessor.class);

    var dataProcessor = (DataProcessor) mdo;
    assertThat(dataProcessor).isNotNull();

    var attributes = dataProcessor.getAttributes();
    var tabularSections = dataProcessor.getTabularSections();
    var forms = dataProcessor.getForms();
    var templates = dataProcessor.getTemplates();
    var commands = dataProcessor.getCommands();
    var modules = dataProcessor.getModules();
    var moduleTypes = dataProcessor.getModuleTypes();

    // --- ModuleOwner ---
    assertThat(moduleTypes).hasSize(modules.size());
    assertThat(modules).allSatisfy(m -> assertThat(m.getOwner()).isEqualTo(dataProcessor.getMdoReference()));
    Assertions.assertThat(dataProcessor.getAllModules(), false)
      .containsAll(modules, forms, commands);

    // --- ChildrenOwner ---
    Assertions.assertThat(dataProcessor.getChildren(), true)
      .containsAll(attributes, tabularSections, forms, templates, commands);
    Assertions.assertThat(dataProcessor.getPlainChildren(), true)
      .containsAllPlain(attributes, tabularSections, forms, templates, commands);

    // --- AttributeOwner ---
    Assertions.assertThat(dataProcessor.getAllAttributes(), false)
      .containsAll(attributes);
    var storageFields = dataProcessor.getStorageFields();
    Assertions.assertThat(storageFields, false)
      .containsAll(attributes, tabularSections);
    var plainStorageFields = dataProcessor.getPlainStorageFields();
    Assertions.assertThat(plainStorageFields, false)
      .containsAllPlain(attributes, tabularSections);
  }
}
