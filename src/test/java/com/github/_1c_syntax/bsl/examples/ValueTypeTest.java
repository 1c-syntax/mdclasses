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
package com.github._1c_syntax.bsl.examples;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.DefinedType;
import com.github._1c_syntax.bsl.mdo.children.ObjectAttribute;
import com.github._1c_syntax.bsl.mdo.support.MetadataValueType;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.qualifiers.NumberQualifiers;
import com.github._1c_syntax.bsl.types.value.PrimitiveValueType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueTypeTest {

  private static final String EXAMPLES_PATH = "src/test/resources/ext";
  private static final String EDT_PATH = "edt";
  private static final String DESIGNER_PATH = "designer";
  private static final String DESIGNER_CF_PATH = "src/cf";
  private static final String EDT_CF_PATH = "configuration";

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, _edt",
      "false, mdclasses"
    }
  )
  void testCatalog(ArgumentsAccessor argumentsAccessor) {
    var configuration = readConfiguration(argumentsAccessor);

    var childMDO = configuration.findChild("Catalog.Справочник1");
    if (childMDO.isPresent() && childMDO.get() instanceof Catalog catalog) {
      assertThat(catalog.getChildren()).isNotEmpty();
      var childAttribute = catalog.findChild(md -> "Реквизит2".equals(md.getName()));
      if (childAttribute.isPresent() && childAttribute.get() instanceof ObjectAttribute objectAttribute) {
        assertThat(objectAttribute.getName()).isEqualTo("Реквизит2");
        assertThat(objectAttribute.getValueType()).isNotNull();
        assertThat(objectAttribute.getValueType().contains(PrimitiveValueType.NUMBER)).isTrue();
        assertThat(objectAttribute.getValueType().isComposite()).isFalse();
        assertThat(objectAttribute.getValueType().getQualifiers()).hasSize(1);

        var qualifier = objectAttribute.getValueType().getQualifiers().get(0);
        assertThat(qualifier).isInstanceOf(NumberQualifiers.class);

        var numberQualifiers = (NumberQualifiers) qualifier;
        assertThat(numberQualifiers.getPrecision()).isEqualTo(10);
        assertThat(numberQualifiers.getScale()).isEqualTo(0);
        assertThat(numberQualifiers.isNonNegative()).isFalse();
      }
    }
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, _edt",
      "false, ssl_3_1"
    }
  )
  void testDefinedType(ArgumentsAccessor argumentsAccessor) {
    var configuration = readConfiguration(argumentsAccessor);

    var childMDO = configuration.findChild("DefinedType.ЗначениеДоступа");
    if (childMDO.isPresent() && childMDO.get() instanceof DefinedType definedType) {
      assertThat(definedType.getName()).isEqualTo("ЗначениеДоступа");
      assertThat(definedType.getValueType()).isNotNull();
      assertThat(definedType.getValueType().contains(PrimitiveValueType.NUMBER)).isFalse();
      assertThat(definedType.getValueType().isComposite()).isTrue();
      assertThat(definedType.getValueType().getQualifiers()).isEmpty();

      var typeContains = MetadataValueType.fromString("EnumRef.ДополнительныеЗначенияДоступа");
      var typeNotContains = MetadataValueType.fromString("CatalogRef.Контрагенты");
      assertThat(typeContains).isNotNull();
      assertThat(typeNotContains).isNotNull();
      assertThat(definedType.getValueType().contains(typeContains)).isTrue();
      assertThat(definedType.getValueType().contains(typeNotContains)).isFalse();

      assertThat(typeContains.getKind()).isEqualTo(MDOType.ENUM);
      assertThat(typeContains.isComposite()).isFalse();
      assertThat(typeContains.getName()).isEqualTo("EnumRef.ДополнительныеЗначенияДоступа");
      assertThat(typeContains.getNameRu()).isEqualTo("ПеречислениеСсылка.ДополнительныеЗначенияДоступа");
    }
  }

  private static Configuration readConfiguration(ArgumentsAccessor argumentsAccessor) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var examplePackName = argumentsAccessor.getString(1);

    Path configurationPath;
    if (isEDT) {
      configurationPath = Path.of(EXAMPLES_PATH, EDT_PATH, examplePackName, EDT_CF_PATH);
    } else {
      configurationPath = Path.of(EXAMPLES_PATH, DESIGNER_PATH, examplePackName, DESIGNER_CF_PATH);
    }

    var mdc = MDClasses.createConfiguration(configurationPath, true);
    assertThat(mdc).isNotNull();
    assertThat(mdc).isInstanceOf(MDClass.class);
    assertThat(mdc).isInstanceOf(Configuration.class);

    return (Configuration) mdc;
  }
}
