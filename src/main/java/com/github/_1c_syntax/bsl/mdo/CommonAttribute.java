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

import com.github._1c_syntax.bsl.mdo.support.AttributeKind;
import com.github._1c_syntax.bsl.mdo.support.DataSeparation;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.mdo.support.ValueType;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MDOType;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class CommonAttribute implements Attribute {

  /**
   * Attribute
   */

  /**
   * Тип метаданных
   */
  static final MDOType mdoType = MDOType.COMMON_ATTRIBUTE;

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
   * Режим пароля. Только для констант с типом `Строка`
   */
  boolean passwordMode;

  /**
   * Вид атрибута
   */
  static final AttributeKind kind = AttributeKind.COMMON;

  /**
   * Вариант индексирования реквизита
   */
  static final IndexingType indexing = IndexingType.DONT_INDEX;

  /**
   * Тип значения
   */
  @Default
  ValueType type = ValueType.EMPTY;

  /**
   * Custom
   */

  /**
   * Признак автоиспользования общего реквизита
   */
  @Default
  UseMode autoUse = UseMode.DONT_USE;

  /**
   * Признак использования общего реквизита как разделителя данных
   */
  @Default
  DataSeparation dataSeparation = DataSeparation.DONT_USE;

  /**
   * Объекты, использующие общий реквизит
   */
  @Default
  List<MdoReference> content = Collections.emptyList();

  /**
   * Использовать полнотекстовый поиск
   */
  @Default
  UseMode fullTextSearch = UseMode.DONT_USE;

  // todo описания

  @Default
  String format = "";

  @Default
  String editFormat = "";

  @Default
  String toolTip = "";

  boolean markNegatives;

  @Default
  String mask = "";

  boolean multiLine;

  boolean extendedEdit;

  @Default
  String minValue = "";

  @Default

  String maxValue = "";

  boolean fillFromFillingValue;

  @Default
  String fillValue = "";

  @Default
  String fillChecking = "";

  @Default
  String choiceFoldersAndItems = "";

  @Default
  List<String> choiceParameterLinks = Collections.emptyList();

  @Default
  List<String> choiceParameters = Collections.emptyList();

  @Default
  String quickChoice = "";

  @Default
  String createOnInput = "";

  @Default
  String choiceForm = "";

  @Default
  String linkByType = "";

  @Default
  String choiceHistoryOnInput = "";

  @Default
  String separatedDataUse = "";

  @Default
  String dataSeparationValue = "";

  @Default
  String dataSeparationUse = "";

  @Default
  String conditionalSeparation = "";

  @Default
  String usersSeparation = "";

  @Default
  String authenticationSeparation = "";

  @Default
  String configurationExtensionsSeparation = "";

  /**
   * Attribute
   */

  @Override
  public MDOType getMdoType() {
    return mdoType;
  }

  @Override
  public AttributeKind getKind() {
    return kind;
  }

  @Override
  public IndexingType getIndexing() {
    return indexing;
  }

}
