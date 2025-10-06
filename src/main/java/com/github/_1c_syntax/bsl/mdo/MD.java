/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.github._1c_syntax.bsl.types.ScriptVariant;

/**
 * Базовый интерфейс всех объектов метаданных, как самостоятельных, так и дочерних
 */
public interface MD {

  /**
   * Возвращает тип метаданных
   */
  default MDOType getMdoType() {
    var mdoType = MDOType.fromValue(getClass().getSimpleName());
    return mdoType.orElse(MDOType.UNKNOWN);
  }

  /**
   * Уникальный идентификатор объекта
   */
  String getUuid();

  /**
   * Имя объекта
   */
  String getName();

  /**
   * Синонимы объекта
   */
  MultiLanguageString getSynonym();

  /**
   * MDO-Ссылка на объект
   */
  MdoReference getMdoReference();

  /**
   * Строковое представление MDO-Ссылки на объект, локализованное для указанного языка разработки
   */
  default String getMdoRef(ScriptVariant scriptVariant) {
    var mdoReference = getMdoReference();
    if (scriptVariant == ScriptVariant.ENGLISH) {
      return mdoReference.getMdoRef();
    } else {
      return mdoReference.getMdoRefRu();
    }
  }

  /**
   * Строковое представление MDO-Ссылки на объект
   */
  default String getMdoRef() {
    return getMdoReference().getMdoRef();
  }

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  ObjectBelonging getObjectBelonging();

  /**
   * Вариант поддержки родительской конфигурации
   */
  SupportVariant getSupportVariant();

  /**
   * Комментарий
   */
  String getComment();

  /**
   * Представление объекта, формируемое на основании синонима для русского языка.
   * Если синонима для русского нет, вернет иной синоним при его наличии. В противном случае вернет имя.
   */
  default String getDescription() {
    return getDescription("ru");
  }

  /**
   * Представление объекта, формируемое на основании синонима для указанного языка.
   * Если синонима для указанного языка нет, вернет иной синоним при его наличии. В противном случае вернет имя.
   *
   * @param code Код языка
   */
  default String getDescription(String code) {
    if (getSynonym().isEmpty()) {
      return getName();
    }
    var description = getSynonym().get(code);
    if (description.isEmpty()) {
      description = getSynonym().getAny();
    }

    if (description.isEmpty()) {
      description = getName();
    }

    return description;
  }
}
