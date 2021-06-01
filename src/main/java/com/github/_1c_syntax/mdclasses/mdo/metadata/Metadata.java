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
package com.github._1c_syntax.mdclasses.mdo.metadata;

import com.github._1c_syntax.mdclasses.mdo.support.MDOType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Содержит набор метаинформации о типе метаданных
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Metadata {

  /**
   * Область использования класса при чтении файлов
   */
  XMLScope xmlScope() default XMLScope.ALL;

  /**
   * Тип метаданных
   */
  MDOType type();

  /**
   * Имя типа на английском
   */
  String name();

  /**
   * Имя типа на русском
   */
  String nameRu();

  /**
   * Имя группы объектов типа на английском
   */
  String groupName();

  /**
   * Имя группы объектов типа на русском
   */
  String groupNameRu();
}
