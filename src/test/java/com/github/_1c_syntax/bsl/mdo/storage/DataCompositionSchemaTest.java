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
package com.github._1c_syntax.bsl.mdo.storage;

import com.github._1c_syntax.bsl.mdo.CommonTemplate;
import com.github._1c_syntax.bsl.mdo.TemplateOwner;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class DataCompositionSchemaTest {

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void testSettingsVariantWithStringPresentation(boolean formatEDT) {
    var mdo = Fixtures.get("mdclasses", "CommonTemplates.СКД", formatEDT);
    assertThat(mdo).isInstanceOf(CommonTemplate.class);

    var data = ((CommonTemplate) mdo).getData();
    assertThat(data).isInstanceOf(DataCompositionSchema.class);

    var variants = ((DataCompositionSchema) data).getSettingsVariants();
    assertThat(variants)
      .singleElement()
      .satisfies(variant -> {
        assertThat(variant.name()).isEqualTo("Основной");
        assertThat(variant.presentation().getAny()).isEqualTo("Основной");
      });
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void testSettingsVariantsWithLocalStringPresentation(boolean formatEDT) {
    var mdo = Fixtures.get("ssl_3_2", "Reports.СведенияОПользователях", formatEDT);
    assertThat(mdo).isInstanceOf(TemplateOwner.class);

    var template = ((TemplateOwner) mdo).getTemplates().stream()
      .filter(item -> "ОсновнаяСхемаКомпоновкиДанных".equals(item.getName()))
      .findFirst()
      .orElseThrow();

    var data = template.getData();
    assertThat(data).isInstanceOf(DataCompositionSchema.class);

    var variants = ((DataCompositionSchema) data).getSettingsVariants();
    assertThat(variants)
      .extracting(DataCompositionSchema.SettingsVariant::name)
      .containsExactly(
        "СведенияОПользователяхИВнешнихПользователях",
        "СведенияОПользователях",
        "СведенияОВнешнихПользователях"
      );

    assertThat(variants)
      .extracting(variant -> variant.presentation().get("ru"))
      .containsExactly(
        "Сведения о пользователях и внешних пользователях",
        "Сведения о пользователях",
        "Сведения о внешних пользователях"
      );
  }
}
