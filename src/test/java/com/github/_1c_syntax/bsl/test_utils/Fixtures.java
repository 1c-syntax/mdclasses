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
package com.github._1c_syntax.bsl.test_utils;

import com.github._1c_syntax.bsl.mdclasses.CF;
import com.github._1c_syntax.bsl.mdclasses.ExternalSource;
import com.github._1c_syntax.bsl.mdclasses.MDCReadSettings;
import com.github._1c_syntax.bsl.mdo.AttributeOwner;
import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.mdo.storage.DataCompositionSchema;
import com.github._1c_syntax.bsl.mdo.storage.ManagedFormData;
import com.github._1c_syntax.bsl.mdo.storage.form.FormElementOwner;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.Qualifier;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.javabean.BeanProvider;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Служебный класс для использования фикстур и кеширования прочитанных данных
 */
@UtilityClass
@NullMarked
public class Fixtures {

  /**
   * Путь к фикстурам (json)
   */
  public static final Path FIXTURES_PATH = Path.of("src", "test", "resources", "fixtures");

  private static final Map<String, MD> objectCache = new ConcurrentHashMap<>();

  private static final String EDT_BASE = "ext/edt";
  private static final String DESIGNER_BASE = "ext/designer";
  private static final String EDT_SUFFIX = "_edt";

  /**
   * Прочитать Configuration из фикстур с возможностью указания MDCReadSettings.
   *
   * @param pack            - Имя каталога репозитория
   * @param formatEDT       - true - читать в формате edt, иначе - в формате конфигуратора
   * @param mdcReadSettings - параметры чтения
   * @return Прочитанная конфигурация (или расширение)
   */
  @Nullable
  public static CF get(String pack, boolean formatEDT, MDCReadSettings mdcReadSettings) {
    return (CF) get(pack, "Configuration", formatEDT, mdcReadSettings);
  }

  /**
   * Ленивое получение объекта нужного пакета исходников по строковой ссылке.
   * Если объект был ранее прочитан, получится из кеша, иначе будет сначала прочитан.
   *
   * @param pack      - Имя каталога репозитория
   * @param mdoRef    - Строковая ссылка на объект MD
   * @param formatEDT - true - читать в формате edt, иначе - в формате конфигуратора
   * @return Прочитанный объект
   */
  @Nullable
  public static MD get(String pack, String mdoRef, boolean formatEDT) {
    return get(pack, mdoRef, formatEDT, MDCReadSettings.DEFAULT);
  }

  @Nullable
  public static MD get(String pack, String mdoRef, boolean formatEDT, MDCReadSettings mdcReadSettings) {
    var key = mdoRef + (formatEDT ? EDT_SUFFIX : "");
    return objectCache.computeIfAbsent(pack + "/" + key,
      k -> loadFromXml(pack, mdoRef, formatEDT, mdcReadSettings));
  }

  @Nullable
  public static MD getNoCache(String pack, String mdoRef, boolean formatEDT, MDCReadSettings mdcReadSettings) {
    return loadFromXml(pack, mdoRef, formatEDT, mdcReadSettings);
  }

  @Nullable
  public MD get(ArgumentsAccessor argumentsAccessor) {
    var formatEDT = argumentsAccessor.getBoolean(0);
    Assertions.assertThat(formatEDT).isNotNull();
    var pack = argumentsAccessor.getString(1);
    Assertions.assertThat(pack).isNotNull();
    var mdoRef = argumentsAccessor.getString(2);
    if(mdoRef == null) {
      mdoRef = "Configuration";
    }
    return get(pack, mdoRef, formatEDT);
  }

  /**
   * Сериализация объекта в json строку
   */
  public static String asJson(Object obj) {
    var xstream = createXstream(obj instanceof CF);
    xstream.registerConverter(new JavaBeanConverter(xstream.getMapper(), getBeanProvider(), obj.getClass()), -20);
    return xstream.toXML(obj);
  }

  /**
   * Записывает объект в json файл-фикстуру
   *
   * @param obj      Записываемый объект
   * @param filePath Путь к файлу-фикстуре
   */
  @SneakyThrows
  public static String write(Object obj, Path filePath) {
    var prettyJson = new JSONObject(asJson(obj)).toString(1);
    Files.writeString(filePath, prettyJson);
    return prettyJson;
  }

  /**
   * Фабрика для создания ссылок на фикстуры-потомков
   */
  @FunctionalInterface
  public interface FixtureRefFactory<T> {
    T create(String pack, String ref, String parentRef, Path fixturePath);
  }

