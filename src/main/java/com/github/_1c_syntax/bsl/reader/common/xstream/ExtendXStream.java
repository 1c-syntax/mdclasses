/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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
package com.github._1c_syntax.bsl.reader.common.xstream;

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.storage.DataCompositionSchema;
import com.github._1c_syntax.bsl.mdo.storage.XdtoPackageData;
import com.github._1c_syntax.bsl.mdo.support.AutoRecordType;
import com.github._1c_syntax.bsl.mdo.support.ConfigurationExtensionPurpose;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.DataSeparation;
import com.github._1c_syntax.bsl.mdo.support.FormType;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.MessageDirection;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.ReturnValueReuse;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.reader.common.converter.CommonConverter;
import com.github._1c_syntax.bsl.reader.common.converter.EnumConverter;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerChildObjects;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.MDConfiguration;
import com.github._1c_syntax.mdclasses.mdo.support.BWAValue;
import com.github._1c_syntax.mdclasses.mdo.support.RoleData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.ByteConverter;
import com.thoughtworks.xstream.converters.basic.CharConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.FloatConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.converters.basic.ShortConverter;
import com.thoughtworks.xstream.converters.basic.StringBufferConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.converters.basic.URIConverter;
import com.thoughtworks.xstream.converters.basic.URLConverter;
import com.thoughtworks.xstream.converters.collections.ArrayConverter;
import com.thoughtworks.xstream.converters.collections.CharArrayConverter;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.converters.collections.PropertiesConverter;
import com.thoughtworks.xstream.converters.collections.SingletonCollectionConverter;
import com.thoughtworks.xstream.converters.collections.SingletonMapConverter;
import com.thoughtworks.xstream.converters.collections.TreeMapConverter;
import com.thoughtworks.xstream.converters.collections.TreeSetConverter;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;
import com.thoughtworks.xstream.converters.extended.FileConverter;
import com.thoughtworks.xstream.converters.extended.GregorianCalendarConverter;
import com.thoughtworks.xstream.converters.extended.JavaClassConverter;
import com.thoughtworks.xstream.converters.extended.LocaleConverter;
import com.thoughtworks.xstream.converters.reflection.ExternalizableConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.SerializableConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.HasName;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;
import java.util.Locale;

/**
 * Расширение функциональности XStream
 */
@Slf4j
public class ExtendXStream extends XStream {

  private static final String CHILDREN_FIELD_NAME = "children";

  /**
   * Используется для чтения элементов формы (см. FormEventConverter, DesignerFormItemConverter)
   */
  @Getter
  private Converter reflectionConverter;

  public ExtendXStream() {
    super(new PureJavaReflectionProvider(), new ExtendStaxDriver());

    init();
  }

