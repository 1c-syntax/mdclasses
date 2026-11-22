/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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

import com.github._1c_syntax.bsl.mdclasses.helpers.Rights;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;

import java.util.List;

/**
 * Расширение - объект имеет ограничения доступа пользователе
 */
public interface AccessRightsOwner {

  /**
   * Возможные ограничения доступа (права).
   * Лучше использовать статик метод possibleRights класса.
   */
  default List<RoleRight> getPossibleRights() {
    return Rights.getPossibleRights(getClass());
  }

  /**
   * Проверяет переданное право на допустимость для применения к объекту
   */
  default boolean isValidRight(RoleRight roleRight) {
    return Rights.isValidRight(getClass(), roleRight);
  }
}
