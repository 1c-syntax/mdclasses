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

import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.CommonForm;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.assertj.core.api.Assertions.assertThat;

class FormElementTest {

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Catalogs.Справочник1, Catalog.Справочник1.Form.ФормаЭлемента",
    "false, mdclasses, Catalogs.Справочник1, Catalog.Справочник1.Form.ФормаЭлемента",
  })
  void shouldReadFlatFormElements(ArgumentsAccessor argumentsAccessor) {
    var formRef = argumentsAccessor.getString(3);
    assertThat(formRef).isNotNull();

    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isNotNull();
    assertThat(mdo).isInstanceOf(ChildrenOwner.class);

    var childOpt = ((ChildrenOwner) mdo).findChild(formRef);
    assertThat(childOpt).isPresent();

    var form = (Form) childOpt.get();
    var formData = form.getData();
    assertThat(formData).isNotNull();

    var elements = formData.getElements();
    assertThat(elements).hasSize(10);

    var plainElements = formData.getPlainElements();
    assertThat(plainElements).hasSize(28);

    var first = findElement(elements, "Ссылка");
    assertThat(first).isInstanceOf(FormField.class);
    assertThat(first.getId()).isEqualTo(1);
    assertThat(first.getType()).isEqualTo(FormElementType.INPUT_FIELD);
    assertThat(((FormField) first).getDataPath()).isEqualTo("Объект.Ref");

    var firstContextMenu = findElement(((FormField) first).getElements(), "СсылкаКонтекстноеМеню");
    assertThat(firstContextMenu).isInstanceOf(FormContextMenu.class);
    assertThat(firstContextMenu.getType()).isEqualTo(FormElementType.CONTEXT_MENU);
    assertThat(((FormContextMenu) firstContextMenu).getElements()).isEmpty();

    var firstExtendedTooltip = findElement(((FormField) first).getElements(), "СсылкаРасширеннаяПодсказка");
    assertThat(firstExtendedTooltip).isInstanceOf(FormExtendedTooltip.class);
    assertThat(firstExtendedTooltip.getType()).isEqualTo(FormElementType.EXTENDED_TOOLTIP);

    var deletionMark = findElement(elements, "ПометкаУдаления");
    assertThat(deletionMark).isInstanceOf(FormField.class);
    assertThat(deletionMark.getId()).isEqualTo(10);
    assertThat(deletionMark.getType()).isEqualTo(FormElementType.CHECK_BOX_FIELD);
    assertThat(((FormField) deletionMark).getDataPath()).isEqualTo("Объект.DeletionMark");

    var last = findElement(elements, "Реквизит3");
    assertThat(last).isInstanceOf(FormField.class);
    assertThat(last.getId()).isEqualTo(25);
    assertThat(last.getType()).isEqualTo(FormElementType.CHECK_BOX_FIELD);
    assertThat(((FormField) last).getDataPath()).isEqualTo("Объект.Реквизит3");

    assertThat(((FormEventHandlerOwner) first).getEventHandlers()).isEmpty();
    assertThat(((FormEventHandlerOwner) deletionMark).getEventHandlers()).isEmpty();
    assertThat(((FormEventHandlerOwner) last).getEventHandlers()).isEmpty();
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, CommonForms.Форма",
    "false, mdclasses, CommonForms.Форма",
  })
  void shouldReadEmptyFormElements(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isNotNull();
    assertThat(mdo).isInstanceOf(CommonForm.class);

    var form = (CommonForm) mdo;
    var formData = form.getData();
    assertThat(formData).isNotNull();

    assertThat(formData.getElements()).hasSize(1);
    assertThat(formData.getElements().getFirst()).isInstanceOf(FormGroup.class);
    assertThat(formData.getPlainElements()).hasSize(1);
    assertThat(formData.getPlainElements().getFirst()).isInstanceOf(FormGroup.class);

    assertThat(formData.getPlainElements())
      .filteredOn(FormEventHandlerOwner.class::isInstance)
      .allSatisfy(e -> assertThat(((FormEventHandlerOwner) e).getEventHandlers()).isEmpty());
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_2, Documents.Анкета, Document.Анкета.Form.ФормаДокумента",
    "false, ssl_3_2, Documents.Анкета, Document.Анкета.Form.ФормаДокумента",
  })
  void shouldReadNestedFormElements(ArgumentsAccessor argumentsAccessor) {
    var formRef = argumentsAccessor.getString(3);
    assertThat(formRef).isNotNull();

    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isNotNull();
    assertThat(mdo).isInstanceOf(ChildrenOwner.class);

    var childOpt = ((ChildrenOwner) mdo).findChild(formRef);
    assertThat(childOpt).isPresent();

    var form = (Form) childOpt.get();
    var formData = form.getData();
    assertThat(formData).isNotNull();

    var elements = formData.getElements();
    assertThat(elements).hasSize(3);

    var plainElements = formData.getPlainElements();
    assertThat(plainElements).hasSize(117);

    var pagesGroup = findElement(elements, "Страницы");
    assertThat(pagesGroup).isNotNull();
    assertThat(pagesGroup.getId()).isEqualTo(257);
    assertThat(pagesGroup.getType()).isEqualTo(FormElementType.PAGES);
    assertThat(pagesGroup).isInstanceOf(FormGroup.class);
    assertThat(((FormGroup) pagesGroup).getElements()).isNotEmpty();

    var footerGroup = findElement(elements, "ГруппаПодвал");
    assertThat(footerGroup).isNotNull();

    var respondent = (FormEventHandlerOwner) findElement(plainElements, "Респондент");
    assertThat(respondent.getEventHandlers()).hasSize(1);
    assertThat(respondent.getEventHandlers().getFirst().event()).isEqualTo("StartChoice");
    assertThat(respondent.getEventHandlers().getFirst().handler()).isEqualTo("РеспондентНачалоВыбора");

    var comment = (FormEventHandlerOwner) findElement(plainElements, "Комментарий");
    assertThat(comment.getEventHandlers()).hasSize(1);
    assertThat(comment.getEventHandlers().getFirst().event()).isEqualTo("StartChoice");
    assertThat(comment.getEventHandlers().getFirst().handler()).isEqualTo("КомментарийНачалоВыбора");

    var tree = (FormEventHandlerOwner) findElement(plainElements, "ДеревоРазделов");
    assertThat(tree.getEventHandlers()).hasSize(1);
    assertThat(tree.getEventHandlers().getFirst().event()).isEqualTo("Selection");
    assertThat(tree.getEventHandlers().getFirst().handler()).isEqualTo("ДеревоРазделовВыбор");
  }

  @ParameterizedTest
  @MethodSource("formFileProvider")
  void shouldReadFormElementCounts(boolean formatEDT, String pack, String parentRef, String formRef,
                                   int total, Map<String, Long> expectedClasses, Map<String, Long> expectedTypes) {
    var mdo = Fixtures.get(pack, parentRef, formatEDT);
    assertThat(mdo).isNotNull().isInstanceOf(ChildrenOwner.class);

    var childOpt = ((ChildrenOwner) mdo).findChild(formRef);
    assertThat(childOpt).isPresent();

    var form = (Form) childOpt.get();
    var formData = form.getData();
    assertThat(formData).isNotNull();

    var plainElements = formData.getPlainElements();
    assertThat(plainElements).hasSize(total);

    var classCounts = plainElements.stream()
      .collect(groupingBy(e -> e.getClass().getSimpleName(), counting()));

    assertCount(classCounts, "FormField", expectedClasses.getOrDefault("FormField", 0L));
    assertCount(classCounts, "FormGroup", expectedClasses.getOrDefault("FormGroup", 0L));
    assertCount(classCounts, "FormTable", expectedClasses.getOrDefault("FormTable", 0L));
    assertCount(classCounts, "FormButton", expectedClasses.getOrDefault("FormButton", 0L));
    assertCount(classCounts, "FormDecoration", expectedClasses.getOrDefault("FormDecoration", 0L));
    assertCount(classCounts, "FormAddition", expectedClasses.getOrDefault("FormAddition", 0L));
    assertCount(classCounts, "FormUnknown", expectedClasses.getOrDefault("FormUnknown", 0L));

    var typeCounts = plainElements.stream()
      .collect(groupingBy(e -> e.getType().name(), counting()));

    for (var type : FormElementType.values()) {
      assertCount(typeCounts, type.name(), expectedTypes.getOrDefault(type.name(), 0L));
    }
  }

  private static void assertCount(Map<String, Long> counts, String key, long expected) {
    var actual = counts.getOrDefault(key, 0L);
    assertThat(actual).as("Count of %s", key).isEqualTo(expected);
  }

  // Индексы файлов:
  //   0 = РассылкиОтчетов (ssl_3_2)        1 = ЗагрузкаДанныхИзФайла (ssl_3_1)
  //   2 = ВыборПериодаДень (ssl_3_1)       3 = ЭлПисьмоИсходящее (ssl_3_1)
  //   4 = КартаМаршрута (ssl_3_1)          5 = Сканирование (ssl_3_1)
  //   6 = ОценкаПроизводительности (ssl_3_1)
  // Строка: className, f0, f1, f2, f3, f4, f5, f6
  private static final String CLASS_MATRIX_TXT = """
    FormField,         57,16, 1,19, 6,11,7
    FormGroup,         78,34, 1,38, 9, 9,1
    FormTable,          5, 2, 0, 2, 0, 0,0
    FormButton,        96,17, 0,38, 6, 3,1
    FormDecoration,     2,15, 0, 6, 0, 3,0
    FormAddition,      17, 7, 0, 6, 0, 0,0
    FormContextMenu,   81,40, 1,33, 6,14,7
    FormExtendedTooltip, 249,88, 1,106,20,25,8
    FormUnknown,        0, 0, 0, 0, 0, 0,0
    """;

  // Строка: typeName, f0, f1, f2, f3, f4, f5, f6
  private static final String TYPE_MATRIX_TXT = """
    POPUP,                     10, 0,0, 4,0,0,0
    PAGES,                      6, 2,0, 3,1,0,0
    COMMAND_BAR,               10, 7,1, 7,2,1,1
    COMMAND_BAR_BUTTON,        94,12,0,37,6,3,1
    COMMAND_BAR_HYPERLINK,      1, 0,0, 0,0,0,0
    BUTTON_GROUP,               7, 0,0, 3,1,0,0
    INPUT_FIELD,               35, 5,0,12,4,8,4
    PAGE,                      18,10,0, 7,2,0,0
    USUAL_GROUP,               23,15,0,13,3,8,0
    USUAL_BUTTON,               1, 3,0, 0,0,0,0
    LABEL_DECORATION,           2,14,0, 3,0,3,0
    TABLE,                      5, 2,0, 2,0,0,0
    CHECK_BOX_FIELD,           16, 1,0, 1,0,2,0
    COLUMN_GROUP,               4, 0,0, 1,0,0,0
    PICTURE_FIELD,              2, 0,0, 1,0,0,0
    LABEL_FIELD,                2, 4,0, 2,1,0,2
    TEXT_DOCUMENT_FIELD,        1, 0,0, 0,0,0,0
    FORMATTED_DOCUMENT_FIELD,   1, 0,0, 1,0,0,0
    SEARCH_STRING_ADDITION,     7, 3,0, 2,0,0,0
    SEARCH_CONTROL_ADDITION,    5, 2,0, 2,0,0,0
    VIEW_STATUS_ADDITION,       5, 2,0, 2,0,0,0
    SPREAD_SHEET_DOCUMENT_FIELD,0, 2,0, 0,0,0,0
    RADIO_BUTTON_FIELD,         0, 3,0, 0,0,0,0
    PICTURE_DECORATION,         0, 1,0, 3,0,0,0
    HYPERLINK,                  0, 2,0, 1,0,0,0
    PROGRESS_BAR_FIELD,         0, 1,0, 0,0,0,0
    HTML_DOCUMENT_FIELD,        0, 0,0, 2,0,0,0
    GRAPHICAL_SCHEMA_FIELD,     0, 0,0, 0,1,0,0
    TRACK_BAR_FIELD,            0, 0,0, 0,0,1,0
    CHART_FIELD,                0, 0,0, 0,0,0,1
    CALENDAR_FIELD,             0, 0,1, 0,0,0,0
    CONTEXT_MENU,              81,40, 1,33, 6,14,7
    EXTENDED_TOOLTIP,         249,88, 1,106,20,25,8
    GANTT_CHART_FIELD,          0, 0,0, 0,0,0,0
    GEOGRAPHICAL_SCHEMA_FIELD,  0, 0,0, 0,0,0,0
    PDF_DOCUMENT_FIELD,         0, 0,0, 0,0,0,0
    PERIOD_FIELD,               0, 0,0, 0,0,0,0
    PLANNER_FIELD,              0, 0,0, 0,0,0,0
    UNKNOWN,                    0, 0,0, 0,0,0,0
    """;

  private static Map<String, Long> parseCsvColumn(String csv, int col) {
    var map = new java.util.HashMap<String, Long>();
    for (var line : csv.strip().split("\n")) {
      var parts = line.trim().split("\\s*,\\s*");
      var name = parts[0].trim();
      var value = Long.parseLong(parts[col + 1].trim());
      map.put(name, value);
    }
    return map;
  }

  private static Stream<Arguments> formFileProvider() {
    // File metadata: pack, parentRef, formRef, total
    var files = new String[][]{
      {"ssl_3_2", "Catalogs.РассылкиОтчетов", "Catalog.РассылкиОтчетов.Form.ФормаЭлемента", "585"},
      {"ssl_3_1", "DataProcessors.ЗагрузкаДанныхИзФайла",
        "DataProcessor.ЗагрузкаДанныхИзФайла.Form.ЗагрузкаДанныхИзФайла", "219"},
      {"ssl_3_1", "SettingsStorages.ХранилищеВариантовОтчетов",
        "SettingsStorage.ХранилищеВариантовОтчетов.Form.ВыборФинансовогоПериодаДень", "4"},
      {"ssl_3_1", "Documents.ЭлектронноеПисьмоИсходящее", "Document.ЭлектронноеПисьмоИсходящее.Form.ФормаДокумента",
        "248"},
      {"ssl_3_1", "DataProcessors.КартаМаршрутаБизнесПроцесса", "DataProcessor.КартаМаршрутаБизнесПроцесса.Form.Форма",
        "47"},
      {"ssl_3_1", "DataProcessors.Сканирование", "DataProcessor.Сканирование.Form.НастройкаСканированияНаСеанс", "65"},
      {"ssl_3_1", "DataProcessors.ОценкаПроизводительности",
        "DataProcessor.ОценкаПроизводительности.Form.ПодборЦелевогоВремениКлючевойОперации", "24"},
    };

    var baseClasses = new HashMap<String, Long>();
    var baseTypes = new HashMap<String, Long>();

    var result = new java.util.ArrayList<Arguments>();
    for (int i = 0; i < files.length; i++) {
      var pack = files[i][0];
      var parentRef = files[i][1];
      var formRef = files[i][2];
      var total = Integer.parseInt(files[i][3]);

      // Both EDT and Designer must produce the same counts
      baseClasses.clear();
      baseClasses.putAll(parseCsvColumn(CLASS_MATRIX_TXT, i));
      baseTypes.clear();
      baseTypes.putAll(parseCsvColumn(TYPE_MATRIX_TXT, i));

      result.add(Arguments.of(true, pack, parentRef, formRef, total,
        Map.copyOf(baseClasses), Map.copyOf(baseTypes)));
      result.add(Arguments.of(false, pack, parentRef, formRef, total,
        Map.copyOf(baseClasses), Map.copyOf(baseTypes)));
    }
    return result.stream();
  }

  private static FormElement findElement(List<FormElement> elements, String name) {
    return elements.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
  }
}
