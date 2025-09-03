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

import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.Optional;

/**
 * Описание одного типа данных
 */
@Value
@Builder
@ToString
@EqualsAndHashCode
public class TypeDescription {
  
  /**
   * Название типа (например: "String", "CatalogRef.Справочник1", "Boolean")
   */
  @Default
  String typeName = "";
  
  /**
   * Категория типа
   */
  @Default
  TypeCategory category = TypeCategory.PRIMITIVE;
  
  /**
   * Квалификаторы типа (для строк, чисел и т.д.)
   */
  @Default
  Optional<TypeQualifier> qualifier = Optional.empty();
  
  /**
   * Возвращает true, если тип является примитивным
   */
  public boolean isPrimitive() {
    return category == TypeCategory.PRIMITIVE;
  }
  
  /**
   * Возвращает true, если тип является ссылочным
   */
  public boolean isReference() {
    return category == TypeCategory.REFERENCE;
  }
}