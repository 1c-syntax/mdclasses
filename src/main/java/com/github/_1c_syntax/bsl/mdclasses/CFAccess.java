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
package com.github._1c_syntax.bsl.mdclasses;


import com.github._1c_syntax.bsl.mdclasses.helpers.Rights;
import com.github._1c_syntax.bsl.mdo.AccessRightsOwner;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.types.MdoReference;

import java.util.List;

/**
 * Расширение - права доступа
 */
public interface CFAccess extends AccessRightsOwner {

  /**
   * Проверяет наличие указанного разрешения хотя бы у одной роли для конфигурации/расширения
   *
   * @param roleRight Право доступа
   * @return Наличие права доступа
   */
  default boolean rightAccess(RoleRight roleRight) {
    return Rights.rightAccess((CF) this, roleRight);
  }

  /**
   * Проверяет наличие указанного разрешения хотя бы у одной роли для MD
   *
   * @param roleRight Право доступа
   * @param md        Любой объект md
   * @return Наличие права доступа
   */
  default boolean rightAccess(RoleRight roleRight, MD md) {
    return Rights.rightAccess((CF) this, roleRight, md);
  }

  /**
   * Проверяет наличие указанного разрешения хотя бы у одной роли для ссылки
   *
   * @param roleRight    Право доступа
   * @param mdoReference Ссылка mdo reference
   * @return Наличие права доступа
   */
  default boolean rightAccess(RoleRight roleRight, MdoReference mdoReference) {
    return Rights.rightAccess((CF) this, roleRight, mdoReference);
  }

  /**
   * Возвращает список ролей, имеющих указанное разрешения для конфигурации/расширения
   *
   * @param roleRight Право доступа
   * @return Список ролей с правом
   */
  default List<Role> rolesAccess(RoleRight roleRight) {
    return Rights.rolesAccess((CF) this, roleRight);
  }

  /**
   * Возвращает список ролей, имеющих указанное разрешения для md
   *
   * @param roleRight Право доступа
   * @param md        Любой объект md
   * @return Список ролей с правом
   */
  default List<Role> rolesAccess(RoleRight roleRight, MD md) {
    return Rights.rolesAccess((CF) this, roleRight, md);
  }

  /**
   * Возвращает список ролей, имеющих указанное разрешения для ссылки
   *
   * @param roleRight    Право доступа
   * @param mdoReference Ссылка mdo reference
   * @return Список ролей с правом
   */
  default List<Role> rolesAccess(RoleRight roleRight, MdoReference mdoReference) {
    return Rights.rolesAccess((CF) this, roleRight, mdoReference);
  }
}
