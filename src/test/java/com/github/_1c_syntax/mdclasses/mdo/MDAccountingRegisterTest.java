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

import com.github._1c_syntax.mdclasses.mdo.attributes.Dimension;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import com.github._1c_syntax.mdclasses.mdo.support.IndexingType;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDAccountingRegisterTest extends AbstractMDOTest {
  MDAccountingRegisterTest() {
    super(MDOType.ACCOUNTING_REGISTER);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("AccountingRegisters/РегистрБухгалтерии1/РегистрБухгалтерии1.mdo");
    checkBaseField(mdo, MDAccountingRegister.class, "РегистрБухгалтерии1",
      "e5930f2f-15d9-48a1-ac69-379ad990b02a");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 2,
      mdo.getMdoReference(), AttributeType.DIMENSION, AttributeType.RESOURCE);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
    var attribute = ((AbstractMDObjectComplex) mdo).getAttributes().get(0);
    assertThat(attribute.getIndexing()).isEqualTo(IndexingType.DONT_INDEX);
    var dimension = (Dimension) ((AbstractMDObjectComplex) mdo).getAttributes().get(0);
    assertThat(dimension.isDenyIncompleteValues()).isFalse();
    assertThat(dimension.isMaster()).isFalse();
    assertThat(dimension.isUseInTotals()).isTrue();
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("AccountingRegisters/РегистрБухгалтерии1.xml");
    checkBaseField(mdo, MDAccountingRegister.class, "РегистрБухгалтерии1",
      "e5930f2f-15d9-48a1-ac69-379ad990b02a");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 2,
      mdo.getMdoReference(), AttributeType.DIMENSION, AttributeType.RESOURCE);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
    var attribute = ((AbstractMDObjectComplex) mdo).getAttributes().get(0);
    assertThat(attribute.getIndexing()).isEqualTo(IndexingType.INDEX);

    var dimension = (Dimension) ((AbstractMDObjectComplex) mdo).getAttributes().get(0);
    assertThat(dimension.isDenyIncompleteValues()).isFalse();
    assertThat(dimension.isMaster()).isFalse();
    assertThat(dimension.isUseInTotals()).isTrue();
  }
}
