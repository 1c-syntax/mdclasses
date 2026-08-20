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

import com.github._1c_syntax.bsl.mdo.utils.LazyLoader;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.util.List;

/**
 * Кнопка формы
 * <p>
 * Маппинг типов: {@link FormElementType#USUAL_BUTTON}, {@link FormElementType#COMMAND_BAR_BUTTON},
 * {@link FormElementType#COMMAND_BAR_HYPERLINK}, {@link FormElementType#HYPERLINK}
 */
@Value
@Builder
@ToString(of = "name")
public class FormButton implements FormElement, FormElementOwner, FormEventHandlerOwner {

  @Default
  int id = -1;

  @Default
  String name = "";

  @Default
  FormElementType type = FormElementType.COMMAND_BAR_BUTTON;

  @Default
  MultiLanguageString title = MultiLanguageString.EMPTY;

  @Default
  String commandName = "";

  @Default
  String comment = "";

  @Singular("addEventHandlers")
  List<FormEventHandler> eventHandlers;

  @Singular("addElements")
  List<FormElement> elements;

  @Getter(lazy = true)
  List<FormElement> plainElements = LazyLoader.computePlainFormElements(this);
}
