/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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

import com.github._1c_syntax.bsl.mdo.children.AccountingFlag;
import com.github._1c_syntax.bsl.mdo.children.ExtDimensionAccountingFlag;
import com.github._1c_syntax.bsl.mdo.children.ObjectCommand;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.support.CodeSeries;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.mdo.utils.LazyLoader;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@Builder(toBuilder = true)
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class ChartOfAccounts implements ReferenceObject, AccessRightsOwner {

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
  @Getter(lazy = true)
  List<Module> allModules = LazyLoader.computeAllModules(this);

  @Singular
  List<ObjectCommand> commands;

  @Singular
  List<Attribute> attributes;
  @Getter(lazy = true)
  List<Attribute> allAttributes = LazyLoader.computeAllAttributes(this);

  @Singular
  List<TabularSection> tabularSections;

  @Getter(lazy = true)
  List<MD> storageFields = LazyLoader.computeStorageFields(this);
  @Getter(lazy = true)
  List<MD> plainStorageFields = LazyLoader.computePlainStorageFields(this);

  @Singular
  List<ObjectForm> forms;

  @Singular
  List<ObjectTemplate> templates;

  @Getter(lazy = true)
  List<MD> children = LazyLoader.computeChildren(this);
  @Getter(lazy = true)
  List<MD> plainChildren = LazyLoader.computePlainChildren(this);

  /*
   * Свое
   */

  /**
   * Признаки учета
   */
  @Singular
  List<AccountingFlag> accountingFlags;

  /**
   * Признаки учета субконто
   */
  @Singular
  List<ExtDimensionAccountingFlag> extDimensionAccountingFlags;

  /**
   * Пояснение
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;

  /**
   * Проверять уникальность кода плана счетов.
   * Определяет, нужно ли проверять уникальность кода плана счетов.
   * Если значение равно true, то код плана счетов должен быть уникальным в пределах области,
   * определяемой свойством {@link #codeSeries}. Если false, проверка уникальности не выполняется.
   */
  @Default
  boolean checkUnique = false;

  /**
   * Серия кодов плана счетов.
   * Определяет область действия уникальности кода плана счетов.
   * Значение по умолчанию: {@link CodeSeries#WHOLE_CATALOG}.
   * Для формата EDT: если поле отсутствует, автоматически устанавливается значение WHOLE_CATALOG.
   */
  @Default
  CodeSeries codeSeries = CodeSeries.WHOLE_CATALOG;

  /**
   * Возвращает перечень возможных прав доступа
   */
  public static List<RoleRight> possibleRights() {
    return Catalog.possibleRights();
  }
}
