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
package com.github._1c_syntax.bsl.mdo.support;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты для типов реквизитов
 */
class AttributeTypeTest {

  @Test
  void testCreateSimpleStringType() {
    AttributeType type = AttributeTypeFactory.createType("String");
    
    assertThat(type.getTypeDescriptions()).hasSize(1);
    assertThat(type.getTypeDescriptions().get(0).getTypeName()).isEqualTo("String");
    assertThat(type.getTypeDescriptions().get(0).getCategory()).isEqualTo(TypeCategory.PRIMITIVE);
    assertThat(type.isComposite()).isFalse();
    assertThat(type.getDisplayName()).isEqualTo("String");
  }
  
  @Test
  void testCreateReferenceType() {
    AttributeType type = AttributeTypeFactory.createType("CatalogRef.Справочник1");
    
    assertThat(type.getTypeDescriptions()).hasSize(1);
    assertThat(type.getTypeDescriptions().get(0).getTypeName()).isEqualTo("CatalogRef.Справочник1");
    assertThat(type.getTypeDescriptions().get(0).getCategory()).isEqualTo(TypeCategory.REFERENCE);
    assertThat(type.getTypeDescriptions().get(0).isReference()).isTrue();
    assertThat(type.isComposite()).isFalse();
  }
  
  @Test
  void testCreateCompositeType() {
    AttributeType type = AttributeTypeFactory.createType(List.of("String", "Number", "CatalogRef.Справочник1"));
    
    assertThat(type.getTypeDescriptions()).hasSize(3);
    assertThat(type.isComposite()).isTrue();
    assertThat(type.getDisplayName()).isEqualTo("String, Number, CatalogRef.Справочник1");
  }
  
  @Test
  void testCreateStringTypeWithQualifiers() {
    AttributeType type = AttributeTypeFactory.createStringType(10, StringQualifier.AllowedLength.VARIABLE);
    
    assertThat(type.getTypeDescriptions()).hasSize(1);
    TypeDescription desc = type.getTypeDescriptions().get(0);
    assertThat(desc.getTypeName()).isEqualTo("String");
    assertThat(desc.getQualifier()).isPresent();
    
    StringQualifier qualifier = (StringQualifier) desc.getQualifier().get();
    assertThat(qualifier.getLength()).isEqualTo(10);
    assertThat(qualifier.getAllowedLength()).isEqualTo(StringQualifier.AllowedLength.VARIABLE);
  }
  
  @Test
  void testCreateNumberTypeWithQualifiers() {
    AttributeType type = AttributeTypeFactory.createNumberType(10, 2, NumberQualifier.AllowedSign.NONNEGATIVE);
    
    assertThat(type.getTypeDescriptions()).hasSize(1);
    TypeDescription desc = type.getTypeDescriptions().get(0);
    assertThat(desc.getTypeName()).isEqualTo("Number");
    assertThat(desc.getQualifier()).isPresent();
    
    NumberQualifier qualifier = (NumberQualifier) desc.getQualifier().get();
    assertThat(qualifier.getPrecision()).isEqualTo(10);
    assertThat(qualifier.getFractionDigits()).isEqualTo(2);
    assertThat(qualifier.getAllowedSign()).isEqualTo(NumberQualifier.AllowedSign.NONNEGATIVE);
  }
  
  @Test
  void testEmptyType() {
    AttributeType type = AttributeTypeFactory.createType((String) null);
    
    assertThat(type).isEqualTo(AttributeTypeImpl.EMPTY);
    assertThat(type.getTypeDescriptions()).isEmpty();
    assertThat(type.isComposite()).isFalse();
    assertThat(type.getDisplayName()).isEqualTo("Unknown");
  }
}