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

class BusinessProcessTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, BusinessProcesses.БизнесПроцесс1, _edt",
      "false, mdclasses, BusinessProcesses.БизнесПроцесс1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);

    var businessProcess = (BusinessProcess) mdo;

    assertThat(businessProcess.getSynonym().isEmpty()).isTrue();
    assertThat(businessProcess.getSynonym().get("ru")).isEmpty();
    assertThat(businessProcess.getSynonym().get("en")).isEmpty();
    assertThat(businessProcess.getSynonym().getAny()).isEmpty();

    assertThat(businessProcess.getDescription()).isEqualTo("БизнесПроцесс1");
    assertThat(businessProcess.getDescription("ru")).isEqualTo("БизнесПроцесс1");
    assertThat(businessProcess.getDescription("en")).isEqualTo("БизнесПроцесс1");

    // FormOwner
    assertThat(businessProcess.getDefaultFormMap()).hasSize(6);

    // Для форм, которых нет в фикстуре
    assertThat(businessProcess.getDefaultFormLink(DefaultFormKind.OBJECT_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(businessProcess.getDefaultForm(DefaultFormKind.OBJECT_FORM)).isEmpty();
    assertThat(businessProcess.getDefaultFormLink(DefaultFormKind.AUX_OBJECT_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(businessProcess.getDefaultForm(DefaultFormKind.AUX_OBJECT_FORM)).isEmpty();
    assertThat(businessProcess.getDefaultFormLink(DefaultFormKind.FOLDER_FORM)).isEqualTo(MdoReference.EMPTY);

    // getFormByLink с несуществующей ссылкой
    assertThat(businessProcess.getFormByLink(MdoReference.create("BusinessProcess.Unknown.Form.Unknown"))).isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, BusinessProcesses.Задание, _edt",
      "false, ssl_3_1, BusinessProcesses.Задание",
      "true, ssl_3_2, BusinessProcesses.Задание, _edt",
      "false, ssl_3_2, BusinessProcesses.Задание"
    }
  )
  void testSSL_3_1(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);

    var businessProcess = (BusinessProcess) mdo;

    assertThat(businessProcess.getSynonym().isEmpty()).isFalse();
    assertThat(businessProcess.getSynonym().get("ru")).isEqualTo("Задание");
    assertThat(businessProcess.getSynonym().get("en")).isEmpty();
    assertThat(businessProcess.getSynonym().getAny()).isEqualTo("Задание");

    assertThat(businessProcess.getDescription()).isEqualTo("Задание");
    assertThat(businessProcess.getDescription("ru")).isEqualTo("Задание");
    assertThat(businessProcess.getDescription("en")).isEqualTo("Задание");
    assertThat(businessProcess.getDescription("")).isEqualTo("Задание");
    assertThat(businessProcess.getDescription("пыщь")).isEqualTo("Задание");

    // FormOwner
    assertThat(businessProcess.getDefaultFormMap()).hasSize(6);

    // Для форм, которые есть в фикстуре
    var formLink = businessProcess.getDefaultFormLink(DefaultFormKind.OBJECT_FORM);
    assertThat(formLink).isEqualTo(businessProcess.getDefaultFormMap().get(DefaultFormKind.OBJECT_FORM));
    assertThat(formLink.isEmpty()).isFalse();
    assertThat(businessProcess.getDefaultForm(DefaultFormKind.OBJECT_FORM)).isPresent();
    assertThat(businessProcess.getFormByLink(formLink)).isPresent();

    // Для форм, которых нет в фикстуре
    assertThat(businessProcess.getDefaultFormLink(DefaultFormKind.AUX_OBJECT_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(businessProcess.getDefaultForm(DefaultFormKind.AUX_OBJECT_FORM)).isEmpty();
    assertThat(businessProcess.getDefaultFormLink(DefaultFormKind.FOLDER_FORM)).isEqualTo(MdoReference.EMPTY);

    // getFormByLink с несуществующей ссылкой
    assertThat(businessProcess.getFormByLink(MdoReference.create("BusinessProcess.Unknown.Form.Unknown"))).isEmpty();
  }
}
