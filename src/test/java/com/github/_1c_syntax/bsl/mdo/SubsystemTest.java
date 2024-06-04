/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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

import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SubsystemTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, Subsystems.ПерваяПодсистема",
      "false, mdclasses, Subsystems.ПерваяПодсистема"
    }
  )
  void testMDClasses(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);

    var subsystem = (Subsystem) mdo;

    var children = subsystem.getSubsystems();
    assertThat(children).hasSize(2)
      .anyMatch(child -> child.getName().equals("ПодчиненнаяПодсистема"))
      .anyMatch(child -> child.getName().equals("ПочиненнаяСистема2"));

    var subsystemChildOpt = subsystem.findChild("Subsystem.ПерваяПодсистема.Subsystem.ПодчиненнаяПодсистема");
    assertThat(subsystemChildOpt).isPresent();

    var subsystemChild = (Subsystem) subsystemChildOpt.get();
    assertThat(subsystemChild.getChildren()).isEmpty();
    assertThat(subsystemChild.getContent())
      .hasSize(2)
      .anyMatch(child -> child.getMdoRef().equals("Catalog.Справочник1"))
      .anyMatch(child -> child.getMdoRef().equals("DataProcessor.Обработка1"));
    assertThat(subsystemChild.getParentSubsystem()).isEqualTo(subsystem.getMdoReference());

    assertThat(subsystemChild.getMdoReference())
      .isNotNull()
      .extracting(MdoReference::getType)
      .isEqualTo(MDOType.SUBSYSTEM);

    assertThat(subsystemChild.getMdoReference().getMdoRef())
      .isEqualTo("Subsystem.ПерваяПодсистема.Subsystem.ПодчиненнаяПодсистема");
    assertThat(subsystemChild.getMdoReference().getMdoRefRu())
      .isEqualTo("Подсистема.ПерваяПодсистема.Подсистема.ПодчиненнаяПодсистема");

    subsystemChildOpt = subsystem.findChild("Subsystem.ПерваяПодсистема.Subsystem.ПочиненнаяСистема2");
    assertThat(subsystemChildOpt).isPresent();

    subsystemChild = (Subsystem) subsystemChildOpt.get();
    assertThat(subsystemChild.getChildren())
      .hasSize(1)
      .anyMatch(child -> child.getName().equals("ПодчиненнаяПодсистема3Уровня"))
      .anyMatch(child -> child instanceof Subsystem);
    assertThat(subsystemChild.getContent()).hasSize(2);
    assertThat(subsystemChild.getParentSubsystem()).isEqualTo(subsystem.getMdoReference());
    var parent2nd = subsystemChild.getMdoReference();

    subsystemChildOpt = subsystem.findChild(
      "Subsystem.ПерваяПодсистема.Subsystem.ПочиненнаяСистема2.Subsystem.ПодчиненнаяПодсистема3Уровня");
    assertThat(subsystemChildOpt).isPresent();
    subsystemChild = (Subsystem) subsystemChildOpt.get();

    assertThat(subsystemChild.getChildren()).isEmpty();
    assertThat(subsystemChild.getSubsystems()).isEmpty();
    assertThat(subsystemChild.getContent()).hasSize(3);
    assertThat(subsystemChild.getParentSubsystem()).isEqualTo(parent2nd);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, Subsystems.Администрирование",
      "false, ssl_3_1, Subsystems.Администрирование"
    }
  )
  void testSSLSimple(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
  }
}
