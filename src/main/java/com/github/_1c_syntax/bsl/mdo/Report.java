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

import com.github._1c_syntax.bsl.mdo.children.ObjectCommand;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
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
import java.util.Map;

@Value
@Builder(toBuilder = true)
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class Report implements MDObject, ModuleOwner, CommandOwner, AttributeOwner, TabularSectionOwner,
  FormOwner, TemplateOwner, AccessRightsOwner {

  private static final List<RoleRight> POSSIBLE_RIGHTS = List.of(RoleRight.EDIT, RoleRight.USE, RoleRight.VIEW);

  /*
   * MDObject
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

  @Getter(lazy = true)
  List<MD> children = LazyLoader.computeChildren(this);
  @Getter(lazy = true)
  List<MD> plainChildren = LazyLoader.computePlainChildren(this);

  /*
   * ModuleOwner
   */

  @Default
  List<Module> modules = Collections.emptyList();
  @Getter(lazy = true)
  List<Module> allModules = LazyLoader.computeAllModules(this);

  /*
   * CommandOwner
   */

  @Singular
  List<ObjectCommand> commands;

  /*
   * AttributeOwner
   */

  @Singular
  List<Attribute> attributes;

  /*
   * TabularSectionOwner
   */

  @Singular
  List<TabularSection> tabularSections;

  @Getter(lazy = true)
  List<MD> storageFields = LazyLoader.computeStorageFields(this);
  @Getter(lazy = true)
  List<MD> plainStorageFields = LazyLoader.computePlainStorageFields(this);

  /*
   * FormOwner
   */

  @Singular
  List<ObjectForm> forms;

  /*
   * TemplateOwner
   */

  @Singular
  List<ObjectTemplate> templates;

  /*
   * Свое
   */

  /**
   * Ссылка на форму по умолчанию
   */
  @Default
  MdoReference defaultForm = MdoReference.EMPTY;

  /**
   * Ссылка на форму настроек по умолчанию
   */
  @Default
  MdoReference defaultSettingsForm = MdoReference.EMPTY;

  /**
   * Ссылка на форму варианта по умолчанию
   */
  @Default
  MdoReference defaultVariantForm = MdoReference.EMPTY;

  /**
   * Ссылка на дополнительную форму
   */
  @Default
  MdoReference auxiliaryForm = MdoReference.EMPTY;

  /**
   * Ссылка на дополнительную форму настроек
   */
  @Default
  MdoReference auxiliarySettingsForm = MdoReference.EMPTY;

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

  @Override
  public List<Attribute> getAllAttributes() {
    return getAttributes();
  }

  /**
   * Возвращает перечень возможных прав доступа
   */
  public static List<RoleRight> possibleRights() {
    return POSSIBLE_RIGHTS;
  }

  private Map<DefaultFormKind, MdoReference> createDefaultFormMap() {
    return Map.ofEntries(
      Map.entry(DefaultFormKind.DEFAULT_FORM, getDefaultForm()),
      Map.entry(DefaultFormKind.SETTINGS_FORM, getDefaultSettingsForm()),
      Map.entry(DefaultFormKind.VARIANT_FORM, getDefaultVariantForm()),
      Map.entry(DefaultFormKind.AUX_FORM, getAuxiliaryForm()),
      Map.entry(DefaultFormKind.AUX_SETTINGS_FORM, getAuxiliarySettingsForm())
    );
  }
}
