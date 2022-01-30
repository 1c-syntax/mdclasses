/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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
package com.github._1c_syntax.bsl.mdo.storages;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.util.List;

/**
 * Хранилище данных XSD схемы пакета
 */
@Value
@ToString(of = {"targetNamespace"})
@EqualsAndHashCode(of = {"targetNamespace"})
@Builder
public class XdtoPackageData {

  public static final XdtoPackageData EMPTY = XdtoPackageData.builder().build();

  /**
   * Пространство имен пакета
   */
  @Default
  String targetNamespace = "";

  /**
   * Список импортов пакета
   */
  @Singular("oneImport")
  List<String> imports;

  /**
   * Список типов значений
   */
  @Singular
  List<ValueType> valueTypes;

  /**
   * Список типов объектов
   */
  @Singular
  List<ObjectType> objectTypes;

  /**
   * Список глобальных атрибутов
   */
  @Singular
  List<Property> properties;

  @Value
  @ToString(of = {"name"})
  @EqualsAndHashCode(of = {"name"})
  @Builder
  public static class ValueType {
    /**
     * Имя типа значений
     */
    String name;

    /**
     * Ссылка на базовый тип
     */
    @Default
    String base = "";

    /**
     * Тип коллекции
     */
    @Default
    String variety = "";

    /**
     * Значения элементов перечисления
     */
    @Singular
    List<String> enumerations;
  }

  @Value
  @ToString(of = {"name"})
  @EqualsAndHashCode(of = {"name"})
  @Builder
  public static class ObjectType {

    /**
     * Имя типа объекта
     */
    String name;

    /**
     * Ссылка на базовый тип
     */
    @Default
    String base = "";

    /**
     * Список атрибутов объекта
     */
    @Singular
    List<Property> properties;
  }

  @Value
  @ToString(of = {"name"})
  @EqualsAndHashCode(of = {"name"})
  @Builder
  public static class Property {
    /**
     * Имя атрибута
     */
    String name;

    /**
     * Тип атрибута
     */
    @Default
    String type = "";

    /**
     * Минимальное количество атрибутов (для множественных)
     */
    int lowerBound;

    /**
     * Максимальное количество атрибутов (для множественных)
     */
    int upperBound;

    /**
     * Возможность принимать NULL
     */
    boolean nillable;

    /**
     * Имя формы
     */
    @Default
    String form = "";

    /**
     * Свойства поля
     */
    @Singular("property")
    List<Property> typeDef;
  }
}
