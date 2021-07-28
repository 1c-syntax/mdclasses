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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CommonTemplateTest extends AbstractMDObjectTest<CommonTemplate> {
  CommonTemplateTest() {
    super(CommonTemplate.class);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "ГрафическаяСхема,4333f027-4fc2-40a0-ae7d-48fbf0cea50b,,Графическая схема,CommonTemplate,ОбщийМакет,0,0,0,0,0,0, " +
        "GRAPHICAL_SCHEME",
      "Active,557665fc-86f5-44e1-9801-490cea841718,,Active,CommonTemplate,ОбщийМакет,0,0,0,0,0,0," +
        "ACTIVE_DOCUMENT",
      "ГеографическаяСхема,1d8d8dfc-e7c5-445a-a88d-6743faad2ab6,,Географическая схема,CommonTemplate,ОбщийМакет,0,0,0,0,0,0," +
        "GEOGRAPHICAL_SCHEMA",
      "ВнешняяКомпонента,4a0dab22-affd-4887-a9b6-57a1e88f8377,,Внешняя компонента,CommonTemplate,ОбщийМакет,0,0,0,0,0,0," +
        "ADD_IN",
      "ТекстовыйДокумент,3084f392-8f90-4134-a82e-790e225aab29,,Текстовый документ,CommonTemplate,ОбщийМакет,0,0,0,0,0,0," +
        "TEXT_DOCUMENT",
      "СКД,8ae95178-7f50-4a77-aaf8-f8ffb72c65d4,,СКД,CommonTemplate,ОбщийМакет,0,0,0,0,0,0," +
        "DATA_COMPOSITION_SCHEME",
      "МакетОформления,f6bbaf46-bc77-412b-9440-3032bfc06c57,,Макет оформления,CommonTemplate,ОбщийМакет,0,0,0,0,0,0," +
        "DATA_COMPOSITION_APPEARANCE_TEMPLATE",
      "ДвоичныеДанные,f4ab9283-1110-4808-9cbf-40c71ebb88a2,,Двоичные данные,CommonTemplate,ОбщийМакет,0,0,0,0,0,0," +
        "BINARY_DATA",
      "HTML,5d76084a-68fa-4579-91da-17d7ffab6225,,HTML,CommonTemplate,ОбщийМакет,0,0,0,0,0,0," +
        "HTML_DOCUMENT",
      "ТабличныйДокумент,5b54ba38-ec04-4fc6-897f-48d36d0312a6,,Табличный документ,CommonTemplate,ОбщийМакет,0,0,0,0,0,0," +
        "SPREADSHEET_DOCUMENT"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("CommonTemplates/" + name + "/" + name);
    mdoTest(mdo, MDOType.COMMON_TEMPLATE, argumentsAccessor);
    assertThat(mdo.getTemplateType()).isEqualTo(TemplateType.valueOf(argumentsAccessor.getString(12)));
    assertThat(mdo.getTemplateDataPath().toString()).endsWith("Template.dcs");
  }
}
