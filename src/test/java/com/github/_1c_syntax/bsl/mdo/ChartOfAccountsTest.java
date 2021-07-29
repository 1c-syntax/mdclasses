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

import com.github._1c_syntax.bsl.mdo.children.AccountingFlag;
import com.github._1c_syntax.bsl.mdo.children.ExtDimensionAccountingFlag;
import com.github._1c_syntax.bsl.mdo.support.AttributeKind;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ChartOfAccountsTest extends AbstractMDObjectTest<ChartOfAccounts> {
  ChartOfAccountsTest() {
    super(ChartOfAccounts.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "ПланСчетов1,2766f353-abd2-4e7f-9a95-53f05c83f5d4,,,ChartOfAccounts,ПланСчетов,2,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("ChartsOfAccounts/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.CHART_OF_ACCOUNTS, argumentsAccessor);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "ПланСчетов1,2766f353-abd2-4e7f-9a95-53f05c83f5d4,,,ChartOfAccounts,ПланСчетов,2,1,1,1,1,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("ChartsOfAccounts/" + name + "/" + name);
    mdoTest(mdo, MDOType.CHART_OF_ACCOUNTS, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "ПризнакУчета", "957a5154-1e63-49e1-8e99-3893490da6f6", List.of("passwordMode"));

    checkChildField(mdo.getForms().get(0),
      "ФормаЭлемента", "10f1186c-db42-4be7-8ba7-777f10807abb");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "21a14299-195f-448c-9f47-cbddf8a63352");

    checkChildField(mdo.getCommands().get(0),
      "Команда", "fd7f03cd-37b0-4914-b4d9-e0bcf51c4d0d");

    checkChildField(mdo.getTabularSections().get(0),
      "ТабличнаяЧасть", "4926546a-32c1-4972-89a7-91f0deb2b6de");

    checkAttribute(mdo.getAttributes().get(1), ExtDimensionAccountingFlag.class,
      "4f04d72a-e3e9-4e9b-96da-7d0e1ad989e6", "ExtDimensionAccountingFlag");
    checkAttribute(mdo.getAttributes().get(0), AccountingFlag.class,
      "957a5154-1e63-49e1-8e99-3893490da6f6", "AccountingFlag");
  }

  private void checkAttribute(Attribute attribute, Class<?> clazz, String uuid, String metadataName) {
    assertThat(attribute).isInstanceOf(clazz);
    assertThat(attribute.getKind()).isEqualTo(AttributeKind.CUSTOM);
    assertThat(attribute.getName()).isEqualTo("ПризнакУчета");
    assertThat(attribute.getType()).isEqualTo(MDOType.ATTRIBUTE);
    assertThat(attribute.isPasswordMode()).isFalse();
    assertThat(attribute.getIndexing()).isEqualTo(IndexingType.DONT_INDEX);
    assertThat(attribute.getUuid()).isEqualTo(uuid);
    assertThat(attribute.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(attribute.getMetadataName()).isEqualTo(metadataName);
    assertThat(attribute.getSynonyms().getContent())
      .hasSize(1)
      .contains(Map.entry("ru", "Признак учета"));
    assertThat(attribute.getMdoReference().getMdoRef())
      .isEqualTo(String.format("ChartOfAccounts.ПланСчетов1.%s.ПризнакУчета", metadataName));
  }
}
