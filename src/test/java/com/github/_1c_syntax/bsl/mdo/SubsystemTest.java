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

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class SubsystemTest extends AbstractMDObjectTest<Subsystem> {
  SubsystemTest() {
    super(Subsystem.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "ПерваяПодсистема,3d00f7d6-e3b0-49cf-8093-e2e4f6ea2293,,Первая подсистема,Subsystem,Подсистема,0,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("Subsystems/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.SUBSYSTEM, argumentsAccessor);
    assertThat(mdo.isIncludeInCommandInterface()).isTrue();
    assertThat(mdo.getChildren())
      .hasSize(2)
      .anyMatch(child -> child.getName().equals("ПодчиненнаяПодсистема"))
      .anyMatch(child -> child.getName().equals("ПочиненнаяСистема2"));
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "ПерваяПодсистема,3d00f7d6-e3b0-49cf-8093-e2e4f6ea2293,,Первая подсистема,Subsystem,Подсистема,0,0,0,0,0,0"
    }
  )
  void testEDT(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("Subsystems/" + name + "/" + name);
    mdoTest(mdo, MDOType.SUBSYSTEM, argumentsAccessor);
    assertThat(mdo.isIncludeInCommandInterface()).isTrue();
    assertThat(mdo.getChildren())
      .hasSize(2)
      .anyMatch(child -> child.getName().equals("ПодчиненнаяПодсистема"))
      .anyMatch(child -> child.getName().equals("ПочиненнаяСистема2"));
    var childSubsystem = mdo.getChildren().stream()
      .filter(mdObject -> mdObject.getName().equals("ПочиненнаяСистема2")).findFirst().get();
    assertThat(((Subsystem) childSubsystem).getChildren())
      .hasSize(1)
      .anyMatch(child -> child.getName().equals("ПодчиненнаяПодсистема3Уровня"));
    assertThat(mdo.getPlainChildren()).hasSize(3);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "ВтораяПодсистема,c3abc915-b27d-4dfc-bfae-31b9c867492e,,Вторая подсистема,Subsystem,Подсистема,0,0,0,0,0,0"
    }
  )
  void testContent(ArgumentsAccessor argumentsAccessor) {
    var rootPath = Path.of("src/test/resources/metadata/edt");
    var mdc = MDClasses.createConfiguration(rootPath);
    assertThat(mdc)
      .isNotNull()
      .isInstanceOf(Configuration.class);
    var configuration = (Configuration) mdc;
    assertThat(configuration.getSubsystems()).hasSize(2);
    var subsystem = configuration.getSubsystems().get(1);
    mdoTest(subsystem, MDOType.SUBSYSTEM, argumentsAccessor);
    assertThat(subsystem.getContent())
      .hasSize(3)
      .contains(MdoReference.find("CalculationRegister.РегистрРасчета1").get())
      .contains(MdoReference.find("AccountingRegister.РегистрБухгалтерии1").get())
      .contains(MdoReference.find("Enum.Перечисление1").get());
  }
}
