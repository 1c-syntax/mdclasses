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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.UseMode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDCommonAttributeTest extends AbstractMDOTest {
  MDCommonAttributeTest() {
    super(MDOType.COMMON_ATTRIBUTE);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("CommonAttributes/ОбщийРеквизит1/ОбщийРеквизит1.mdo");
    checkBaseField(mdo, MDCommonAttribute.class, "ОбщийРеквизит1",
      "d4f0c0ac-ed26-4085-a1b4-e52314b973ad");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    var commonAttribute = (MDCommonAttribute) mdo;
    assertThat(commonAttribute.getAutoUse()).isEqualTo(UseMode.USE);
    assertThat(commonAttribute.getDataSeparation()).isEqualTo(UseMode.DONT_USE);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("CommonAttributes/ОбщийРеквизит1.xml");
    checkBaseField(mdo, MDCommonAttribute.class, "ОбщийРеквизит1",
      "d4f0c0ac-ed26-4085-a1b4-e52314b973ad");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    var commonAttribute = (MDCommonAttribute) mdo;
    assertThat(commonAttribute.getAutoUse()).isEqualTo(UseMode.USE);
    assertThat(commonAttribute.getDataSeparation()).isEqualTo(UseMode.DONT_USE);
  }

}
