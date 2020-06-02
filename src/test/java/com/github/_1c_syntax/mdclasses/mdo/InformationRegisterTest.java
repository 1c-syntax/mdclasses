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

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InformationRegisterTest extends AbstractMDOTest {
  InformationRegisterTest() {
    super(MDOType.INFORMATION_REGISTER);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("InformationRegisters/РегистрСведений1/РегистрСведений1.mdo");
    checkBaseField(mdo, InformationRegister.class, "РегистрСведений1",
      "184d9d78-9523-4cfa-9542-a7ba72efe4dd");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 1, "InformationRegister.РегистрСведений1",
      AttributeType.DIMENSION);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "InformationRegisters/РегистрСведений1",
      ModuleType.RecordSetModule);

  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("InformationRegisters/РегистрСведений1.xml");
    checkBaseField(mdo, InformationRegister.class, "РегистрСведений1",
      "184d9d78-9523-4cfa-9542-a7ba72efe4dd");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 1, "InformationRegister.РегистрСведений1",
      AttributeType.DIMENSION);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
