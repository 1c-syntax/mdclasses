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

import com.github._1c_syntax.bsl.types.ValueType;
import com.github._1c_syntax.bsl.types.ValueTypeVariant;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class FormAttributeValueType implements ValueType {
  public static final FormAttributeValueType VALUE_TABLE = new FormAttributeValueType("ValueTable", "ТаблицаЗначений");
  public static final FormAttributeValueType VALUE_TREE = new FormAttributeValueType("ValueTree", "ДеревоЗначений");
  public static final FormAttributeValueType VALUE_LIST = new FormAttributeValueType("ValueList", "СписокЗначений");
  public static final FormAttributeValueType ACCOUNTING_RECORD_TYPE = new FormAttributeValueType("AccountingRecordType",
    "ВидДвиженияБухгалтерии");
  public static final FormAttributeValueType ACCUMULATION_RECORD_TYPE = new FormAttributeValueType("AccumulationRecordType",
    "ВидДвиженияНакопления");

  public static final FormAttributeValueType FORMATTED_DOCUMENT = new FormAttributeValueType("FormattedDocument",
    "ФорматированныйДокумент");
  public static final FormAttributeValueType SPREADSHEET_DOCUMENT = new FormAttributeValueType("SpreadsheetDocument",
    "ТабличныйДокумент");
  public static final FormAttributeValueType TEXT_DOCUMENT = new FormAttributeValueType("TextDocument",
    "ТекстовыйДокумент");
  public static final FormAttributeValueType GRAPHICAL_SCHEMA = new FormAttributeValueType("GraphicalSchema",
    "ГрафическаяСхема");
  public static final FormAttributeValueType CHART = new FormAttributeValueType("Chart", "Диаграмма");
  public static final FormAttributeValueType GANTT_CHART = new FormAttributeValueType("GanttChart", "ДиаграммаГанта");
  public static final FormAttributeValueType GEOGRAPHICAL_SCHEMA = new FormAttributeValueType("GeographicalSchema",
    "ГеографическаяСхема");
  public static final FormAttributeValueType PDF_DOCUMENT = new FormAttributeValueType("PDFDocument", "PDFДокумент");

  public static final FormAttributeValueType STANDARD_PERIOD = new FormAttributeValueType("StandardPeriod",
    "СтандартныйПериод");
  public static final FormAttributeValueType FORMATTED_STRING = new FormAttributeValueType("FormattedString",
    "ФорматированнаяСтрока");
  public static final FormAttributeValueType TYPE_DESCRIPTION = new FormAttributeValueType("TypeDescription",
    "ОписаниеТипа");
  public static final FormAttributeValueType STANDARD_BEGINNING_DATE = new FormAttributeValueType("StandardBeginningDate",
    "СтандартнаяДатаНачала");

  public static final FormAttributeValueType DYNAMIC_LIST = new FormAttributeValueType("DynamicList",
    "ДинамическийСписок");
  public static final FormAttributeValueType DATA_COMPOSITION_SETTINGS_COMPOSER = new FormAttributeValueType(
    "DataCompositionSettingsComposer", "КомпоновщикНастроекКомпоновкиДанных");
  public static final FormAttributeValueType SETTINGS_COMPOSER = new FormAttributeValueType("SettingsComposer",
    "НастройкиКомпоновщика");
  public static final FormAttributeValueType REPORT_BUILDER = new FormAttributeValueType("ReportBuilder",
    "ПостроительОтчета");
  public static final FormAttributeValueType DATA_ANALYSIS_TIME_INTERVAL_UNIT_TYPE = new FormAttributeValueType(
    "DataAnalysisTimeIntervalUnitType", "ТипЕдиницыИнтервалаВремениАнализаДанных");
  public static final FormAttributeValueType FILTER = new FormAttributeValueType("Filter", "Отбор");
  public static final FormAttributeValueType ORDER = new FormAttributeValueType("Order", "Порядок");
  public static final FormAttributeValueType PLANNER = new FormAttributeValueType("Planner", "Планировщик");
  public static final FormAttributeValueType COMPARISON_TYPE = new FormAttributeValueType("ComparisonType",
    "ВидСравнения");

  public static final FormAttributeValueType COLOR = new FormAttributeValueType("Color", "Цвет");
  public static final FormAttributeValueType FONT = new FormAttributeValueType("Font", "Шрифт");
  public static final FormAttributeValueType VERTICAL_ALIGN = new FormAttributeValueType("VerticalAlign",
    "ВертикальноеПоложение");
  public static final FormAttributeValueType PICTURE = new FormAttributeValueType("Picture", "Картинка");
  public static final FormAttributeValueType SIZE_CHANGE_MODE = new FormAttributeValueType("SizeChangeMode",
    "РежимИзмененияРазмера");

  private static final List<ValueType> BUILTIN_TYPES = List.of(
    VALUE_TABLE, VALUE_TREE, VALUE_LIST, ACCOUNTING_RECORD_TYPE, ACCUMULATION_RECORD_TYPE,
    FORMATTED_DOCUMENT, SPREADSHEET_DOCUMENT, TEXT_DOCUMENT, GRAPHICAL_SCHEMA, CHART, GANTT_CHART, GEOGRAPHICAL_SCHEMA,
    PDF_DOCUMENT,
    STANDARD_PERIOD, FORMATTED_STRING, TYPE_DESCRIPTION, STANDARD_BEGINNING_DATE,
    DYNAMIC_LIST, DATA_COMPOSITION_SETTINGS_COMPOSER, SETTINGS_COMPOSER, REPORT_BUILDER,
    DATA_ANALYSIS_TIME_INTERVAL_UNIT_TYPE, FILTER, ORDER, PLANNER, COMPARISON_TYPE,
    COLOR, FONT, VERTICAL_ALIGN, PICTURE, SIZE_CHANGE_MODE
  );

  @Getter
  private final String name;
  @Getter
  private final String nameRu;

  private FormAttributeValueType(String name, String nameRu) {
    this.name = name;
    this.nameRu = nameRu;
  }

  @Override
  @NonNull
  public ValueTypeVariant getVariant() {
    return ValueTypeVariant.FORM;
  }

  /**
   * Коллекция встроенных типов
   *
   * @return Список встроенных типов
   */
  public static List<ValueType> builtinTypes() {
    return BUILTIN_TYPES;
  }
}
