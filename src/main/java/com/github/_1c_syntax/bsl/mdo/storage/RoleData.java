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
package com.github._1c_syntax.bsl.mdo.storage;

import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.utils.GenericInterner;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;

/**
 * Хранилище данных конкретной роли
 *
 * @param setForNewObjects                Устанавливать права для новых объектов
 * @param setForAttributesByDefault       Устанавливать права для реквизитов и табличных частей по умолчанию
 * @param independentRightsOfChildObjects Независимые права подчиненных объектов
 */
@Builder
public record RoleData(boolean setForNewObjects, boolean setForAttributesByDefault,
                       boolean independentRightsOfChildObjects, @NonNull @Singular List<ObjectRight> objectRights) {

  public static final RoleData EMPTY = RoleData.builder().build();
  public static final GenericInterner<Right> RIGHT_INTERNER = new GenericInterner<>();

  /**
   * @param name   Имя субъекта права (mdoref и базовые)
   * @param rights Набор самих прав
   */
  @Builder
  public record ObjectRight(@NonNull MdoReference name, @NonNull @Singular List<Right> rights) {
  }

  /**
   * @param name  Право
   * @param value Признак установленности права
   */
  @Builder
  public record Right(@NonNull RoleRight name, boolean value) {
  }
}
