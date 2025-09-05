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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Фабрика для создания типов реквизитов из данных парсинга
 */
public final class AttributeTypeFactory {
  
  private AttributeTypeFactory() {
    // utility class
  }
  
  /**
   * Создает тип на основе списка строковых представлений типов
   */
  public static AttributeType createType(List<String> typeNames) {
    if (typeNames == null || typeNames.isEmpty()) {
      return AttributeTypeImpl.EMPTY;
    }
    
    List<TypeDescription> descriptions = new ArrayList<>();
    for (String typeName : typeNames) {
      TypeDescription description = createTypeDescription(typeName);
      descriptions.add(description);
    }
    
    return AttributeTypeImpl.builder()
      .typeDescriptions(descriptions)
      .build();
  }
  
  /**
   * Создает тип на основе одного строкового представления
   */
  public static AttributeType createType(String typeName) {
    if (typeName == null) {
      return AttributeTypeImpl.EMPTY;
    }
    return createType(List.of(typeName));
  }
  
  /**
   * Создает описание типа на основе строкового представления
   */
  private static TypeDescription createTypeDescription(String typeName) {
    String normalized = normalizeTypeName(typeName);
    TypeCategory category = determineTypeCategory(normalized);
    
    return TypeDescription.builder()
      .typeName(normalized)
      .category(category)
      .build();
  }
  
  /**
   * Нормализует имя типа, удаляя префиксы пространств имен
   */
  private static String normalizeTypeName(String typeName) {
    if (typeName == null) {
      return null;
    }
    String trimmed = typeName.trim();
    if (trimmed.startsWith("xs:")) {
      return trimmed.substring(3);
    }
    if (trimmed.startsWith("cfg:")) {
      return trimmed.substring(4);
    }
    return trimmed;
  }

  /**
   * Определяет категорию типа по его названию
   */
  private static TypeCategory determineTypeCategory(String typeName) {
    if (typeName == null || typeName.isEmpty()) {
      return TypeCategory.PRIMITIVE;
    }
    
    // Примитивные типы
    if (isPrimitiveType(typeName)) {
      return TypeCategory.PRIMITIVE;
    }
    
    // Ссылочные типы
    if (isReferenceType(typeName)) {
      return TypeCategory.REFERENCE;
    }
    
    // Определяемые типы
    if (typeName.startsWith("DefinedType.")) {
      return TypeCategory.DEFINED;
    }
    
    // По умолчанию - примитивный
    return TypeCategory.PRIMITIVE;
  }
  
  /**
   * Проверяет, является ли тип примитивным
   */
  private static boolean isPrimitiveType(String typeName) {
    return typeName.equals("String") 
        || typeName.equals("Number") 
        || typeName.equals("Boolean") 
        || typeName.equals("Date")
        || typeName.equals("Binary")
        || typeName.equals("UUID")
        || typeName.startsWith("xs:");
  }
  
  /**
   * Проверяет, является ли тип ссылочным
   */
  private static boolean isReferenceType(String typeName) {
    return typeName.contains("Ref.") 
        || typeName.contains("Object.")
        || typeName.contains("Manager.")
        || typeName.contains("List.")
        || typeName.contains("Selection.");
  }
  
  /**
   * Создает тип с квалификаторами строки
   */
  public static AttributeType createStringType(int length, StringQualifier.AllowedLength allowedLength) {
    // Валидация параметров
    if (length < 0) {
      throw new IllegalArgumentException("String length must be >= 0");
    }
    if (allowedLength == null) {
      allowedLength = StringQualifier.AllowedLength.VARIABLE;
    }
    
    StringQualifier qualifier = StringQualifier.builder()
      .length(length)
      .allowedLength(allowedLength)
      .build();
      
    TypeDescription description = TypeDescription.builder()
      .typeName("String")
      .category(TypeCategory.PRIMITIVE)
      .qualifier(Optional.of(qualifier))
      .build();
      
    return AttributeTypeImpl.builder()
      .typeDescriptions(List.of(description))
      .build();
  }
  
  /**
   * Создает тип с квалификаторами числа
   */
  public static AttributeType createNumberType(int precision, int fractionDigits, NumberQualifier.AllowedSign allowedSign) {
    // Валидация параметров
    if (precision <= 0) {
      throw new IllegalArgumentException("Number precision must be > 0");
    }
    if (fractionDigits < 0 || fractionDigits > precision) {
      throw new IllegalArgumentException("fractionDigits must be in range [0, precision]");
    }
    if (allowedSign == null) {
      allowedSign = NumberQualifier.AllowedSign.ANY;
    }
    
    NumberQualifier qualifier = NumberQualifier.builder()
      .precision(precision)
      .fractionDigits(fractionDigits)
      .allowedSign(allowedSign)
      .build();
      
    TypeDescription description = TypeDescription.builder()
      .typeName("Number")
      .category(TypeCategory.PRIMITIVE)
      .qualifier(Optional.of(qualifier))
      .build();
      
    return AttributeTypeImpl.builder()
      .typeDescriptions(List.of(description))
      .build();
  }
}