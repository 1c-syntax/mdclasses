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

import com.github._1c_syntax.bsl.mdo.children.ObjectCommand;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.mdo.utils.LazyLoader;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.github._1c_syntax.utils.Lazy;
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
public class Catalog implements ReferenceObject, AccessRightsOwner {

  private static final List<RoleRight> POSSIBLE_RIGHTS = computePossibleRights();

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
  Lazy<List<Module>> allModules = new Lazy<>(this::computeAllModules);

  @Singular
  List<ObjectCommand> commands;

  @Singular
  List<Attribute> attributes;

  @Singular
  List<TabularSection> tabularSections;

  Lazy<List<MD>> storageFields = new Lazy<>(this::computeStorageFields);
  Lazy<List<MD>> plainStorageFields = new Lazy<>(this::computePlainStorageFields);

  @Singular
  List<ObjectForm> forms;

  @Singular
  List<ObjectTemplate> templates;

  Lazy<List<MD>> children = new Lazy<>(this::computeChildren);
  Lazy<List<MD>> plainChildren = new Lazy<>(this::computePlainChildren);

  /*
   * Свое
   */

  /**
   * Пояснение
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;

  /**
   * Список владельцев
   */
  @Singular("addOwners")
  List<MdoReference> owners;

  /**
   * Retrieve the catalog's child metadata objects.
   *
   * @return the list of child MD objects for this catalog, computed on first access and cached for subsequent calls
   */
  @Override
  public List<MD> getChildren() {
    return children.getOrCompute();
  }

  @Override
  public List<MD> getPlainChildren() {
    return plainChildren.getOrCompute();
  }

  @Override
  public List<MD> getStorageFields() {
    return storageFields.getOrCompute();
  }

  @Override
  public List<MD> getPlainStorageFields() {
    return plainStorageFields.getOrCompute();
  }

  @Override
  public List<Module> getAllModules() {
    return allModules.getOrCompute();
  }

  /**
   * Возвращает перечень возможных прав доступа
   */
  public static List<RoleRight> possibleRights() {
    return POSSIBLE_RIGHTS;
  }

  private List<MD> computeChildren() {
    return LazyLoader.computeChildren(this);
  }

  private List<MD> computePlainChildren() {
    return LazyLoader.computePlainChildren(this);
  }

  private List<MD> computeStorageFields() {
    return LazyLoader.computeStorageFields(this);
  }

  private List<MD> computePlainStorageFields() {
    return LazyLoader.computePlainStorageFields(this);
  }

  private List<Module> computeAllModules() {
    return LazyLoader.computeAllModules(this);
  }

  private static List<RoleRight> computePossibleRights() {
    return List.of(
      RoleRight.INSERT,
      RoleRight.READ,
      RoleRight.UPDATE,
      RoleRight.DELETE,
      RoleRight.VIEW,
      RoleRight.EDIT,
      RoleRight.INPUT_BY_STRING,
      RoleRight.INTERACTIVE_DELETE,
      RoleRight.INTERACTIVE_INSERT,
      RoleRight.INTERACTIVE_SET_DELETION_MARK,
      RoleRight.INTERACTIVE_CLEAR_DELETION_MARK,
      RoleRight.INTERACTIVE_DELETE_MARKED,
      RoleRight.INTERACTIVE_DELETE_PREDEFINED_DATA,
      RoleRight.INTERACTIVE_SET_DELETION_MARK_PREDEFINED_DATA,
      RoleRight.INTERACTIVE_CLEAR_DELETION_MARK_PREDEFINED_DATA,
      RoleRight.INTERACTIVE_DELETE_MARKED_PREDEFINED_DATA,
      RoleRight.READ_DATA_HISTORY,
      RoleRight.VIEW_DATA_HISTORY,
      RoleRight.READ_DATA_HISTORY_OF_MISSING_DATA,
      RoleRight.UPDATE_DATA_HISTORY,
      RoleRight.UPDATE_DATA_HISTORY_OF_MISSING_DATA,
      RoleRight.UPDATE_DATA_HISTORY_SETTINGS,
      RoleRight.UPDATE_DATA_HISTORY_VERSION_COMMENT,
      RoleRight.EDIT_DATA_HISTORY_VERSION_COMMENT,
      RoleRight.SWITCH_TO_DATA_HISTORY_VERSION
    );
  }
}