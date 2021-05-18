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
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDConstantTest extends AbstractMDOTest {
  MDConstantTest() {
    super(MDOType.CONSTANT);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("Constants/Константа1/Константа1.mdo");
    checkBaseField(mdo, MDConstant.class, "Константа1",
      "61e6a6f2-7057-4e93-96c3-7bd2559217f4");
    checkNoChildren(mdo);
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1,
      "Constants/Константа1", ModuleType.ValueManagerModule);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("Constants/Константа1.xml");
    checkBaseField(mdo, MDConstant.class, "Константа1",
      "61e6a6f2-7057-4e93-96c3-7bd2559217f4");
    checkNoChildren(mdo);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
  }
}
