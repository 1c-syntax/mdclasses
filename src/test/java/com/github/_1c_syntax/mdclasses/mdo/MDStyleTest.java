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

class MDStyleTest extends AbstractMDOTest {
  MDStyleTest() {
    super(MDOType.STYLE);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("Styles/Стиль/Стиль.mdo");
    checkBaseField(mdo, MDStyle.class, "Стиль",
      "d6aaa851-cba7-486d-92f4-ab31b1628c6b");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("Styles/Стиль1.xml");
    checkBaseField(mdo, MDStyle.class, "Стиль1",
      "2ef7f6ca-b11c-4e2d-a233-5c5b01675e9a");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
