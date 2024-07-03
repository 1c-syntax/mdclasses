/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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

import com.github._1c_syntax.bsl.mdclasses.ConfigurationTree;
import com.github._1c_syntax.bsl.mdclasses.ExternalDataProcessor;
import com.github._1c_syntax.bsl.mdclasses.ExternalReport;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.storage.DataCompositionSchema;
import com.github._1c_syntax.bsl.mdo.storage.RoleData;
import com.github._1c_syntax.bsl.mdo.storage.XdtoPackageData;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.AutoRecordType;
import com.github._1c_syntax.bsl.mdo.support.ConfigurationExtensionPurpose;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.DataSeparation;
import com.github._1c_syntax.bsl.mdo.support.FormType;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.MessageDirection;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.ReturnValueReuse;
import com.github._1c_syntax.bsl.mdo.support.ReuseSessions;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.mdo.support.TransferDirection;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.mdo.support.UsePurposes;
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.converter.CommonConverter;
import com.github._1c_syntax.bsl.reader.common.converter.EnumConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.converters.collections.ArrayConverter;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.converters.collections.SingletonCollectionConverter;
import com.thoughtworks.xstream.converters.collections.SingletonMapConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.mapper.CachingMapper;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;
import com.thoughtworks.xstream.mapper.DefaultImplementationsMapper;
import com.thoughtworks.xstream.mapper.DefaultMapper;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.SecurityMapper;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.HasName;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;

/**
 * Расширение функциональности XStream
 */
@Slf4j
@Getter
public class ExtendXStream extends XStream {

  public ExtendXStream(MDReader reader, ClassLoaderReference classLoaderReference, Mapper mapper) {
    super(new PureJavaReflectionProvider(), new ExtendStaxDriver(reader), classLoaderReference, mapper);
    init();
  }

  public ExtendXStream(MDReader reader, QNameMap qNameMap, ClassLoaderReference classLoaderReference, Mapper mapper) {
    super(new PureJavaReflectionProvider(), new ExtendStaxDriver(reader, qNameMap), classLoaderReference, mapper);
    init();
  }

  /**
   * Читает объект из файла
   *
   * @param file Читаемый файл. Если нечитаемые или ошибочный, то будет ошибка
   * @return Прочитанный объект
   */
  @Override
  public Object fromXML(File file) {
    Object result = null;
    if (file.exists()) {
      try {
        result = super.fromXML(file);
      } catch (ConversionException e) {
        LOGGER.error("Can't read file '{}' - it's broken \n: ", file, e);
        throw e;
      } catch (CannotResolveClassException e) {
        LOGGER.debug("Can't read file '{}' - unknown class \n: ", file, e);
      }
    }
    return result;
  }

  /**
   * Возвращает класс реализации объекта по имени поля / строковому краткому имени
   *
   * @param className Имя искомого класса
   * @return Найденный класс
   */
  public Class<?> getRealClass(String className) {
    return getMapper().realClass(className);
  }

  /**
   * Возвращает класс реализации объекта по имени поля / строковому краткому имени
   *
   * @param reader    Ридер файла
   * @param className Имя искомого класса
   * @return Найденный класс
   */
  public static Class<?> getRealClass(HierarchicalStreamReader reader, String className) {
    return getCurrentMDReader(reader).getXstream().getRealClass(className);
  }

  /**
   * Выполняет чтение объекта из файла
   *
   * @param reader      Ридер файла
   * @param contentPath Путь к файлу
   * @return Найденный класс
   */
  public static Object read(HierarchicalStreamReader reader, Path contentPath) {
    return getCurrentMDReader(reader).read(contentPath);
  }

  /**
   * Выполняет чтение объекта из файла по имени
   *
   * @param reader      Ридер файла
   * @param contentPath Путь к файлу
   * @param fullName    Имя читаемого объекта
   * @return Найденный класс
   */
  public static Object read(HierarchicalStreamReader reader, Path contentPath, String fullName) {
    return getCurrentMDReader(reader).read(contentPath, fullName);
  }

  /**
   * Возвращает путь текущего читаемого файла
   *
   * @param reader Текущий ридер
   * @return Путь к читаемому файлу
   */
  public static Path getCurrentPath(HierarchicalStreamReader reader) {
    return ((ExtendReaderWrapper) reader).getPath();
  }

  /**
   * Выполняет ссылку на MDReader, связанный с читатем файла
   *
   * @param reader Ридер файла
   * @return Найденный класс
   */
  public static MDReader getCurrentMDReader(HierarchicalStreamReader reader) {
    return ((ExtendReaderWrapper) reader).getMDReader();
  }

  /**
   * Читает значение из файла
   *
   * @param context Контекст чтения файла
   * @param clazz   Класс для преобразования
   * @return Прочитанное значение
   */
  @SuppressWarnings("unchecked")
  public static <T> T readValue(UnmarshallingContext context, Class<T> clazz) {
    return (T) context.convertAnother(null, clazz);
  }

