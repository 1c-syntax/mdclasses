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

import com.github._1c_syntax.bsl.mdo.children.RegisterDimension;
import com.github._1c_syntax.bsl.mdo.support.AttributeKind;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccumulationRegisterTest extends AbstractMDObjectTest<AccumulationRegister> {
  AccumulationRegisterTest() {
    super(AccumulationRegister.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "РегистрНакопления1,8ea07f36-d671-4649-bc7a-94daa939e77f,,,AccumulationRegister,РегистрНакопления,2,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("AccumulationRegisters/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.ACCUMULATION_REGISTER, argumentsAccessor);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "РегистрНакопления1,8ea07f36-d671-4649-bc7a-94daa939e77f,,Регистр накопления,AccumulationRegister,РегистрНакопления,3,0,1,1,1,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("AccumulationRegisters/" + name + "/" + name);
    mdoTest(mdo, MDOType.ACCUMULATION_REGISTER, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "Ресурс1", "a187a281-f5cd-4e1c-8f3f-37212a840339",
      List.of("passwordMode"));
    checkAttributeField(mdo.getAttributes().get(1),
      "Реквизит", "393bc58b-5bf0-429c-b7d8-1b94bd3bbdf9"
    );

    checkChildField(mdo.getForms().get(0),
      "ФормаСписка", "9873715b-7983-4ee5-b09a-764fb340f47b");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "90958c5b-069b-4c59-9aee-28d8c23bfb68");

    checkChildField(mdo.getCommands().get(0),
      "Команда", "1494e7c4-8428-4a41-8797-f4b6d0718dd0");

    var attribute = mdo.getAttributes().get(2);
    assertThat(attribute).isInstanceOf(RegisterDimension.class);
    assertThat(attribute.getKind()).isEqualTo(AttributeKind.CUSTOM);
    assertThat(attribute.getName()).isEqualTo("Измерение1");
    assertThat(attribute.getType()).isEqualTo(MDOType.ATTRIBUTE);
    assertThat(attribute.isPasswordMode()).isFalse();
    assertThat(attribute.getIndexing()).isEqualTo(IndexingType.DONT_INDEX);
    assertThat(attribute.getUuid()).isEqualTo("461cae93-fe90-4bbb-8f79-0963e2d39ec5");
    assertThat(attribute.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(attribute.getMetadataName()).isEqualTo("Dimension");
    assertThat(attribute.getSynonyms().getContent()).isEmpty();
    assertThat(((RegisterDimension) attribute).isDenyIncompleteValues()).isFalse();
    assertThat(((RegisterDimension) attribute).isUseInTotals()).isTrue();
    assertThat(((RegisterDimension) attribute).isMaster()).isFalse();
    assertThat(attribute.getMdoReference().getMdoRef())
      .isEqualTo("AccumulationRegister.РегистрНакопления1.Dimension.Измерение1");

  }
}