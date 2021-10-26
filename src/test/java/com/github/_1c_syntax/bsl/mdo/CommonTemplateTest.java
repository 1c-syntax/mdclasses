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
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CommonTemplateTest extends AbstractMDObjectTest<CommonTemplate> {
  CommonTemplateTest() {
    super(CommonTemplate.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "original, CommonTemplate.ГрафическаяСхема",
      "original, CommonTemplate.Active",
      "original, CommonTemplate.ГеографическаяСхема",
      "original, CommonTemplate.ВнешняяКомпонента",
      "original, CommonTemplate.ТекстовыйДокумент",
      "original, CommonTemplate.СКД",
      "original, CommonTemplate.МакетОформления",
      "original, CommonTemplate.ДвоичныеДанные",
      "original, CommonTemplate.HTML",
      "original, CommonTemplate.ТабличныйДокумент",
      "original_3_18, CommonTemplate.ГрафическаяСхема",
      "original_3_18, CommonTemplate.Active",
      "original_3_18, CommonTemplate.ГеографическаяСхема",
      "original_3_18, CommonTemplate.ВнешняяКомпонента",
      "original_3_18, CommonTemplate.ТекстовыйДокумент",
      "original_3_18, CommonTemplate.СКД",
      "original_3_18, CommonTemplate.МакетОформления",
      "original_3_18, CommonTemplate.ДвоичныеДанные",
      "original_3_18, CommonTemplate.HTML",
      "original_3_18, CommonTemplate.ТабличныйДокумент"
//      "EDT, AccumulationRegister.Бот1",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }

}
