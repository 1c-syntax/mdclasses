/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, CommonTemplates.Active",
      "false, mdclasses, CommonTemplates.Active",
      "true, mdclasses, CommonTemplates.HTML",
      "false, mdclasses, CommonTemplates.HTML",
      "true, mdclasses, CommonTemplates.ВнешняяКомпонента",
      "false, mdclasses, CommonTemplates.ВнешняяКомпонента",
      "true, mdclasses, CommonTemplates.ГеографическаяСхема",
      "false, mdclasses, CommonTemplates.ГеографическаяСхема",
      "true, mdclasses, CommonTemplates.ГрафическаяСхема",
      "false, mdclasses, CommonTemplates.ГрафическаяСхема",
      "true, mdclasses, CommonTemplates.ДвоичныеДанные",
      "false, mdclasses, CommonTemplates.ДвоичныеДанные",
      "true, mdclasses, CommonTemplates.МакетОформления",
      "false, mdclasses, CommonTemplates.МакетОформления",
      "true, mdclasses, CommonTemplates.СКД, _edt",
      "false, mdclasses, CommonTemplates.СКД",
      "true, mdclasses, CommonTemplates.ТабличныйДокумент",
      "false, mdclasses, CommonTemplates.ТабличныйДокумент",
      "true, mdclasses, CommonTemplates.ТекстовыйДокумент",
      "false, mdclasses, CommonTemplates.ТекстовыйДокумент",
      "true, ssl_3_1, CommonTemplates.СтруктураПодчиненности",
      "false, ssl_3_1, CommonTemplates.СтруктураПодчиненности"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
  }
}