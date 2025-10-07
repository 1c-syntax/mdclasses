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
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalDataProcessorTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, ТестоваяВнешняяОбработка, false, _edt",
      "false, ТестоваяВнешняяОбработка, false"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdc = MDTestUtils.readExternalSourceWithSimpleTest(argumentsAccessor);
    assertThat(mdc).isInstanceOf(ExternalDataProcessor.class);

    var epf = (ExternalDataProcessor) mdc;

    assertThat(epf.getModules())
      .hasSize(1)
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));
    assertThat(epf.getModules().stream().filter(Module::isProtected)).isEmpty();

    assertThat(epf.getAllModules())
      .hasSize(2)
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));
    assertThat(epf.getAllModules().stream().filter(Module::isProtected)).isEmpty();

    assertThat(epf.getChildren())
      .hasSize(5)
      .allMatch(md -> md.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(epf.getPlainChildren())
      .hasSize(7)
      .allMatch(md -> md.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(epf.getForms()).hasSize(2);
    assertThat(epf.getForms().stream().filter(form -> !form.getData().isEmpty())).hasSize(1);
    assertThat(epf.getForms().stream().filter(form -> form.getData().isEmpty())).hasSize(1);
  }
}