  /**
   * Регистрирует конверторы нужного типа, фильтруя по пакету и аннотации
   *
   * @param xStream               объект xStream
   * @param convertersPackageName полное имя пакета, где расположены конверторы
   * @param annotation            аннотация, которой помечены конверторы
   */
  public static void registerConverters(ExtendXStream xStream, String convertersPackageName, Class<?> annotation) {
    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .enableAnnotationInfo()
      .acceptPackages(convertersPackageName)
      .scan()) {

      var classes = scanResult.getClassesWithAnnotation(annotation.getName());
      classes.stream()
        .map(getObjectsFromInfoClass())
        .filter(Objects::nonNull)
        .forEach(xStream::registerMDCConverter);
    }
  }

  /**
   * Переопределение списка регистрируемых конвертеров. Оставлены только те, что нужны, особенно исключены те,
   * что вызывают недовольство у JVM, в связи с неправильным доступом при рефлексии
   */
  @Override
  protected void setupConverters() {
    registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
    registerConverter(new IntConverter(), PRIORITY_NORMAL);
    registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
    registerConverter(new StringConverter(), PRIORITY_LOW);

    registerConverter(new CollectionConverter(getMapper()));
    registerConverter(new ArrayConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(new MapConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(new SingletonCollectionConverter(getMapper()), PRIORITY_NORMAL);
    registerConverter(new SingletonMapConverter(getMapper()), PRIORITY_NORMAL);

    registerConverter(new ReflectionConverter(getMapper(), getReflectionProvider()), PRIORITY_VERY_LOW);

    registerConverters(this,
      "com.github._1c_syntax.bsl.reader.common.converter",
      CommonConverter.class);

    registerConverter(new EnumConverter<>(ApplicationRunMode.class));
    registerConverter(new EnumConverter<>(AutoRecordType.class));
    registerConverter(new EnumConverter<>(ConfigurationExtensionPurpose.class));
    registerConverter(new EnumConverter<>(DataLockControlMode.class));
    registerConverter(new EnumConverter<>(DataSeparation.class));
    registerConverter(new EnumConverter<>(FormType.class));
    registerConverter(new EnumConverter<>(IndexingType.class));
    registerConverter(new EnumConverter<>(MessageDirection.class));
    registerConverter(new EnumConverter<>(ObjectBelonging.class));
    registerConverter(new EnumConverter<>(ReturnValueReuse.class));
    registerConverter(new EnumConverter<>(ReuseSessions.class));
    registerConverter(new EnumConverter<>(RoleRight.class));
    registerConverter(new EnumConverter<>(ScriptVariant.class));
    registerConverter(new EnumConverter<>(TemplateType.class));
    registerConverter(new EnumConverter<>(TransferDirection.class));
    registerConverter(new EnumConverter<>(UseMode.class));
    registerConverter(new EnumConverter<>(UsePurposes.class));
  }

  private void init() {
    // настройки безопасности доступа к данным
    setMode(XStream.NO_REFERENCES);
    addPermission(new WildcardTypePermission(new String[]{"com.github._1c_syntax.**"}));

    registerClasses();
  }

  /**
   * Обертка для удобства регистрации разных конвертеров посредством чтения классов из пакета
   *
   * @param converter один из поддерживаемых конвертеров
   */
  protected void registerMDCConverter(Object converter) {
    if (converter instanceof Converter simpleConverter) {
      registerConverter(simpleConverter);
    } else if (converter instanceof SingleValueConverter singleValueConverter) {
      registerConverter(singleValueConverter);
    } else {
      throw new IllegalArgumentException("Unknown converter type " + converter);
    }
  }

  private void registerClasses() {

    // регистрация основных классов
    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .acceptPackages("com.github._1c_syntax.bsl.mdo")
      .rejectPackages("com.github._1c_syntax.bsl.mdo.children")
      .scan()) {

      scanResult.getClassesImplementing(MD.class.getName())
        .filter(classInfo -> !classInfo.isInterface())
        .forEach((ClassInfo clazzInfo) -> {
          var clazz = getClassFromClassInfo(clazzInfo);
          var simpleName = clazzInfo.getSimpleName();
          alias(simpleName, clazz);
        });
    }

    // регистрация дочерних
    alias("Rights", RoleData.class);
    alias("package", XdtoPackageData.class);
    alias("DataCompositionSchema", DataCompositionSchema.class);
    alias("Configuration", ConfigurationTree.class);
    alias("ExternalDataProcessor", ExternalDataProcessor.class);
    alias("ExternalReport", ExternalReport.class);
  }

  @Nullable
  private static Class<?> getClassFromClassInfo(HasName classInfo) {
    try {
      return Class.forName(classInfo.getName());
    } catch (ClassNotFoundException e) {
      LOGGER.error("Cannot resolve class {}\n", classInfo.getName(), e);
      return null;
    }
  }

  private static Function<ClassInfo, Object> getObjectsFromInfoClass() {
    return (ClassInfo classInfo) -> {
      try {
        var clazz = Class.forName(classInfo.getName());
        return clazz.getDeclaredConstructors()[0].newInstance();
      } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException |
               InstantiationException e) {
        LOGGER.error("Cannot resolve class {}\n", classInfo.getName(), e);
        throw new IllegalArgumentException("Cannot resolve class");
      }
    };
  }

  public static Mapper buildMapper(ClassLoaderReference classLoaderReference) {
    Mapper mapper = new DefaultMapper(classLoaderReference);
    mapper = new ClassAliasingMapper(mapper);
    mapper = new DefaultImplementationsMapper(mapper);
    mapper = new SecurityMapper(mapper);
    mapper = new CachingMapper(mapper);
    return mapper;
  }
}
