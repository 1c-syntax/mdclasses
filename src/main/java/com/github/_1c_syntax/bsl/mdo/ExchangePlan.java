/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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

import com.github._1c_syntax.bsl.mdo.children.ObjectCommand;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.support.AutoRecordType;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MdoReference;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class ExchangePlan implements ReferenceObject {

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

  @Default
  List<Module> modules = Collections.emptyList();

  @Singular
  List<ObjectCommand> commands;

  @Singular
  List<Attribute> attributes;

  @Singular
  List<TabularSection> tabularSections;

  @Singular
  List<ObjectForm> forms;

  @Singular
  List<ObjectTemplate> templates;

  /*
   * Свое
   */

  /**
   * Распределенная информационная база
   */
  boolean distributedInfoBase;

  /**
   * Включать расширения информационной базы
   */
  boolean includeConfigurationExtensions;

  /**
   * Список объектов, включенных в состав плана обмена
   */
  @Singular("addContent")
  List<RecordContent> content;

  /**
   * Пояснение
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;

  /**
   * Проверяет наличие объекта в составе плана обмена (вне зависимости от режима авторегистрации)
   *
   * @param mdoReference Ссылка на искомый объект
   * @return Признак вхождения в состав
   */
  public boolean contains(MdoReference mdoReference) {
    return content.stream().anyMatch(useContent -> useContent.getMetadata().equals(mdoReference));
  }

  /**
   * Возвращает режим авторегистрации в плане обмена для объекта метаданных. Если объект не входит в состав
   * общего реквизита, то будет возвращено AutoRecordType.DENY
   *
   * @param mdoReference Ссылка на искомый объект
   * @return Признак использования
   */
  public AutoRecordType autoRecord(MdoReference mdoReference) {
    var value = content.stream()
      .filter(useContent -> useContent.getMetadata().equals(mdoReference))
      .findAny();
    if (value.isPresent()) {
      return value.get().getAutoRecord();
    } else {
      return AutoRecordType.DENY;
    }
  }

  @Value
  @Builder
  public static class RecordContent {

    /**
     * Ссылка на объект метаданных
     */
    @Default
    MdoReference metadata = MdoReference.EMPTY;

    /**
     * Режим авторегистрации
     */
    @Default
    AutoRecordType autoRecord = AutoRecordType.DENY;
  }
}
