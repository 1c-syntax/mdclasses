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

import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

class CommonTemplateTest {

  @ParameterizedTest()
  @CsvSource(
    {
      "designer/mdclasses, CommonTemplate.ГрафическаяСхема",
      "designer/mdclasses, CommonTemplate.Active",
      "designer/mdclasses, CommonTemplate.ГеографическаяСхема",
      "designer/mdclasses, CommonTemplate.ВнешняяКомпонента",
      "designer/mdclasses, CommonTemplate.ТекстовыйДокумент",
      "designer/mdclasses, CommonTemplate.СКД",
      "designer/mdclasses, CommonTemplate.МакетОформления",
      "designer/mdclasses, CommonTemplate.ДвоичныеДанные",
      "designer/mdclasses, CommonTemplate.HTML",
      "designer/mdclasses, CommonTemplate.ТабличныйДокумент",
      "designer/mdclasses_3_18, CommonTemplate.ГрафическаяСхема",
      "designer/mdclasses_3_18, CommonTemplate.Active",
      "designer/mdclasses_3_18, CommonTemplate.ГеографическаяСхема",
      "designer/mdclasses_3_18, CommonTemplate.ВнешняяКомпонента",
      "designer/mdclasses_3_18, CommonTemplate.ТекстовыйДокумент",
      "designer/mdclasses_3_18, CommonTemplate.СКД",
      "designer/mdclasses_3_18, CommonTemplate.МакетОформления",
      "designer/mdclasses_3_18, CommonTemplate.ДвоичныеДанные",
      "designer/mdclasses_3_18, CommonTemplate.HTML",
      "designer/mdclasses_3_18, CommonTemplate.ТабличныйДокумент",
      "designer/ssl_3_1, CommonTemplate.КомпонентаTWAIN"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }

}
