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

import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.utils.LazyLoader;
import com.github._1c_syntax.utils.Lazy;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/**
 * Сильно упрощенная реализация хранения информации элемента форм
 */
@Value
@Builder
public class SimpleFormItem implements FormItem {
  @Default
  FormElementType type = FormElementType.UNKNOWN;
  @Default
  int id = -1;
  @Default
  String name = "";
  @Default
  MultiLanguageString title = MultiLanguageString.EMPTY;
  @Default
  FormDataPath dataPath = FormDataPath.EMPTY;
  @Singular("addItems")
  List<FormItem> items;

  Lazy<List<FormItem>> plainItems = new Lazy<>(this::computePlainItems);

  @Override
  public List<FormItem> getPlainItems() {
    return plainItems.getOrCompute();
  }

  private List<FormItem> computePlainItems() {
    return LazyLoader.computePlainFormItems(this);
  }
}
