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

class ReportTest {
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Reports.Отчет1",
    "false, mdclasses, Reports.Отчет1",
    "true, ssl_3_1, Reports.АнализВерсийОбъектов",
    "false, ssl_3_1, Reports.АнализВерсийОбъектов",
    "true, ssl_3_2, Reports.АнализВерсийОбъектов",
    "false, ssl_3_2, Reports.АнализВерсийОбъектов"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Report.class);

    var report = (Report) mdo;
    assertThat(report).isNotNull();

    var modules = report.getModules();
    var attributes = report.getAttributes();
    var tabularSections = report.getTabularSections();
    var forms = report.getForms();
    var templates = report.getTemplates();
    var commands = report.getCommands();

    // --- ModuleOwner ---
    Assertions.assertThat(report.getAllModules(), true).containsAll(modules, forms, commands);

    // --- AttributeOwner ---
    Assertions.assertThat(report.getAllAttributes(), true)
      .containsAll(attributes);
    Assertions.assertThat(report.getStorageFields(), true)
      .containsAll(attributes);
    Assertions.assertThat(report.getPlainStorageFields(), true)
      .containsAllPlain(attributes, tabularSections);

    // --- ChildrenOwner ---
    Assertions.assertThat(report.getChildren(), true)
      .containsAll(attributes, tabularSections, forms, templates, commands);
    Assertions.assertThat(report.getPlainChildren(), true)
      .containsAllPlain(attributes, tabularSections, forms, templates, commands);
  }
}
