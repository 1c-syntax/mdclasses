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
 * Поле формы (ввода, отображения, выбора и т.д.)
 * <p>
 * Маппинг типов: {@link FormElementType#INPUT_FIELD}, {@link FormElementType#CHECK_BOX_FIELD},
 * {@link FormElementType#CALENDAR_FIELD}, {@link FormElementType#HTML_DOCUMENT_FIELD},
 * {@link FormElementType#TEXT_DOCUMENT_FIELD}, {@link FormElementType#SPREAD_SHEET_DOCUMENT_FIELD},
 * {@link FormElementType#FORMATTED_DOCUMENT_FIELD}, {@link FormElementType#PDF_DOCUMENT_FIELD},
 * {@link FormElementType#RADIO_BUTTON_FIELD}, {@link FormElementType#TRACK_BAR_FIELD},
 * {@link FormElementType#PROGRESS_BAR_FIELD}, {@link FormElementType#PICTURE_FIELD},
 * {@link FormElementType#LABEL_FIELD}, {@link FormElementType#PERIOD_FIELD},
 * {@link FormElementType#PLANNER_FIELD},
 * {@link FormElementType#GRAPHICAL_SCHEMA_FIELD}, {@link FormElementType#CHART_FIELD},
 * {@link FormElementType#GANTT_CHART_FIELD}, {@link FormElementType#GEOGRAPHICAL_SCHEMA_FIELD},
 * {@link FormElementType#DENDROGRAM_FIELD}
 */
@Value
@Builder
@ToString(of = "name")
public class FormField implements FormElement, FormElementOwner, FormDataPathOwner, FormEventHandlerOwner {

  @Default
  int id = -1;

  @Default
  String name = "";

  @Default
  FormElementType type = FormElementType.INPUT_FIELD;

  @Default
  MultiLanguageString title = MultiLanguageString.EMPTY;

  @Default
  String dataPath = "";

  @Default
  String comment = "";

  @Singular("addEventHandlers")
  List<FormEventHandler> eventHandlers;

  @Singular("addElements")
  List<FormElement> elements;

  @Getter(lazy = true)
  List<FormElement> plainElements = LazyLoader.computePlainFormElements(this);
}
