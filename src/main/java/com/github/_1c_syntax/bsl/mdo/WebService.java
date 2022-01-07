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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.children.WebServiceOperation;
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
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class WebService implements MDObject, ModuleOwner, ChildrenOwner {

  /**
   * MDObject
   */

  /**
   * Тип метаданных
   */
  static final MDOType mdoType = MDOType.WEB_SERVICE;

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
   * ModuleOwner
   */

  /**
   * Список модулей объекта
   */
  @Default
  List<Module> modules = Collections.emptyList();

  /**
   * ChildrenOwner
   */

  @Default
  List<MDObject> children = Collections.emptyList();

  /**
   * Custom
   */

  /**
   * Пространство имен сервиса
   */
  @Default
  String namespace = "";

  /**
   * Используемые XDTO пакеты
   */
  @Default
  List<String> xdtoPackages = Collections.emptyList(); // todo пока не читается

  /**
   * Имя файла публикации
   */
  @Default
  String descriptorFileName = "";

  /**
   * Повторное использование сеанса
   */
  @Default
  String reuseSessions = "";

  /**
   * Время жизни сеанса
   */
  int sessionMaxAge;

  /**
   * MDObject
   */

  @Override
  public MDOType getMdoType() {
    return mdoType;
  }

  /**
   * Custom
   */

  /**
   * Возвращает список операций вебсервиса
   */
  public List<WebServiceOperation> getWebServiceOperations() {
    return getChildren().stream()
      .filter(WebServiceOperation.class::isInstance)
      .map(WebServiceOperation.class::cast)
      .collect(Collectors.toList());
  }
}
