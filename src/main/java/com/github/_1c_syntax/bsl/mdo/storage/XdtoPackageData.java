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
package com.github._1c_syntax.bsl.mdo.storage;

import lombok.Builder;
import lombok.Singular;

import java.util.Collections;
import java.util.List;

/**
 * Хранилище данных XSD схемы пакета
 *
 * @param targetNamespace Пространство имен пакета
 * @param imports         Список импортов пакета
 * @param valueTypes      Список типов значений
 * @param objectTypes     Список типов объектов
 * @param properties      Список глобальных атрибутов
 */
@Builder
public record XdtoPackageData(String targetNamespace,
                              @Singular("oneImport") List<String> imports,
                              @Singular List<ValueType> valueTypes,
                              @Singular List<ObjectType> objectTypes,
                              @Singular List<Property> properties) {

  public static final XdtoPackageData EMPTY = new XdtoPackageData(
    "",
    Collections.emptyList(),
    Collections.emptyList(),
    Collections.emptyList(),
    Collections.emptyList()
  );

  /**
   * @param name         Имя типа значений
   * @param base         Ссылка на базовый тип
   * @param variety      Тип коллекции
   * @param enumerations Значения элементов перечисления
   */
  @Builder
  public record ValueType(String name,
                          String base,
                          String variety,
                          @Singular List<String> enumerations) {
  }

  /**
   * @param name       Имя типа объекта
   * @param base       Ссылка на базовый тип
   * @param properties Список атрибутов объекта
   */
  @Builder
  public record ObjectType(String name, String base, @Singular List<Property> properties) {
  }

  /**
   * @param name       Имя атрибута
   * @param type       Тип атрибута
   * @param lowerBound Минимальное количество атрибутов (для множественных)
   * @param upperBound Максимальное количество атрибутов (для множественных)
   * @param nillable   Возможность принимать NULL
   * @param form       Имя формы
   * @param typeDef    Свойства поля
   */
  @Builder
  public record Property(String name,
                         String type,
                         int lowerBound,
                         int upperBound,
                         boolean nillable,
                         String form,
                         @Singular("property") List<Property> typeDef) {
  }
}
