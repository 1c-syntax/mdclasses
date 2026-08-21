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

import com.github._1c_syntax.bsl.mdo.children.Dimension;
import com.github._1c_syntax.bsl.mdo.children.ObjectCommand;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.children.Resource;
import com.github._1c_syntax.bsl.mdo.storage.AdditionalIndex;
import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
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
import java.util.Map;

@Value
@Builder(toBuilder = true)
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class AccountingRegister implements Register {

  /*
   * Register
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
  @Singular
  List<Resource> resources;
  @Singular
  List<Dimension> dimensions;
  @Getter(lazy = true)
  List<Attribute> allAttributes = LazyLoader.computeAllAttributes(this);

  @Singular
  List<ObjectForm> forms;

  @Singular
  List<ObjectTemplate> templates;

  /**
   * Дополнительные индексы
   */
  @Singular("addAdditionalIndex")
  List<AdditionalIndex> additionalIndexes;

  @Getter(lazy = true)
  List<MD> children = LazyLoader.computeChildren(this);

  /*
   * Свое
   */

  /**
   * План счетов, к которому относится регистр
   */
  @Default
  MdoReference chartOfAccounts = MdoReference.EMPTY;

  /**
   * Ссылка на форму списка по умолчанию
   */
  @Default
  MdoReference defaultListForm = MdoReference.EMPTY;

  /**
   * Ссылка на дополнительную форму списка
   */
  @Default
  MdoReference auxiliaryListForm = MdoReference.EMPTY;

  /**
   * Возможные формы по умолчанию
   */
  @Getter(lazy = true)
  Map<DefaultFormKind, MdoReference> defaultFormMap = createDefaultFormMap();

  /**
   * Пояснение
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;

  /**
   * Режим полнотекстового поиска
   */
  @Default
  UseMode fullTextSearch = UseMode.DONT_USE;

  /**
   * Возвращает перечень возможных прав доступа
   */
  public static List<RoleRight> possibleRights() {
    return AccumulationRegister.possibleRights();
  }

  private Map<DefaultFormKind, MdoReference> createDefaultFormMap() {
    return Map.ofEntries(
      Map.entry(DefaultFormKind.LIST_FORM, getDefaultListForm()),
      Map.entry(DefaultFormKind.AUX_LIST_FORM, getAuxiliaryListForm())
    );
  }
}
