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
package com.github._1c_syntax.bsl.mdclasses.helpers;

import com.github._1c_syntax.bsl.mdclasses.CF;
import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdo.AccessRightsOwner;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.storage.RoleData;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.types.MdoReference;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Помощник для работы с ролями и правами
 */
@UtilityClass
public class Rights {

  private static final Map<String, List<RoleRight>> POSSIBLE_RIGHTS = computePossibleRights();

  /**
   * Проверяет наличие указанного разрешения хотя бы у одной роли для конфигурации/расширения
   *
   * @param cf        Конфигурация/расширение
   * @param roleRight Право доступа
   * @return Наличие права доступа
   */
  public boolean rightAccess(CF cf, RoleRight roleRight) {
    return rightAccess(cf, cf.getMdoReference(), roleRight);
  }

  /**
   * Проверяет наличие указанного разрешения хотя бы у одной роли для MD
   *
   * @param cf        Конфигурация/расширение
   * @param roleRight Право доступа
   * @param md        Любой объект md
   * @return Наличие права доступа
   */
  public boolean rightAccess(CF cf, RoleRight roleRight, MD md) {
    return rightAccess(cf, md.getMdoReference(), roleRight);
  }

  /**
   * Проверяет наличие указанного разрешения хотя бы у одной роли для ссылки
   *
   * @param cf           Конфигурация/расширение
   * @param roleRight    Право доступа
   * @param mdoReference Ссылка mdo reference
   * @return Наличие права доступа
   */
  public boolean rightAccess(CF cf, RoleRight roleRight, MdoReference mdoReference) {
    return rightAccess(cf, mdoReference, roleRight);
  }

  /**
   * Возвращает список ролей, имеющих указанное разрешения для конфигурации/расширения
   *
   * @param cf        Конфигурация/расширение
   * @param roleRight Право доступа
   * @return Список ролей с правом
   */
  public List<Role> rolesAccess(CF cf, RoleRight roleRight) {
    return rolesAccess(cf, cf.getMdoReference(), roleRight);
  }

  /**
   * Возвращает список ролей, имеющих указанное разрешения для md
   *
   * @param cf        Конфигурация/расширение
   * @param roleRight Право доступа
   * @param md        Любой объект md
   * @return Список ролей с правом
   */
  public List<Role> rolesAccess(CF cf, RoleRight roleRight, MD md) {
    return rolesAccess(cf, md.getMdoReference(), roleRight);
  }

  /**
   * Возвращает список ролей, имеющих указанное разрешения для ссылки
   *
   * @param cf           Конфигурация/расширение
   * @param roleRight    Право доступа
   * @param mdoReference Ссылка mdo reference
   * @return Список ролей с правом
   */
  public List<Role> rolesAccess(CF cf, RoleRight roleRight, MdoReference mdoReference) {
    return rolesAccess(cf, mdoReference, roleRight);
  }

  /**
   * Возможные ограничения доступа (права).
   * Лучше использовать статик метод possibleRights класса.
   */
  public List<RoleRight> getPossibleRights(Class<? extends AccessRightsOwner> clazz) {
    return POSSIBLE_RIGHTS.getOrDefault(clazz.getName(), Collections.emptyList());
  }

  /**
   * Проверяет переданное право на допустимость для применения к объекту
   */
  public boolean isValidRight(Class<? extends AccessRightsOwner> clazz, RoleRight roleRight) {
    return getPossibleRights(clazz).contains(roleRight);
  }

  private static boolean rightAccess(CF cf, MdoReference mdoReference, RoleRight roleRight) {
    if (cf.equals(Configuration.EMPTY)) {
      return false;
    }
    return cf.getRoles().stream()
      .map(Role::getData)
      .filter(roleData -> !roleData.equals(RoleData.EMPTY))
      .map(RoleData::getObjectRights)
      .flatMap(Collection::stream)
      .filter(objectRight -> objectRight.getName().equals(mdoReference.getMdoRef()))
      .map(RoleData.ObjectRight::getRights)
      .flatMap(Collection::stream)
      .anyMatch(right -> roleRight == right.getName() && right.isValue());
  }

  private static List<Role> rolesAccess(CF cf, MdoReference mdoReference, RoleRight roleRight) {
    if (cf.equals(Configuration.EMPTY)) {
      return Collections.emptyList();
    }

    var mdoRef = mdoReference.getMdoRef();
    List<Role> roles = new ArrayList<>();
    cf.getRoles().forEach((Role role) -> {
      var hasAcccess = role.getData().getObjectRights().stream()
        .filter(objectRight -> objectRight.getName().equals(mdoRef))
        .map(RoleData.ObjectRight::getRights)
        .flatMap(Collection::stream)
        .anyMatch(right -> roleRight == right.getName() && right.isValue());

      if (hasAcccess) {
        roles.add(role);
      }
    });

    return Collections.unmodifiableList(roles);
  }

  private static Map<String, List<RoleRight>> computePossibleRights() {
    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .acceptPackages("com.github._1c_syntax.bsl")
      .scan()) {

      var classes = scanResult.getClassesImplementing(AccessRightsOwner.class);
      Map<String, List<RoleRight>> rights = new HashMap<>();

      classes.stream()
        .filter(ci -> !ci.isInterface())
        .map(ClassInfo::getName)
        .forEach(name -> rights.put(name, getPossibleRights(name)));
      return Collections.unmodifiableMap(rights);
    }
  }

  @SuppressWarnings("unchecked")
  private List<RoleRight> getPossibleRights(String className) {
    Class<AccessRightsOwner> clazz;
    try {
      clazz = (Class<AccessRightsOwner>) Class.forName(className);
    } catch (ClassNotFoundException e) {
      return Collections.emptyList();
    }

    var value = Arrays.stream(clazz.getDeclaredMethods())
      .filter(method -> "possibleRights".equals(method.getName()))
      .findFirst();

    if (value.isPresent()) {
      try {
        var result = value.get().invoke(clazz);
        if (result instanceof List<?> rights) {
          return rights.stream()
            .filter(RoleRight.class::isInstance)
            .map(RoleRight.class::cast)
            .toList();
        }
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        // no-op
      }
    }

    return Collections.emptyList();
  }
}
