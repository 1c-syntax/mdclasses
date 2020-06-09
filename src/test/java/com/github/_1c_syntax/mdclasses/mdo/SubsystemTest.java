/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOReference;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SubsystemTest extends AbstractMDOTest {
  SubsystemTest() {
    super(MDOType.SUBSYSTEM);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("Subsystems/ПерваяПодсистема/ПерваяПодсистема.mdo");
    checkBaseField(mdo, Subsystem.class, "ПерваяПодсистема",
      "3d00f7d6-e3b0-49cf-8093-e2e4f6ea2293");
    checkNoChildren(mdo);
    checkNoModules(mdo);

    assertThat(((Subsystem) mdo).isIncludeInCommandInterface()).isTrue();

    var children = ((Subsystem) mdo).getChildren();
    assertThat(children).hasSize(2);
    assertThat(children)
      .allMatch(Either::isRight)
      .extracting(Either::get)
      .anyMatch(child -> child.getName().equals("ПодчиненнаяПодсистема"))
      .anyMatch(child -> child.getName().equals("ПочиненнаяСистема2"));

    var subsystem = (Subsystem) children.stream().map(Either::get)
      .filter(child -> child.getName().equals("ПодчиненнаяПодсистема"))
      .findFirst().get();
    assertThat(subsystem.getChildren()).hasSize(2)
      .allMatch(Either::isLeft);
    assertThat(subsystem.getMdoReference())
      .isNotNull()
      .extracting(MDOReference::getType)
      .isEqualTo(MDOType.SUBSYSTEM);
    assertThat(subsystem.getMdoReference().getMdoRef())
      .isEqualTo("Subsystem.ПерваяПодсистема.Subsystem.ПодчиненнаяПодсистема");

    subsystem = (Subsystem) children.stream().map(Either::get)
      .filter(child -> child.getName().equals("ПочиненнаяСистема2"))
      .findFirst().get();
    assertThat(subsystem.getChildren()).hasSize(3);
    assertThat(subsystem.getChildren()).filteredOn(Either::isLeft).hasSize(2);
    assertThat(subsystem.getChildren()).filteredOn(Either::isRight).hasSize(1);
    assertThat(subsystem.getChildren()).filteredOn(Either::isRight)
      .extracting(Either::get)
      .anyMatch(child -> child.getName().equals("ПодчиненнаяПодсистема3Уровня"))
      .anyMatch(child -> child instanceof Subsystem);
    assertThat(subsystem.getMdoReference())
      .isNotNull()
      .extracting(MDOReference::getType)
      .isEqualTo(MDOType.SUBSYSTEM);
    assertThat(subsystem.getMdoReference().getMdoRef())
      .isEqualTo("Subsystem.ПерваяПодсистема.Subsystem.ПочиненнаяСистема2");

    var childSubsystem = (Subsystem) subsystem.getChildren().stream()
      .filter(Either::isRight)
      .map(Either::get)
      .filter(child -> child.getName().equals("ПодчиненнаяПодсистема3Уровня"))
      .findFirst().get();
    assertThat(childSubsystem.getChildren()).hasSize(3);
    assertThat(childSubsystem.getChildren()).filteredOn(Either::isLeft).hasSize(3);
  }

  @Override
  @Test
  void testDesigner() {

    var mdo = getMDObjectDesigner("Subsystems/ПерваяПодсистема.xml");
    checkBaseField(mdo, Subsystem.class, "ПерваяПодсистема",
      "3d00f7d6-e3b0-49cf-8093-e2e4f6ea2293");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((Subsystem) mdo).isIncludeInCommandInterface()).isTrue();

    var children = ((Subsystem) mdo).getChildren();
    assertThat(children).hasSize(4);
    assertThat(children).filteredOn(Either::isLeft).hasSize(2);
    assertThat(children).filteredOn(Either::isRight).hasSize(2);
    assertThat(children)
      .filteredOn(Either::isRight)
      .extracting(Either::get)
      .anyMatch(child -> child.getName().equals("ПодчиненнаяПодсистема"))
      .anyMatch(child -> child.getName().equals("ПочиненнаяСистема2"));

    var subsystem = (Subsystem) children.stream().filter(Either::isRight).map(Either::get)
      .filter(child -> child.getName().equals("ПодчиненнаяПодсистема"))
      .findFirst().get();
    assertThat(subsystem.getChildren()).hasSize(3)
      .allMatch(Either::isLeft);
    assertThat(subsystem.getMdoReference())
      .isNotNull()
      .extracting(MDOReference::getType)
      .isEqualTo(MDOType.SUBSYSTEM);
    assertThat(subsystem.getMdoReference().getMdoRef())
      .isEqualTo("Subsystem.ПерваяПодсистема.Subsystem.ПодчиненнаяПодсистема");

    subsystem = (Subsystem) children.stream().filter(Either::isRight).map(Either::get)
      .filter(child -> child.getName().equals("ПочиненнаяСистема2"))
      .findFirst().get();
    assertThat(subsystem.getChildren()).hasSize(2);
    assertThat(subsystem.getChildren()).filteredOn(Either::isLeft).hasSize(2);
    assertThat(subsystem.getMdoReference())
      .isNotNull()
      .extracting(MDOReference::getType)
      .isEqualTo(MDOType.SUBSYSTEM);
    assertThat(subsystem.getMdoReference().getMdoRef())
      .isEqualTo("Subsystem.ПерваяПодсистема.Subsystem.ПочиненнаяСистема2");

  }

}