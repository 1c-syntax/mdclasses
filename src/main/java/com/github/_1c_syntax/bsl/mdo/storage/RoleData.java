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
package com.github._1c_syntax.bsl.mdo.storage;

import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.utils.GenericInterner;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Хранилище данных конкретной роли
 */
@Value
@ToString
@EqualsAndHashCode
@Builder
@Slf4j
public class RoleData {

  public static final RoleData EMPTY = RoleData.builder().build();
  public static final GenericInterner<Right> RIGHT_INTERNER = new GenericInterner<>();

  /**
   * Устанавливать права для новых объектов
   */
  boolean setForNewObjects;

  /**
   * Устанавливать права для реквизитов и табличных частей по умолчанию
   */
  boolean setForAttributesByDefault;

  /**
   * Независимые права подчиненных объектов
   */
  boolean independentRightsOfChildObjects;

  @Singular
  List<ObjectRight> objectRights;

  @Value
  @ToString(of = {"name"})
  @EqualsAndHashCode(of = {"name"})
  @Builder
  public static class ObjectRight {
    /**
     * Имя субъекта права (mdoref и базовые)
     */
    @Default
    String name = "";

    /**
     * Набор самих прав
     */
    @Singular
    List<Right> rights;
  }

  @Value
  @ToString(of = {"name", "value"})
  @EqualsAndHashCode(of = {"name", "value"})
  @Builder
  public static class Right {
    /**
     * Право
     */
    RoleRight name;

    /**
     * Признак установленности права
     */
    boolean value;
  }
}
