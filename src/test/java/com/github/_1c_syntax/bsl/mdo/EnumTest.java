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
import com.github._1c_syntax.bsl.types.ScriptVariant;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class EnumTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, Enums.Перечисление1, _edt",
      "false, mdclasses, Enums.Перечисление1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);

    var mdoRef = MdoReference.create(argumentsAccessor.getString(2));
    var mdoRefString = mdoRef.getMdoRef();
    var mdoRefStringRu = mdoRef.getMdoRefRu();

    assertThat(mdo.getMdoReference()).isEqualTo(mdoRef);
    assertThat(mdo.getMdoRef(ScriptVariant.ENGLISH)).isEqualTo(mdoRefString);
    assertThat(mdo.getMdoRef(ScriptVariant.UNKNOWN)).isEqualTo(mdoRefStringRu);
    assertThat(mdo.getMdoRef(ScriptVariant.RUSSIAN)).isEqualTo(mdoRefStringRu);

    assertThat(mdo.getMdoRef()).isEqualTo(mdo.getMdoRef(ScriptVariant.ENGLISH));

    // FormOwner
    assertThat(mdo).isInstanceOf(Enum.class);
    var anEnum = (Enum) mdo;
    assertThat(anEnum.getDefaultFormMap()).hasSize(4);

    assertThat(anEnum.getDefaultFormLink(DefaultFormKind.LIST_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(anEnum.getDefaultForm(DefaultFormKind.LIST_FORM)).isEmpty();
    assertThat(anEnum.getDefaultForm(DefaultFormKind.CHOICE_FORM)).isEmpty();
    assertThat(anEnum.getDefaultFormLink(DefaultFormKind.AUX_LIST_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(anEnum.getDefaultForm(DefaultFormKind.AUX_LIST_FORM)).isEmpty();
    assertThat(anEnum.getDefaultForm(DefaultFormKind.AUX_CHOICE_FORM)).isEmpty();

    assertThat(anEnum.getFormByLink(MdoReference.create("Enum.Unknown.Form.Unknown"))).isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, Enums.СтатусыОбработчиковОбновления, _edt",
      "false, ssl_3_1, Enums.СтатусыОбработчиковОбновления"
    }
  )
  void testSSL(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Enum.class);
    var anEnum = (Enum) mdo;

    // FormOwner
    assertThat(anEnum.getDefaultFormMap()).hasSize(4);

    assertThat(anEnum.getDefaultFormLink(DefaultFormKind.LIST_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(anEnum.getDefaultForm(DefaultFormKind.LIST_FORM)).isEmpty();
    assertThat(anEnum.getDefaultForm(DefaultFormKind.CHOICE_FORM)).isEmpty();
    assertThat(anEnum.getDefaultFormLink(DefaultFormKind.AUX_LIST_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(anEnum.getDefaultForm(DefaultFormKind.AUX_LIST_FORM)).isEmpty();
    assertThat(anEnum.getDefaultForm(DefaultFormKind.AUX_CHOICE_FORM)).isEmpty();

    assertThat(anEnum.getFormByLink(MdoReference.create("Enum.Unknown.Form.Unknown"))).isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_2, Enums.СтатусыОбработчиковОбновления, _edt",
      "false, ssl_3_2, Enums.СтатусыОбработчиковОбновления"
    }
  )
  void testSSL32(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Enum.class);
    var anEnum = (Enum) mdo;

    // FormOwner
    assertThat(anEnum.getDefaultFormMap()).hasSize(4);

    assertThat(anEnum.getDefaultFormLink(DefaultFormKind.LIST_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(anEnum.getDefaultForm(DefaultFormKind.LIST_FORM)).isEmpty();
    assertThat(anEnum.getDefaultForm(DefaultFormKind.CHOICE_FORM)).isEmpty();
    assertThat(anEnum.getDefaultFormLink(DefaultFormKind.AUX_LIST_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(anEnum.getDefaultForm(DefaultFormKind.AUX_LIST_FORM)).isEmpty();
    assertThat(anEnum.getDefaultForm(DefaultFormKind.AUX_CHOICE_FORM)).isEmpty();

    assertThat(anEnum.getFormByLink(MdoReference.create("Enum.Unknown.Form.Unknown"))).isEmpty();
  }
}