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
package com.github._1c_syntax.bsl.mdo.storage.form;

import com.github._1c_syntax.bsl.mdo.ValueTypeOwner;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/**
 * Хранит описание атрибута формы
 */
@Value
@Builder
public class FormAttribute implements ValueTypeOwner {

  /**
   * Идентификатор
   */
  @Default
  int id = -1;

  /**
   * Имя
   */
  @Default
  String name = "";

  /**
   * Синоним
   */
  @Default
  MultiLanguageString title = MultiLanguageString.EMPTY;

  /*
   * ValueTypeOwner
   */

  @Default
  @Getter(AccessLevel.NONE)
  ValueTypeDescription type = ValueTypeDescription.EMPTY;

  @Override
  @NonNull
  public ValueTypeDescription getValueType() {
    return type;
  }
}
