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
package com.github._1c_syntax.mdclasses.mdo.children.template;

import com.github._1c_syntax.mdclasses.mdo.support.DataSetType;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class DataCompositionSchemaTest {
  private static final String QUERY_TEXT = "ВЫБРАТЬ\n" +
    "\tПервыйСправочник.Ссылка КАК Ссылка,\n" +
    "\tПервыйСправочник.Код КАК Код1\n" +
    "ИЗ\n" +
    "\tСправочник.ПервыйСправочник КАК ПервыйСправочник";

  @Test
  void testEdt() {
    var path = Path.of("src/test/resources/metadata/skd/edt/src/Reports/Отчет1/Templates/СКД/Template.dcs");
    var dataCompositionSchema =
      MDOFactory.readDataCompositionSchema(path).get();
    checkDataCompositionSchema(dataCompositionSchema);
  }

  @Test
  void testOriginal() {
    var path = Path.of("src/test/resources/metadata/skd/original/Reports/Отчет1/Templates/СКД/Ext/Template.xml");
    var dataCompositionSchema =
      MDOFactory.readDataCompositionSchema(path).get();
    assertThat(dataCompositionSchema).isNotNull();
    checkDataCompositionSchema(dataCompositionSchema);
  }

  private void checkDataCompositionSchema(DataCompositionSchema dataCompositionSchema) {
    assertThat(dataCompositionSchema).isNotNull();
    assertThat(dataCompositionSchema.getDataSets())
      .hasSize(4)
      .anyMatch(dataSet -> dataSet.getName().equals("НаборДанных1") && dataSet.getType() == DataSetType.DATA_SET_QUERY)
      .anyMatch(dataSet -> dataSet.getName().equals("НаборДанных2") && dataSet.getType() == DataSetType.DATA_SET_QUERY)
      .anyMatch(dataSet -> dataSet.getName().equals("НаборДанных3") && dataSet.getType() == DataSetType.DATA_SET_UNION
        && dataSet.getItems().size() == 3)
      .anyMatch(dataSet -> dataSet.getName().equals("НаборДанных3") && dataSet.getType() == DataSetType.DATA_SET_OBJECT)
    ;

    assertThat(dataCompositionSchema.getPlainDataSets())
      .hasSize(8)
      .anyMatch(dataSet -> dataSet.getName().equals("НаборДанных1")
        && dataSet.getType() == DataSetType.DATA_SET_QUERY
        && dataSet.getQuerySource().getTextQuery().equals(QUERY_TEXT)
        && dataSet.getQuerySource().getPosition().getLine() == 24);
  }

}