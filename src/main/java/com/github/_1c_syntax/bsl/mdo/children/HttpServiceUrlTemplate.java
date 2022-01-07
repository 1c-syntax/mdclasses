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
package com.github._1c_syntax.bsl.mdo.children;

import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MDOType;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
@ToString(of = {"name", "uuid", "mdoReference"})
@EqualsAndHashCode(of = {"name", "uuid", "mdoReference"})
public class HttpServiceUrlTemplate implements MDObject, MDChildObject, ChildrenOwner {
  /**
   * MDObject
   */

  /**
   * Тип метаданных
   */
  static final MDOType mdoType = MDOType.HTTP_SERVICE_URL_TEMPLATE;

  /**
   * Уникальный идентификатор
   */
  String uuid;

  /**
   * Имя
   */
  String name;

  /**
   * Синонимы объекта
   */
  @Default
  MultiLanguageString synonym = MultiLanguageString.EMPTY;

  /**
   * MDO-Ссылка на объект
   */
  @Default
  MdoReference mdoReference = MdoReference.EMPTY;

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  @Default
  ObjectBelonging objectBelonging = ObjectBelonging.OWN;

  /**
   * Вариант поддержки родительской конфигурации
   */
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;

  /**
   * Комментарий
   */
  @Default
  String comment = "";

  /**
   * MDChildObject
   */

  /**
   * Родительский объект
   */
  @Default
  MdoReference owner = MdoReference.EMPTY;

  /**
   * ChildrenOwner
   */

  @Default
  List<MDObject> children = Collections.emptyList();

  /**
   * Custom
   */

  /**
   * Возвращает список методов шаблона URL
   */
  public List<HttpServiceMethod> getHttpServiceMethods() {
    return getChildren().stream()
      .filter(HttpServiceMethod.class::isInstance)
      .map(HttpServiceMethod.class::cast)
      .collect(Collectors.toList());
  }

  /**
   * Custom
   */

  /**
   * MDObject
   */

  @Override
  public MDOType getMdoType() {
    return mdoType;
  }
}
