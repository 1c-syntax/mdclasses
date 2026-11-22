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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class XdtoPackageDataTest {

  @Test
  void testEmptyConstant() {
    assertThat(XdtoPackageData.EMPTY).isNotNull();
    assertThat(XdtoPackageData.EMPTY.targetNamespace()).isEmpty();
    assertThat(XdtoPackageData.EMPTY.imports()).isEmpty();
    assertThat(XdtoPackageData.EMPTY.valueTypes()).isEmpty();
    assertThat(XdtoPackageData.EMPTY.objectTypes()).isEmpty();
    assertThat(XdtoPackageData.EMPTY.properties()).isEmpty();
  }

  @Test
  void testValueType() {
    var valueType = new XdtoPackageData.ValueType(
      "TestType",
      "BaseType",
      "Atomic",
      java.util.List.of("Value1", "Value2")
    );

    assertThat(valueType).isNotNull();
    assertThat(valueType.name()).isEqualTo("TestType");
    assertThat(valueType.base()).isEqualTo("BaseType");
    assertThat(valueType.variety()).isEqualTo("Atomic");
    assertThat(valueType.enumerations()).hasSize(2);
  }

  @Test
  void testObjectType() {
    var objectType = new XdtoPackageData.ObjectType(
      "TestObject",
      "BaseObject",
      java.util.List.of()
    );

    assertThat(objectType).isNotNull();
    assertThat(objectType.name()).isEqualTo("TestObject");
    assertThat(objectType.base()).isEqualTo("BaseObject");
    assertThat(objectType.properties()).isEmpty();
  }

  @Test
  void testProperty() {
    var property = new XdtoPackageData.Property(
      "TestProperty",
      "string",
      0,
      1,
      false,
      "Element",
      java.util.List.of()
    );

    assertThat(property).isNotNull();
    assertThat(property.name()).isEqualTo("TestProperty");
    assertThat(property.type()).isEqualTo("string");
    assertThat(property.lowerBound()).isZero();
    assertThat(property.upperBound()).isOne();
    assertThat(property.nillable()).isFalse();
    assertThat(property.form()).isEqualTo("Element");
    assertThat(property.typeDef()).isEmpty();
  }
}
