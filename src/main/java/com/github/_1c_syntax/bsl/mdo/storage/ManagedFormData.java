/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;

import java.nio.file.Path;
import java.util.List;

/**
 * Реализация содержимого управляемой формы
 */
@Value
@Builder
public class ManagedFormData implements FormData {
  /**
   * адрес основного файла формы (Form.form или Form.xml)
   */
  @Getter
  Path dataPath;
  @Default
  MultiLanguageString title = MultiLanguageString.EMPTY;
  @Singular
  List<FormHandler> handlers;
  @Singular
  List<FormItem> items;
  @Singular
  List<FormAttribute> attributes;

  @Override
  public boolean isEmpty() {
    return false;
  }
}
