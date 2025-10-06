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
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import com.github._1c_syntax.utils.Lazy;
import lombok.AccessLevel;
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
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class ChartOfCharacteristicTypes implements ReferenceObject, AccessRightsOwner, ValueTypeOwner {

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
   * ValueTypeOwner
   */

  @Default
  @Getter(AccessLevel.NONE)
  ValueTypeDescription type = ValueTypeDescription.EMPTY;

  /*
   * Свое
   */

  /**
   * Пояснение
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;


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
    return Catalog.possibleRights();
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

  /**
   * Compute the complete list of modules associated with this chart of characteristic types.
   *
   * @return the list of all modules that apply to this object, possibly empty
   */
  private List<Module> computeAllModules() {
    return LazyLoader.computeAllModules(this);
  }

  /**
   * Gets the value type description for this chart of characteristic types.
   *
   * @return the ValueTypeDescription describing this object's value type
   */
  @Override
  public ValueTypeDescription getValueType() {
    return type;
  }
}