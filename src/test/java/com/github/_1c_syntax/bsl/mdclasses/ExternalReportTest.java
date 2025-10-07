/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.storage.ManagedFormData;
import com.github._1c_syntax.bsl.mdo.support.FormType;
import com.github._1c_syntax.bsl.mdo.support.UsePurposes;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalReportTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, ТестовыйВнешнийОтчет, true, _edt",
      "false, ТестовыйВнешнийОтчет, true"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdc = MDTestUtils.readExternalSourceWithSimpleTest(argumentsAccessor);
    assertThat(mdc).isInstanceOf(ExternalReport.class);

    var erf = (ExternalReport) mdc;

    assertThat(erf.getModules())
      .hasSize(1)
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));
    assertThat(erf.getModules().stream().filter(Module::isProtected)).isEmpty();

    assertThat(erf.getAllModules())
      .hasSize(2)
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));
    assertThat(erf.getAllModules().stream().filter(Module::isProtected)).isEmpty();

    assertThat(erf.getChildren())
      .hasSize(8)
      .allMatch(md -> md.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(erf.getPlainChildren())
      .hasSize(10)
      .allMatch(md -> md.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(erf.getForms()).hasSize(5);
    assertThat(erf.getForms().stream().filter(form -> !form.getData().isEmpty())).hasSize(3);
    assertThat(erf.getForms().stream().filter(form -> form.getData().isEmpty())).hasSize(2);

    var form = erf.getForms().stream().filter(objForm -> objForm.getName().equals("ФормаОтчета"))
      .findFirst().get();
    assertThat(form.getUsePurposes())
      .hasSize(2)
      .contains(UsePurposes.MOBILE_PLATFORM_APPLICATION)
      .contains(UsePurposes.PLATFORM_APPLICATION);
    assertThat(form.getFormType()).isEqualTo(FormType.MANAGED);

    assertThat(form.getData().isEmpty()).isFalse();
    assertThat(form.getData()).isInstanceOf(ManagedFormData.class);
    assertThat(form.getData().getHandlers()).isEmpty();
    assertThat(form.getData().getItems()).isEmpty();
    assertThat(form.getData().getTitle()).isEqualTo(MultiLanguageString.EMPTY);
    assertThat(form.getData().getAttributes())
      .hasSize(1)
      .allMatch(attr -> attr.getId() == 1)
      .allMatch(attr -> attr.getName().equals("Отчет"));

    form = erf.getForms().stream().filter(objForm -> objForm.getName().equals("ФормаОтчета1"))
      .findFirst().get();
    assertThat(form.getUsePurposes())
      .hasSize(2)
      .contains(UsePurposes.MOBILE_PLATFORM_APPLICATION)
      .contains(UsePurposes.PLATFORM_APPLICATION);
    assertThat(form.getFormType()).isEqualTo(FormType.ORDINARY);

    assertThat(form.getData().isEmpty()).isTrue();
  }
}
