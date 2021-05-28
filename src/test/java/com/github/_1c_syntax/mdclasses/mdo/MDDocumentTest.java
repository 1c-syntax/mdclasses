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

import com.github._1c_syntax.mdclasses.mdo.attributes.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDDocumentTest extends AbstractMDOTest {
  MDDocumentTest() {
    super(MDOType.DOCUMENT);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("Documents/Документ1/Документ1.mdo");
    checkBaseField(mdo, MDDocument.class, "Документ1",
      "ce4fb46b-4af7-493e-9fcb-76ad8c4f8acd");
    checkForms(mdo, 3, "ФормаДокумента", "ФормаСписка", "ФормаВыбора");
    checkTemplates(mdo);
    checkCommands(mdo, 2, "Команда", "Команда2");
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 4, mdo.getMdoReference(),
      AttributeType.ATTRIBUTE, AttributeType.TABULAR_SECTION);
    var tabularSection = (TabularSection) ((AbstractMDObjectComplex) mdo).getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION)
      .findFirst().get();
    checkAttributes(tabularSection.getAttributes(), 2, tabularSection.getMdoReference(), AttributeType.ATTRIBUTE);
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 2, "Documents/Документ1",
      ModuleType.ObjectModule, ModuleType.ManagerModule);
    checkRegisterRecords((MDDocument) mdo);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("Documents/Документ1.xml");
    checkBaseField(mdo, MDDocument.class, "Документ1",
      "ce4fb46b-4af7-493e-9fcb-76ad8c4f8acd");
    checkForms(mdo, 3, "ФормаДокумента", "ФормаСписка", "ФормаВыбора");
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 4, mdo.getMdoReference(),
      AttributeType.ATTRIBUTE, AttributeType.TABULAR_SECTION);
    var tabularSection = (TabularSection) ((AbstractMDObjectComplex) mdo).getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION)
      .findFirst().get();
    checkAttributes(tabularSection.getAttributes(), 2, tabularSection.getMdoReference(), AttributeType.ATTRIBUTE);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
    checkRegisterRecords((MDDocument) mdo);
  }

  private void checkRegisterRecords(MDDocument mdo) {
    assertThat(mdo.getRegisterRecords())
      .hasSize(4)
      .anyMatch(value -> value.equals("AccumulationRegister.РегистрНакопления1"))
      .anyMatch(value -> value.equals("CalculationRegister.РегистрРасчета1"))
      .anyMatch(value -> value.equals("InformationRegister.РегистрСведений2"))
      .anyMatch(value -> value.equals("AccountingRegister.РегистрБухгалтерии1"))
    ;
  }

}
