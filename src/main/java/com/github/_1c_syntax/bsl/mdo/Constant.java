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

import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MdoReference;
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
public class Constant implements MDObject, ModuleOwner, AccessRightsOwner {

  private static final List<RoleRight> POSSIBLE_RIGHTS = computePossibleRighs();

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
   * Режим пароля. Только для констант с типом `Строка`
   */
  boolean passwordMode;

  /**
   * Пояснение
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;

  /**
   * Возвращает перечень возможных прав доступа
   */
  public static List<RoleRight> possibleRights() {
    return POSSIBLE_RIGHTS;
  }

  private static List<RoleRight> computePossibleRighs() {
    return List.of(
      RoleRight.READ,
      RoleRight.UPDATE,
      RoleRight.VIEW,
      RoleRight.EDIT,
      RoleRight.READ_DATA_HISTORY,
      RoleRight.VIEW_DATA_HISTORY,
      RoleRight.UPDATE_DATA_HISTORY,
      RoleRight.UPDATE_DATA_HISTORY_SETTINGS,
      RoleRight.UPDATE_DATA_HISTORY_VERSION_COMMENT,
      RoleRight.EDIT_DATA_HISTORY_VERSION_COMMENT,
      RoleRight.SWITCH_TO_DATA_HISTORY_VERSION
    );
  }
}
