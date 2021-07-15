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
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDTaskTest extends AbstractMDOTest {
  MDTaskTest() {
    super(MDOType.TASK);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("Tasks/Задача1/Задача1.mdo");
    checkBaseField(mdo, MDTask.class, "Задача1",
      "c251fcec-ec02-4ef4-8f70-4d70db6631ea");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 1, mdo.getMdoReference(),
      AttributeType.ADDRESSING_ATTRIBUTE);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("Tasks/Задача1.xml");
    checkBaseField(mdo, MDTask.class, "Задача1",
      "c251fcec-ec02-4ef4-8f70-4d70db6631ea");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 1, mdo.getMdoReference(),
      AttributeType.ADDRESSING_ATTRIBUTE);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
  }
}
