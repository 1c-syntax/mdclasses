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
import com.github._1c_syntax.mdclasses.mdo.support.FormType;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDCatalogTest extends AbstractMDOTest {
  MDCatalogTest() {
    super(MDOType.CATALOG);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("Catalogs/Справочник1/Справочник1.mdo");
    checkBaseField(mdo, MDCatalog.class, "Справочник1",
      "eeef463d-d5e7-42f2-ae53-10279661f59d");
    checkForms(mdo, 3, "Catalog.Справочник1", "ФормаЭлемента", "ФормаСписка", "ФормаВыбора");
    checkTemplates(mdo, 1, "Catalog.Справочник1", "Макет");
    checkCommands(mdo, 1, "Catalog.Справочник1", "Команда1");
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 4, "Catalog.Справочник1",
      AttributeType.ATTRIBUTE, AttributeType.TABULAR_SECTION);
    var tabularSection = (TabularSection) ((AbstractMDObjectComplex) mdo).getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION)
      .findFirst().get();
    checkAttributes(tabularSection.getAttributes(), 2,
      "Catalog.Справочник1.TabularSection.ТабличнаяЧасть1", AttributeType.ATTRIBUTE);
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 2, "Catalogs/Справочник1",
      ModuleType.ObjectModule, ModuleType.ManagerModule);

  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("Catalogs/Справочник1.xml");
    checkBaseField(mdo, MDCatalog.class, "Справочник1",
      "eeef463d-d5e7-42f2-ae53-10279661f59d");
    checkForms(mdo, 4, "Catalog.Справочник1", "ФормаЭлемента", "ФормаСписка", "ФормаВыбора",
      "ФормаЭлементаОбычная");
    checkTemplates(mdo, 2, "Catalog.Справочник1", "Макет", "Макет2");
    checkCommands(mdo, 1, "Catalog.Справочник1", "Команда1");
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 4, "Catalog.Справочник1",
      AttributeType.ATTRIBUTE, AttributeType.TABULAR_SECTION);
    var tabularSection = (TabularSection) ((AbstractMDObjectComplex) mdo).getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION)
      .findFirst().get();
    checkAttributes(tabularSection.getAttributes(), 2,
      "Catalog.Справочник1.TabularSection.ТабличнаяЧасть1", AttributeType.ATTRIBUTE);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();

    var catalog = (MDCatalog) mdo;
    assertThat(catalog.getForms())
      .anyMatch(form -> form.getFormType() == FormType.ORDINARY);
  }

}
