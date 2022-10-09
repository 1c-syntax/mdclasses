/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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
package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.github._1c_syntax.mdclasses.mdo.MDAccountingRegister;
import com.thoughtworks.xstream.converters.ConversionException;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XStreamFactoryTest {

  private static final String SRC_EDT = "src/test/resources/metadata/edt";
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";

  @Test
  void test() {
    var mdo = MDClasses.readMDObject(SRC_EDT, "AccountingRegisters.РегистрБухгалтерии1");
    assertThat(mdo)
      .isPresent()
      .containsInstanceOf(MDAccountingRegister.class)
      .map(MDObject::getName)
      .contains("РегистрБухгалтерии1");
    assertThat(mdo)
      .map(MDObject::getUuid)
      .contains("e5930f2f-15d9-48a1-ac69-379ad990b02a");

    var mdo2 = MDClasses.readMDObject(SRC_DESIGNER, "AccountingRegisters.РегистрБухгалтерии1");
    assertThat(mdo2)
      .isPresent()
      .containsInstanceOf(MDAccountingRegister.class)
      .map(MDObject::getName)
      .contains("РегистрБухгалтерии1");
    assertThat(mdo2)
      .map(MDObject::getUuid)
      .contains("e5930f2f-15d9-48a1-ac69-379ad990b02a");

    var jsonMdo1 = MDTestUtils.createJsonWithEmptyPath(mdo.get());
    var jsonMdo2 = MDTestUtils.createJsonWithEmptyPath(mdo2.get());
    Assertions.assertThat(jsonMdo1, true).isEqual(jsonMdo2);

  }

  @Test
  void testBrokenXml() {
    assertThrows(ConversionException.class,
      () -> MDClasses.readMDObject(Paths.get("src/test/resources/metadata/original_broken"), "Configuration.Configuration"));
  }
}
