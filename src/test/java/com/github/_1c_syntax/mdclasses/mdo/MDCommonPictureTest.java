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
import org.junit.jupiter.api.Test;

class MDCommonPictureTest extends AbstractMDOTest {
  MDCommonPictureTest() {
    super(MDOType.COMMON_PICTURE);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("CommonPictures/ОбщаяКартинка1/ОбщаяКартинка1.mdo");
    checkBaseField(mdo, MDCommonPicture.class, "ОбщаяКартинка1",
      "db84513d-2535-494b-843e-6d8931cb2f82");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("CommonPictures/ОбщаяКартинка1.xml");
    checkBaseField(mdo, MDCommonPicture.class, "ОбщаяКартинка1",
      "db84513d-2535-494b-843e-6d8931cb2f82");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
