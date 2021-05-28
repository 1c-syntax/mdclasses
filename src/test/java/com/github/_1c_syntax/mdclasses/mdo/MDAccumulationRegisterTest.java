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

import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDAccumulationRegisterTest extends AbstractMDOTest {
  MDAccumulationRegisterTest() {
    super(MDOType.ACCUMULATION_REGISTER);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("AccumulationRegisters/РегистрНакопления1/РегистрНакопления1.mdo");
    checkBaseField(mdo, MDAccumulationRegister.class, "РегистрНакопления1",
      "8ea07f36-d671-4649-bc7a-94daa939e77f");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 2,
      mdo.getMdoReference(), AttributeType.DIMENSION, AttributeType.RESOURCE);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();

  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("AccumulationRegisters/РегистрНакопления1.xml");
    checkBaseField(mdo, MDAccumulationRegister.class, "РегистрНакопления1",
      "8ea07f36-d671-4649-bc7a-94daa939e77f");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 2,
      mdo.getMdoReference(), AttributeType.DIMENSION, AttributeType.RESOURCE);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
  }
}