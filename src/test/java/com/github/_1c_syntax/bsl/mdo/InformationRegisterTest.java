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

import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class InformationRegisterTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, InformationRegisters.РегистрСведений1, _edt",
      "false, mdclasses, InformationRegisters.РегистрСведений1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(InformationRegister.class);
    var ir = (InformationRegister) mdo;

    // FormOwner
    assertThat(ir.getDefaultFormMap()).hasSize(4);

    // Для форм, которых нет в фикстуре
    assertThat(ir.getDefaultFormLink(DefaultFormKind.RECORD_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(ir.getDefaultForm(DefaultFormKind.RECORD_FORM)).isEmpty();
    assertThat(ir.getDefaultFormLink(DefaultFormKind.AUX_RECORD_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(ir.getDefaultForm(DefaultFormKind.AUX_RECORD_FORM)).isEmpty();
    assertThat(ir.getDefaultFormLink(DefaultFormKind.LIST_FORM)).isEqualTo(MdoReference.EMPTY);

    // getFormByLink с несуществующей ссылкой
    assertThat(ir.getFormByLink(MdoReference.create("InformationRegister.Unknown.Form.Unknown"))).isEmpty();
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, InformationRegisters.ЭлектронныеПодписи, _edt",
    "false, ssl_3_1, InformationRegisters.ЭлектронныеПодписи",
    "true, ssl_3_2, InformationRegisters.ЭлектронныеПодписи, _edt",
    "false, ssl_3_2, InformationRegisters.ЭлектронныеПодписи"
  })
  void testSSL(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(InformationRegister.class);
    var ir = (InformationRegister) mdo;

    // FormOwner
    assertThat(ir.getDefaultFormMap()).hasSize(4);

    // Для форм, которых нет в фикстуре
    assertThat(ir.getDefaultFormLink(DefaultFormKind.RECORD_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(ir.getDefaultForm(DefaultFormKind.RECORD_FORM)).isEmpty();
    assertThat(ir.getDefaultFormLink(DefaultFormKind.AUX_RECORD_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(ir.getDefaultForm(DefaultFormKind.AUX_RECORD_FORM)).isEmpty();
    assertThat(ir.getDefaultFormLink(DefaultFormKind.LIST_FORM)).isEqualTo(MdoReference.EMPTY);

    // getFormByLink с несуществующей ссылкой
    assertThat(ir.getFormByLink(MdoReference.create("InformationRegister.Unknown.Form.Unknown"))).isEmpty();
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, InformationRegisters.СклоненияПредставленийОбъектов, _edt",
    "false, ssl_3_1, InformationRegisters.СклоненияПредставленийОбъектов",
    "true, ssl_3_2, InformationRegisters.СклоненияПредставленийОбъектов, _edt",
    "false, ssl_3_2, InformationRegisters.СклоненияПредставленийОбъектов"
  })
  void testСклонения(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(InformationRegister.class);
    var ir = (InformationRegister) mdo;

    // FormOwner
    assertThat(ir.getDefaultFormMap()).hasSize(4);

    // Для форм, которые есть в фикстуре
    var formLink = ir.getDefaultFormLink(DefaultFormKind.RECORD_FORM);
    assertThat(formLink).isEqualTo(ir.getDefaultFormMap().get(DefaultFormKind.RECORD_FORM));
    assertThat(formLink.isEmpty()).isFalse();
    assertThat(ir.getDefaultForm(DefaultFormKind.RECORD_FORM)).isPresent();
    assertThat(ir.getFormByLink(formLink)).isPresent();

    // Для форм, которых нет в фикстуре
    assertThat(ir.getDefaultFormLink(DefaultFormKind.AUX_RECORD_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(ir.getDefaultForm(DefaultFormKind.AUX_RECORD_FORM)).isEmpty();
    assertThat(ir.getDefaultFormLink(DefaultFormKind.LIST_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(ir.getDefaultForm(DefaultFormKind.LIST_FORM)).isEmpty();

    // getFormByLink с несуществующей ссылкой
    assertThat(ir.getFormByLink(MdoReference.create("InformationRegister.Unknown.Form.Unknown"))).isEmpty();
  }
}