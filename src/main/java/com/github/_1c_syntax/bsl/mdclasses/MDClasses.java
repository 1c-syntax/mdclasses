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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@UtilityClass
public class MDClasses {

//  /**
//   * Создает пустую конфигурацию
//   *
//   * @return Пустая конфигурация
//   */
//  public MDClass createConfiguration() {
//    return Configuration.EMPTY;
//  }
//
//  /**
//   * Создает конфигурацию или расширение по указанному пути
//   *
//   * @param path Путь к корню проекта
//   * @return Конфигурация или расширение
//   */
//  public MDClass createConfiguration(Path path) {
//    return createConfiguration(path, false);
//  }
//
//  /**
//   * Создает конфигурацию или расширение по указанному пути
//   *
//   * @param path        Путь к корню проекта
//   * @param skipSupport Флаг управления чтением информации о поддержке
//   * @return Конфигурация или расширение
//   */
//  public MDClass createConfiguration(Path path, boolean skipSupport) {
//    return MDOReader.readConfiguration(path, skipSupport);
//  }

  // Для обратной совместимости, временно
  // todo убрать после переезда

  /**
   * Читает объект по его файлу описания, а также его дочерние при наличии
   *
   * @param rootPath каталог конфигурации
   * @param fullName полное имя MDO
   * @return контейнер с прочитанным объектом
   */
  public Optional<MDObject> readMDObject(Path rootPath, String fullName) {
    var mdo = Optional.ofNullable(MDOReader.readMDObject(rootPath, fullName));
    if (mdo.isPresent() && mdo.get() instanceof AbstractMDObjectBase) {
      ((AbstractMDObjectBase) mdo.get()).supplement();
    }
    return mdo;
  }

  /**
   * Читает объект по его файлу описания, а также его дочерние при наличии
   *
   * @param rootPath каталог конфигурации строкой
   * @param fullName полное имя MDO
   * @return контейнер с прочитанным объектом
   */
  public Optional<MDObject> readMDObject(String rootPath, String fullName) {
    return readMDObject(Paths.get(rootPath), fullName);
  }
}
