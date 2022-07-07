/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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

import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.mdclasses.mdo.metadata.MetadataStorage;

/**
 * Базовый интерфейс всех объектов метаданных
 */
public interface MDObject {

  /**
   * Возвращает тип метаданных
   */
  default MDOType getMdoType() {
    return MetadataStorage.get(getClass()).type();
  }

  /**
   * уникальный идентификатор объекта
   */
  String getUuid();

  /**
   * Имя объекта
   */
  String getName();

//  /**
//   * Синонимы объекта
//   */
//  MultiLanguageString getSynonym();

  /**
   * MDO-Ссылка на объект
   */
  MdoReference getMdoReference();

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  ObjectBelonging getObjectBelonging();

//  /**
//   * Вариант поддержки родительской конфигурации
//   */
//  SupportVariant getSupportVariant();

  /**
   * Комментарий
   */
  String getComment();

  /**
   * Возвращает представление типа метаданных
   */
  default String getMetadataName() {
    return getMdoType().getName();
  }

  /**
   * Возвращает представление типа метаданных на русском
   */
  default String getMetadataNameRu() {
    return getMdoType().getNameRu();
  }

}
