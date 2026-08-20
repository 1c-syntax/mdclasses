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
package com.github._1c_syntax.bsl.reader.common.context;

import com.github._1c_syntax.bsl.mdo.storage.ManagedFormData;
import com.github._1c_syntax.bsl.mdo.storage.form.FormAddition;
import com.github._1c_syntax.bsl.mdo.storage.form.FormAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormButton;
import com.github._1c_syntax.bsl.mdo.storage.form.FormCommand;
import com.github._1c_syntax.bsl.mdo.storage.form.FormContextMenu;
import com.github._1c_syntax.bsl.mdo.storage.form.FormDecoration;
import com.github._1c_syntax.bsl.mdo.storage.form.FormElementType;
import com.github._1c_syntax.bsl.mdo.storage.form.FormField;
import com.github._1c_syntax.bsl.mdo.storage.form.FormGroup;
import com.github._1c_syntax.bsl.mdo.storage.form.FormParameter;
import com.github._1c_syntax.bsl.mdo.storage.form.FormTable;
import com.github._1c_syntax.bsl.mdo.storage.form.FormUnknown;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@EqualsAndHashCode(callSuper = true)
public class FormElementReaderContext extends AbstractReaderContext {

  private static final Class<?> DEFAULT_CLASS_FORM_ELEMENT = FormUnknown.class;
  private static final Map<String, Class<?>> CLASSES = fillClassesMap();
  private static final Map<FormElementType, Class<?>> ELEMENT_CLASSES = fillElementClasses();

  public FormElementReaderContext(String elementName, HierarchicalStreamReader reader) {
    super(reader);
    name = elementName;
    realClass = CLASSES.get(elementName.toLowerCase(Locale.ROOT));
    builder = TransformationUtils.builder(realClass);
  }

  public FormElementReaderContext(FormElementType formElementType, String elementName, HierarchicalStreamReader reader) {
    super(reader);
    name = elementName;
    realClass = CLASSES.getOrDefault(elementName.toLowerCase(Locale.ROOT), DEFAULT_CLASS_FORM_ELEMENT);
    if (realClass == DEFAULT_CLASS_FORM_ELEMENT) {
      var clazz = ELEMENT_CLASSES.getOrDefault(formElementType, FormUnknown.class);
      if (clazz != realClass) {
        realClass = clazz;
      }
    }

    builder = TransformationUtils.builder(realClass);
    setValue("type", formElementType);
  }

  @Override
  public Object build() {
    if (realClass == FormAttribute.class) {
      // нужно смержить колонки с допколонками
      mergeAdditionalColumns();
    }
    return super.build();
  }

  @Override
  public @Nullable Class<?> fieldType(String fieldName) {
    var clazz = CLASSES.get(fieldName.toLowerCase(Locale.ROOT));
    if (clazz == null) {
      clazz = super.fieldType(fieldName);
    }
    return clazz;
  }

