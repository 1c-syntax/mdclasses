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

import static org.assertj.core.api.Assertions.assertThat;

class MDXdtoPackageTest extends AbstractMDOTest {
  MDXdtoPackageTest() {
    super(MDOType.XDTO_PACKAGE);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("XDTOPackages/ПакетXDTO1/ПакетXDTO1.mdo");
    checkBaseField(mdo, MDXdtoPackage.class, "ПакетXDTO1",
      "b8a93cce-56e4-4507-b281-5c525a466a0f");
    checkNoChildren(mdo);
    checkNoModules(mdo);

    var xdto = (MDXdtoPackage) mdo;
    assertThat(xdto.getNamespace()).isEqualTo("http://v8.1c.ru/edi/edi_stnd/EnterpriseData/1.8");
    assertThat(xdto.getData()).isNotNull();
    assertThat(xdto.getData().getTargetNamespace()).isEqualTo("http://v8.1c.ru/edi/edi_stnd/EnterpriseData/1.8");
    assertThat(xdto.getData().getImports()).hasSize(2)
      .anyMatch(xdtoImport -> "http://www.1c.ru/SSL/Exchange/Message".equals(xdtoImport.getNamespace()))
      .anyMatch(xdtoImport -> "http://www.1c.ru/SSL/Exchange/Message2".equals(xdtoImport.getNamespace()));

    assertThat(xdto.getData().getValueTypes()).hasSize(278)
      .anyMatch(xdtoValueType -> xdtoValueType.getName().equals("ТипКоличество"))
      .anyMatch(xdtoValueType -> xdtoValueType.getBase().equals("xs:decimal"))
      .anyMatch(xdtoValueType -> xdtoValueType.getEnumerations().size() == 1)
      .anyMatch(xdtoValueType -> xdtoValueType.getVariety().equals("Atomic"))
    ;

    assertThat(xdto.getData().getProperties()).hasSize(1);
    var xdtoProperty = xdto.getData().getProperties().get(0);
    assertThat(xdtoProperty.getName()).isEqualTo("performance");
    assertThat(xdtoProperty.getType()).isEqualTo("d2p1:Performance");
    assertThat(xdtoProperty.getForm()).isEqualTo("Attribute");

    assertThat(xdto.getData().getObjectTypes()).hasSize(737)
      .anyMatch(xdtoObjectType -> xdtoObjectType.getName().equals("КлючевыеСвойстваМаркиНоменклатуры"))
      .anyMatch(xdtoObjectType -> xdtoObjectType.getBase().equals("d2p1:Object"))
      .anyMatch(xdtoValueType -> xdtoValueType.getProperties().size() == 5)
    ;

    var example = xdto.getData().getObjectTypes().get(732);
    assertThat(example.getBase()).isEmpty();
    assertThat(example.getName()).isEqualTo("КлючевыеСвойстваПринадлежностьПрайсЛистаКонтрагенту");
    assertThat(example.getProperties()).hasSize(4);

    var exampleProperty = example.getProperties().get(3);
    assertThat(exampleProperty.getName()).isEqualTo("status");
    assertThat(exampleProperty.getType()).isEqualTo("xs:int");
    assertThat(exampleProperty.getLowerBound()).isEqualTo(1);
    assertThat(exampleProperty.getUpperBound()).isEqualTo(-1);
    assertThat(exampleProperty.getForm()).isEmpty();
    assertThat(exampleProperty.isNillable()).isTrue();
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("XDTOPackages/ПакетXDTO1.xml");
    checkBaseField(mdo, MDXdtoPackage.class, "ПакетXDTO1",
      "b8a93cce-56e4-4507-b281-5c525a466a0f");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    var xdto = (MDXdtoPackage) mdo;
    assertThat(xdto.getNamespace()).isEqualTo("http://v8.1c.ru/edi/edi_stnd/EnterpriseData/1.8");
    assertThat(xdto.getData()).isNotNull();
    assertThat(xdto.getData().getTargetNamespace()).isEqualTo("http://v8.1c.ru/edi/edi_stnd/EnterpriseData/1.8");
    assertThat(xdto.getData().getImports()).hasSize(1)
      .anyMatch(xdtoImport -> "http://www.1c.ru/SSL/Exchange/Message".equals(xdtoImport.getNamespace()));

    assertThat(xdto.getData().getValueTypes()).hasSize(278)
      .anyMatch(xdtoValueType -> xdtoValueType.getName().equals("ТипКоличество"))
      .anyMatch(xdtoValueType -> xdtoValueType.getBase().equals("xs:decimal"))
      .anyMatch(xdtoValueType -> xdtoValueType.getEnumerations().size() == 1)
      .anyMatch(xdtoValueType -> xdtoValueType.getVariety().equals("Atomic"))
    ;

    assertThat(xdto.getData().getProperties()).isEmpty();

    assertThat(xdto.getData().getObjectTypes()).hasSize(737)
      .anyMatch(xdtoObjectType -> xdtoObjectType.getName().equals("КлючевыеСвойстваМаркиНоменклатуры"))
      .anyMatch(xdtoObjectType -> xdtoObjectType.getBase().equals("d2p1:Object"))
      .anyMatch(xdtoValueType -> xdtoValueType.getProperties().size() == 5)
    ;

    var example = xdto.getData().getObjectTypes().get(732);
    assertThat(example.getBase()).isEmpty();
    assertThat(example.getName()).isEqualTo("КлючевыеСвойстваПринадлежностьПрайсЛистаКонтрагенту");
    assertThat(example.getProperties()).hasSize(3);

    var exampleProperty = xdto.getData().getObjectTypes().get(40).getProperties().get(0);
    assertThat(exampleProperty.getName()).isEqualTo("Строка");
    assertThat(exampleProperty.getType()).isEqualTo("d3p1:АвизоМПЗДанныеПоСФ.Строка");
    assertThat(exampleProperty.getLowerBound()).isZero();
    assertThat(exampleProperty.getUpperBound()).isEqualTo(-1);
    assertThat(exampleProperty.getForm()).isEmpty();
    assertThat(exampleProperty.isNillable()).isFalse();
  }
}
