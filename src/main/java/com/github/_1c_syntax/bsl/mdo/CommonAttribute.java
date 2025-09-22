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

import com.github._1c_syntax.bsl.mdo.children.ObjectAttribute;
import com.github._1c_syntax.bsl.mdo.support.DataSeparation;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class CommonAttribute implements MDObject, AccessRightsOwner, ValueTypeOwner {

  // todo соединить с атрибутом

  /*
   * ReferenceObject
   */

  @Default
  String uuid = "";
  @Default
  String name = "";
  @Default
  MdoReference mdoReference = MdoReference.EMPTY;
  @Default
  ObjectBelonging objectBelonging = ObjectBelonging.OWN;
  @Default
  String comment = "";
  @Default
  MultiLanguageString synonym = MultiLanguageString.EMPTY;
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;

  /*
   * ValueTypeOwner
   */

  @Default
  @Getter(AccessLevel.NONE)
  ValueTypeDescription type = ValueTypeDescription.EMPTY;

  /*
   * Свое
   */

  /**
   * Автоиспользование
   */
  @Default
  UseMode autoUse = UseMode.USE;

  /**
   * Режим пароля. Только для общих реквизитов с типом с типом `Строка`
   */
  boolean passwordMode;

  /**
   * Индексирование
   */
  @Default
  IndexingType indexing = IndexingType.DONT_INDEX;

  /**
   * Разделение данных
   */
  @Default
  DataSeparation dataSeparation = DataSeparation.SEPARATE;

  /**
   * Значение разделяемых данных
   */
  @Default
  MdoReference dataSeparationValue = MdoReference.EMPTY;

  /**
   * Использование разделения данных
   */
  @Default
  MdoReference dataSeparationUse = MdoReference.EMPTY;

  /**
   * Условное разделение
   */
  @Default
  MdoReference conditionalSeparation = MdoReference.EMPTY;

  /**
   * Разделение пользователей
   */
  @Default
  DataSeparation usersSeparation = DataSeparation.SEPARATE;

  /**
   * Разделение аутентификаций
   */
  @Default
  DataSeparation authenticationSeparation = DataSeparation.SEPARATE;

  /**
   * Разделение расширений конфигурации
   */
  @Default
  DataSeparation configurationExtensionsSeparation = DataSeparation.SEPARATE;

  /**
   * Список объектов, включенных в состав общего реквизита
   */
  @Singular("addContent")
  List<UseContent> content;

  @Override
  public ValueTypeDescription getValueType() {
    return type;
  }

  /**
   * Проверяет наличие объекта в составе общего реквизита (вне зависимости от режима использования)
   *
   * @param mdoReference Ссылка на искомый объект
   * @return Признак вхождения в состав
   */
  public boolean contains(MdoReference mdoReference) {
    return content.stream().anyMatch(useContent -> useContent.getMetadata().equals(mdoReference));
  }

  /**
   * Возвращает режим использования общего реквизита для объекта метаданных. Если объект не входит в состав
   * общего реквизита, то будет возвращено UseMode.DONT_USE
   *
   * @param mdoReference Ссылка на искомый объект
   * @return Признак использования
   */
  public UseMode useMode(MdoReference mdoReference) {
    var value = content.stream()
      .filter(useContent -> useContent.getMetadata().equals(mdoReference))
      .findAny();
    return value.map(UseContent::getUse).orElse(UseMode.DONT_USE);
  }

  /**
   * Возвращает перечень возможных прав доступа
   */
  public static List<RoleRight> possibleRights() {
    return ObjectAttribute.possibleRights();
  }

  @Value
  @Builder
  public static class UseContent {

    /**
     * Ссылка на объект метаданных
     */
    @Default
    MdoReference metadata = MdoReference.EMPTY;

    /**
     * Режим использования
     */
    @Default
    UseMode use = UseMode.USE;
  }
}
