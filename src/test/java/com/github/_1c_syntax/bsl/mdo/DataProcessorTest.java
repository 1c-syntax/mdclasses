/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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

import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DataProcessorTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, DataProcessors.Обработка1, _edt",
      "false, mdclasses, DataProcessors.Обработка1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(DataProcessor.class);

    var dataProcessor = (DataProcessor) mdo;
    assertThat(dataProcessor.getForms()).hasSize(2);

    var formData = dataProcessor.getForms().stream()
      .filter(form -> form.getName().equals("ЖурналРегистрации")).findFirst().get().getData();

    // FormOwner
    assertThat(dataProcessor.getDefaultFormMap()).hasSize(2);

    var formLink = dataProcessor.getDefaultFormLink(DefaultFormKind.DEFAULT_FORM);
    assertThat(formLink)
      .isEqualTo(dataProcessor.getDefaultFormMap().get(DefaultFormKind.DEFAULT_FORM));
    assertThat(formLink.isEmpty()).isFalse();

    var form = dataProcessor.getDefaultForm(DefaultFormKind.DEFAULT_FORM);
    assertThat(form).isPresent();
    assertThat(form.get().getName()).isEqualTo("Форма");

    var formByLink = dataProcessor.getFormByLink(formLink);
    assertThat(formByLink).isPresent();
    assertThat(formByLink.get().getName()).isEqualTo("Форма");

    assertThat(dataProcessor.getDefaultFormLink(DefaultFormKind.AUX_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(dataProcessor.getDefaultForm(DefaultFormKind.AUX_FORM)).isEmpty();

    assertThat(dataProcessor.getFormByLink(MdoReference.create("DataProcessor.Unknown.Form.Unknown"))).isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, DataProcessors.ЗагрузкаКурсовВалют, _edt",
      "false, ssl_3_1, DataProcessors.ЗагрузкаКурсовВалют"
    }
  )
  void testSSL(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(DataProcessor.class);
    var dataProcessor = (DataProcessor) mdo;

    // FormOwner
    assertThat(dataProcessor.getDefaultFormMap()).hasSize(2);

    var formLink = dataProcessor.getDefaultFormLink(DefaultFormKind.DEFAULT_FORM);
    assertThat(formLink)
      .isEqualTo(dataProcessor.getDefaultFormMap().get(DefaultFormKind.DEFAULT_FORM));
    assertThat(formLink.isEmpty()).isFalse();

    var form = dataProcessor.getDefaultForm(DefaultFormKind.DEFAULT_FORM);
    assertThat(form).isPresent();
    assertThat(form.get().getName()).isEqualTo("Форма");

    var formByLink = dataProcessor.getFormByLink(formLink);
    assertThat(formByLink).isPresent();
    assertThat(formByLink.get().getName()).isEqualTo("Форма");

    assertThat(dataProcessor.getDefaultFormLink(DefaultFormKind.AUX_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(dataProcessor.getDefaultForm(DefaultFormKind.AUX_FORM)).isEmpty();

    assertThat(dataProcessor.getFormByLink(MdoReference.create("DataProcessor.Unknown.Form.Unknown"))).isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_2, DataProcessors.ЗагрузкаКурсовВалют, _edt",
      "false, ssl_3_2, DataProcessors.ЗагрузкаКурсовВалют"
    }
  )
  void testSSL32(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(DataProcessor.class);
    var dataProcessor = (DataProcessor) mdo;

    // FormOwner
    assertThat(dataProcessor.getDefaultFormMap()).hasSize(2);

    var formLink = dataProcessor.getDefaultFormLink(DefaultFormKind.DEFAULT_FORM);
    assertThat(formLink)
      .isEqualTo(dataProcessor.getDefaultFormMap().get(DefaultFormKind.DEFAULT_FORM));
    assertThat(formLink.isEmpty()).isFalse();

    var form = dataProcessor.getDefaultForm(DefaultFormKind.DEFAULT_FORM);
    assertThat(form).isPresent();
    assertThat(form.get().getName()).isEqualTo("Форма");

    var formByLink = dataProcessor.getFormByLink(formLink);
    assertThat(formByLink).isPresent();
    assertThat(formByLink.get().getName()).isEqualTo("Форма");

    assertThat(dataProcessor.getDefaultFormLink(DefaultFormKind.AUX_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(dataProcessor.getDefaultForm(DefaultFormKind.AUX_FORM)).isEmpty();

    assertThat(dataProcessor.getFormByLink(MdoReference.create("DataProcessor.Unknown.Form.Unknown"))).isEmpty();
  }
}