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

import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentTest extends AbstractMDObjectTest<Document> {
  DocumentTest() {
    super(Document.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "Документ1,ce4fb46b-4af7-493e-9fcb-76ad8c4f8acd,,,Document,Документ,3,1,3,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("Documents/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.DOCUMENT, argumentsAccessor);
    assertThat(mdo.getRegisterRecords()).hasSize(4);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "Документ1,ce4fb46b-4af7-493e-9fcb-76ad8c4f8acd,,,Document,Документ,3,1,3,2,1,2"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("Documents/" + name + "/" + name);
    mdoTest(mdo, MDOType.DOCUMENT, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "Реквизит1", "681aaa11-422a-41a6-bd93-28b2ad83f595");

    checkChildField(mdo.getForms().get(0),
      "ФормаДокумента", "875c9efc-f2ae-4773-bac2-3556424557c3");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "37ad5673-ec3d-44f5-af6b-6b9d69591fc6");

    checkChildField(mdo.getCommands().get(0),
      "Команда", "cdb33f38-7e75-444a-ab2f-bb267be790da");

    checkChildField(mdo.getTabularSections().get(0),
      "ТабличнаяЧасть1", "508fe2d9-44b0-4b34-a349-2b0bcae6adc4");
    assertThat(mdo.getRegisterRecords()).hasSize(4);
  }
}
