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
package com.github._1c_syntax.bsl.mdo.children;

import com.github._1c_syntax.bsl.mdo.AccessRightsOwner;
import com.github._1c_syntax.bsl.mdo.Attribute;
import com.github._1c_syntax.bsl.mdo.support.AttributeKind;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.github._1c_syntax.bsl.types.MultiName;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

import java.util.List;

/**
 * Стандартный реквизит объекта
 */
@Value
@Builder
@ToString(of = {"uuid", "fullName"})
@EqualsAndHashCode(of = {"uuid", "fullName"})
public class StandardAttribute implements Attribute, AccessRightsOwner {

  private static final List<RoleRight> POSSIBLE_RIGHTS = List.of(RoleRight.VIEW, RoleRight.EDIT);

  /*
   * Для Attribute
   */

  /**
   * Равен UID владельца
   */
  @Default
  String uuid = "";
  @Default
  MdoReference mdoReference = MdoReference.EMPTY;
  @Default
  String comment = "";
  @Default
  MultiLanguageString synonym = MultiLanguageString.EMPTY;
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;
  @Default
  MdoReference owner = MdoReference.EMPTY;
  boolean passwordMode;
  @Default
  AttributeKind kind = AttributeKind.STANDARD;
  @Default
  @Getter(AccessLevel.NONE)
  ValueTypeDescription type = ValueTypeDescription.EMPTY;

  /*
   * Свое
   */

  /**
   * Полное имя реквизита на обоих языках
   */
  @Default
  MultiName fullName = MultiName.EMPTY;

  /*
   * Свое
   */

  /**
   * Формат
   */
  @Default
  MultiLanguageString format = MultiLanguageString.EMPTY;

  /**
   * Формат редактирования
   */
  @Default
  MultiLanguageString editFormat = MultiLanguageString.EMPTY;

  /**
   * Выделять отрицательное
   */
  boolean markNegatives;

  /**
   * Маска
   */
  @Default
  String mask = "";

  /**
   * Многострочный режим
   */
  boolean multiLine;

  /**
   * Расширенное редактирование
   */
  boolean extendedEdit;

  /**
   * Заполнять из данных заполнения
   */
  boolean fillFromFillingValue;

  /**
   * Возвращает перечень возможных прав доступа
   */
  public static List<RoleRight> possibleRights() {
    return POSSIBLE_RIGHTS;
  }

  /**
   * Всегда собственный
   */
  @Override
  public ObjectBelonging getObjectBelonging() {
    return ObjectBelonging.OWN;
  }

  /**
   * Настроек индексирования у стандартных нет
   */
  @Override
  public IndexingType getIndexing() {
    return IndexingType.DONT_INDEX;
  }

  @Override
  public String getName() {
    return fullName.get();
  }

  @Override
  public ValueTypeDescription getValueType() {
    return type;
  }

  @Override
  public String getDescription(String code) {
    if (getSynonym().isEmpty()) {
      return fullName.get(code);
    }
    var description = getSynonym().get(code);
    if (description.isEmpty()) {
      description = getSynonym().getAny();
    }

    if (description.isEmpty()) {
      description = fullName.get(code);
    }

    return description;
  }
}
