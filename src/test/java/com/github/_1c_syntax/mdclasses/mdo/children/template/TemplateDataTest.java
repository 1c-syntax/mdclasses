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
package com.github._1c_syntax.mdclasses.mdo.children.template;

import com.github._1c_syntax.bsl.mdo.data_storage.TemplateData;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectComplex;
import com.github._1c_syntax.mdclasses.mdo.MDConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDOTemplate;
import com.github._1c_syntax.mdclasses.mdo.children.Template;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import io.vavr.control.Either;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateDataTest {

  @ParameterizedTest(name = "{index}: {0}")
  @CsvSource(
    {
      "DESIGNER,src/test/resources/metadata/skd/original",
      "EDT,src/test/resources/metadata/skd/edt",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    final var src = Path.of(argumentsAccessor.getString(1));
    MDOFactory.readMDOConfiguration(ConfigurationSource.valueOf(argumentsAccessor.getString(0)), src)
      .ifPresent(configuration -> checkTemplates((MDConfiguration) configuration));
  }

  private void checkTemplates(MDConfiguration configuration) {
    var commonTemplates = configuration.getChildren().stream()
      .filter(Either::isRight)
      .map(Either::get)
      .filter(mdObjectBase -> mdObjectBase.getType() == MDOType.COMMON_TEMPLATE)
      .collect(Collectors.toList());
    assertThat(commonTemplates).hasSize(8)
      .extracting(AbstractMDObjectBase.class::cast)
      .allMatch(mdObjectBase -> Objects.nonNull(mdObjectBase.getPath()));

    checkCommonTemplate(commonTemplates);

    var templates = configuration.getChildren().stream()
      .filter(Either::isRight)
      .map(Either::get)
      .filter(AbstractMDObjectComplex.class::isInstance)
      .map(AbstractMDObjectComplex.class::cast)
      .map(AbstractMDObjectComplex::getTemplates)
      .flatMap(Collection::stream)
      .filter(mdObjectBase -> mdObjectBase.getType() == MDOType.TEMPLATE)
      .collect(Collectors.toList());
    assertThat(templates).hasSize(4);

    checkTemplate(templates);
  }

  private void checkCommonTemplate(List<AbstractMDObjectBase> commonTemplates) {
    var template = (MDOTemplate) commonTemplates.stream()
      .filter(mdObjectBase -> mdObjectBase.getName().equals("МакетСКД"))
      .findAny()
      .get();

    assertThat(template.getTemplateType()).isEqualTo(TemplateType.DATA_COMPOSITION_SCHEME);
    assertThat(template.getTemplateData()).isNotNull();

    var templateData = template.getTemplateData().getData();
    assertThat(templateData).isPresent();
    assertThat(templateData.get()).isNotEqualTo(TemplateData.empty());
  }

  private void checkTemplate(List<Template> templates) {
    var template = (MDOTemplate) templates.stream()
      .filter(mdObjectBase -> mdObjectBase.getName().equals("СКД"))
      .findAny()
      .get();

    assertThat(template.getTemplateType()).isEqualTo(TemplateType.DATA_COMPOSITION_SCHEME);
    assertThat(template.getTemplateData()).isNotNull();

    var templateData = template.getTemplateData().getData();
    assertThat(templateData).isPresent();
    assertThat(templateData.get()).isNotEqualTo(TemplateData.empty());
  }

}