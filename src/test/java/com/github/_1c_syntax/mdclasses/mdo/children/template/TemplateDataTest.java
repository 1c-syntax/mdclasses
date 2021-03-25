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

import com.github._1c_syntax.mdclasses.Configuration;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDOTemplate;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateDataTest {

  @Test
  void testEdt() {
    var srcPath = new File("src/test/resources/metadata/skd/edt");
    var configuration = Configuration.create(srcPath.toPath());
    checkTemplates(configuration);
  }

  @Test
  void testOriginal() {
    var srcPath = new File("src/test/resources/metadata/skd/original");
    var configuration = Configuration.create(srcPath.toPath());
    checkTemplates(configuration);
  }

  private void checkTemplates(Configuration configuration) {
    var commonTemplates = configuration.getChildren().stream()
      .filter(mdObjectBase -> mdObjectBase.getType() == MDOType.COMMON_TEMPLATE)
      .collect(Collectors.toList());
    assertThat(commonTemplates).hasSize(8)
      .extracting(AbstractMDObjectBase.class::cast)
      .allMatch(mdObjectBase -> Objects.nonNull(mdObjectBase.getPath()));

    checkCommonTemplate(commonTemplates);

    var templates = configuration.getChildren().stream()
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

  private void checkTemplate(List<AbstractMDObjectBase> templates) {
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