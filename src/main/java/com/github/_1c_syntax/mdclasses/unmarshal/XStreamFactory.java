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
package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDAccountingRegister;
import com.github._1c_syntax.mdclasses.mdo.MDAccumulationRegister;
import com.github._1c_syntax.mdclasses.mdo.MDBusinessProcess;
import com.github._1c_syntax.mdclasses.mdo.MDCalculationRegister;
import com.github._1c_syntax.mdclasses.mdo.MDCatalog;
import com.github._1c_syntax.mdclasses.mdo.MDChartOfAccounts;
import com.github._1c_syntax.mdclasses.mdo.MDChartOfCalculationTypes;
import com.github._1c_syntax.mdclasses.mdo.MDChartOfCharacteristicTypes;
import com.github._1c_syntax.mdclasses.mdo.MDCommandGroup;
import com.github._1c_syntax.mdclasses.mdo.MDCommonAttribute;
import com.github._1c_syntax.mdclasses.mdo.MDCommonCommand;
import com.github._1c_syntax.mdclasses.mdo.MDCommonForm;
import com.github._1c_syntax.mdclasses.mdo.MDCommonModule;
import com.github._1c_syntax.mdclasses.mdo.MDCommonPicture;
import com.github._1c_syntax.mdclasses.mdo.MDCommonTemplate;
import com.github._1c_syntax.mdclasses.mdo.MDConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDConstant;
import com.github._1c_syntax.mdclasses.mdo.MDDataProcessor;
import com.github._1c_syntax.mdclasses.mdo.MDDefinedType;
import com.github._1c_syntax.mdclasses.mdo.MDDocument;
import com.github._1c_syntax.mdclasses.mdo.MDDocumentJournal;
import com.github._1c_syntax.mdclasses.mdo.MDDocumentNumerator;
import com.github._1c_syntax.mdclasses.mdo.MDEnum;
import com.github._1c_syntax.mdclasses.mdo.MDEventSubscription;
import com.github._1c_syntax.mdclasses.mdo.MDExchangePlan;
import com.github._1c_syntax.mdclasses.mdo.MDFilterCriterion;
import com.github._1c_syntax.mdclasses.mdo.MDFunctionalOption;
import com.github._1c_syntax.mdclasses.mdo.MDFunctionalOptionsParameter;
import com.github._1c_syntax.mdclasses.mdo.MDHTTPService;
import com.github._1c_syntax.mdclasses.mdo.MDInformationRegister;
import com.github._1c_syntax.mdclasses.mdo.MDInterface;
import com.github._1c_syntax.mdclasses.mdo.MDLanguage;
import com.github._1c_syntax.mdclasses.mdo.MDOForm;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBSL;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectComplex;
import com.github._1c_syntax.mdclasses.mdo.MDReport;
import com.github._1c_syntax.mdclasses.mdo.MDRole;
import com.github._1c_syntax.mdclasses.mdo.MDScheduledJob;
import com.github._1c_syntax.mdclasses.mdo.MDSequence;
import com.github._1c_syntax.mdclasses.mdo.MDSessionParameter;
import com.github._1c_syntax.mdclasses.mdo.MDSettingsStorage;
import com.github._1c_syntax.mdclasses.mdo.MDStyle;
import com.github._1c_syntax.mdclasses.mdo.MDStyleItem;
import com.github._1c_syntax.mdclasses.mdo.MDSubsystem;
import com.github._1c_syntax.mdclasses.mdo.MDTask;
import com.github._1c_syntax.mdclasses.mdo.MDWSReference;
import com.github._1c_syntax.mdclasses.mdo.MDWebService;
import com.github._1c_syntax.mdclasses.mdo.MDXDTOPackage;
import com.github._1c_syntax.mdclasses.mdo.attributes.AbstractMDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.attributes.AccountingFlag;
import com.github._1c_syntax.mdclasses.mdo.attributes.AddressingAttribute;
import com.github._1c_syntax.mdclasses.mdo.attributes.Attribute;
import com.github._1c_syntax.mdclasses.mdo.attributes.Column;
import com.github._1c_syntax.mdclasses.mdo.attributes.Dimension;
import com.github._1c_syntax.mdclasses.mdo.attributes.ExtDimensionAccountingFlag;
import com.github._1c_syntax.mdclasses.mdo.attributes.Recalculation;
import com.github._1c_syntax.mdclasses.mdo.attributes.Resource;
import com.github._1c_syntax.mdclasses.mdo.attributes.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.children.Command;
import com.github._1c_syntax.mdclasses.mdo.children.Form;
import com.github._1c_syntax.mdclasses.mdo.children.HTTPServiceMethod;
import com.github._1c_syntax.mdclasses.mdo.children.HTTPServiceURLTemplate;
import com.github._1c_syntax.mdclasses.mdo.children.Template;
import com.github._1c_syntax.mdclasses.mdo.children.WEBServiceOperation;
import com.github._1c_syntax.mdclasses.mdo.children.form.DynamicListExtInfo;
import com.github._1c_syntax.mdclasses.mdo.children.form.ExtInfo;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormAttribute;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormCommand;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormData;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormHandlerItem;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormItem;
import com.github._1c_syntax.mdclasses.mdo.children.template.DataCompositionSchema;
import com.github._1c_syntax.mdclasses.mdo.children.template.DataSet;
import com.github._1c_syntax.mdclasses.mdo.children.template.TemplateType;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeMetadata;
import com.github._1c_syntax.mdclasses.mdo.metadata.MetadataStorage;
import com.github._1c_syntax.mdclasses.mdo.support.ConfigurationExtensionPurpose;
import com.github._1c_syntax.mdclasses.mdo.support.DataLockControlMode;
import com.github._1c_syntax.mdclasses.mdo.support.DataPath;
import com.github._1c_syntax.mdclasses.mdo.support.Handler;
import com.github._1c_syntax.mdclasses.mdo.support.MDOModule;
import com.github._1c_syntax.mdclasses.mdo.support.MDOReference;
import com.github._1c_syntax.mdclasses.mdo.support.MDOSynonym;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ObjectBelonging;
import com.github._1c_syntax.mdclasses.mdo.support.ObjectRight;
import com.github._1c_syntax.mdclasses.mdo.support.ReturnValueReuse;
import com.github._1c_syntax.mdclasses.mdo.support.Right;
import com.github._1c_syntax.mdclasses.mdo.support.RoleData;
import com.github._1c_syntax.mdclasses.mdo.support.ScriptVariant;
import com.github._1c_syntax.mdclasses.mdo.support.UseMode;
import com.github._1c_syntax.mdclasses.mdo.support.ValueType;
import com.github._1c_syntax.mdclasses.unmarshal.converters.AttributeConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.CompatibilityModeConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.DataPathConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.DataSetConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.DesignerFormConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.DesignerFormItemConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.DesignerMDOConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.EnumConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.FormEventConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.FormItemConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.PairConverter;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerChildObjects;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerFormWrapper;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerProperties;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerRootWrapper;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerSynonym;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerXRItems;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerAttribute;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerAttributeSetting;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerAttributeType;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerAttributes;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerChildItems;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerColumn;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerColumns;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerEvent;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerEvents;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerForm;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerFormCommand;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerFormCommands;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerFormItem;
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
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.nio.file.Path;
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

  public Class<?> getRealClass(String className) {
    return getXstream().getMapper().realClass(className);
  }

  public Path getCurrentPath(HierarchicalStreamReader reader) {
    return ((ExtendReaderWrapper) reader).getPath();
  }

  public XMLStreamReader getXMLStreamReader(HierarchicalStreamReader reader) {
    return ((ExtendReaderWrapper) reader).getXMLStreamReader();
  }

  private XStream createXMLMapper() {
    // данный провайдер необходим для корректной обработки значений по умолчанию, чтобы не было null
    var qNameMap = new QNameMap();
    qNameMap.registerMapping(new QName("http://g5.1c.ru/v8/dt/form", "Form", "form"), FormData.class);
    qNameMap.registerMapping(new QName("http://v8.1c.ru/8.3/xcf/logform", "Form"), DesignerFormWrapper.class);

    var xStream = new XStream(new PureJavaReflectionProvider(), new ExtendStaxDriver(qNameMap)) {

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
    xStream.autodetectAnnotations(false);
    processAnnotationsForMDO(xStream);

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

  private void processAnnotationsForMDO(XStream xstream) {
    xstream.processAnnotations(AccountingFlag.class);
    xstream.processAnnotations(MDAccountingRegister.class);
    xstream.processAnnotations(MDAccumulationRegister.class);
    xstream.processAnnotations(DataPath.class);
    xstream.processAnnotations(MDOModule.class);
    xstream.processAnnotations(MDOReference.class);
    xstream.processAnnotations(ValueType.class);
    xstream.processAnnotations(AddressingAttribute.class);
    xstream.processAnnotations(Attribute.class);
    xstream.processAnnotations(MDBusinessProcess.class);
    xstream.processAnnotations(MDCalculationRegister.class);
    xstream.processAnnotations(MDCatalog.class);
    xstream.processAnnotations(MDChartOfAccounts.class);
    xstream.processAnnotations(MDChartOfCalculationTypes.class);
    xstream.processAnnotations(MDChartOfCharacteristicTypes.class);
    xstream.processAnnotations(Column.class);
    xstream.processAnnotations(Command.class);
    xstream.processAnnotations(MDCommandGroup.class);
    xstream.processAnnotations(MDCommonAttribute.class);
    xstream.processAnnotations(MDCommonCommand.class);
    xstream.processAnnotations(MDCommonForm.class);
    xstream.processAnnotations(MDCommonModule.class);
    xstream.processAnnotations(MDCommonPicture.class);
    xstream.processAnnotations(MDCommonTemplate.class);
    xstream.processAnnotations(MDConstant.class);
    xstream.processAnnotations(MDDataProcessor.class);
    xstream.processAnnotations(MDDefinedType.class);
    xstream.processAnnotations(Dimension.class);
    xstream.processAnnotations(MDDocument.class);
    xstream.processAnnotations(MDDocumentJournal.class);
    xstream.processAnnotations(MDDocumentNumerator.class);
    xstream.processAnnotations(MDEventSubscription.class);
    xstream.processAnnotations(MDExchangePlan.class);
    xstream.processAnnotations(ExtDimensionAccountingFlag.class);
    xstream.processAnnotations(MDFilterCriterion.class);
    xstream.processAnnotations(DynamicListExtInfo.class);
    xstream.processAnnotations(ExtInfo.class);
    xstream.processAnnotations(FormAttribute.class);
    xstream.processAnnotations(FormCommand.class);
    xstream.processAnnotations(FormData.class);
    xstream.processAnnotations(DataCompositionSchema.class);
    xstream.processAnnotations(DataSet.class);
    xstream.processAnnotations(FormHandlerItem.class);
    xstream.processAnnotations(FormItem.class);
    xstream.processAnnotations(Form.class);
    xstream.processAnnotations(MDFunctionalOption.class);
    xstream.processAnnotations(MDFunctionalOptionsParameter.class);
    xstream.processAnnotations(Handler.class);
    xstream.processAnnotations(MDHTTPService.class);
    xstream.processAnnotations(HTTPServiceMethod.class);
    xstream.processAnnotations(HTTPServiceURLTemplate.class);
    xstream.processAnnotations(MDInformationRegister.class);
    xstream.processAnnotations(MDLanguage.class);
    xstream.processAnnotations(AbstractMDOAttribute.class);
    xstream.processAnnotations(AbstractMDObjectBase.class);
    xstream.processAnnotations(AbstractMDObjectBSL.class);
    xstream.processAnnotations(AbstractMDObjectComplex.class);
    xstream.processAnnotations(MDConfiguration.class);
    xstream.processAnnotations(MDEnum.class);
    xstream.processAnnotations(MDOForm.class);
    xstream.processAnnotations(MDInterface.class);
    xstream.processAnnotations(MDOSynonym.class);
    xstream.processAnnotations(ObjectRight.class);
    xstream.processAnnotations(Recalculation.class);
    xstream.processAnnotations(MDReport.class);
    xstream.processAnnotations(Resource.class);
    xstream.processAnnotations(Right.class);
    xstream.processAnnotations(MDRole.class);
    xstream.processAnnotations(RoleData.class);
    xstream.processAnnotations(MDScheduledJob.class);
    xstream.processAnnotations(MDSequence.class);
    xstream.processAnnotations(MDSessionParameter.class);
    xstream.processAnnotations(MDSettingsStorage.class);
    xstream.processAnnotations(MDStyle.class);
    xstream.processAnnotations(MDStyleItem.class);
    xstream.processAnnotations(MDSubsystem.class);
    xstream.processAnnotations(TabularSection.class);
    xstream.processAnnotations(MDTask.class);
    xstream.processAnnotations(Template.class);
    xstream.processAnnotations(MDWebService.class);
    xstream.processAnnotations(WEBServiceOperation.class);
    xstream.processAnnotations(DesignerChildObjects.class);
    xstream.processAnnotations(DesignerMDO.class);
    xstream.processAnnotations(DesignerProperties.class);
    xstream.processAnnotations(DesignerSynonym.class);
    xstream.processAnnotations(DesignerRootWrapper.class);
    xstream.processAnnotations(DesignerXRItems.class);
    xstream.processAnnotations(DesignerAttribute.class);
    xstream.processAnnotations(DesignerAttributes.class);
    xstream.processAnnotations(DesignerAttributeSetting.class);
    xstream.processAnnotations(DesignerAttributeType.class);
    xstream.processAnnotations(DesignerChildItems.class);
    xstream.processAnnotations(DesignerColumn.class);
    xstream.processAnnotations(DesignerColumns.class);
    xstream.processAnnotations(DesignerEvent.class);
    xstream.processAnnotations(DesignerEvents.class);
    xstream.processAnnotations(DesignerForm.class);
    xstream.processAnnotations(DesignerFormCommand.class);
    xstream.processAnnotations(DesignerFormCommands.class);
    xstream.processAnnotations(DesignerFormItem.class);
    xstream.processAnnotations(MDWSReference.class);
    xstream.processAnnotations(MDXDTOPackage.class);

    MetadataStorage.getStorage().forEach((aClass, metadata) -> xstream.aliasType(metadata.name(), aClass));

    MetadataStorage.getAttributeStorage().forEach((Class<?> aClass, AttributeMetadata metadata) -> {
      xstream.aliasType(metadata.name(), aClass);
      xstream.aliasType(metadata.fieldNameEDT(), aClass);
    });
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
      xStream.aliasField(fieldName, MDConfiguration.class, CHILDREN_FIELD_NAME);
    });

    // реквизиты объекта
    xStream.aliasField("dimensions", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("resources", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("recalculations", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField(ATTRIBUTE_FIELD_NAME, AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("tabularSections", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("accountingFlags", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("extDimensionAccountingFlags", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("columns", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("addressingAttributes", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);

    // у табличной части тоже есть свои атрибуты
    xStream.aliasField(ATTRIBUTE_FIELD_NAME, TabularSection.class, ATTRIBUTE_FIELD_NAME);

    // поля подсистем
    xStream.aliasField("subsystems", MDSubsystem.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("content", MDSubsystem.class, CHILDREN_FIELD_NAME);

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
    xStream.alias("AccountingRegister", MDAccountingRegister.class);
    xStream.alias("AccumulationRegister", MDAccumulationRegister.class);
    xStream.alias("BusinessProcess", MDBusinessProcess.class);
    xStream.alias("CalculationRegister", MDCalculationRegister.class);
    xStream.alias("Catalog", MDCatalog.class);
    xStream.alias("ChartOfAccounts", MDChartOfAccounts.class);
    xStream.alias("ChartOfCalculationTypes", MDChartOfCalculationTypes.class);
    xStream.alias("ChartOfCharacteristicTypes", MDChartOfCharacteristicTypes.class);
    xStream.alias("CommandGroup", MDCommandGroup.class);
    xStream.alias("CommonAttribute", MDCommonAttribute.class);
    xStream.alias("CommonCommand", MDCommonCommand.class);
    xStream.alias("CommonForm", MDCommonForm.class);
    xStream.alias("CommonModule", MDCommonModule.class);
    xStream.alias("CommonPicture", MDCommonPicture.class);
    xStream.alias("CommonTemplate", MDCommonTemplate.class);
    xStream.alias("Constant", MDConstant.class);
    xStream.alias("DataProcessor", MDDataProcessor.class);
    xStream.alias("DefinedType", MDDefinedType.class);
    xStream.alias("Document", MDDocument.class);
    xStream.alias("DocumentJournal", MDDocumentJournal.class);
    xStream.alias("DocumentNumerator", MDDocumentNumerator.class);
    xStream.alias("EventSubscription", MDEventSubscription.class);
    xStream.alias("ExchangePlan", MDExchangePlan.class);
    xStream.alias("FilterCriterion", MDFilterCriterion.class);
    xStream.alias("FunctionalOption", MDFunctionalOption.class);
    xStream.alias("FunctionalOptionsParameter", MDFunctionalOptionsParameter.class);
    xStream.alias("HTTPService", MDHTTPService.class);
    xStream.alias("InformationRegister", MDInformationRegister.class);
    xStream.alias("Enum", MDEnum.class);
    xStream.alias("Report", MDReport.class);
    xStream.alias("Role", MDRole.class);
    xStream.alias("Rights", RoleData.class);
    xStream.alias("ScheduledJob", MDScheduledJob.class);
    xStream.alias("Sequence", MDSequence.class);
    xStream.alias("SessionParameter", MDSessionParameter.class);
    xStream.alias("SettingsStorage", MDSettingsStorage.class);
    xStream.alias("Style", MDStyle.class);
    xStream.alias("StyleItem", MDStyleItem.class);
    xStream.alias("Subsystem", MDSubsystem.class);
    xStream.alias("Task", MDTask.class);
    xStream.alias("WebService", MDWebService.class);
    xStream.alias("WSReference", MDWSReference.class);
    xStream.alias("XDTOPackage", MDXDTOPackage.class);
    xStream.alias("Configuration", MDConfiguration.class);
    xStream.alias("MetaDataObject", DesignerRootWrapper.class);
    xStream.alias("DataCompositionSchema", DataCompositionSchema.class);
  }

  private void addConverters(XStream xStream) {
    xStream.registerConverter(new EnumConverter<>(ReturnValueReuse.class));
    xStream.registerConverter(new EnumConverter<>(UseMode.class));
    xStream.registerConverter(new EnumConverter<>(ScriptVariant.class));
    xStream.registerConverter(new EnumConverter<>(ConfigurationExtensionPurpose.class));
    xStream.registerConverter(new EnumConverter<>(ObjectBelonging.class));
    xStream.registerConverter(new EnumConverter<>(TemplateType.class));
    xStream.registerConverter(new EnumConverter<>(DataLockControlMode.class));
    xStream.registerConverter(new AttributeConverter());
    xStream.registerConverter(new CompatibilityModeConverter());
    xStream.registerConverter(new PairConverter());
    xStream.registerConverter(new DataPathConverter());
    xStream.registerConverter(new FormEventConverter());
    xStream.registerConverter(new DesignerFormItemConverter());
    xStream.registerConverter(new FormItemConverter());
    xStream.registerConverter(new DataSetConverter());
    xStream.registerConverter(new DesignerMDOConverter());
    xStream.registerConverter(new DesignerFormConverter());
  }

  private static List<Class<?>> createListClassesForForm() {
    List<Class<?>> list = new ArrayList<>();
    list.add(FormData.class);
    list.add(FormItem.class);
    return list;
  }
}
