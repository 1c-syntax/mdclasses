/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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

import static org.assertj.core.api.Assertions.assertThat;

class DocumentTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, Documents.Анкета, _edt",
      "false, ssl_3_1, Documents.Анкета"
    }
  )
  void testSSL(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, Documents.Документ1, _edt",
      "false, mdclasses, Documents.Документ1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);

    var doc = (Document) mdo;
    assertThat(doc.getModules().stream().filter(Module::isProtected)).isEmpty();
    assertThat(doc.getAllModules().stream().filter(Module::isProtected)).isEmpty();

    assertThat(doc.getForms().stream().filter(form -> !form.getData().isEmpty())).hasSize(3);

    var formData = doc.getForms().stream().filter(form -> form.getName().equals("ФормаДокумента"))
      .findFirst().get().getData();

    assertThat(formData.getAttributes()).hasSize(1);
    assertThat(formData.getItems())
      .hasSize(6)
      .anyMatch(item -> item.getName().equals("Реквизит1"))
      .anyMatch(item -> item.getId() == 13);

    assertThat(formData.getHandlers())
      .hasSize(1)
      .allMatch(formHandler -> formHandler.event().equals("NewWriteProcessing"));

    assertThat(formData.getPlainItems())
      .hasSize(9)
      .anyMatch(item -> item.getName().equals("ТабличнаяЧасть1НомерСтроки"))
      .anyMatch(item -> item.getId() == 35)
      .anyMatch(item -> item.getType().equals("InputField"))
      .anyMatch(item -> item.getDataPath().getSegments().startsWith("~"));
  }
}
