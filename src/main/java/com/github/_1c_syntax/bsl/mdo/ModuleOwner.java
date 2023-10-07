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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.types.ModuleType;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Расширение - владелец модулей с исходным кодом
 */
public interface ModuleOwner extends MD {
  /**
   * Список модулей объекта
   */
  List<Module> getModules();

  /**
   * Список модулей объекта, включая дочерние
   */
  default List<Module> getAllModules() {
    var modules = new ArrayList<>(getModules());
    if (this instanceof ChildrenOwner) {
      modules.addAll(((ChildrenOwner) this).getPlainChildren().stream()
        .filter(ModuleOwner.class::isInstance)
        .map(ModuleOwner.class::cast)
        .map(ModuleOwner::getModules)
        .flatMap(List::stream)
        .collect(Collectors.toList()));
    }

    return modules;
  }

  /**
   * Ищет модуль по адресу его файла
   *
   * @param uri Адрес файла модуля
   * @return Контейнер с найденным файлом
   */
  default Optional<Module> getModuleByUri(URI uri) {
    return getAllModules().stream().filter(module -> module.getUri().equals(uri)).findFirst();
  }

  /**
   * Возвращает тип модуля по URI
   */
  default ModuleType getModuleTypeByURI(URI uri) {
    return getModuleByUri(uri).map(Module::getModuleType).orElse(ModuleType.UNKNOWN);
  }

  /**
   * Возвращает соответствие типов модулей их путям к файлам
   */
  default Map<ModuleType, URI> getModuleTypes() {
    return getModules().stream().collect(Collectors.toMap(Module::getModuleType, Module::getUri));
  }
}
