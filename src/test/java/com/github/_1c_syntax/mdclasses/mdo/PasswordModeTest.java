/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.Configuration;
import com.github._1c_syntax.mdclasses.mdo.attributes.AbstractMDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.attributes.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordModeTest {
  private static final String BASE_DIR = "src/test/resources/metadata/passwordmode";

  @Test
  void testOrigin() {
    final var src = Path.of(BASE_DIR, "original");
    var configuration = Configuration.create(src);
    performCheck(configuration);
  }

  @Test
  void testEDT() {
    final var src = Path.of(BASE_DIR, "edt");
    var configuration = Configuration.create(src);
    performCheck(configuration);
  }

  private void performCheck(Configuration configuration) {
    var optionalCatalog = configuration.getChildren().stream()
      .filter(abstractMDObjectBase -> abstractMDObjectBase instanceof MDCatalog)
      .findAny();

    assertThat(optionalCatalog).isPresent();
    var catalog = (AbstractMDObjectComplex) optionalCatalog.get();

    assertThat(catalog.getAttributes())
      .hasSize(2)
      .anyMatch(attribute -> attribute.getAttributeType() == AttributeType.ATTRIBUTE && attribute.isPasswordMode())
      .anyMatch(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION && validTabularSection(attribute));
  }

  private boolean validTabularSection(AbstractMDOAttribute mdoAttribute) {
    var tabularSection = (TabularSection) mdoAttribute;
    assertThat(tabularSection.getAttributes())
      .hasSize(2)
      .anyMatch(attribute -> attribute.getName().equals("Пароль") && attribute.isPasswordMode())
      .anyMatch(attribute -> attribute.getName().equals("Реквизит1") && !attribute.isPasswordMode());
    return true;
  }

}
