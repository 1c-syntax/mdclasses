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

import com.github._1c_syntax.bsl.mdo.children.Recalculation;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CalculationRegisterTest extends AbstractMDObjectTest<CalculationRegister> {
  CalculationRegisterTest() {
    super(CalculationRegister.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "РегистрРасчета1,90587c7d-b950-4476-ac14-426e4a83d9c4,,,CalculationRegister,РегистрРасчета,3,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("CalculationRegisters/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.CALCULATION_REGISTER, argumentsAccessor);
    assertThat(mdo.getAllModules()).hasSize(1);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "РегистрРасчета1,90587c7d-b950-4476-ac14-426e4a83d9c4,,,CalculationRegister,РегистрРасчета,2,1,1,1,1,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("CalculationRegisters/" + name + "/" + name);
    mdoTest(mdo, MDOType.CALCULATION_REGISTER, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "Ресурс1", "86f41061-e298-4da5-8d28-489a349d55fc",
      List.of("passwordMode") // бывает только для строк, а здесь строк быть не может
    );

    checkChildField(mdo.getForms().get(0),
      "ФормаСписка", "eee0b9fc-95de-4cb0-bb73-c78ddade2be9");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "1509af7a-aeee-4906-adcb-32d2df6b7e21");

    checkChildField(mdo.getCommands().get(0),
      "Команда", "ecd63a0d-51c9-49f2-8df6-d8a1b14aee04");

    checkChildField(mdo.getRecalculations().get(0),
      "Перерасчет", "16b54095-8711-4ef1-a38b-93d12f6f6e93");

    assertThat(mdo.getAllModules()).hasSize(4);

    assertThat(mdo.getRecalculations()).hasSize(1);
    var recalculation = mdo.getRecalculations().get(0);
    assertThat(recalculation).isInstanceOf(Recalculation.class);
    assertThat(recalculation.getName()).isEqualTo("Перерасчет");
    assertThat(recalculation.getType()).isEqualTo(MDOType.RECALCULATION);
    assertThat(recalculation.getUuid()).isEqualTo("16b54095-8711-4ef1-a38b-93d12f6f6e93");
    assertThat(recalculation.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(recalculation.getMetadataName()).isEqualTo("Recalculation");
    assertThat(recalculation.getSynonyms().getContent())
      .hasSize(1)
      .contains(Map.entry("ru", "Перерасчет"));
    assertThat(recalculation.getMdoReference().getMdoRef())
      .isEqualTo("CalculationRegister.РегистрРасчета1.Recalculation.Перерасчет");
  }
}
