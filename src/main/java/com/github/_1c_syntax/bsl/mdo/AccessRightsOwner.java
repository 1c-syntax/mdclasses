/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.support.RoleRight;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Расширение - объект имеет ограничения доступа пользователе
 */
public interface AccessRightsOwner {

  /**
   * Возможные ограничения доступа (права).
   * Лучше использовать статик метод posibleRights класса.
   */
  @SuppressWarnings("unchecked")
  default List<RoleRight> getPosibleRights() {
    var value = Arrays.stream(getClass().getDeclaredMethods())
      .filter(method -> "posibleRights".equals(method.getName()))
      .findFirst();

    if (value.isEmpty()) {
      return Collections.emptyList();
    }

    try {
      return (List<RoleRight>) value.get().invoke(getClass());
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }

  /**
   * Проверяет переданное право на допустимость для применения к объекту
   */
  default boolean isValidRight(RoleRight roleRight) {
    return getPosibleRights().contains(roleRight);
  }
}
