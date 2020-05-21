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

import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class SubsystemTest {

  private static final String SRC_EDT = "src/test/resources/metadata/edt/src";
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";

  @Test
  void testEDT() {
    Subsystem mdo = (Subsystem) MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.SUBSYSTEM, getMDOPathEDT("Subsystems/ПерваяПодсистема/ПерваяПодсистема.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo.getChildren()).isNotNull();
    assertThat(mdo.getChildren()).hasSize(2);

    mdo = (Subsystem) MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.SUBSYSTEM, getMDOPathEDT("Subsystems/ПерваяПодсистема/Subsystems/ПочиненнаяСистема2/ПочиненнаяСистема2.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo.getChildren()).isNotNull();
    assertThat(mdo.getChildren()).hasSize(2);
  }

  @Test
  void testDesigner() {
    Subsystem mdo = (Subsystem) MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.SUBSYSTEM, getMDOPathDesigner("Subsystems/ПерваяПодсистема.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo.getChildren()).isNotNull();
    assertThat(mdo.getChildren()).hasSize(4);

    mdo = (Subsystem) MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.SUBSYSTEM, getMDOPathDesigner("Subsystems/ПерваяПодсистема/Subsystems/ПочиненнаяСистема2.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo.getChildren()).isNotNull();
    assertThat(mdo.getChildren()).hasSize(2);
  }

  private Path getMDOPathEDT(String path) {
    return Paths.get(SRC_EDT, path);
  }

  private Path getMDOPathDesigner(String path) {
    return Paths.get(SRC_DESIGNER, path);
  }

}