  private static Map<FormElementType, Class<?>> fillElementClasses() {
    Map<FormElementType, Class<?>> elementMap = new HashMap<>();
    elementMap.put(FormElementType.TABLE, FormTable.class);
    elementMap.put(FormElementType.CONTEXT_MENU, FormContextMenu.class);

    List.of(FormElementType.INPUT_FIELD, FormElementType.CHECK_BOX_FIELD,
        FormElementType.CALENDAR_FIELD, FormElementType.HTML_DOCUMENT_FIELD,
        FormElementType.TEXT_DOCUMENT_FIELD, FormElementType.SPREAD_SHEET_DOCUMENT_FIELD,
        FormElementType.FORMATTED_DOCUMENT_FIELD, FormElementType.PDF_DOCUMENT_FIELD,
        FormElementType.RADIO_BUTTON_FIELD, FormElementType.TRACK_BAR_FIELD,
        FormElementType.PROGRESS_BAR_FIELD, FormElementType.PICTURE_FIELD,
        FormElementType.LABEL_FIELD, FormElementType.PERIOD_FIELD,
        FormElementType.PLANNER_FIELD)
      .forEach(type -> elementMap.put(type, FormField.class));

    List.of(FormElementType.GRAPHICAL_SCHEMA_FIELD, FormElementType.CHART_FIELD,
        FormElementType.GANTT_CHART_FIELD, FormElementType.GEOGRAPHICAL_SCHEMA_FIELD,
        FormElementType.DENDROGRAM_FIELD)
      .forEach(type -> elementMap.put(type, FormField.class));

    List.of(FormElementType.USUAL_GROUP, FormElementType.PAGE,
        FormElementType.PAGES, FormElementType.BUTTON_GROUP,
        FormElementType.COLUMN_GROUP, FormElementType.COMMAND_BAR,
        FormElementType.POPUP)
      .forEach(type -> elementMap.put(type, FormGroup.class));

    List.of(FormElementType.USUAL_BUTTON, FormElementType.COMMAND_BAR_BUTTON,
        FormElementType.COMMAND_BAR_HYPERLINK, FormElementType.HYPERLINK)
      .forEach(type -> elementMap.put(type, FormButton.class));

    List.of(FormElementType.LABEL_DECORATION, FormElementType.PICTURE_DECORATION)
      .forEach(type -> elementMap.put(type, FormDecoration.class));

    List.of(FormElementType.SEARCH_STRING_ADDITION,
        FormElementType.SEARCH_CONTROL_ADDITION, FormElementType.VIEW_STATUS_ADDITION)
      .forEach(type -> elementMap.put(type, FormAddition.class));

    elementMap.put(FormElementType.UNKNOWN, FormUnknown.class);

    return Collections.unmodifiableMap(elementMap);
  }

  private static Map<String, Class<?>> fillClassesMap() {
    return Map.ofEntries(
      Map.entry("formcommands", FormCommand.class),
      Map.entry("commands", FormCommand.class),
      Map.entry("command", FormCommand.class),
      Map.entry("parameters", FormParameter.class),
      Map.entry("parameter", FormParameter.class),
      Map.entry("form", ManagedFormData.class),
      Map.entry("attributes", FormAttribute.class),
      Map.entry("columns", FormAttribute.class),
      Map.entry("autocommandbar", FormGroup.class),
      Map.entry("additionalcolumns", FormAttribute.class),
      Map.entry("attribute", FormAttribute.class),
      Map.entry("column", FormAttribute.class),
      Map.entry(ManagedFormData.class.getName().toLowerCase(Locale.ROOT), ManagedFormData.class)
    );
  }

  private void mergeAdditionalColumns() {
    var value = getFromCache("columns");

    List<FormAttribute> columns = new ArrayList<>();
    if (value instanceof FormAttribute attribute) {
      columns.add(attribute);
    } else if (value instanceof List<?> list) {
      columns = list.stream().map(FormAttribute.class::cast).toList();
    } else {
      return;
    }

    var hasDotted = columns.stream().anyMatch(c -> c.getName().contains("."));
    if (!hasDotted) {
      return;
    }

    var regular = new ArrayList<FormAttribute>();
    var additional = new ArrayList<FormAttribute>();
    for (var fa : columns) {
      (fa.getName().contains(".") ? additional : regular).add(fa);
    }

    for (var addCol : additional) {
      var baseName = addCol.getName().substring(addCol.getName().lastIndexOf('.') + 1);
      for (int i = 0; i < regular.size(); i++) {
        if (regular.get(i).getName().equals(baseName)) {
          var mergedChildren = new ArrayList<>(regular.get(i).getColumns());
          mergedChildren.addAll(addCol.getColumns());
          regular.set(i, regular.get(i).toBuilder()
            .clearColumns()
            .columns(mergedChildren)
            .build());
          break;
        }
      }
    }

    TransformationUtils.invoke(builder, "clearColumns");
    setValue("columns", regular);
  }

}
