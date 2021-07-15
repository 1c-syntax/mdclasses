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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.types.ConfigurationSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface MDClass {
  /**
   * уникальный идентификатор объекта
   */
  String getUuid();

  /**
   * Имя объекта
   */
  String getName();

  /**
   * Дочерние объекты
   */
  Set<MDObject> getChildren();

  /**
   * Вариант исходников
   */
  ConfigurationSource getConfigurationSource();

//  /**
//   * Модули в связке со ссылкой на файлы
//   */
//  Map<URI, ModuleType> getModulesByType();
//
//  /**
//   * Модули в связке со ссылкой на файлы, сгруппированные по представлению mdoRef
//   */
//  Map<String, Map<ModuleType, URI>> getModulesByMDORef();
//
//  /**
//   * Объекты в связке со ссылкой на файлы
//   */
//  Map<URI, MDObject> getModulesByObject();
//
//  /**
//   * Модули
//   */
//  List<Module> getModules();
//
//  /**
//   * Дочерние объекты конфигурации с MDO ссылками на них
//   */
//  Map<MdoReference, MDObject> getChildrenByMdoRef();
//
//  /**
//   * Возвращает тип модуля по ссылке на его файл
//   */
//  ModuleType getModuleType(URI uri);
//
//  /**
//   * Модули объекта в связке со ссылкой на файлы по ссылке mdoRef
//   *
//   * @param mdoRef Строковая ссылка на объект
//   * @return Соответствие ссылки на файл и его тип
//   */
//  Map<ModuleType, URI> getModulesByMDORef(String mdoRef);
//
//  /**
//   * Модули объекта в связке со ссылкой на файлы по ссылке mdoRef
//   *
//   * @param mdoRef Ссылка на объект
//   * @return Соответствие ссылки на файл и его тип
//   */
//  Map<ModuleType, URI> getModulesByMDORef(MdoReference mdoRef);

  /**
   * Получение списка дочерних элементов по типу
   *
   * @param clazz Класс дочернего элемента
   * @param <T>   Тип дочернего элемента
   * @return Список дочерних элементов
   */
  default <T extends MDObject> List<T> getChildrenByType(Class<T> clazz) {
    return getChildren().stream().filter(clazz::isInstance).map(clazz::cast)
      .collect(Collectors.toUnmodifiableList());
  }
}
