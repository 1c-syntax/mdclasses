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
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessProcessTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, BusinessProcesses.Задание",
      "false, ssl_3_1, BusinessProcesses.Задание",
      "true, ssl_3_2, BusinessProcesses.Задание",
      "false, ssl_3_2, BusinessProcesses.Задание"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(BusinessProcess.class);

    var businessProcess = (BusinessProcess) mdo;
    assertThat(businessProcess).isNotNull();

    // --- ModuleOwner ---
    var moduleTypes = businessProcess.getModuleTypes();
    assertThat(moduleTypes.keySet()).containsExactlyInAnyOrder(ModuleType.ManagerModule, ModuleType.ObjectModule);
    for (var entry : moduleTypes.entrySet()) {
      for (var uri : entry.getValue()) {
        assertThat(businessProcess.getModuleByUri(uri)).isPresent();
      }
    }
    Assertions.assertThat(businessProcess.getAllModules(), false)
      .containsAll(businessProcess.getModules(), businessProcess.getForms());

    // --- ChildrenOwner ---
    Assertions.assertThat(businessProcess.getChildren(), false)
      .containsAll(businessProcess.getAttributes(), businessProcess.getForms());
    Assertions.assertThat(businessProcess.getPlainChildren(), true)
      .containsAllPlain(businessProcess.getAttributes(), businessProcess.getForms());

    // --- AttributeOwner ---
    Assertions.assertThat(businessProcess.getAllAttributes(), false)
      .containsAll(businessProcess.getAttributes());
    Assertions.assertThat(businessProcess.getStorageFields(), false)
      .containsAll(businessProcess.getAttributes());
    Assertions.assertThat(businessProcess.getPlainStorageFields(), false)
      .containsAllPlain(businessProcess.getAttributes());

    // --- BasedOn ---
    assertThat(businessProcess.getBasedOn())
      .containsExactlyInAnyOrder(
        MdoReference.create("Catalog.Файлы"),
        MdoReference.create("Catalog.Пользователи"),
        MdoReference.create("Task.ЗадачаИсполнителя")
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses_3_27, BusinessProcesses.БизнесПроцесс1",
    "false, mdclasses_3_27, BusinessProcesses.БизнесПроцесс1"
  })
  void testIndexFields(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(BusinessProcess.class);

    var businessProcess = (BusinessProcess) mdo;
    assertThat(businessProcess).isNotNull();

    assertThat(businessProcess.getNumberLength()).isEqualTo(9);
  }
}
