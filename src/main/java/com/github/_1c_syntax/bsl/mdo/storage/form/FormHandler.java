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
package com.github._1c_syntax.bsl.mdo.storage.form;

import com.github._1c_syntax.utils.GenericInterner;
import com.github._1c_syntax.utils.StringInterner;
import org.jspecify.annotations.Nullable;

/**
 * Обработчик события формы
 */
public record FormHandler(String event, String name) implements Comparable<FormHandler> {

  private static final GenericInterner<FormHandler> interner = new GenericInterner<>();
  private static final StringInterner stringInterner = new StringInterner();

  public FormHandler(String event, String name) {
    this.event = stringInterner.intern(event);
    this.name = stringInterner.intern(name);
  }

  /**
   * @param event Имя события
   * @param name  Имя обработчика (метода) формы
   */

  public static FormHandler create(String event, String name) {
    return new FormHandler(event, name).intern();
  }

  @Override
  public int compareTo(@Nullable FormHandler formHandler) {
    if (formHandler == null) {
      return 1;
    }

    if (this.equals(formHandler)) {
      return 0;
    }

    int compareResult = event.compareTo(formHandler.event);
    if (compareResult == 0) {
      compareResult = name.compareTo(formHandler.name);
    }

    return compareResult;
  }

  private FormHandler intern() {
    return interner.intern(this);
  }
}
