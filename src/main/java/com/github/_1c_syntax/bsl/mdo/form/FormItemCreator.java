/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.bsl.mdo.form;

import com.github._1c_syntax.bsl.mdo.form.item.BaseFormItem;
import com.github._1c_syntax.bsl.mdo.form.item.DataPathRelated;
import com.github._1c_syntax.bsl.mdo.form.item.FormItemEventDriven;
import com.github._1c_syntax.bsl.mdo.form.item.UnknownFormItem;
import com.github._1c_syntax.bsl.mdo.form.item.additional.ExtendedTooltip;
import com.github._1c_syntax.bsl.mdo.form.item.additional.SearchStringAddition;
import com.github._1c_syntax.bsl.mdo.form.item.additional.ViewStatusAddition;
import com.github._1c_syntax.bsl.mdo.form.item.button.CommandBarButton;
import com.github._1c_syntax.bsl.mdo.form.item.button.CommandBarHyperlink;
import com.github._1c_syntax.bsl.mdo.form.item.button.Hyperlink;
import com.github._1c_syntax.bsl.mdo.form.item.button.UsualButton;
import com.github._1c_syntax.bsl.mdo.form.item.decoration.LabelDecoration;
import com.github._1c_syntax.bsl.mdo.form.item.decoration.PictureDecoration;
import com.github._1c_syntax.bsl.mdo.form.item.field.CalendarField;
import com.github._1c_syntax.bsl.mdo.form.item.field.ChartField;
import com.github._1c_syntax.bsl.mdo.form.item.field.CheckBoxField;
import com.github._1c_syntax.bsl.mdo.form.item.field.DendrogramField;
import com.github._1c_syntax.bsl.mdo.form.item.field.FormattedDocumentField;
import com.github._1c_syntax.bsl.mdo.form.item.field.GanttChartField;
import com.github._1c_syntax.bsl.mdo.form.item.field.GeographicalSchemaField;
import com.github._1c_syntax.bsl.mdo.form.item.field.GraphicalSchemaField;
import com.github._1c_syntax.bsl.mdo.form.item.field.HtmlDocumentField;
import com.github._1c_syntax.bsl.mdo.form.item.field.InputField;
import com.github._1c_syntax.bsl.mdo.form.item.field.LabelField;
import com.github._1c_syntax.bsl.mdo.form.item.field.PeriodField;
import com.github._1c_syntax.bsl.mdo.form.item.field.PictureField;
import com.github._1c_syntax.bsl.mdo.form.item.field.PlannerField;
import com.github._1c_syntax.bsl.mdo.form.item.field.ProgressBarField;
import com.github._1c_syntax.bsl.mdo.form.item.field.RadioButtonField;
import com.github._1c_syntax.bsl.mdo.form.item.field.SpreadSheetDocumentField;
import com.github._1c_syntax.bsl.mdo.form.item.field.TextDocumentField;
import com.github._1c_syntax.bsl.mdo.form.item.field.TrackBarField;
import com.github._1c_syntax.bsl.mdo.form.item.group.AutoCommandBar;
import com.github._1c_syntax.bsl.mdo.form.item.group.ButtonGroup;
import com.github._1c_syntax.bsl.mdo.form.item.group.ColumnGroup;
import com.github._1c_syntax.bsl.mdo.form.item.group.CommandBar;
import com.github._1c_syntax.bsl.mdo.form.item.group.ContextMenu;
import com.github._1c_syntax.bsl.mdo.form.item.group.Page;
import com.github._1c_syntax.bsl.mdo.form.item.group.Pages;
import com.github._1c_syntax.bsl.mdo.form.item.group.Popup;
import com.github._1c_syntax.bsl.mdo.form.item.group.UsualGroup;
import com.github._1c_syntax.bsl.mdo.form.item.table.TableItem;
import com.github._1c_syntax.mdclasses.mdo.children.form.EdtFormData;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormHandlerItem;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormItem;
import com.github._1c_syntax.mdclasses.mdo.children.form.InputFieldExtInfo;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@UtilityClass
public class FormItemCreator {
  private static final Map<String, Class<? extends BaseFormItem>> CLASS_MAPPING = createClassMapping();
  private static final Map<Class<? extends BaseFormItem>, Callable<Object>> BUILDER_MAPPING = createBuilderMapping();