  public ExtendXStream(QNameMap qNameMap) {
    super(new PureJavaReflectionProvider(), new ExtendStaxDriver(qNameMap));

    // автоопределение аннотаций
    init();
  }

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
    registerConverter(new StringConverter(), PRIORITY_LOW);
    registerConverter(new DateConverter(), PRIORITY_NORMAL);
    registerConverter(new CollectionConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(reflectionConverter, PRIORITY_VERY_LOW);

    registerConverter(new SerializableConverter(getMapper(), getReflectionProvider(), getClassLoaderReference()),
      PRIORITY_LOW);
    registerConverter(new ExternalizableConverter(getMapper(), getClassLoaderReference()), PRIORITY_LOW);

    registerConverter((Converter) new CharConverter(), PRIORITY_NORMAL);

    registerConverter(new StringBufferConverter(), PRIORITY_NORMAL);
    registerConverter(new URIConverter(), PRIORITY_NORMAL);
    registerConverter(new URLConverter(), PRIORITY_NORMAL);

    registerConverter(new ArrayConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(new CharArrayConverter(), PRIORITY_NORMAL);
    registerConverter(new CollectionConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(new MapConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(new TreeMapConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(new TreeSetConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(new SingletonCollectionConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(new SingletonMapConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(new PropertiesConverter(), PRIORITY_NORMAL);
    registerConverter((Converter) new EncodedByteArrayConverter(), PRIORITY_NORMAL);

    registerConverter(new FileConverter(), PRIORITY_NORMAL);
    registerConverter(new JavaClassConverter(getClassLoaderReference()), PRIORITY_NORMAL);

    registerConverter(new LocaleConverter(), PRIORITY_NORMAL);
    registerConverter(new GregorianCalendarConverter(), PRIORITY_NORMAL);
  }

  @Override
  public Object fromXML(File file) {
    Object result = null;
    if (file.exists()) {
      try {
        result = super.fromXML(file);
      } catch (ConversionException e) {
        LOGGER.error("Can't read file '{}' - it's broken \n: ", file, e);
        throw e;
      }
    }
    return result;
  }

  public Class<?> getRealClass(String className) {
    return getMapper().realClass(className);
  }

  public static Path getCurrentPath(HierarchicalStreamReader reader) {
    return ((ExtendReaderWrapper) reader).getPath();
  }

  private void init() {
    // автоопределение аннотаций
    autodetectAnnotations(false);

    // игнорирование неизвестных тегов
    ignoreUnknownElements();

    // настройки безопасности доступа к данным
    setMode(XStream.NO_REFERENCES);
    addPermission(new WildcardTypePermission(new String[]{"com.github._1c_syntax.**"}));

    // регистрируем общие конверторы
    registerCommonConverters();

    registerClasses();
  }

  /**
   * Обертка для удобства регистрации разных конвертеров посредством чтения классов из пакета
   *
   * @param converter один из поддерживаемых конвертеров
   */
  protected void registerConverter(Object converter, boolean nonuse) {
    if (converter instanceof Converter) {
      registerConverter((Converter) converter);
    } else if (converter instanceof SingleValueConverter) {
      registerConverter((SingleValueConverter) converter);
    } else {
      throw new IllegalArgumentException("Unknown converter type " + converter);
    }
  }

  private void registerCommonConverters() {
    XStreamUtils.registerConverters(this,
      "com.github._1c_syntax.bsl.reader.common.converter",
      CommonConverter.class);

    registerConverter(new EnumConverter<>(ConfigurationExtensionPurpose.class));
    registerConverter(new EnumConverter<>(DataLockControlMode.class));
    registerConverter(new EnumConverter<>(DataSeparation.class));
    registerConverter(new EnumConverter<>(IndexingType.class));
    registerConverter(new EnumConverter<>(ReturnValueReuse.class));
    registerConverter(new EnumConverter<>(TemplateType.class));
    registerConverter(new EnumConverter<>(UseMode.class));
    registerConverter(new EnumConverter<>(BWAValue.class));
    registerConverter(new EnumConverter<>(FormType.class));
    registerConverter(new EnumConverter<>(ScriptVariant.class));
    registerConverter(new EnumConverter<>(ObjectBelonging.class));
    registerConverter(new EnumConverter<>(AutoRecordType.class));
    registerConverter(new EnumConverter<>(MessageDirection.class));

  }

  private void registerClasses() {
    alias("DataCompositionSchema", DataCompositionSchema.class);
    alias("Rights", RoleData.class);
    alias("package", XdtoPackageData.class);

    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .acceptPackages("com.github._1c_syntax.bsl.mdo")
      .acceptPackages("com.github._1c_syntax.mdclasses.mdo") // todo времянка
      .rejectPackages("com.github._1c_syntax.bsl.mdo.children")
      .scan()) {

      scanResult.getClassesImplementing(MDObject.class.getName())
        .filter(classInfo -> !classInfo.isInterface())
        .forEach(clazzInfo -> {
          var clazz = getClassFromClassInfo(clazzInfo);
          var simpleName = clazzInfo.getSimpleName();

          // todo временное решение для старой модели
          //  процессинг и имена буду изменены
          if (simpleName.equals("MDHttpService")) {
            alias("HTTPService", clazz);
          } else if (simpleName.equals("MDXdtoPackage")) {
            alias("XDTOPackage", clazz);
          } else if (simpleName.startsWith("MD")) {
            alias(simpleName.substring(2), clazz);
          } else if (!simpleName.equals("CommonAttribute")) {
            alias(simpleName, clazz);
          }
          processAnnotations(clazz);
        });
    }

    MDOType.valuesWithoutChildren().forEach((MDOType type) -> {
      aliasField(type.getName(), DesignerChildObjects.class, CHILDREN_FIELD_NAME);

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
      aliasField(fieldName, MDConfiguration.class, CHILDREN_FIELD_NAME);
    });

  }

  @Nullable
  private static Class<?> getClassFromClassInfo(HasName classInfo) {
    try {
      return Class.forName(classInfo.getName());
    } catch (ClassNotFoundException e) {
      LOGGER.error("Cannot resolve class {}\n {}", classInfo.getName(), e);
      return null;
    }
  }
}
