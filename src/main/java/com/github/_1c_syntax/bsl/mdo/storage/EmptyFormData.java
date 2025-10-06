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

import com.github._1c_syntax.bsl.mdo.storage.form.FormAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormHandler;
import com.github._1c_syntax.bsl.mdo.storage.form.FormItem;
import com.github._1c_syntax.bsl.types.MultiLanguageString;

import java.util.Collections;
import java.util.List;

/**
 * Реализация содержимого пустой формы
 */
public final class EmptyFormData implements FormData {

  /**
   * Возвращает ссылку на пустое содержимое формы
   */
  public static final EmptyFormData EMPTY = new EmptyFormData();

  private EmptyFormData() {
  }

  @Override
  public MultiLanguageString getTitle() {
    return MultiLanguageString.EMPTY;
  }

  @Override
  public List<FormHandler> getHandlers() {
    return Collections.emptyList();
  }

  @Override
  public List<FormItem> getItems() {
    return Collections.emptyList();
  }

  @Override
  public List<FormItem> getPlainItems() {
    return Collections.emptyList();
  }

  @Override
  public List<FormAttribute> getAttributes() {
    return Collections.emptyList();
  }
}
