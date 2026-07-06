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

import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.github._1c_syntax.bsl.types.ScriptVariant;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class EnumTest {
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Enums.Перечисление1",
    "false, mdclasses, Enums.Перечисление1",
    "true, ssl_3_1, Enums.СтатусыОбработчиковОбновления",
    "false, ssl_3_1, Enums.СтатусыОбработчиковОбновления",
    "true, ssl_3_2, Enums.СтатусыОбработчиковОбновления",
    "false, ssl_3_2, Enums.СтатусыОбработчиковОбновления"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Enum.class);

    var enum_ = (Enum) mdo;
    assertThat(enum_).isNotNull();

    var mdoRef = enum_.getMdoReference();
    var mdoRefString = mdoRef.getMdoRef();
    var mdoRefStringRu = mdoRef.getMdoRefRu();

    assertThat(enum_.getMdoReference()).isEqualTo(mdoRef);
    assertThat(enum_.getMdoRef(ScriptVariant.ENGLISH)).isEqualTo(mdoRefString);
    assertThat(enum_.getMdoRef(ScriptVariant.UNKNOWN)).isEqualTo(mdoRefStringRu);
    assertThat(enum_.getMdoRef(ScriptVariant.RUSSIAN)).isEqualTo(mdoRefStringRu);
    assertThat(enum_.getMdoRef()).isEqualTo(enum_.getMdoRef(ScriptVariant.ENGLISH));

    var enumValues = enum_.getEnumValues();
    var attributes = enum_.getAttributes();
    var forms = enum_.getForms();
    var templates = enum_.getTemplates();
    var commands = enum_.getCommands();
    var modules = enum_.getModules();

    var storageFields = enum_.getStorageFields();

    // --- AttributeOwner ---
    Assertions.assertThat(storageFields, false)
      .containsAll(attributes);
    Assertions.assertThat(enum_.getStorageFields(), false)
      .containsAll(attributes);
    Assertions.assertThat(enum_.getPlainStorageFields(), false)
      .containsAllPlain(attributes);

    // --- ModuleOwner ---
    Assertions.assertThat(enum_.getAllModules(), false)
      .containsAll(modules, forms, commands);

    // --- ChildrenOwner ---
    Assertions.assertThat(enum_.getChildren(), true)
      .containsAll(enumValues, attributes, forms, templates, commands);
    Assertions.assertThat(enum_.getPlainChildren(), true)
      .containsAllPlain(enumValues, attributes, forms, templates, commands);
  }
}
