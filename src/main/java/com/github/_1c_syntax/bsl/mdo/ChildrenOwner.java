/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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

import com.github._1c_syntax.bsl.types.MdoReference;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Расширение - владелец дочерних объектов
 */
public interface ChildrenOwner {

  /**
   * Возвращает все дочерние элементы объекта
   */
  List<MD> getChildren();

  /**
   * Возвращает дочерние элементы объекта плоским списком.
   */
  default List<MD> getPlainChildren() {
    List<MD> children = new ArrayList<>(getChildren());
    getChildren().stream()
      .filter(ChildrenOwner.class::isInstance)
      .map(ChildrenOwner.class::cast)
      .forEach(mdObject ->
        children.addAll(mdObject.getPlainChildren())
      );

    return children;
  }

  /**
   * Выполняет поиск дочернего (включая все уровни) объекта по ссылке
   *
   * @param ref Ссылка MdoReference на искомый объект
   * @return Контейнер с найденным значением (может быть пустым)
   */
  default Optional<MD> findChild(MdoReference ref) {
    return getPlainChildren().stream()
      .filter(mdObject -> mdObject.getMdoReference().equals(ref))
      .findFirst();
  }

  /**
   * Выполняет поиск дочернего (включая все уровни) объекта по ссылке, переданной строкой
   *
   * @param mdoRef Строковое представление ссылки MdoReference на искомый объект
   * @return Контейнер с найденным значением (может быть пустым)
   */
  default Optional<MD> findChild(String mdoRef) {
    return getPlainChildren().stream()
      .filter(mdObject -> mdObject.getMdoReference().getMdoRef().equalsIgnoreCase(mdoRef)
        || mdObject.getMdoReference().getMdoRefRu().equalsIgnoreCase(mdoRef))
      .findFirst();
  }

  /**
   * Выполняет поиск дочернего (включая все уровни) объекта по ссылке на его модуль
   *
   * @param uri Ссылка на модуль исходного объекта
   * @return Контейнер с найденным значением (может быть пустым)
   */
  default Optional<MD> findChild(URI uri) {
    return getPlainChildren().stream()
      .filter(ModuleOwner.class::isInstance)
      .filter(mdObject -> ((ModuleOwner) mdObject).getModuleByUri(uri).isPresent())
      .findFirst();
  }
}
