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
package com.github._1c_syntax.reader.edt;


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
import com.thoughtworks.xstream.security.ExplicitTypePermission;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.nio.file.Path;

/**
 * Класс для чтения XML и MDO файлов конфигурации
 */
@Slf4j
@UtilityClass
public class XStreamFactory {
  private final String ATTRIBUTE_FIELD_NAME = "attributes";
  private final String CHILDREN_FIELD_NAME = "children";
//  /**
//   * Используется для чтения элементов формы (см. FormEventConverter, DesignerFormItemConverter)
//   */
//  @Getter
//  private static Converter reflectionConverter;
//  @Getter(lazy = true)
//  private final XStream xstream = createXMLMapper();
//
//  /**
//   * Выполняет чтение объекта из XML файла
//   */
//  public Object fromXML(File file) {
//    Object result;
//    try {
//      result = getXstream().fromXML(file);
//    } catch (ConversionException e) {
//      LOGGER.error("Can't read file '{}' - it's broken \n: ", file.toString(), e);
//      throw e;
//    }
//    return result;
//  }
//
//  public Class<?> getRealClass(String className) {
//    return getXstream().getMapper().realClass(className);
//  }
//
//  public Path getCurrentPath(HierarchicalStreamReader reader) {
//    return ((ExtendReaderWrapper) reader).getPath();
//  }
//
//  public XMLStreamReader getXMLStreamReader(HierarchicalStreamReader reader) {
//    return ((ExtendReaderWrapper) reader).getXMLStreamReader();
//  }
//
//  private XStream createXMLMapper() {
//    // данный провайдер необходим для корректной обработки значений по умолчанию, чтобы не было null
//    var qNameMap = new QNameMap();
////    qNameMap.registerMapping(new QName("http://g5.1c.ru/v8/dt/form", "Form", "form"), FormData.class);
////    qNameMap.registerMapping(new QName("http://v8.1c.ru/8.3/xcf/logform", "Form"), DesignerFormWrapper.class);
//
//    var xStream = new XStream(new PureJavaReflectionProvider(), new ExtendStaxDriver(qNameMap)) {
//
//      // TODO как починят https://github.com/x-stream/xstream/issues/101
//      // После исправления бага (с 2017 года) убрать этот код
//
//      /**
//       * Переопределение списка регистрируемых конвертеров. Оставлены только те, что нужны, особенно исключены те,
//       * что вызывают недовольство у JVM, в связи с неправильным доступом при рефлексии
//       */
//      @Override
//      protected void setupConverters() {
//        reflectionConverter = new ReflectionConverter(getMapper(), getReflectionProvider());
//
//        registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
//        registerConverter(new IntConverter(), PRIORITY_NORMAL);
//        registerConverter(new FloatConverter(), PRIORITY_NORMAL);
//        registerConverter(new DoubleConverter(), PRIORITY_NORMAL);
//        registerConverter(new LongConverter(), PRIORITY_NORMAL);
//        registerConverter(new ShortConverter(), PRIORITY_NORMAL);
//        registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
//        registerConverter(new ByteConverter(), PRIORITY_NORMAL);
//        registerConverter(new StringConverter(), PRIORITY_NORMAL);
//        registerConverter(new DateConverter(), PRIORITY_NORMAL);
//        registerConverter(new CollectionConverter(getMapper()), PRIORITY_NORMAL);
//        registerConverter(reflectionConverter, PRIORITY_VERY_LOW);
//      }
//    };
//    // автоопределение аннотаций
//    xStream.autodetectAnnotations(false);
//
//    // игнорирование неизвестных тегов
//    xStream.ignoreUnknownElements();
//    // настройки безопасности доступа к данным
//    xStream.setMode(XStream.NO_REFERENCES);
//    XStream.setupDefaultSecurity(xStream);
//    xStream.addPermission(NoTypePermission.NONE);
//    xStream.addPermission(new WildcardTypePermission(new String[]{"com.github._1c_syntax.**"}));
//    xStream.addPermission(new ExplicitTypePermission(new String[]{"java.time.Period"}));
//
//    // необходимо зарегистрировать все классы, имена которых в XML отличаются от имен самих классов
//    registerClasses(xStream);
//    // для каждого типа данных или поля необходимо зарегистрировать конвертер
//    registerConverters(xStream);
//
//    return xStream;
//  }
//
//  private void registerClasses(XStream xStream) {
//
//    registerClassesByMetadata(xStream);
//
////    xStream.processAnnotations(AccountingFlag.class);
////    xStream.processAnnotations(DataPath.class);
////    xStream.processAnnotations(MDOModule.class);
////    xStream.processAnnotations(ValueType.class);
////    xStream.processAnnotations(DynamicListExtInfo.class);
////    xStream.processAnnotations(InputFieldExtInfo.class);
////    xStream.processAnnotations(DataCompositionSchema.class);
////    xStream.processAnnotations(Recalculation.class);
////    xStream.processAnnotations(TabularSection.class);
////    xStream.processAnnotations(DesignerChildObjects.class);
////    xStream.processAnnotations(DesignerMDO.class);
////    xStream.processAnnotations(DesignerAttributeType.class);
////    xStream.processAnnotations(DesignerColumns.class);
////    xStream.processAnnotations(DesignerEvent.class);
////    xStream.processAnnotations(DesignerForm.class);
////    xStream.processAnnotations(DesignerFormCommand.class);
////    xStream.processAnnotations(DesignerFormCommands.class);
////    xStream.processAnnotations(DesignerContentItem.class);
////    xStream.processAnnotations(DesignerExchangePlanContent.class);
////
////    xStream.alias("Rights", RoleData.class);
////    xStream.alias("MetaDataObject", DesignerRootWrapper.class);
////    xStream.alias("DataCompositionSchema", DataCompositionSchema.class);
////
////    registerDesignerFormItemAliases(xStream);
////    registerAbstractMDOAttributeAliases(xStream);
////    registerSubsystemChildrenAliases(xStream);
////    registerFormsChildrenAliases(xStream);
////    registerSimpleTypeAliases(xStream);
////    registerExchangePlanItemMDOAliases(xStream);
//  }
//
//  private void registerClassesByMetadata(XStream xStream) {
////    MetadataStorage.getStorage().forEach((Class<?> aClass, Metadata metadata) -> {
////      xStream.alias(metadata.name(), aClass);
////      xStream.processAnnotations(aClass);
////    });
////
////    MetadataStorage.getAttributeStorage().forEach((Class<?> aClass, AttributeMetadata metadata) -> {
////      xStream.alias(metadata.name(), aClass);
////      xStream.alias(metadata.fieldNameEDT(), aClass);
////    });
//  }
//
//  private void registerDesignerFormItemAliases(XStream xStream) {
////    xStream.aliasField("AutoCommandBar", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("Button", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("ButtonGroup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("CalendarField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("ChartField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("CheckBoxField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("ColumnGroup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("Command", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("CommandBar", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("CommandBarButton", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("CommandBarHyperlink", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("ContextMenu", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("DendrogramField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("FormattedDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("GanttChartField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("GeographicalSchemaField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("GraphicalSchemaField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("HTMLDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("Hyperlink", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("InputField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("LabelDecoration", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("LabelField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("Navigator", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("Page", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("Pages", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("PeriodField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("PictureDecoration", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("PictureField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("PlannerField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("ProgressBarField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("RadioButtonField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("SpreadSheetDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("Table", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("TextDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("TrackBarField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("UsualButton", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("UsualGroup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("Popup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("ViewStatusAddition", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("ExtendedTooltip", DesignerChildItems.class, CHILDREN_FIELD_NAME);
////    xStream.aliasField("SearchStringAddition", DesignerChildItems.class, CHILDREN_FIELD_NAME);
//  }
//
//  private void registerAbstractMDOAttributeAliases(XStream xStream) {
////    xStream.aliasField("dimensions", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
////    xStream.aliasField("resources", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
////    xStream.aliasField("recalculations", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
////    xStream.aliasField(ATTRIBUTE_FIELD_NAME, AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
////    xStream.aliasField("tabularSections", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
////    xStream.aliasField("accountingFlags", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
////    xStream.aliasField("extDimensionAccountingFlags", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
////    xStream.aliasField("columns", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
////    xStream.aliasField("addressingAttributes", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
//  }
//
//  private void registerConverters(XStream xStream) {
////    xStream.registerConverter(new EnumConverter<>(ReturnValueReuse.class));
////    xStream.registerConverter(new EnumConverter<>(UseMode.class));
////    xStream.registerConverter(new EnumConverter<>(ScriptVariant.class));
////    xStream.registerConverter(new EnumConverter<>(MessageDirection.class));
////    xStream.registerConverter(new EnumConverter<>(ConfigurationExtensionPurpose.class));
////    xStream.registerConverter(new EnumConverter<>(ObjectBelonging.class));
////    xStream.registerConverter(new EnumConverter<>(TemplateType.class));
////    xStream.registerConverter(new EnumConverter<>(DataLockControlMode.class));
////    xStream.registerConverter(new EnumConverter<>(DataSeparation.class));
////    xStream.registerConverter(new EnumConverter<>(FormType.class));
////    xStream.registerConverter(new EnumConverter<>(IndexingType.class));
////    xStream.registerConverter(new EnumConverter<>(AutoRecordType.class));
////    xStream.registerConverter(new EnumConverter<>(BWAValue.class));
////    xStream.registerConverter(new AttributeConverter());
////    xStream.registerConverter(new CompatibilityModeConverter());
////    xStream.registerConverter(new PairConverter());
////    xStream.registerConverter(new DataPathConverter());
////    xStream.registerConverter(new FormEventConverter());
////    xStream.registerConverter(new DesignerFormItemConverter());
////    xStream.registerConverter(new FormItemConverter());
////    xStream.registerConverter(new DataSetConverter());
////    xStream.registerConverter(new DesignerMDOConverter());
////    xStream.registerConverter(new DesignerFormConverter());
////    xStream.registerConverter(new DesignerXRItemConverter());
//  }

}
