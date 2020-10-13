/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.AccountingRegister;
import com.github._1c_syntax.mdclasses.mdo.AccumulationRegister;
import com.github._1c_syntax.mdclasses.mdo.BusinessProcess;
import com.github._1c_syntax.mdclasses.mdo.CalculationRegister;
import com.github._1c_syntax.mdclasses.mdo.Catalog;
import com.github._1c_syntax.mdclasses.mdo.ChartOfAccounts;
import com.github._1c_syntax.mdclasses.mdo.ChartOfCalculationTypes;
import com.github._1c_syntax.mdclasses.mdo.ChartOfCharacteristicTypes;
import com.github._1c_syntax.mdclasses.mdo.CommandGroup;
import com.github._1c_syntax.mdclasses.mdo.CommonAttribute;
import com.github._1c_syntax.mdclasses.mdo.CommonCommand;
import com.github._1c_syntax.mdclasses.mdo.CommonForm;
import com.github._1c_syntax.mdclasses.mdo.CommonModule;
import com.github._1c_syntax.mdclasses.mdo.CommonPicture;
import com.github._1c_syntax.mdclasses.mdo.CommonTemplate;
import com.github._1c_syntax.mdclasses.mdo.Constant;
import com.github._1c_syntax.mdclasses.mdo.DataProcessor;
import com.github._1c_syntax.mdclasses.mdo.DefinedType;
import com.github._1c_syntax.mdclasses.mdo.Document;
import com.github._1c_syntax.mdclasses.mdo.DocumentJournal;
import com.github._1c_syntax.mdclasses.mdo.DocumentNumerator;
import com.github._1c_syntax.mdclasses.mdo.EventSubscription;
import com.github._1c_syntax.mdclasses.mdo.ExchangePlan;
import com.github._1c_syntax.mdclasses.mdo.FilterCriterion;
import com.github._1c_syntax.mdclasses.mdo.FormData;
import com.github._1c_syntax.mdclasses.mdo.FormItem;
import com.github._1c_syntax.mdclasses.mdo.FunctionalOption;
import com.github._1c_syntax.mdclasses.mdo.FunctionalOptionsParameter;
import com.github._1c_syntax.mdclasses.mdo.HTTPService;
import com.github._1c_syntax.mdclasses.mdo.InformationRegister;
import com.github._1c_syntax.mdclasses.mdo.MDOConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDOEnum;
import com.github._1c_syntax.mdclasses.mdo.MDObjectComplex;
import com.github._1c_syntax.mdclasses.mdo.Report;
import com.github._1c_syntax.mdclasses.mdo.Role;
import com.github._1c_syntax.mdclasses.mdo.ScheduledJob;
import com.github._1c_syntax.mdclasses.mdo.Sequence;
import com.github._1c_syntax.mdclasses.mdo.SessionParameter;
import com.github._1c_syntax.mdclasses.mdo.SettingsStorage;
import com.github._1c_syntax.mdclasses.mdo.Style;
import com.github._1c_syntax.mdclasses.mdo.StyleItem;
import com.github._1c_syntax.mdclasses.mdo.Subsystem;
import com.github._1c_syntax.mdclasses.mdo.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.Task;
import com.github._1c_syntax.mdclasses.mdo.WSReference;
import com.github._1c_syntax.mdclasses.mdo.WebService;
import com.github._1c_syntax.mdclasses.mdo.XDTOPackage;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerChildObjects;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerWrapper;
import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerAttribute;
import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerChildItems;
import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerForm;
import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerFormItem;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationExtensionPurpose;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ObjectBelonging;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.ByteConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.FloatConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.converters.basic.ShortConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Класс для чтения XML и MDO файлов конфигурации
 */
@Slf4j
@UtilityClass
public class XStreamFactory {
  private final String ATTRIBUTE_FIELD_NAME = "attributes";
  private final String CHILDREN_FIELD_NAME = "children";
  private static final List<Class<?>> CLASSES_FOR_FORM = createListClassesForForm();
  /**
   * Используется для чтения элементов формы (см. FormEventConverter, DesignerFormItemConverter)
   */
  @Getter
  private static Converter reflectionConverter;
  @Getter(lazy = true)
  private final XStream xstream = createXMLMapper();

