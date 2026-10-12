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
 *
 * @param event   Имя события (например, OnCreateAtServer)
 * @param handler Имя метода-обработчика
 */
public record FormEventHandler(
  String event,
  String handler
) implements Comparable<FormEventHandler> {

  /**
   * События в принципе повторяются, так что попробуем закешировать
   */
  private static final GenericInterner<FormEventHandler> INTERNER = new GenericInterner<>();
  private static final StringInterner STRING_INTERNER = new StringInterner();

  public FormEventHandler(String event, String handler) {
    this.event = STRING_INTERNER.intern(event);
    this.handler = STRING_INTERNER.intern(handler);
  }

  /**
   * Создает обработчик события
   *
   * @param event   Имя события
   * @param handler Имя метода-обработчика
   * @return Новый обработчик события
   */
  public static FormEventHandler create(String event, String handler) {
    return new FormEventHandler(event, handler).intern();
  }

  @Override
  public int compareTo(@Nullable FormEventHandler formHandler) {
    if (formHandler == null) {
      return 1;
    }

    if (this.equals(formHandler)) {
      return 0;
    }

    int compareResult = event.compareTo(formHandler.event);
    if (compareResult == 0) {
      compareResult = handler.compareTo(formHandler.handler);
    }

    return compareResult;
  }

  private FormEventHandler intern() {
    return INTERNER.intern(this);
  }
}
