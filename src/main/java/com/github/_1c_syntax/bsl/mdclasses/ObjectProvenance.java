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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.reader.MDMerger;
import com.github._1c_syntax.bsl.types.MdoReference;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/**
 * Хранит информацию о происхождении объекта метаданных в составе {@link Solution}.
 * <p>
 * Заполняется автоматически в {@link MDMerger#mergeAll(Configuration, java.util.List)}
 * при построении решения.
 *
 */
@Value
@Builder
public class ObjectProvenance {

  /**
   * Конфигурация или расширение, которая является владельцем объекта
   */
  @Builder.Default
  MdoReference ownerRef = MdoReference.EMPTY;

  /**
   * Расширения (если есть), которые модифицировали объект
   */
  @Singular
  List<MdoReference> modifiedByExtensionRefs;

  /**
   * Признак собственного или заимствованного (унаследованного) объекта.
   */
  @Builder.Default
  ObjectBelonging objectBelonging = ObjectBelonging.OWN;
}
