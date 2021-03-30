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

class MDCommonCommandTest extends AbstractMDOTest {
  MDCommonCommandTest() {
    super(MDOType.COMMON_COMMAND);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("CommonCommands/ОбщаяКоманда1/ОбщаяКоманда1.mdo");
    checkBaseField(mdo, MDCommonCommand.class, "ОбщаяКоманда1",
      "a608f796-f58e-4f8a-b63f-272342b32f35");
    checkNoChildren(mdo);
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1,
      "CommonCommands/ОбщаяКоманда1", ModuleType.CommandModule);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("CommonCommands/ОбщаяКоманда1.xml");
    checkBaseField(mdo, MDCommonCommand.class, "ОбщаяКоманда1",
      "a608f796-f58e-4f8a-b63f-272342b32f35");
    checkNoChildren(mdo);
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1,
      "CommonCommands/ОбщаяКоманда1", ModuleType.CommandModule);
  }
}