  @SneakyThrows
  public BaseFormItem createByType(FormItem formItem, String type) {
    var itemClass = CLASS_MAPPING.getOrDefault(type, UnknownFormItem.class);
    var builder = BUILDER_MAPPING.get(itemClass).call();

    fillIfDataPathRelated(builder, itemClass, formItem);
    fillIfEventDrivenItem(builder, itemClass, formItem.getHandlers());

    fillByItemType(builder, itemClass, formItem);

    return baseBuild(builder, formItem);
  }

  public List<FormItemHandler> createFormItemHandlers(List<FormHandlerItem> handlers) {
    return handlers.stream()
      .map(handler -> new FormItemHandler(handler.getEvent(), handler.getName()))
      .collect(Collectors.toUnmodifiableList());
  }

  public BaseFormItem createFormItem(EdtFormData formData) {
    final var itemClass = com.github._1c_syntax.bsl.mdo.form.item.form.FormItem.class;
    var builder = com.github._1c_syntax.bsl.mdo.form.item.form.FormItem.builder();

    TransformationUtils.setValue(builder, "children", new ArrayList<>());
    TransformationUtils.setValue(builder, "visibility", true);
    TransformationUtils.setValue(builder, "enabled", formData.isEnabled());

    fillIfEventDrivenItem(builder, itemClass, formData.getHandlers());

    return (BaseFormItem) TransformationUtils.build(builder);
  }

  private void fillByItemType(Object builder, Class<? extends BaseFormItem> itemClass, FormItem formItem) {

    if (itemClass.equals(InputField.class)) {
      var extInfo = formItem.getExtInfo();
      if (extInfo instanceof InputFieldExtInfo) {
        TransformationUtils.setValue(builder, "passwordMode", ((InputFieldExtInfo) extInfo).getPasswordMode());
      }
    }

  }

  private void fillIfDataPathRelated(Object builder, Class<? extends BaseFormItem> itemClass, FormItem formItem) {
    if (DataPathRelated.class.isAssignableFrom(itemClass)) {
      TransformationUtils.setValue(builder, "dataPath", formItem.getDataPath().getSegment());
    }
  }

  private void fillIfEventDrivenItem(Object builder, Class<? extends BaseFormItem> itemClass, List<FormHandlerItem> handlers) {
    if (FormItemEventDriven.class.isAssignableFrom(itemClass)) {
      var newHandlers = createFormItemHandlers(handlers);
      TransformationUtils.setValue(builder, "handlers", newHandlers);
    }

  }

  private BaseFormItem baseBuild(Object baseBuilder, FormItem formItem) {
    fillCommonFiled(baseBuilder, formItem);
    return (BaseFormItem) TransformationUtils.build(baseBuilder);
  }

  private void fillCommonFiled(Object builder, FormItem formItem) {
    TransformationUtils.setValue(builder, "name", formItem.getName());
    TransformationUtils.setValue(builder, "id", formItem.getId());
    TransformationUtils.setValue(builder, "children", new ArrayList<>());
    TransformationUtils.setValue(builder, "visibility", formItem.isVisible());
    TransformationUtils.setValue(builder, "enabled", formItem.isEnabled());
  }

  private Map<String, Class<? extends BaseFormItem>> createClassMapping() {
    var map = new TreeMap<String, Class<? extends BaseFormItem>>(String.CASE_INSENSITIVE_ORDER);

    // additional
    // TODO: не ставить тип Label для ExtendedTooltip
    map.put("ExtendedTooltip", ExtendedTooltip.class);
    map.put("SearchControlAddition", SearchStringAddition.class);
    map.put("SearchStringAddition", SearchStringAddition.class);
    map.put("ViewStatusAddition", ViewStatusAddition.class);

    // button
    map.put("form:Button", CommandBarButton.class);
    map.put("CommandBarButton", CommandBarButton.class);
    map.put("CommandBarHyperlink", CommandBarHyperlink.class);
    map.put("Hyperlink", Hyperlink.class);
    map.put("UsualButton", UsualButton.class);
    map.put("Button", UsualButton.class);

    // decoration
    map.put("Label", LabelDecoration.class);
    map.put("LabelDecoration", LabelDecoration.class);
    map.put("form:Decoration", PictureDecoration.class);
    map.put("PictureDecoration", PictureDecoration.class);

    // field
    map.put("CalendarField", CalendarField.class);
    map.put("ChartField", ChartField.class);
    map.put("CheckBoxField", CheckBoxField.class);
    map.put("DendrogramField", DendrogramField.class);
    map.put("FormattedDocumentField", FormattedDocumentField.class);
    map.put("GanttChartField", GanttChartField.class);
    map.put("GeographicalSchemaField", GeographicalSchemaField.class);
    map.put("GraphicalSchemaField", GraphicalSchemaField.class);
    map.put("HTMLDocumentField", HtmlDocumentField.class);
    map.put("InputField", InputField.class);
    map.put("LabelField", LabelField.class);
    map.put("PeriodField", PeriodField.class);
    map.put("PictureField", PictureField.class);
    map.put("PlannerField", PlannerField.class);
    map.put("ProgressBarField", ProgressBarField.class);
    map.put("RadioButtonField", RadioButtonField.class);
    map.put("SpreadSheetDocumentField", SpreadSheetDocumentField.class);
    map.put("TextDocumentField", TextDocumentField.class);
    map.put("TrackBarField", TrackBarField.class);

    // form здесь нет

    // group
    map.put("AutoCommandBar", AutoCommandBar.class);
    map.put("form:FormGroup", ButtonGroup.class);
    map.put("ButtonGroup", ButtonGroup.class);
    map.put("ColumnGroup", ColumnGroup.class);
    map.put("CommandBar", CommandBar.class);
    map.put("ContextMenu", ContextMenu.class);
    map.put("Page", Page.class);
    map.put("Pages", Pages.class);
    map.put("Popup", Popup.class);
    map.put("UsualGroup", UsualGroup.class);

    // table
    map.put("form:Table", TableItem.class);
    map.put("Table", TableItem.class);

    return map;
  }

