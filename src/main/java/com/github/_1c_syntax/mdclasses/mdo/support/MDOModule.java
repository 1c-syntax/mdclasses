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
package com.github._1c_syntax.mdclasses.mdo.support;

import com.github._1c_syntax.mdclasses.mdo.MDOHasModule;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.net.URI;

/**
 * Класс-описание модуля объекта
 */
@Value
@EqualsAndHashCode(exclude = {"owner"})
@AllArgsConstructor
public class MDOModule {

  /**
   * Тип модуля
   */
  ModuleType moduleType;

  /**
   * Ссылка на файл bsl модуля
   */
  URI uri;

  /**
   * Ссылка на объект метаданных которому принадлежит модуль
   */
  MDOHasModule owner;
}
