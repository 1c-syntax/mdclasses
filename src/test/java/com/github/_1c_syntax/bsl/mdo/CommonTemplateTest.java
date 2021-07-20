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

import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CommonTemplateTest extends AbstractMDObjectTest<CommonTemplate> {
  CommonTemplateTest() {
    super(CommonTemplate.class);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "ГрафическаяСхема,4333f027-4fc2-40a0-ae7d-48fbf0cea50b,,Графическая схема,CommonTemplate,ОбщийМакет,0,0,0,0,0,0"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("CommonTemplates/" + name + "/" + name);
    mdoTest(mdo, MDOType.COMMON_TEMPLATE, argumentsAccessor);
    //    assertThat(mdo.getTemplateData());
    assertThat(mdo.getTemplateType()).isEqualTo(TemplateType.GRAPHICAL_SCHEME);
//    assertThat(mdo.getTemplateDataPath());
  }
}
