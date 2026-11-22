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
package com.github._1c_syntax.bsl.reader.common.context;

import com.github._1c_syntax.bsl.mdo.storage.form.FormAdditionalColumnsAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormDynamicListAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormSimpleAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormTableAttribute;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import com.github._1c_syntax.bsl.types.value.PrimitiveValueType;
import com.github._1c_syntax.bsl.types.value.V8ValueType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FormAttributeWrapperTest {

  @Test
  void shouldDispatchSimpleAttribute() {
    var wrapper = FormAttributeWrapper.builder()
      .id(1)
      .name("Объект")
      .type(ValueTypeDescription.create(PrimitiveValueType.STRING))
      .build();

    assertThat(wrapper.toFormAttribute())
      .isInstanceOf(FormSimpleAttribute.class)
      .extracting(FormAttribute::getName, FormAttribute::getId)
      .containsExactly("Объект", 1);
  }

  @Test
  void shouldDispatchTableAttribute() {
    var wrapper = FormAttributeWrapper.builder()
      .id(2)
      .name("Таблица")
      .type(ValueTypeDescription.create(V8ValueType.VALUE_TABLE))
      .build();

    assertThat(wrapper.toFormAttribute())
      .isInstanceOf(FormTableAttribute.class)
      .extracting(FormAttribute::getName, FormAttribute::getId)
      .containsExactly("Таблица", 2);
  }

  @Test
  void shouldDispatchTreeAttributeAsTable() {
    var wrapper = FormAttributeWrapper.builder()
      .name("Дерево")
      .type(ValueTypeDescription.create(V8ValueType.VALUE_TREE))
      .build();

    assertThat(wrapper.toFormAttribute()).isInstanceOf(FormTableAttribute.class);
  }

  @Test
  void shouldDispatchDynamicListAttribute() {
    var wrapper = FormAttributeWrapper.builder()
      .name("Список")
      .type(ValueTypeDescription.create(V8ValueType.DYNAMIC_LIST))
      .build();

    assertThat(wrapper.toFormAttribute()).isInstanceOf(FormDynamicListAttribute.class);
  }

  @Test
  void shouldDispatchAdditionalColumnsAttribute() {
    var column = FormAttributeWrapper.builder()
      .id(1)
      .name("НомерКартинки")
      .type(ValueTypeDescription.create(PrimitiveValueType.NUMBER))
      .build();
    var wrapper = FormAttributeWrapper.builder()
      .name("Объект.Пользователи")
      
      .addColumns(column)
      .build();

    var result = wrapper.toFormAttribute();
    assertThat(result).isInstanceOf(FormAdditionalColumnsAttribute.class);
    assertThat(result.getName()).isEqualTo("Объект.Пользователи");
    assertThat(result.getColumns()).hasSize(1);
    assertThat(result.getColumns().getFirst()).isInstanceOf(FormSimpleAttribute.class);
  }

  @Test
  void shouldDispatchTableTypeAdditionalColumnAsTable() {
    // дополнительная колонка типа таблица — диспетчеризуется по типу
    var column = FormAttributeWrapper.builder()
      .name("Колонка")
      .type(ValueTypeDescription.create(PrimitiveValueType.STRING))
      .build();
    var wrapper = FormAttributeWrapper.builder()
      .name("Объект.НоваяТаблица")
      .type(ValueTypeDescription.create(V8ValueType.VALUE_TABLE))
      
      .addColumns(column)
      .build();

    assertThat(wrapper.toFormAttribute())
      .isInstanceOf(FormTableAttribute.class)
      .extracting(FormAttribute::getName)
      .isEqualTo("Объект.НоваяТаблица");
  }

  @Test
  void shouldMergeMatchedAdditionalColumns() {
    var regular = FormAttributeWrapper.builder()
      .id(1)
      .name("Состав")
      .type(ValueTypeDescription.create(V8ValueType.VALUE_TABLE))
      .build();
    var addChild = FormAttributeWrapper.builder()
      .id(5)
      .name("ЭлементарныйВопрос")
      .type(ValueTypeDescription.create(PrimitiveValueType.STRING))
      .build();
    var additional = FormAttributeWrapper.builder()
      .name("Таблица.Состав")
      
      .addColumns(addChild)
      .build();

    var table = FormAttributeWrapper.builder()
      .id(8)
      .name("Таблица")
      .type(ValueTypeDescription.create(V8ValueType.VALUE_TABLE))
      
      .addColumns(regular)
      .addColumns(additional)
      .build();

    var result = table.toFormAttribute();
    assertThat(result).isInstanceOf(FormTableAttribute.class);
    assertThat(result.getColumns()).hasSize(1);
    assertThat(result.getColumns().getFirst().getName()).isEqualTo("Состав");
    assertThat(result.getColumns().getFirst().getColumns())
      .extracting(FormAttribute::getName)
      .containsExactly("ЭлементарныйВопрос");
  }

  @Test
  void shouldKeepUnmatchedAdditionalColumns() {
    var regular = FormAttributeWrapper.builder()
      .id(1)
      .name("Вопрос")
      .type(ValueTypeDescription.create(PrimitiveValueType.STRING))
      .build();
    var addChild = FormAttributeWrapper.builder()
      .id(7)
      .name("НомерКартинки")
      .type(ValueTypeDescription.create(PrimitiveValueType.NUMBER))
      .build();
    var additional = FormAttributeWrapper.builder()
      .name("Объект.Пользователи")
      
      .addColumns(addChild)
      .build();

    var wrapper = FormAttributeWrapper.builder()
      .id(3)
      .name("Объект")
      .type(ValueTypeDescription.create(PrimitiveValueType.STRING))
      
      .addColumns(regular)
      .addColumns(additional)
      .build();

    var result = wrapper.toFormAttribute();
    assertThat(result).isInstanceOf(FormSimpleAttribute.class);
    assertThat(result.getColumns()).hasSize(2);
    assertThat(result.getColumns()).anySatisfy(col ->
      assertThat(col).isInstanceOf(FormAdditionalColumnsAttribute.class));
    var addCols = result.getColumns().stream()
      .filter(FormAdditionalColumnsAttribute.class::isInstance)
      .map(FormAdditionalColumnsAttribute.class::cast)
      .findFirst()
      .orElseThrow();
    assertThat(addCols.getName()).isEqualTo("Объект.Пользователи");
    assertThat(addCols.getColumns()).extracting(FormAttribute::getName)
      .containsExactly("НомерКартинки");
  }

  @Test
  void shouldDispatchNestedColumnsRecursively() {
    var nested = FormAttributeWrapper.builder()
      .name("Вопрос")
      .type(ValueTypeDescription.create(PrimitiveValueType.STRING))
      .build();
    var table = FormAttributeWrapper.builder()
      .name("Таблица")
      .type(ValueTypeDescription.create(V8ValueType.VALUE_TABLE))
      
      .addColumns(nested)
      .build();
    var wrapper = FormAttributeWrapper.builder()
      .name("Объект")
      .type(ValueTypeDescription.create(PrimitiveValueType.STRING))
      
      .addColumns(table)
      .build();

    var result = wrapper.toFormAttribute();
    assertThat(result.getColumns()).hasSize(1);
    assertThat(result.getColumns().getFirst()).isInstanceOf(FormTableAttribute.class);
    assertThat(result.getColumns().getFirst().getColumns().getFirst())
      .isInstanceOf(FormSimpleAttribute.class);
  }

  @Test
  void shouldBuildEmptyColumnsWithoutError() {
    var wrapper = FormAttributeWrapper.builder()
      .name("Объект")
      .type(ValueTypeDescription.create(PrimitiveValueType.STRING))
      .columns(List.of())
      .build();

    assertThat(wrapper.toFormAttribute().getColumns()).isEmpty();
  }
}