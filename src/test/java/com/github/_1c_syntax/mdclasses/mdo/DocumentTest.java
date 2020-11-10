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

class DocumentTest extends AbstractMDOTest {
  DocumentTest() {
    super(MDOType.DOCUMENT);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("Documents/Документ1/Документ1.mdo");
    checkBaseField(mdo, Document.class, "Документ1",
      "ce4fb46b-4af7-493e-9fcb-76ad8c4f8acd");
    checkForms(mdo, 3, "Document.Документ1", "ФормаДокумента", "ФормаСписка", "ФормаВыбора");
    checkTemplates(mdo);
    checkCommands(mdo, 2, "Document.Документ1", "Команда", "Команда2");
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 4, "Document.Документ1",
      AttributeType.ATTRIBUTE, AttributeType.TABULAR_SECTION);
    var tabularSection = (TabularSection) ((MDObjectComplex) mdo).getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION)
      .findFirst().get();
    checkAttributes(tabularSection.getAttributes(), 2,
      "Document.Документ1.TabularSection.ТабличнаяЧасть1", AttributeType.ATTRIBUTE);
    checkModules(((MDObjectBSL) mdo).getModules(), 2, "Documents/Документ1",
      ModuleType.ObjectModule, ModuleType.ManagerModule);
    checkRegisterRecords((Document) mdo);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("Documents/Документ1.xml");
    checkBaseField(mdo, Document.class, "Документ1",
      "ce4fb46b-4af7-493e-9fcb-76ad8c4f8acd");
    checkForms(mdo, 3, "Document.Документ1", "ФормаДокумента", "ФормаСписка", "ФормаВыбора");
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 4, "Document.Документ1",
      AttributeType.ATTRIBUTE, AttributeType.TABULAR_SECTION);
    var tabularSection = (TabularSection) ((MDObjectComplex) mdo).getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION)
      .findFirst().get();
    checkAttributes(tabularSection.getAttributes(), 2,
      "Document.Документ1.TabularSection.ТабличнаяЧасть1", AttributeType.ATTRIBUTE);
    assertThat(((MDObjectBSL) mdo).getModules()).isEmpty();
    checkRegisterRecords((Document) mdo);
  }

  private void checkRegisterRecords(Document mdo) {
    assertThat(mdo.getRegisterRecords())
      .hasSize(4)
      .anyMatch(value -> value.equals("AccumulationRegister.РегистрНакопления1"))
      .anyMatch(value -> value.equals("CalculationRegister.РегистрРасчета1"))
      .anyMatch(value -> value.equals("InformationRegister.РегистрСведений2"))
      .anyMatch(value -> value.equals("AccountingRegister.РегистрБухгалтерии1"))
    ;
  }

}
