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

import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.api.Test;

class MDFunctionalOptionTest extends AbstractMDOTest {
  MDFunctionalOptionTest() {
    super(MDOType.FUNCTIONAL_OPTION);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("FunctionalOptions/ФункциональнаяОпция1/ФункциональнаяОпция1.mdo");
    checkBaseField(mdo, MDFunctionalOption.class, "ФункциональнаяОпция1",
      "d3b7fd71-6570-4047-91e0-b3df75dba08d");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("FunctionalOptions/ФункциональнаяОпция1.xml");
    checkBaseField(mdo, MDFunctionalOption.class, "ФункциональнаяОпция1",
      "d3b7fd71-6570-4047-91e0-b3df75dba08d");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

}
