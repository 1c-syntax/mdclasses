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
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.mdo.support.AttributeType;
import com.github._1c_syntax.bsl.mdo.support.AttributeTypeImpl;
import com.github._1c_syntax.bsl.mdo.support.NumberQualifier;
import com.github._1c_syntax.bsl.mdo.support.StringQualifier;
import com.github._1c_syntax.bsl.mdo.support.TypeDescription;
import com.github._1c_syntax.bsl.mdo.support.TypeCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Базовый класс для конвертеров типов атрибутов
 */
public abstract class AttributeTypeConverterBase extends AbstractReadConverter {

  /**
   * Создает TypeDescription с правильным применением квалификаторов
   * @param typeName имя типа
   * @param stringQualifier строковый квалификатор (применяется только к String)
   * @param numberQualifier числовой квалификатор (применяется только к Number)
   * @return объект TypeDescription
   * @throws IllegalArgumentException если typeName null или пустой
   */
  protected TypeDescription createTypeDescription(String typeName, 
                                                  Optional<StringQualifier> stringQualifier, 
                                                  Optional<NumberQualifier> numberQualifier) {
    // Валидация входных данных
    if (typeName == null || typeName.trim().isEmpty()) {
      throw new IllegalArgumentException("Type name cannot be null or empty");
    }

    final String normalizedName = typeName.trim();
    TypeCategory category = determineTypeCategory(normalizedName);
    TypeDescription.TypeDescriptionBuilder builder = TypeDescription.builder()
      .typeName(normalizedName)
      .category(category);

    // Применяем квалификаторы только к соответствующим примитивам
    if (category == TypeCategory.PRIMITIVE) {
      if ("String".equalsIgnoreCase(normalizedName) && stringQualifier.isPresent()) {
        builder.qualifier(Optional.of(stringQualifier.get()));
      } else if ("Number".equalsIgnoreCase(normalizedName) && numberQualifier.isPresent()) {
        builder.qualifier(Optional.of(numberQualifier.get()));
      }
    }

    return builder.build();
  }

  /**
   * Создает AttributeTypeImpl из списка типов и квалификаторов
   * @param typeNames список имен типов
   * @param stringQualifier строковый квалификатор
   * @param numberQualifier числовой квалификатор
   * @return объект AttributeType
   */
  protected AttributeType createAttributeType(List<String> typeNames,
                                              Optional<StringQualifier> stringQualifier,
                                              Optional<NumberQualifier> numberQualifier) {
    if (typeNames == null || typeNames.isEmpty()) {
      return AttributeTypeImpl.EMPTY;
    }

    List<TypeDescription> typeDescriptions = new ArrayList<>();
    for (String typeName : typeNames) {
      if (typeName != null && !typeName.trim().isEmpty()) {
        typeDescriptions.add(createTypeDescription(typeName, stringQualifier, numberQualifier));
      }
    }

    if (typeDescriptions.isEmpty()) {
      return AttributeTypeImpl.EMPTY;
    }

    return AttributeTypeImpl.builder()
      .typeDescriptions(typeDescriptions)
      .build();
  }

  /**
   * Определяет категорию типа по его названию
   * @param typeName название типа
   * @return категория типа
   */
  protected TypeCategory determineTypeCategory(String typeName) {
    if (typeName.contains("Ref.") || typeName.contains("Object.")) {
      return TypeCategory.REFERENCE;
    }
    if (typeName.startsWith("DefinedType.")) {
      return TypeCategory.DEFINED;
    }
    return TypeCategory.PRIMITIVE;
  }
}

