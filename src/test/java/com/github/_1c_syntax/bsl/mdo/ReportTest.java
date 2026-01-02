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

import com.github._1c_syntax.bsl.mdo.storage.DataCompositionSchema;
import com.github._1c_syntax.bsl.mdo.support.DataSetType;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ReportTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, Reports.Отчет1, _edt",
      "false, mdclasses, Reports.Отчет1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Report.class);
    var report = (Report) mdo;

    assertThat(report.getTemplates()).hasSize(3);

    var templateData = report.getTemplates().stream()
      .filter(template -> template.getName().equals("СКД")).findFirst().get().getData();

    assertThat(templateData).isInstanceOf(DataCompositionSchema.class);
    checkDataCompositionSchema((DataCompositionSchema) templateData);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, Reports.АнализВерсийОбъектов, _edt",
      "false, ssl_3_1, Reports.АнализВерсийОбъектов"
    }
  )
  void testSSL(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
  }

  private void checkDataCompositionSchema(DataCompositionSchema dataCompositionSchema) {

    final var QUERY_TEXT = """
      ВЫБРАТЬ
      \tПервыйСправочник.Ссылка КАК Ссылка,
      \tПервыйСправочник.Код КАК Код1
      ИЗ
      \tСправочник.ПервыйСправочник КАК ПервыйСправочник""";

    assertThat(dataCompositionSchema).isNotNull();
    assertThat(dataCompositionSchema.getDataSets())
      .hasSize(4)
      .anyMatch(dataSet -> dataSet.name().equals("НаборДанных1") && dataSet.type() == DataSetType.DATA_SET_QUERY)
      .anyMatch(dataSet -> dataSet.name().equals("НаборДанных2") && dataSet.type() == DataSetType.DATA_SET_QUERY)
      .anyMatch(dataSet -> dataSet.name().equals("НаборДанных3") && dataSet.type() == DataSetType.DATA_SET_UNION
        && dataSet.items().size() == 3)
      .anyMatch(dataSet -> dataSet.name().equals("НаборДанных3") && dataSet.type() == DataSetType.DATA_SET_OBJECT)
    ;

    assertThat(dataCompositionSchema.getPlainDataSets())
      .hasSize(8)
      .anyMatch(dataSet -> dataSet.name().equals("НаборДанных1")
        && dataSet.type() == DataSetType.DATA_SET_QUERY
        && dataSet.querySource().textQuery().equals(QUERY_TEXT)
        && dataSet.querySource().line() == 24);
  }
}