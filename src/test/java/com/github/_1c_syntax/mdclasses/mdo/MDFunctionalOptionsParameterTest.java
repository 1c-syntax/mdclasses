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

class MDFunctionalOptionsParameterTest extends AbstractMDOTest {
  MDFunctionalOptionsParameterTest() {
    super(MDOType.FUNCTIONAL_OPTIONS_PARAMETER);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("FunctionalOptionsParameters/ПараметрФункциональныхОпций/ПараметрФункциональныхОпций.mdo");
    checkBaseField(mdo, MDFunctionalOptionsParameter.class, "ПараметрФункциональныхОпций",
      "9fae7345-6220-4e5b-b4c1-84bb921a58b7");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("FunctionalOptionsParameters/ПараметрФункциональныхОпций1.xml");
    checkBaseField(mdo, MDFunctionalOptionsParameter.class, "ПараметрФункциональныхОпций1",
      "8e2f9f0c-8727-4ffc-a6ea-f510b37814eb");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

}
