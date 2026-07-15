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
import com.github._1c_syntax.bsl.types.ModuleType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CommonModuleTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, CommonModules.ГлобальныйОбщийМодуль",
      "false, mdclasses, CommonModules.ГлобальныйОбщийМодуль",
      "true, mdclasses, CommonModules.ОбщийМодульВызовСервера",
      "false, mdclasses, CommonModules.ОбщийМодульВызовСервера",
      "true, mdclasses, CommonModules.ОбщийМодульПовтИспВызов",
      "false, mdclasses, CommonModules.ОбщийМодульПовтИспВызов",
      "true, mdclasses, CommonModules.ОбщийМодульПовтИспСеанс",
      "false, mdclasses, CommonModules.ОбщийМодульПовтИспСеанс",
      "true, mdclasses, CommonModules.ОбщийМодульПолныйеПрава",
      "false, mdclasses, CommonModules.ОбщийМодульПолныйеПрава",
      "true, mdclasses, CommonModules.ПростойОбщийМодуль",
      "false, mdclasses, CommonModules.ПростойОбщийМодуль",
      "true, ssl_3_1, CommonModules.АвтономнаяРабота",
      "false, ssl_3_1, CommonModules.АвтономнаяРабота",
      "true, ssl_3_2, CommonModules.АвтономнаяРабота",
      "false, ssl_3_2, CommonModules.АвтономнаяРабота"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(CommonModule.class);

    var commonModule = (CommonModule) mdo;
    assertThat(commonModule).isNotNull();
    assertThat(commonModule.getModules()).hasSize(1);
    assertThat(commonModule.getAllModules()).isEqualTo(commonModule.getModules());

    assertThat(commonModule.getModuleTypes())
      .hasSize(1)
      .containsKey(ModuleType.CommonModule);
    assertThat(commonModule.getModuleTypes().get(ModuleType.CommonModule))
      .hasSize(1)
      .contains(commonModule.getUri());

    assertThat(commonModule.isProtected()).isFalse();
    assertThat(commonModule.getOwner()).isEqualTo(commonModule.getMdoReference());
  }
}
