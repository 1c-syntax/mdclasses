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

import com.github._1c_syntax.bsl.mdo.children.RecalculationDimension;
import com.github._1c_syntax.bsl.mdo.support.CalculationRegisterPeriodicity;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CalculationRegisterTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, CalculationRegisters.РегистрРасчета1",
      "false, mdclasses, CalculationRegisters.РегистрРасчета1",
      "true, mdclasses_3_25, CalculationRegisters.РегистрРасчета1",
      "false, mdclasses_3_25, CalculationRegisters.РегистрРасчета1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(CalculationRegister.class);

    var calculationRegister = (CalculationRegister) mdo;
    assertThat(calculationRegister).isNotNull();

    // --- Периодичность ---
    assertThat(calculationRegister.getPeriodicity()).isEqualTo(CalculationRegisterPeriodicity.MONTH);

    // --- ModuleOwner ---
    assertThat(calculationRegister.getModuleTypes()).isEmpty();
    Assertions.assertThat(calculationRegister.getAllModules(), false)
      .containsAll(calculationRegister.getModules(),
        calculationRegister.getRecalculations(),
        calculationRegister.getForms(),
        calculationRegister.getCommands());

    // --- ChildrenOwner ---
    Assertions.assertThat(calculationRegister.getChildren(), true)
      .containsAll(calculationRegister.getAttributes(),
        calculationRegister.getResources(),
        calculationRegister.getDimensions(),
        calculationRegister.getRecalculations(),
        calculationRegister.getForms(),
        calculationRegister.getTemplates(),
        calculationRegister.getCommands());
    Assertions.assertThat(calculationRegister.getPlainChildren(), true)
      .containsAllPlain(calculationRegister.getAttributes(),
        calculationRegister.getResources(),
        calculationRegister.getDimensions(),
        calculationRegister.getRecalculations(),
        calculationRegister.getForms(),
        calculationRegister.getTemplates(),
        calculationRegister.getCommands());

    // --- AttributeOwner ---
    Assertions.assertThat(calculationRegister.getAllAttributes(), false)
      .containsAll(calculationRegister.getAttributes(),
        calculationRegister.getResources(),
        calculationRegister.getDimensions());
    Assertions.assertThat(calculationRegister.getStorageFields(), false)
      .containsAll(calculationRegister.getAttributes(),
        calculationRegister.getResources(),
        calculationRegister.getDimensions());
    Assertions.assertThat(calculationRegister.getPlainStorageFields(), false)
      .containsAll(calculationRegister.getStorageFields());

    // --- CalculationRegister ---
    var recalc = calculationRegister.getRecalculations().getFirst();
    if (!recalc.getModules().isEmpty()) {
      assertThat(recalc.getModules()).allMatch(Module::isProtected);
    }

    if (recalc.getName().equals("Перерасчет1")) {
      var recalcDimensions = recalc.getDimensions();
      assertThat(recalcDimensions).hasSize(3);

      var names = recalcDimensions.stream().map(RecalculationDimension::getName).toList();
      assertThat(names).containsExactlyInAnyOrder("Измерение1", "Измерение2", "Измерение3");

      var types = recalcDimensions.stream().map(RecalculationDimension::getMdoType).distinct().toList();
      assertThat(types)
        .contains(MDOType.RECALCULATION_DIMENSION);
    }
  }
}
