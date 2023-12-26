/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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
package com.github._1c_syntax.bsl.reader.common.context;

import com.github._1c_syntax.bsl.reader.common.TransformationUtils;

import java.lang.reflect.ParameterizedType;

/**
 * Сохраняемый контекст при чтении файла
 */
public interface ReaderContext {

  /**
   * Билдер читаемого объекта
   */
  Object getBuilder();


  /**
   * Имя последней прочитанной ноды
   */
  String getLastName();

  void setLastName(String lastName);

  /**
   * Последнее прочитанное значение
   */
  Object getLastValue();

  void setLastValue(Object lastValue);

  /**
   * Признак чтения файла в формате выгрузки из конфигуратора.
   * Введено для сокращения копипасты в случае наличия общего алгоритма, но разных путей расположения файлоа
   */
  boolean isDesignerFormat();

  /**
   * Для установки значения поля собираемого объекта
   *
   * @param methodName Имя поля\метода
   * @param value      устанавливаемое значение
   */
  default void setValue(String methodName, Object value) {
    TransformationUtils.setValue(getBuilder(), methodName, value);
  }

  /**
   * Получение класса типа поля
   *
   * @param fieldName Имя поля\метода
   * @return Определенный класс
   */
  default Class<?> fieldType(String fieldName) {
    var fieldClass = TransformationUtils.fieldType(getBuilder(), fieldName);
    if (fieldClass instanceof ParameterizedType parameterizedType) {
      fieldClass = TransformationUtils.computeType(parameterizedType);
    }
    return (Class<?>) fieldClass;
  }

  /**
   * Сборка контекста в объект
   */
  Object build();
}
