/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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
package com.github._1c_syntax.mdclasses.mdo.metadata;

import com.github._1c_syntax.bsl.types.MDOType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Типы атрибутов (реквизитов)
 */
@AllArgsConstructor
public enum AttributeType {
  ATTRIBUTE(MDOType.ATTRIBUTE),
  DIMENSION(MDOType.DIMENSION),
  RESOURCE(MDOType.RESOURCE),
  TABULAR_SECTION(MDOType.TABULAR_SECTION),
  RECALCULATION(MDOType.RECALCULATION),
  ACCOUNTING_FLAG(MDOType.ACCOUNTING_FLAG),
  EXT_DIMENSION_ACCOUNTING_FLAG(MDOType.EXT_DIMENSION_ACCOUNTING_FLAG),
  COLUMN(MDOType.COLUMN),
  ADDRESSING_ATTRIBUTE(MDOType.TASK_ADDRESSING_ATTRIBUTE),
  UNKNOWN(MDOType.UNKNOWN),
  COMMON_ATTRIBUTE(MDOType.COMMON_ATTRIBUTE);

  @Getter
  private final MDOType mdoType;
}

