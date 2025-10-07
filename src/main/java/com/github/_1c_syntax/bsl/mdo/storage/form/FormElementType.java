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

import com.github._1c_syntax.bsl.types.EnumWithName;
import com.github._1c_syntax.bsl.types.MultiName;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.Map;

@Slf4j
@ToString(of = "fullName")
public enum FormElementType implements EnumWithName {
  ADDITION("Addition", "Дополнение"),
  ATTRIBUTE("Attribute", "Реквизит"),
  BUTTON("Button", "Кнопка"),
  BUTTON_GROUP("ButtonGroup", "ГруппаКнопок"),
  CALENDAR_FIELD("CalendarField", "ПолеКалендаря"),
  CHART_FIELD("ChartField", "ПолеДиаграммы"),
  CHECK_BOX_FIELD("CheckBoxField", "ПолеФлажка"),
  COLUMN_GROUP("ColumnGroup", "ГруппаКолонок"),
  COMMAND_BAR("CommandBar", "КоманднаяПанель"),
  COMMAND_BAR_BUTTON("CommandBarButton", "КнопкаКоманднойПанели"),
  COMMAND_BAR_HYPERLINK("CommandBarHyperlink", "ГиперссылкаКоманднойПанели"),
  DECORATION("Decoration", "Декорация"),
  FORMATTED_DOCUMENT_FIELD("FormattedDocumentField", "ПолеФорматированногоДокумента"),
  FORM_FIELD("FormField", "ПолеФормы"),
  FORM_GROUP("FormGroup", "ГруппаФормы"),
  GANTT_CHART_FIELD("GanttChartField", "ПолеДиаграммыГанта"),
  GRAPHICAL_SCHEMA_FIELD("GraphicalSchemaField", "ПолеГрафическойСхемы"),
  HTML_DOCUMENT_FIELD("HTMLDocumentField", "ПолеHTMLДокумента"),
  HYPERLINK("Hyperlink", "Гиперссылка"),
  INPUT_FIELD("InputField", "ПолеВвода"),
  LABEL("Label", "Надпись"),
  LABEL_DECORATION("LabelDecoration", "ДекорацияНадпись"),
  LABEL_FIELD("LabelField", "ПолеНадписи"),
  PAGE("Page", "Страница"),
  PAGES("Pages", "Страницы"),
  PDF_DOCUMENT_FIELD("PDFDocumentField", "ПолеPDFДокумента"),
  PERIOD_FIELD("PeriodField", "ПолеПериода"),
  PICTURE_DECORATION("PictureDecoration", "ДекорацияКартинка"),
  PICTURE_FIELD("PictureField", "ПолеКартинки"),
  PLANNER_FIELD("PlannerField", "ПолеПланировщика"),
  POPUP("Popup", "Подменю"),
  PROGRESS_BAR_FIELD("ProgressBarField", "ПолеИндикатора"),
  RADIO_BUTTON_FIELD("RadioButtonField", "ПолеПереключателя"),
  SEARCH_CONTROL_ADDITION("SearchControlAddition", "ДополнениеУправлениеПоиском"),
  SEARCH_STRING_ADDITION("SearchStringAddition", "ДополнениеСтрокаПоиска"),
  SPREAD_SHEET_DOCUMENT_FIELD("SpreadsheetDocumentField", "ПолеТабличногоДокумента"),
  TABLE("Table", "Таблица"),
  TEXT_DOCUMENT_FIELD("TextDocumentField", "ПолеТекстовогоДокумента"),
  TRACK_BAR_FIELD("TrackBarField", "ПолеПолосыРегулирования"),
  UNKNOWN("unknown", "неизвестный"),
  USUAL_BUTTON("UsualButton", "ОбычнаяКнопка"),
  USUAL_GROUP("UsualGroup", "ОбычнаяГруппа"),
  VIEW_STATUS_ADDITION("ViewStatusAddition", "ДополнениеОтображениеСостояния");

  private static final Map<String, FormElementType> KEYS = EnumWithName.computeKeys(values());

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  FormElementType(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Ищет элемент перечисления по именам (рус, анг)
   *
   * @param string Имя искомого элемента
   * @return Найденное значение, если не найден - то UNKNOWN
   */
  public static FormElementType valueByName(String string) {
    var result = KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
    if (result == UNKNOWN) {
      LOGGER.warn("Unknown form element type: {}", string);
    }
    return result;
  }
}
