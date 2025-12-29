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
package com.github._1c_syntax.bsl.reader.common;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Singular;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransformationUtilsTest {

  @Test
  void testSetValueWithValidParameters() {
    var builder = TestClass.builder();
    TransformationUtils.setValue(builder, "name", "TestName");

    var result = builder.build();
    assertThat(result.getName()).isEqualTo("TestName");
  }

  @Test
  void testSetValueWithListParameter() {
    var builder = TestClassWithList.builder();
    TransformationUtils.setValue(builder, "items", List.of("item1", "item2"));

    var result = builder.build();
    assertThat(result.getItems()).containsExactly("item1", "item2");
  }

  @Test
  void testSetValueWithSingularParameter() {
    var builder = TestClassWithList.builder();
    TransformationUtils.setValue(builder, "item", "singleItem");

    var result = builder.build();
    assertThat(result.getItems()).contains("singleItem");
  }

  @Test
  void testInvokeWithValidMethod() {
    var mutableObject = new MutableTestClass();
    TransformationUtils.invoke(mutableObject, "reset");

    assertThat(mutableObject.isReset()).isTrue();
  }

  @Test
  void testInvokeWithNonExistentMethod() {
    var mutableObject = new MutableTestClass();
    // Should not throw, just log error
    TransformationUtils.invoke(mutableObject, "nonExistentMethod");

    assertThat(mutableObject.isReset()).isFalse();
  }

  @Test
  void testFieldType() {
    var builder = TestClass.builder();
    var type = TransformationUtils.fieldType(builder, "name");

    assertThat(type).isNotNull();
    assertThat(type.getTypeName()).contains("String");
  }

  @Test
  void testFieldTypeWithNonExistentField() {
    var builder = TestClass.builder();
    var type = TransformationUtils.fieldType(builder, "nonExistent");

    assertThat(type).isNull();
  }

  @Test
  void testBuilder() {
    var builder = TransformationUtils.builder(TestClass.class);

    assertThat(builder)
      .isNotNull()
      .isInstanceOf(TestClass.TestClassBuilder.class);
  }

  @Test
  void testBuilderWithClassWithoutBuilder() {
    assertThatThrownBy(() -> TransformationUtils.builder(String.class))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Incorrect class");
  }

  @Test
  void testToBuilderWithClassWithoutToBuilder() {
    var simpleObject = new SimpleTestClass("test");
    var builder = TransformationUtils.toBuilder(simpleObject);

    assertThat(builder).isNull();
  }

  @Test
  void testBuildWithBuilder() {
    var builder = TestClass.builder().name("Test").value(100);
    var result = TransformationUtils.build(builder);

    assertThat(result)
      .isNotNull()
      .isInstanceOf(TestClass.class);
    assertThat(((TestClass) result).getName()).isEqualTo("Test");
    assertThat(((TestClass) result).getValue()).isEqualTo(100);
  }

  @Test
  void testBuildWithBuilderAndPath() {
    var builder = TestClass.builder().name("Test").value(100);
    var path = Paths.get("test/path");
    var result = TransformationUtils.build(builder, path);

    assertThat(result)
      .isNotNull()
      .isInstanceOf(TestClass.class);
  }

  @Test
  void testBuildWithInvalidBuilder() {
    var result = TransformationUtils.build("not a builder");

    assertThat(result).isNull();
  }

  @Test
  void testSetValueWithInvalidMethodName() {
    var builder = TestClass.builder();
    // Should not throw, just log error
    TransformationUtils.setValue(builder, "nonExistentMethod", "value");

    var result = builder.build();
    assertThat(result).isNotNull();
  }

  @Test
  void testFieldTypeWithListParameter() {
    var builder = TestClassWithList.builder();
    var type = TransformationUtils.fieldType(builder, "items");

    assertThat(type).isNotNull();
  }

  // Test helper classes
  @Data
  @Builder
  private static class TestClass {
    private String name;
    private int value;
  }

  @Data
  @Builder
  private static class TestClassWithList {
    @Singular
    private List<String> items;
  }

  private record SimpleTestClass(String name) {
  }

  @Getter
  private static class MutableTestClass {
    private boolean reset = false;

    public void reset() {
      this.reset = true;
    }
  }
}
