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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.children.RegisterResource;
import com.github._1c_syntax.bsl.mdo.support.AttributeKind;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class InformationRegisterTest extends AbstractMDObjectTest<InformationRegister> {
  InformationRegisterTest() {
    super(InformationRegister.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "original, InformationRegister.РегистрСведений1",
      "original, InformationRegister.РегистрСведений2",
      "original_3_18, InformationRegister.РегистрСведений1"
//      "EDT, AccumulationRegister.Бот1",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }

//  @ParameterizedTest(name = "EDT {index}: {0}")
//  @CsvSource(
//    {
//      "РегистрСведений1,184d9d78-9523-4cfa-9542-a7ba72efe4dd,,,InformationRegister,РегистрСведений,3,0,1,1,1,1"
//    }
//  )
//  void testEdt(ArgumentsAccessor argumentsAccessor) {
//    var name = argumentsAccessor.getString(0);
//    var mdo = getMDObjectEDT("InformationRegisters/" + name + "/" + name);
//    mdoTest(mdo, MDOType.INFORMATION_REGISTER, argumentsAccessor);
//
//    checkAttributeField(mdo.getAttributes().get(0),
//      "Ресурс", "a4cd4d71-4264-4204-a14f-d611ba1aa146",
//      List.of("passwordMode"));
//    checkAttributeField(mdo.getAttributes().get(1),
//      "Реквизит", "38052b0a-d304-4703-86d4-5c9171bec887"
//    );
//
//    checkChildField(mdo.getForms().get(0),
//      "ФормаЗаписи", "f50bacc1-2499-4a1a-a978-cbc5b427ceb0");
//
//    var template = mdo.getTemplates().get(0);
//    checkChildField(template,
//      "Макет", "eac550d5-f69a-4ae7-bd23-b9966f3dfcc1");
//    assertThat(template.getType()).isEqualTo(MDOType.TEMPLATE);
//    assertThat(template.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
//    assertThat(template.getSynonym().getContent())
//      .hasSize(1)
//      .contains(Map.entry("ru", "Макет"));
//    assertThat(template.getMdoReference().getMdoRef()).isEqualTo("InformationRegister.РегистрСведений1.Template.Макет");
//    assertThat(template.getTemplateDataPath().toString()).endsWith("Template.dcs");
//
//    checkChildField(mdo.getCommands().get(0),
//      "Команда", "5731309d-82b0-4c06-833a-8bf84f1fde4b");
//
//    var attribute = mdo.getAttributes().get(0);
//    assertThat(attribute).isInstanceOf(RegisterResource.class);
//    assertThat(attribute.getKind()).isEqualTo(AttributeKind.CUSTOM);
//    assertThat(attribute.getName()).isEqualTo("Ресурс");
//    assertThat(attribute.getType()).isEqualTo(MDOType.ATTRIBUTE);
//    assertThat(attribute.isPasswordMode()).isFalse();
//    assertThat(attribute.getIndexing()).isEqualTo(IndexingType.DONT_INDEX);
//    assertThat(attribute.getUuid()).isEqualTo("a4cd4d71-4264-4204-a14f-d611ba1aa146");
//    assertThat(attribute.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
//    assertThat(attribute.getSynonym().getContent())
//      .hasSize(1)
//      .contains(Map.entry("ru", "Ресурс"));
//    assertThat(attribute.getMdoReference().getMdoRef()).isEqualTo("InformationRegister.РегистрСведений1.Resource.Ресурс");
//
//  }
}
