/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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
import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.javabean.BeanProvider;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.beans.PropertyDescriptor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@UtilityClass
public class MDTestUtils {
  private static final String EXAMPLES_PATH = "src/test/resources/ext";
  private static final String EDT_PATH = "edt";
  private static final String DESIGNER_PATH = "designer";
  private static final String FIXTURES_PATH = "src/test/resources/fixtures";
  private static final String DESIGNER_CF_PATH = "src/cf";

  /**
   * Для загрузки фикстуры по пути к файлу
   *
   * @param path Путь к файлу
   * @return Содержимое файла
   */
  @SneakyThrows
  public String getFixture(String path) {
    return getFixture(Paths.get(path));
  }

  @SneakyThrows
  public String getFixture(Path path) {
    return Files.readString(path);
  }

  /**
   * Генерация Json представления объекта
   *
   * @param md Контейнер или объект метаданных
   * @return Сериализованное в Json представление объекта
   */
  public String createJson(Object md) {
    XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
    xstream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    xstream.registerConverter(new TestURIConverter());
    xstream.registerConverter(new JavaBeanConverter(xstream.getMapper(), getBeanProvider(), md.getClass()), -20);
    return xstream.toXML(md);
  }

  /**
   * Генерация Json представления объекта со стертыми путями к файлу
   *
   * @param md Контейнер или объект метаданных
   * @return Сериализованное в Json представление объекта
   */
  public String createJsonWithEmptyPath(Object md) {
    XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
    xstream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    xstream.registerConverter(new TestURIConverter());
    xstream.registerConverter(new JavaBeanConverter(xstream.getMapper(), getBeanProvider(), md.getClass()), -20);
    xstream.registerConverter(new TestMDOPathConverter());
    return xstream.toXML(md);
  }

  /**
   * Генерация Json представления объекта
   *
   * @param md Контейнер или объект метаданных
   * @return Сериализованное в Json представление объекта
   */
  public String createJson(Object md, boolean useRefs) {
    XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
    if (useRefs) {
      xstream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    } else {
      xstream.setMode(XStream.NO_REFERENCES);
    }
    xstream.registerConverter(new TestURIConverter());
    xstream.registerConverter(new JavaBeanConverter(xstream.getMapper(), getBeanProvider(), md.getClass()), -20);
    if (md instanceof MDClass) {
      xstream.registerConverter(new TestCollectionConverter(xstream.getMapper()));
    }
    return xstream.toXML(md);
  }

  public MD getMDWithSimpleTest(ArgumentsAccessor argumentsAccessor) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var examplePackName = argumentsAccessor.getString(1);
    var mdoRef = argumentsAccessor.getString(2);

    Path configurationPath;
    if (isEDT) {
      configurationPath = Path.of(EXAMPLES_PATH, EDT_PATH, examplePackName);
    } else {
      configurationPath = Path.of(EXAMPLES_PATH, DESIGNER_PATH, examplePackName, DESIGNER_CF_PATH);
    }

    var mdo = MDOReader.readMDObject(configurationPath, mdoRef);
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
    var fixture = getFixture(fixturePath);

    var useRef = false;
    if (argumentsAccessor.size() > 4) {
      useRef = argumentsAccessor.getBoolean(4);
    }
    var current = createJson(mdo, useRef);
    Assertions.assertThat(current, true).isEqual(fixture);

    return (MD) mdo;
  }

  public MDClass getMDCWithSimpleTest(ArgumentsAccessor argumentsAccessor, boolean skipSupport) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var examplePackName = argumentsAccessor.getString(1);
    var mdoRef = "Configuration";

    Path configurationPath;
    if (isEDT) {
      configurationPath = Path.of(EXAMPLES_PATH, EDT_PATH, examplePackName);
    } else {
      configurationPath = Path.of(EXAMPLES_PATH, DESIGNER_PATH, examplePackName, DESIGNER_CF_PATH);
    }

    var mdc = MDClasses.createConfiguration(configurationPath, skipSupport);
    assertThat(mdc).isNotNull();
    assertThat(mdc).isInstanceOf(MDClass.class);

    var current = createJson(mdc, false);
    Path fixturePath;
    if (argumentsAccessor.size() > 2) {
      var fixturePostfix = argumentsAccessor.getString(2);
      fixturePath = Path.of(FIXTURES_PATH, examplePackName, mdoRef + fixturePostfix + ".json");
    } else {
      fixturePath = Path.of(FIXTURES_PATH, examplePackName, mdoRef + ".json");
    }
    var fixture = getFixture(fixturePath);
    Assertions.assertThat(current, true).isEqual(fixture);

    return mdc;
  }

  public CF readConfiguration(ArgumentsAccessor argumentsAccessor, boolean skipSupport) {
    var isEDT = argumentsAccessor.getBoolean(0);
    var examplePackName = argumentsAccessor.getString(1);
    var mdoRef = "Configuration";

    Path configurationPath;
    if (isEDT) {
      configurationPath = Path.of(EXAMPLES_PATH, EDT_PATH, examplePackName);
    } else {
      configurationPath = Path.of(EXAMPLES_PATH, DESIGNER_PATH, examplePackName, DESIGNER_CF_PATH);
    }

    var mdc = MDClasses.createConfiguration(configurationPath, skipSupport);
    assertThat(mdc).isNotNull();
    assertThat(mdc).isInstanceOf(MDClass.class);
    assertThat(mdc).isInstanceOf(CF.class);

    return (CF) mdc;
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
}
