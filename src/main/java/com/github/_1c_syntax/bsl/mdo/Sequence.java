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

import com.github._1c_syntax.bsl.mdo.children.Dimension;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
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
public class Sequence implements MDObject, AttributeOwner, ModuleOwner, AccessRightsOwner {

  private static final List<RoleRight> POSIBLE_RIGHTS = List.of(RoleRight.READ, RoleRight.EDIT);

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

  /*
   * ModuleOwner
   */

  @Default
  List<Module> modules = Collections.emptyList();

  /*
   * Свое
   */

  /**
   * Измерения
   */
  @Singular("dimension")
  List<Dimension> dimensions;

  /**
   * Документы
   */
  @Singular("addDocuments")
  List<MdoReference> documents;

  /*
   * Для AttributeOwner
   */

  @Override
  public List<Attribute> getAllAttributes() {
    return Collections.unmodifiableList(dimensions);
  }

  @Override
  public List<MD> getChildren() {
    return Collections.unmodifiableList(dimensions);
  }

  /**
   * Возвращает перечень возможных прав доступа
   */
  public static List<RoleRight> posibleRights() {
    return POSIBLE_RIGHTS;
  }
}
