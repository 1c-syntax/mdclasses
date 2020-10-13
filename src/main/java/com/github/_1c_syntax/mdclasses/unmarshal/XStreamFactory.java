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

import com.github._1c_syntax.mdclasses.mdo.*;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerChildObjects;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerWrapper;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationExtensionPurpose;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ObjectBelonging;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
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
import java.util.Locale;

/**
 * Класс для чтения XML и MDO файлов конфигурации
 */
@Slf4j
@UtilityClass
public class XStreamFactory {

  private final String ATTRIBUTE_FIELD_NAME = "attributes";
  private final String CHILDREN_FIELD_NAME = "children";

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
        registerConverter(new ReflectionConverter(getMapper(), getReflectionProvider()), PRIORITY_VERY_LOW);
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
    xStream.alias("Rights", RoleData.class);
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

    xStream.alias("MetaDataObject", DesignerWrapper.class);
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
  }
}
