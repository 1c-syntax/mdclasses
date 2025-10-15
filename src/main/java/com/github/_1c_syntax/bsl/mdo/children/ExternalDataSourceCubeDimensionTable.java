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
import com.github._1c_syntax.bsl.mdo.AttributeOwner;
import com.github._1c_syntax.bsl.mdo.CommandOwner;
import com.github._1c_syntax.bsl.mdo.FormOwner;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.MDChild;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.TemplateOwner;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
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
public class ExternalDataSourceCubeDimensionTable implements MDChild, ModuleOwner, CommandOwner, AttributeOwner, FormOwner,
  TemplateOwner, AccessRightsOwner {

  private static final List<RoleRight> POSSIBLE_RIGHTS = computePossibleRights();

  /*
   * Для MDChild
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
  MdoReference owner = MdoReference.EMPTY;

  Lazy<List<MD>> children = new Lazy<>(this::computeChildren);

  /*
   * ModuleOwner
   */

  @Default
  List<Module> modules = Collections.emptyList();
  Lazy<List<Module>> allModules = new Lazy<>(this::computeAllModules);

  /*
   * CommandOwner
   */

  @Singular
  List<ObjectCommand> commands;

  /*
   * AttributeOwner
   */

  /**
   * Поля
   */
  @Singular
  List<ExternalDataSourceTableField> fields;

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
   * Режим управления блокировкой
   */
  @Default
  DataLockControlMode dataLockControlMode = DataLockControlMode.AUTOMATIC;

  @Override
  public List<Attribute> getAllAttributes() {
    return Collections.unmodifiableList(fields);
  }

  @Override
  public List<MD> getChildren() {
    return children.getOrCompute();
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
      RoleRight.INTERACTIVE_INSERT
    );
  }
}
