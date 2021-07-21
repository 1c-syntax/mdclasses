/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.bsl.mdo.data_storage;

import lombok.Builder;
import lombok.EqualsAndHashCode;
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
  /**
   * Пространство имен пакета
   */
  String targetNamespace;

  /**
   * Список импортов пакета
   */
  List<String> imports;

  /**
   * Список типов значений
   */
  List<ValueType> valueTypes;

  /**
   * Список типов объектов
   */
  List<ObjectType> objectTypes;

  /**
   * Список глобальных атрибутов
   */
  List<Property> properties;

  @Value
  @ToString(of = {"name"})
  @EqualsAndHashCode(of = {"name"})
  public static class ValueType {
    /**
     * Имя типа значений
     */
    String name;

    /**
     * Ссылка на базовый тип
     */
    String base;

    /**
     * Тип коллекции
     */
    String variety;

    /**
     * Значения элементов перечисления
     */
    List<String> enumerations;
  }

  @Value
  @ToString(of = {"name"})
  @EqualsAndHashCode(of = {"name"})
  public static class ObjectType {

    /**
     * Имя типа объекта
     */
    String name;

    /**
     * Ссылка на базовый тип
     */
    String base;

    /**
     * Список атрибутов объекта
     */
    List<Property> properties;
  }

  @Value
  @ToString(of = {"name"})
  @EqualsAndHashCode(of = {"name"})
  public static class Property {
    /**
     * Имя атрибута
     */
    String name;

    /**
     * Тип атрибута
     */
    String type;

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
    String form;
  }
}
