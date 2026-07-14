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

import com.github._1c_syntax.bsl.mdo.support.InformationRegisterPeriodicity;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class InformationRegisterTest {
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, InformationRegisters.РегистрСведений1, NONPERIODICAL",
    "false, mdclasses, InformationRegisters.РегистрСведений1, NONPERIODICAL",
    "true, ssl_3_1, InformationRegisters.ЭлектронныеПодписи, NONPERIODICAL",
    "false, ssl_3_1, InformationRegisters.ЭлектронныеПодписи, NONPERIODICAL",
    "true, ssl_3_2, InformationRegisters.ЭлектронныеПодписи, NONPERIODICAL",
    "false, ssl_3_2, InformationRegisters.ЭлектронныеПодписи, NONPERIODICAL",
    "true, ssl_3_1, InformationRegisters.СклоненияПредставленийОбъектов, NONPERIODICAL",
    "false, ssl_3_1, InformationRegisters.СклоненияПредставленийОбъектов, NONPERIODICAL",
    "true, ssl_3_2, InformationRegisters.СклоненияПредставленийОбъектов, NONPERIODICAL",
    "false, ssl_3_2, InformationRegisters.СклоненияПредставленийОбъектов, NONPERIODICAL",
    "true, ssl_3_2, InformationRegisters.КурсыВалют, DAY",
    "false, ssl_3_2, InformationRegisters.КурсыВалют, DAY"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(InformationRegister.class);

    var informationRegister = (InformationRegister) mdo;
    assertThat(informationRegister).isNotNull();

    // --- Периодичность ---
    var expectedPeriodicity = InformationRegisterPeriodicity.valueOf(argumentsAccessor.getString(3));
    assertThat(informationRegister.getInformationRegisterPeriodicity()).isEqualTo(expectedPeriodicity);

    // --- ModuleOwner ---
    Assertions.assertThat(informationRegister.getAllModules(), false)
      .containsAll(informationRegister.getModules(),
        informationRegister.getForms(),
        informationRegister.getCommands());

    // --- ChildrenOwner ---
    Assertions.assertThat(informationRegister.getChildren(), false)
      .containsAll(informationRegister.getAttributes(),
        informationRegister.getDimensions(),
        informationRegister.getResources(),
        informationRegister.getForms(),
        informationRegister.getCommands(),
        informationRegister.getTemplates());
    Assertions.assertThat(informationRegister.getPlainChildren(), true)
      .containsAll(informationRegister.getChildren());

    // --- AttributeOwner ---
    Assertions.assertThat(informationRegister.getAllAttributes(), false)
      .containsAll(informationRegister.getAttributes(),
        informationRegister.getDimensions(),
        informationRegister.getResources());
    Assertions.assertThat(informationRegister.getStorageFields(), false)
      .containsAll(informationRegister.getAttributes(),
        informationRegister.getDimensions(),
        informationRegister.getResources());
    Assertions.assertThat(informationRegister.getPlainStorageFields(), false)
      .containsAll(informationRegister.getAttributes(),
        informationRegister.getDimensions(),
        informationRegister.getResources());
  }
}
