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

class SequenceTest extends AbstractMDOTest {
  SequenceTest() {
    super(MDOType.SEQUENCE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("Sequences/Последовательность1/Последовательность1.mdo");
    checkBaseField(mdo, Sequence.class, "Последовательность1",
      "514bbcf4-7fc4-4a3e-9245-598fad397eec");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 1, "Sequence.Последовательность1",
      AttributeType.DIMENSION);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "Sequences/Последовательность1",
      ModuleType.RecordSetModule);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Sequences/Последовательность1.xml");
    checkBaseField(mdo, Sequence.class, "Последовательность1",
      "514bbcf4-7fc4-4a3e-9245-598fad397eec");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 1, "Sequence.Последовательность1",
      AttributeType.DIMENSION);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