  /**
   * Выполняет чтение объекта из XML файла
   */
  public Object fromXML(File file) {
    Object result;
    try {
      result = getXstream().fromXML(file);
    } catch (ConversionException e) {
      LOGGER.error("Can't read file '{}' - it's broken \n: ", file.toString(), e);
      throw e;
    }
    return result;
  }

  private XStream createXMLMapper() {
    // данный провайдер необходим для корректной обработки значений по умолчанию, чтобы не было null
    var xStream = new XStream(new PureJavaReflectionProvider()) {

      // TODO как починят https://github.com/x-stream/xstream/issues/101
      // После исправления бага (с 2017 года) убрать этот код

      /**
       * Переопределение списка регистрируемых конвертеров. Оставлены только те, что нужны, особенно исключены те,
       * что вызывают недовольство у JVM, в связи с неправильным доступом при рефлексии
       */
      @Override
      protected void setupConverters() {
        reflectionConverter = new ReflectionConverter(getMapper(), getReflectionProvider());

        registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
        registerConverter(new IntConverter(), PRIORITY_NORMAL);
        registerConverter(new FloatConverter(), PRIORITY_NORMAL);
        registerConverter(new DoubleConverter(), PRIORITY_NORMAL);
        registerConverter(new LongConverter(), PRIORITY_NORMAL);
        registerConverter(new ShortConverter(), PRIORITY_NORMAL);
        registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
        registerConverter(new ByteConverter(), PRIORITY_NORMAL);
        registerConverter(new StringConverter(), PRIORITY_NORMAL);
        registerConverter(new DateConverter(), PRIORITY_NORMAL);
        registerConverter(new CollectionConverter(getMapper()), PRIORITY_NORMAL);
        registerConverter(reflectionConverter, PRIORITY_VERY_LOW);
      }
    };
    // автоопределение аннотаций
    xStream.autodetectAnnotations(true);
    // игнорирование неизвестных тегов
    xStream.ignoreUnknownElements();
    // настройки безопасности доступа к данным
    xStream.setMode(XStream.NO_REFERENCES);
    XStream.setupDefaultSecurity(xStream);
    xStream.addPermission(NoTypePermission.NONE);
    xStream.addPermission(new WildcardTypePermission(new String[]{"com.github._1c_syntax.**"}));

    // необходимо зарегистрировать все классы, имена которых в XML отличаются от имен самих классов
    addClassAliases(xStream);
    // для каждого типа данных или поля необходимо зарегистрировать конвертер
    addConverters(xStream);
    // все типы реквизитов добавляются в одно поле
    addFieldAliases(xStream);

    return xStream;
  }

