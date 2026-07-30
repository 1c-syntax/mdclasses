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

import com.github._1c_syntax.bsl.types.MultiLanguageString;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.ToString;
import lombok.Value;

/**
 * Дополнение формы
 * <p>
 * Маппинг типов: {@link FormElementType#SEARCH_STRING_ADDITION},
 * {@link FormElementType#SEARCH_CONTROL_ADDITION}, {@link FormElementType#VIEW_STATUS_ADDITION}
 */
@Value
@Builder
@ToString(of = "name")
public class FormAddition implements FormElement {

  @Default
  int id = -1;

  @Default
  String name = "";

  @Default
  FormElementType type = FormElementType.SEARCH_CONTROL_ADDITION;

  @Default
  MultiLanguageString title = MultiLanguageString.EMPTY;

  @Default
  String comment = "";
}
