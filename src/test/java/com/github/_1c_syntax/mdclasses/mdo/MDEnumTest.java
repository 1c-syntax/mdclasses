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
import com.github._1c_syntax.bsl.types.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDEnumTest extends AbstractMDOTest {
  MDEnumTest() {
    super(MDOType.ENUM);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("Enums/Перечисление1/Перечисление1.mdo");
    checkBaseField(mdo, MDEnum.class, "Перечисление1",
      "f11f3441-4b64-4344-b1a0-0e4b3e466e03");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((AbstractMDObjectComplex) mdo).getAttributes()).isEmpty();
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1, "Enums/Перечисление1",
      ModuleType.ManagerModule);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("Enums/Перечисление1.xml");
    checkBaseField(mdo, MDEnum.class, "Перечисление1",
      "f11f3441-4b64-4344-b1a0-0e4b3e466e03");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((AbstractMDObjectComplex) mdo).getAttributes()).isEmpty();
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
  }
}
