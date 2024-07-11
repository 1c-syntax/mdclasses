/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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

import com.github._1c_syntax.bsl.mdo.support.EnumWithValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
@Slf4j
public enum FormElementType implements EnumWithValue {
  ADDITION("Addition"),
  ATTRIBUTE("Attribute"),
  BUTTON("Button"),
  BUTTON_GROUP("ButtonGroup"),
  CALENDAR_FIELD("CalendarField"),
  CHART_FIELD("ChartField"),
  CHECK_BOX_FIELD("CheckBoxField"),
  COLUMN_GROUP("ColumnGroup"),
  COMMAND_BAR("CommandBar"),
  COMMAND_BAR_BUTTON("CommandBarButton"),
  COMMAND_BAR_HYPERLINK("CommandBarHyperlink"),
  DECORATION("Decoration"),
  FORMATTED_DOCUMENT_FIELD("FormattedDocumentField"),
  FORM_FIELD("FormField"),
  FORM_GROUP("FormGroup"),
  GANTT_CHART_FIELD("GanttChartField"),
  GRAPHICAL_SCHEMA_FIELD("GraphicalSchemaField"),
  HTML_DOCUMENT_FIELD("HTMLDocumentField"),
  HYPERLINK("Hyperlink"),
  INPUT_FIELD("InputField"),
  LABEL("Label"),
  LABEL_DECORATION("LabelDecoration"),
  LABEL_FIELD("LabelField"),
  PAGE("Page"),
  PAGES("Pages"),
  PDF_DOCUMENT_FIELD("PDFDocumentField"),
  PICTURE_DECORATION("PictureDecoration"),
  PICTURE_FIELD("PictureField"),
  POPUP("Popup"),
  PROGRESS_BAR_FIELD("ProgressBarField"),
  RADIO_BUTTON_FIELD("RadioButtonField"),
  SEARCH_CONTROL_ADDITION("SearchControlAddition"),
  SEARCH_STRING_ADDITION("SearchStringAddition"),
  SPREADSHEET_DOCUMENT_FIELD("SpreadsheetDocumentField"),
  SPREAD_SHEET_DOCUMENT_FIELD("SpreadSheetDocumentField"),
  TABLE("Table"),
  TEXT_DOCUMENT_FIELD("TextDocumentField"),
  TRACK_BAR_FIELD("TrackBarField"),
  UNKNOWN("unknown") {
    @Override
    public boolean isUnknown() {
      return true;
    }
  },
  USUAL_BUTTON("UsualButton"),
  USUAL_GROUP("UsualGroup");

  private static final Map<String, FormElementType> KEYS = computeKeys();

  @Accessors(fluent = true)
  private final String value;

  /**
   * Выполняет преобразование из строкового представления в значение
   *
   * @param value Строковое представление
   * @return Найденный тип
   */
  public static FormElementType fromString(String value) {
    var result = KEYS.getOrDefault(value, UNKNOWN);
    if (result.isUnknown()) {
      LOGGER.info(value);
    }

    return KEYS.getOrDefault(value, UNKNOWN);
  }

  private static Map<String, FormElementType> computeKeys() {
    Map<String, FormElementType> keys = new HashMap<>();
    for (FormElementType formElementType : FormElementType.values()) {
      keys.put(formElementType.value, formElementType);
    }
    return Collections.unmodifiableMap(keys);
  }
}