  /**
   * Обход каталога фикстур и сбор ссылок на дочерние объекты (ObjectForm, ObjectTemplate и т.п.)
   *
   * @param subDir  Подкаталог внутри пака (например, "formdata", "templatedata")
   * @param factory Фабрика для создания ссылок
   * @param <T>     Тип ссылки
   * @return Список ссылок
   */
  public static <T> List<T> discoverChildFixtureRefs(String subDir, FixtureRefFactory<T> factory) {
    List<T> refs = new ArrayList<>();
    try (var packsStream = Files.list(FIXTURES_PATH)) {
      packsStream.filter(Files::isDirectory).forEach(packDir -> {
        var packName = packDir.getFileName().toString();
        var dataDir = packDir.resolve(subDir);
        if (!Files.isDirectory(dataDir)) return;
        try (var filesStream = Files.list(dataDir)) {
          filesStream.filter(path -> path.toString().endsWith(".json"))
            .forEach(fixtureFile -> {
              var ref = org.apache.commons.io.file.PathUtils
                .getBaseName(fixtureFile.getFileName());
              var parentRef = parseParentRef(ref);
              if (parentRef != null) {
                refs.add(factory.create(packName, ref, parentRef, fixtureFile));
              }
            });
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return refs;
  }

  /**
   * Парсинг ссылки на дочерний объект для получения ссылки на родителя.
   * Например, "Catalog.Справочник1.Form.ФормаЭлемента" -> "Catalogs.Справочник1"
   *
   * @param childMdoRef Ссылка на дочерний объект
   * @return Ссылка на родителя или null, если не удалось распарсить
   */
  @Nullable
  public static String parseParentRef(String childMdoRef) {
    var dotIndex = childMdoRef.indexOf('.');
    if (dotIndex < 0) return null;
    var typeStr = childMdoRef.substring(0, dotIndex);
    var rest = childMdoRef.substring(dotIndex + 1);
    var secondDot = rest.indexOf('.');
    if (secondDot < 0) return null;
    var parentName = rest.substring(0, secondDot);
    var mdoTypeOpt = MDOType.fromValue(typeStr);
    return mdoTypeOpt.map(mdoType -> mdoType.groupName() + "." + parentName).orElse(null);
  }

  @Nullable
  private static MD loadFromXml(String pack, String mdoRef, boolean formatEDT, MDCReadSettings mdcReadSettings) {

    if ("external".equals(pack)) { // внешние отчеты и обработки
      var mdoType = mdoRef.endsWith("ВнешняяОбработка") ? MDOType.EXTERNAL_DATA_PROCESSOR : MDOType.EXTERNAL_REPORT;
      var isReport = mdoType == MDOType.EXTERNAL_REPORT;

      var mdPath = formatEDT
        ? Paths.get("src", "test", "resources", EDT_BASE, pack, "src", mdoType.groupName(), mdoRef, mdoRef + ".mdo")
        : Paths.get("src", "test", "resources", DESIGNER_BASE, pack, "src", (isReport) ? "erf" : "epf", mdoRef + ".xml");

      return MDOReader.readExternalSource(mdPath);

    } else {

      var mdPath = formatEDT
        ? Paths.get("src", "test", "resources", EDT_BASE, pack, "configuration")
        : Paths.get("src", "test", "resources", DESIGNER_BASE, pack, "src", "cf");

      if (Files.exists(mdPath)) {
        if (MDOType.CONFIGURATION.nameEn().equals(mdoRef)) {
          return MDOReader.readConfiguration(mdPath, mdcReadSettings);
        } else {
          return (MD) MDOReader.read(mdPath, mdoRef, mdcReadSettings);
        }
      }
    }
    return null;
  }

  private static XStream createXstream(boolean compact) {
    var xstream = new XStream(new JsonHierarchicalStreamDriver());
    xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
    xstream.registerConverter(new TestCollectionConverter(xstream.getMapper(), compact));
    xstream.registerConverter(new TestURIConverter());

    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .enableAnnotationInfo()
      .acceptPackages(
        "com.github._1c_syntax.bsl.mdo",
        "com.github._1c_syntax.bsl.mdclasses",
        "com.github._1c_syntax.bsl.types")
      .scan()) {

      // Исключаем некоторые поля для сокращения объема фикстуры
      scanResult.getAllClasses().forEach((ClassInfo classInfo) -> {
        try {
          var clazz = Class.forName(classInfo.getName());

          if (ModuleOwner.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "allModules");
            xstream.omitField(clazz, "moduleTypes");
          }

          if (CF.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "modulesByType");
            xstream.omitField(clazz, "modulesByURI");
            xstream.omitField(clazz, "modulesByObject");
            xstream.omitField(clazz, "commonModulesByName");
            xstream.omitField(clazz, "childrenByMdoRef");
            xstream.omitField(clazz, "configurationSource");
            xstream.omitField(clazz, "configurationExtensionCompatibilityMode");
          }

          if (ExternalSource.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "configurationSource");
          }

          if (ChildrenOwner.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "children");
            xstream.omitField(clazz, "plainChildren");
          }

          if (AttributeOwner.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "allAttributes");
            xstream.omitField(clazz, "storageFields");
            xstream.omitField(clazz, "plainStorageFields");
            xstream.omitField(clazz, "additionalIndexes");
          }

          if (Form.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "data");
          }

          if (Template.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "data");
          }

          if (DataCompositionSchema.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "dataPath");
            xstream.omitField(clazz, "plainDataSets");
          }

          if (FormElementOwner.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "plainElements");
          }

          if (ManagedFormData.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "plainAttributes");
            xstream.omitField(clazz, "plainEventHandlers");
          }

          if (Module.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "uri");
          }

          if (Qualifier.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "description");
          }

        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        }
      });
    }
    return xstream;
  }

  @SneakyThrows
  private static BeanProvider getBeanProvider() {
    return new BeanProvider() {
      private final Objenesis objenesis = new ObjenesisStd();

      @Override
      public Object newInstance(Class type) {
        try {
          return objenesis.newInstance((Class<?>) type);
        } catch (Exception ignored) {
          return super.newInstance(type);
        }
      }

      @Override
      protected boolean canStreamProperty(PropertyDescriptor descriptor) {
        return descriptor.getReadMethod() != null;
      }
    };
  }
}
