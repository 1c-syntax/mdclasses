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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация типа реквизита метаданных
 */
@Value
@Builder
@ToString
@EqualsAndHashCode
public class AttributeTypeImpl implements AttributeType {
  
  /**
   * Список описаний типов
   */
  @Default
  List<TypeDescription> typeDescriptions = Collections.emptyList();
  
  @Override
  public List<TypeDescription> getTypeDescriptions() {
    return Collections.unmodifiableList(typeDescriptions);
  }
  
  @Override
  public boolean isComposite() {
    return typeDescriptions.size() > 1;
  }
  
  @Override
  public String getDisplayName() {
    if (typeDescriptions.isEmpty()) {
      return "Unknown";
    }
    
    if (typeDescriptions.size() == 1) {
      return typeDescriptions.get(0).getTypeName();
    }
    
    return typeDescriptions.stream()
      .map(TypeDescription::getTypeName)
      .collect(Collectors.joining(", "));
  }
  
  /**
   * Пустой тип (когда тип не определен)
   */
  public static final AttributeType EMPTY = AttributeTypeImpl.builder().build();
}