  private void addFieldAliases(XStream xStream) {

    // дочерние элементы
    MDOType.valuesWithoutChildren().forEach((MDOType type) -> {
      xStream.aliasField(type.getName(), DesignerChildObjects.class, CHILDREN_FIELD_NAME);

      if (type.getGroupName().isEmpty()) {
        return;
      }
      String fieldName;
      switch (type) {
        case WS_REFERENCE:
          fieldName = "wsReferences";
          break;
        case XDTO_PACKAGE:
          fieldName = "xDTOPackages";
          break;
        case HTTP_SERVICE:
          fieldName = "httpServices";
          break;
        default:
          var groupName = type.getGroupName();
          fieldName = groupName.substring(0, 1).toLowerCase(Locale.ENGLISH) + groupName.substring(1);
      }
      xStream.aliasField(fieldName, MDOConfiguration.class, CHILDREN_FIELD_NAME);
    });

    // реквизиты объекта
    xStream.aliasField("dimensions", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("resources", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("recalculations", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField(ATTRIBUTE_FIELD_NAME, MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("tabularSections", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("accountingFlags", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("extDimensionAccountingFlags", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("columns", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("addressingAttributes", MDObjectComplex.class, ATTRIBUTE_FIELD_NAME);

    // у табличной части тоже есть свои атрибуты
    xStream.aliasField(ATTRIBUTE_FIELD_NAME, TabularSection.class, ATTRIBUTE_FIELD_NAME);

    // поля подсистем
    xStream.aliasField("subsystems", Subsystem.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("content", Subsystem.class, CHILDREN_FIELD_NAME);

    // свойства формы
    CLASSES_FOR_FORM.forEach(aClass -> {
      xStream.aliasField("items", aClass, CHILDREN_FIELD_NAME);
      xStream.aliasField("autoCommandBar", aClass, CHILDREN_FIELD_NAME);
      xStream.aliasField("extendedTooltip", aClass, CHILDREN_FIELD_NAME);
      xStream.aliasField("contextMenu", aClass, CHILDREN_FIELD_NAME);
      xStream.aliasField("viewStatusAddition", aClass, CHILDREN_FIELD_NAME);
      xStream.aliasField("searchControlAddition", aClass, CHILDREN_FIELD_NAME);
    });

    xStream.aliasField("Events", DesignerForm.class, "events");

    addDesignerFormItemAliases(xStream);
    addDesignerFormCommonAliases(xStream);
  }

  private void addDesignerFormCommonAliases(XStream xStream) {
    xStream.aliasField("ChildItems", DesignerForm.class, "childItems");
    xStream.aliasField("ChildItems", DesignerFormItem.class, "childItems");

    xStream.aliasField("MainAttribute", DesignerAttribute.class, "main");
    xStream.aliasField("Columns", DesignerAttribute.class, "designerColumns");
    xStream.aliasField("DataPath", DesignerFormItem.class, "dataPath");
  }

  private void addDesignerFormItemAliases(XStream xStream) {
    // элементы формы
    xStream.aliasField("AutoCommandBar", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Button", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ButtonGroup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("CalendarField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ChartField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("CheckBoxField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ColumnGroup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Command", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("CommandBar", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("CommandBarButton", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("CommandBarHyperlink", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ContextMenu", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("DendrogramField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("FormattedDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("GanttChartField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("GeographicalSchemaField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("GraphicalSchemaField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("HTMLDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Hyperlink", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("InputField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("LabelDecoration", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("LabelField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Navigator", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Page", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Pages", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("PeriodField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("PictureDecoration", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("PictureField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("PlannerField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ProgressBarField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("RadioButtonField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("SpreadSheetDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Table", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("TextDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("TrackBarField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("UsualButton", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("UsualGroup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Popup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ViewStatusAddition", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ExtendedTooltip", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("SearchStringAddition", DesignerChildItems.class, CHILDREN_FIELD_NAME);

    // корень формы
    xStream.aliasField("AutoCommandBar", DesignerForm.class, "autoCommandBar");

    // элемент формы
    xStream.aliasField("ContextMenu", DesignerFormItem.class, "contextMenu");
    xStream.aliasField("ExtendedTooltip", DesignerFormItem.class, "extendedTooltip");
    xStream.aliasField("AutoCommandBar", DesignerFormItem.class, "autoCommandBar");
    xStream.aliasField("SearchStringAddition", DesignerFormItem.class, "searchStringAddition");
    xStream.aliasField("ViewStatusAddition", DesignerFormItem.class, "viewStatusAddition");
  }

  private void addClassAliases(XStream xStream) {
    xStream.alias("mdclass:AccountingRegister", AccountingRegister.class);
    xStream.alias("mdclass:AccumulationRegister", AccumulationRegister.class);
    xStream.alias("mdclass:BusinessProcess", BusinessProcess.class);
    xStream.alias("mdclass:CalculationRegister", CalculationRegister.class);
    xStream.alias("mdclass:Catalog", Catalog.class);
    xStream.alias("mdclass:ChartOfAccounts", ChartOfAccounts.class);
    xStream.alias("mdclass:ChartOfCalculationTypes", ChartOfCalculationTypes.class);
    xStream.alias("mdclass:ChartOfCharacteristicTypes", ChartOfCharacteristicTypes.class);
    xStream.alias("mdclass:CommandGroup", CommandGroup.class);
    xStream.alias("mdclass:CommonAttribute", CommonAttribute.class);
    xStream.alias("mdclass:CommonCommand", CommonCommand.class);
    xStream.alias("mdclass:CommonForm", CommonForm.class);
    xStream.alias("mdclass:CommonModule", CommonModule.class);
    xStream.alias("mdclass:CommonPicture", CommonPicture.class);
    xStream.alias("mdclass:CommonTemplate", CommonTemplate.class);
    xStream.alias("mdclass:Constant", Constant.class);
    xStream.alias("mdclass:DataProcessor", DataProcessor.class);
    xStream.alias("mdclass:DefinedType", DefinedType.class);
    xStream.alias("mdclass:Document", Document.class);
    xStream.alias("mdclass:DocumentJournal", DocumentJournal.class);
    xStream.alias("mdclass:DocumentNumerator", DocumentNumerator.class);
    xStream.alias("mdclass:EventSubscription", EventSubscription.class);
    xStream.alias("mdclass:ExchangePlan", ExchangePlan.class);
    xStream.alias("mdclass:FilterCriterion", FilterCriterion.class);
    xStream.alias("mdclass:FunctionalOption", FunctionalOption.class);
    xStream.alias("mdclass:FunctionalOptionsParameter", FunctionalOptionsParameter.class);
    xStream.alias("mdclass:HTTPService", HTTPService.class);
    xStream.alias("mdclass:InformationRegister", InformationRegister.class);
    xStream.alias("mdclass:Enum", MDOEnum.class);
    xStream.alias("mdclass:Report", Report.class);
    xStream.alias("mdclass:Role", Role.class);
    xStream.alias("mdclass:ScheduledJob", ScheduledJob.class);
    xStream.alias("mdclass:Sequence", Sequence.class);
    xStream.alias("mdclass:SessionParameter", SessionParameter.class);
    xStream.alias("mdclass:SettingsStorage", SettingsStorage.class);
    xStream.alias("mdclass:Style", Style.class);
    xStream.alias("mdclass:StyleItem", StyleItem.class);
    xStream.alias("mdclass:Subsystem", Subsystem.class);
    xStream.alias("mdclass:Task", Task.class);
    xStream.alias("mdclass:WebService", WebService.class);
    xStream.alias("mdclass:WSReference", WSReference.class);
    xStream.alias("mdclass:XDTOPackage", XDTOPackage.class);
    xStream.alias("mdclass:Configuration", MDOConfiguration.class);
    xStream.alias("form:Form", FormData.class);
    xStream.alias("MetaDataObject", DesignerWrapper.class);
    xStream.alias("Form", DesignerForm.class);
  }

  private void addConverters(XStream xStream) {
    xStream.registerConverter(new EnumConverter(ReturnValueReuse.class));
    xStream.registerConverter(new EnumConverter(UseMode.class));
    xStream.registerConverter(new EnumConverter(ScriptVariant.class));
    xStream.registerConverter(new EnumConverter(ConfigurationExtensionPurpose.class));
    xStream.registerConverter(new EnumConverter(ObjectBelonging.class));
    xStream.registerConverter(new AttributeConverter());
    xStream.registerConverter(new CompatibilityModeConverter());
    xStream.registerConverter(new PairConverter());
    xStream.registerConverter(new DataPathConverter());
    xStream.registerConverter(new FormEventConverter());
    xStream.registerConverter(new DesignerFormItemConverter());
    xStream.registerConverter(new FormItemConverter());
  }

  private static List<Class<?>> createListClassesForForm() {
    List<Class<?>> list = new ArrayList<>();
    list.add(FormData.class);
    list.add(FormItem.class);
    return list;
  }
}
