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
package com.github._1c_syntax.bsl.mdo.storages;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdo.CommonTemplate;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateDataTest {

  @ParameterizedTest()
  @CsvSource(
    {
      "designer/mdclasses_skd",
//      "EDT,src/test/resources/metadata/skd/edt",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var pack = argumentsAccessor.getString(0);
    var mdc = MDTestUtils.getMDClass("src/test/resources/ext/" + pack + "/src/cf", false);

    assertThat(mdc).isInstanceOf(Configuration.class);
    checkTemplates((Configuration) mdc);
  }

  private void checkTemplates(Configuration configuration) {
    var commonTemplates = configuration.getCommonTemplates();
    assertThat(commonTemplates).hasSize(8);

    checkCommonTemplate(commonTemplates);

    var templates = configuration.getPlainChildren().stream()
      .filter(ObjectTemplate.class::isInstance)
      .map(ObjectTemplate.class::cast)
      .collect(Collectors.toList());
    assertThat(templates).hasSize(4);

    checkTemplate(templates);
  }

  private void checkCommonTemplate(List<CommonTemplate> commonTemplates) {
    var template = commonTemplates.stream()
      .filter(commonTemplate -> commonTemplate.getName().equals("МакетСКД"))
      .findAny()
      .get();

    assertThat(template.getTemplateType()).isEqualTo(TemplateType.DATA_COMPOSITION_SCHEME);
    assertThat(template.getTemplateData()).isNotNull();

    var templateData = template.getTemplateData();
    assertThat(templateData.isEmpty()).isFalse();
  }

  private void checkTemplate(List<ObjectTemplate> templates) {
    var template = templates.stream()
      .filter(objectTemplate -> objectTemplate.getName().equals("СКД"))
      .findAny()
      .get();

    assertThat(template.getTemplateType()).isEqualTo(TemplateType.DATA_COMPOSITION_SCHEME);
    assertThat(template.getTemplateData()).isNotNull();

    var templateData = template.getTemplateData();
    assertThat(templateData.isEmpty()).isFalse();
  }
}