  private Map<Class<? extends BaseFormItem>, Callable<Object>> createBuilderMapping() {
    Map<Class<? extends BaseFormItem>, Callable<Object>> map = new HashMap<>();
    map.put(UnknownFormItem.class, UnknownFormItem::builder);

    // additional
    map.put(ExtendedTooltip.class, ExtendedTooltip::builder);
    map.put(SearchStringAddition.class, SearchStringAddition::builder);
    map.put(ViewStatusAddition.class, ViewStatusAddition::builder);

    // button
    map.put(CommandBarButton.class, CommandBarButton::builder);
    map.put(CommandBarHyperlink.class, CommandBarHyperlink::builder);
    map.put(Hyperlink.class, Hyperlink::builder);
    map.put(UsualButton.class, UsualButton::builder);

    // decoration
    map.put(LabelDecoration.class, LabelDecoration::builder);
    map.put(PictureDecoration.class, PictureDecoration::builder);

    // field
    map.put(CalendarField.class, CalendarField::builder);
    map.put(ChartField.class, ChartField::builder);
    map.put(CheckBoxField.class, CheckBoxField::builder);
    map.put(DendrogramField.class, DendrogramField::builder);
    map.put(FormattedDocumentField.class, FormattedDocumentField::builder);
    map.put(GanttChartField.class, GanttChartField::builder);
    map.put(GeographicalSchemaField.class, GeographicalSchemaField::builder);
    map.put(GraphicalSchemaField.class, GraphicalSchemaField::builder);
    map.put(HtmlDocumentField.class, HtmlDocumentField::builder);
    map.put(InputField.class, InputField::builder);
    map.put(LabelField.class, LabelField::builder);
    map.put(PeriodField.class, PeriodField::builder);
    map.put(PictureField.class, PictureField::builder);
    map.put(PlannerField.class, PlannerField::builder);
    map.put(ProgressBarField.class, ProgressBarField::builder);
    map.put(RadioButtonField.class, RadioButtonField::builder);
    map.put(SpreadSheetDocumentField.class, SpreadSheetDocumentField::builder);
    map.put(TextDocumentField.class, TextDocumentField::builder);
    map.put(TrackBarField.class, TrackBarField::builder);

    // form
    map.put(com.github._1c_syntax.bsl.mdo.form.item.form.FormItem.class,
      com.github._1c_syntax.bsl.mdo.form.item.form.FormItem::builder);

    // group
    map.put(AutoCommandBar.class, AutoCommandBar::builder);
    map.put(ButtonGroup.class, ButtonGroup::builder);
    map.put(ColumnGroup.class, ColumnGroup::builder);
    map.put(CommandBar.class, CommandBar::builder);
    map.put(ContextMenu.class, ContextMenu::builder);
    map.put(Page.class, Page::builder);
    map.put(Pages.class, Pages::builder);
    map.put(Popup.class, Popup::builder);
    map.put(UsualGroup.class, UsualGroup::builder);

    // table
    map.put(TableItem.class, TableItem::builder);

    return map;
  }

}
