/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.javabean.BeanProvider;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.beans.PropertyDescriptor;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@UtilityClass
public class MDTestUtils {
  private static final String EXAMPLES_PATH = "src/test/resources/ext";
  private static final String EDT_PATH = "edt";
  private static final String DESIGNER_PATH = "designer";
  private static final String FIXTURES_PATH = "src/test/resources/fixtures";
  private static final String DESIGNER_CF_PATH = "src/cf";
  private static final String EDT_CF_PATH = "configuration";
  private static final String EXTERNAL_SOURCE_PATH = "external/src";

  private static final String EXTERNAL_PATH = "external";

  /**
   * Для загрузки фикстуры по пути к файлу
   *
   * @param path Путь к файлу
   * @return Содержимое файла
   */
  @SneakyThrows
  public String getFixture(Path path) {
    return Files.readString(path, StandardCharsets.UTF_8);
  }

  /**
   * Генерация Json представления объекта
   *
   * @param obj сериализуемое значение
   * @return Сериализованное в Json представление объекта
   */
  public String createJson(Object obj) {
    var xstream = new XStream(new JsonHierarchicalStreamDriver());
    xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
    xstream.registerConverter(new TestURIConverter());
    xstream.registerConverter(new JavaBeanConverter(xstream.getMapper(), getBeanProvider(), obj.getClass()), -20);
    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .enableAnnotationInfo()
      .acceptPackages("com.github._1c_syntax.bsl.mdo", "com.github._1c_syntax.bsl.mdclasses")
      .scan()) {

      scanResult.getAllClasses().forEach((ClassInfo classInfo) -> {
        try {
          var clazz = Class.forName(classInfo.getName());
          if (MD.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "children");
            xstream.omitField(clazz, "modulesByType");
            xstream.omitField(clazz, "modulesByMDORef");
            xstream.omitField(clazz, "modulesByObject");
            xstream.omitField(clazz, "modulesByURI");
          }
          if (CommonModule.class.isAssignableFrom(clazz)) {
            xstream.omitField(clazz, "modules");
          }
          xstream.omitField(clazz, "storageFields");
          xstream.omitField(clazz, "plainStorageFields");
          xstream.omitField(clazz, "plainChildren");
          xstream.omitField(clazz, "allAttributes");
          xstream.omitField(clazz, "allModules");
          xstream.omitField(clazz, "plainItems");
          xstream.omitField(clazz, "commonModulesByName");
          xstream.omitField(clazz, "childrenByMdoRef");
          xstream.omitField(clazz, "mdoRef");

        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        }
      });
    }

    if (obj instanceof MDClass) {
      xstream.registerConverter(new TestCollectionConverter(xstream.getMapper()));
    }
    return xstream.toXML(obj);
  }

  public MD getMDWithSimpleTest(ArgumentsAccessor argumentsAccessor) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var examplePackName = argumentsAccessor.getString(1);
    var mdoRef = argumentsAccessor.getString(2);

    Path configurationPath;
    if (isEDT) {
      configurationPath = Path.of(EXAMPLES_PATH, EDT_PATH, examplePackName, EDT_CF_PATH);
    } else {
      configurationPath = Path.of(EXAMPLES_PATH, DESIGNER_PATH, examplePackName, DESIGNER_CF_PATH);
    }

    var mdo = MDOReader.read(configurationPath, mdoRef);
    assertThat(mdo).isInstanceOf(MD.class);

    Path fixturePath;
    if (argumentsAccessor.size() > 3) {
      var fixturePostfix = argumentsAccessor.getString(3);
      if (fixturePostfix == null) {
        fixturePostfix = "";
      }
      fixturePath = Path.of(FIXTURES_PATH, examplePackName, mdoRef + fixturePostfix + ".json");
    } else {
      fixturePath = Path.of(FIXTURES_PATH, examplePackName, mdoRef + ".json");
    }
    objectEqualJson(mdo, fixturePath);
    return (MD) mdo;
  }

  public MDClass getMDCWithSimpleTest(ArgumentsAccessor argumentsAccessor, boolean skipSupport) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var examplePackName = argumentsAccessor.getString(1);
    var mdoRef = "Configuration";

    Path configurationPath;
    if (isEDT) {
      configurationPath = Path.of(EXAMPLES_PATH, EDT_PATH, examplePackName, EDT_CF_PATH);
    } else {
      configurationPath = Path.of(EXAMPLES_PATH, DESIGNER_PATH, examplePackName, DESIGNER_CF_PATH);
    }

    var mdc = MDClasses.createConfiguration(configurationPath, skipSupport);
    assertThat(mdc).isNotNull();
    assertThat(mdc).isInstanceOf(MDClass.class);

    Path fixturePath;
    if (argumentsAccessor.size() > 2) {
      var fixturePostfix = argumentsAccessor.getString(2);
      fixturePath = Path.of(FIXTURES_PATH, examplePackName, mdoRef + fixturePostfix + ".json");
    } else {
      fixturePath = Path.of(FIXTURES_PATH, examplePackName, mdoRef + ".json");
    }

    objectEqualJson(mdc, fixturePath);
    return mdc;
  }

  public CF readConfiguration(ArgumentsAccessor argumentsAccessor, boolean skipSupport) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var examplePackName = argumentsAccessor.getString(1);

    Path configurationPath;
    if (isEDT) {
      configurationPath = Path.of(EXAMPLES_PATH, EDT_PATH, examplePackName, EDT_CF_PATH);
    } else {
      configurationPath = Path.of(EXAMPLES_PATH, DESIGNER_PATH, examplePackName, DESIGNER_CF_PATH);
    }

    var mdc = MDClasses.createConfiguration(configurationPath, skipSupport);
    assertThat(mdc).isNotNull();
    assertThat(mdc).isInstanceOf(MDClass.class);
    assertThat(mdc).isInstanceOf(CF.class);

    return (CF) mdc;
  }

  public ExternalSource readExternalSourceWithSimpleTest(ArgumentsAccessor argumentsAccessor) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var name = argumentsAccessor.getString(1);
    var isReport = argumentsAccessor.getBoolean(2);

    Path externalSourcePath;
    if (isEDT) {
      var sourceTypeName = (isReport)
        ? MDOType.EXTERNAL_REPORT.getGroupName()
        : MDOType.EXTERNAL_DATA_PROCESSOR.getGroupName();
      externalSourcePath = Path.of(EXAMPLES_PATH, EDT_PATH, EXTERNAL_SOURCE_PATH, sourceTypeName, name, name + ".mdo");
    } else {
      var sourceTypeName = (isReport) ? "erf" : "epf";
      externalSourcePath = Path.of(EXAMPLES_PATH, DESIGNER_PATH, EXTERNAL_SOURCE_PATH, sourceTypeName, name + ".xml");
    }

    var mdc = MDClasses.createExternalSource(externalSourcePath);
    assertThat(mdc).isNotNull();
    assertThat(mdc).isInstanceOf(MDClass.class);
    assertThat(mdc).isInstanceOf(ExternalSource.class);

    Path fixturePath;
    if (argumentsAccessor.size() > 3) {
      var fixturePostfix = argumentsAccessor.getString(3);
      fixturePath = Path.of(FIXTURES_PATH, EXTERNAL_PATH, name + fixturePostfix + ".json");
    } else {
      fixturePath = Path.of(FIXTURES_PATH, EXTERNAL_PATH, name + ".json");
    }

    objectEqualJson(mdc, fixturePath);
    return (ExternalSource) mdc;
  }

  @SneakyThrows
  private void objectEqualJson(Object obj, Path fixturePath) {
    var fixture = getFixture(fixturePath);
    var current = createJson(obj);
    Assertions.assertThat(fixRusYi(current), true).isEqual(fixRusYi(fixture));
  }

  private BeanProvider getBeanProvider() {
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

  /**
   * Костыль-защита от буквы Й: заменяем ее на подчеркивание
   *
   * @param jsonText исходный текст
   * @return обработанный
   */
  private String fixRusYi(String jsonText) {
    return jsonText
      .replace("Й", "_")
      .replace("й", "_")
      .replace("\u0419", "_")
      .replace("\u0439", "_")
      .replace("%D0%99", "_")
      .replace("%D0%B9", "_")
      .replace("%D0%98%CC%86", "_")
      .replace("%D0%B8%CC%86", "_");
  }
}
