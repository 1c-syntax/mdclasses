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
package com.github._1c_syntax.bsl.test_utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.javabean.BeanProvider;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.beans.PropertyDescriptor;
import java.nio.file.Files;
import java.nio.file.Paths;

@UtilityClass
public class MDTestUtils {

//  private static final Map<Path, MDClass> MDCLASSES = new HashMap<>();

  /**
   * Для загрузки фикстуры по пути к файлу
   *
   * @param path Путь к файлу
   * @return Содержимое файла
   */
  @SneakyThrows
  public String getFixture(String path) {
    return Files.readString(Paths.get(path));
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
    return xstream.toXML(md);
  }

//  /**
//   * Читает контейнер MD из файла
//   *
//   * @param path Путь к корневому файлу
//   * @return Прочитанный контейнер
//   */
//  public MDClass getMDClass(Path path, boolean skipSupport) {
//
//    var mdc = MDCLASSES.get(path);
//    if (mdc == null) {
//      mdc = MDClasses.createConfiguration(path, skipSupport);
//      MDCLASSES.put(path, mdc);
//    }
//
//    return mdc;
//  }

//  /**
//   * Читает контейнер MD по строковому пути
//   *
//   * @param path Строковый путь к файлу
//   * @return Прочитанный контейнер
//   */
//  public MDClass getMDClass(String path, boolean skipSupport) {
//    return getMDClass(Paths.get(path), skipSupport);
//  }

//  /**
//   * Возвращает ссылку на объект метаданных конфигурации (расширения), предварительно сверяя с эталоном
//   *
//   * @param argumentsAccessor Параметры теста
//   */
//  public MDObject testAndGetMDO(ArgumentsAccessor argumentsAccessor) {
//    var pack = argumentsAccessor.getString(0);
//    var mdoRef = argumentsAccessor.getString(1);
//
//    var skipSupport = true;
//    if (argumentsAccessor.size() > 2) {
//      skipSupport = argumentsAccessor.getBoolean(2);
//    }
//
//    var sourcePath = "";
//    var fixturePath = "src/test/resources/fixtures/" + pack + "/" + mdoRef + ".json";
//    if (pack.startsWith("edt")) {
//      if (!new File(fixturePath).exists()) {
//        fixturePath = "src/test/resources/fixtures/"
//          + pack.replace("edt", "designer") + "/" + mdoRef + ".json";
//      }
//      sourcePath = "src/test/resources/ext/" + pack;
//    } else {
//      sourcePath = "src/test/resources/ext/" + pack + "/src/cf";
//    }
//
//    var mdc = getMDClass(sourcePath, skipSupport);
//    assertThat(mdc).isNotNull();
//    var mdoOptional = mdc.findChild(mdoRef);
//    assertThat(mdoOptional).isPresent();
//    var mdo = mdoOptional.get();
//    var current = createJson(mdo);
//    var fixture = getFixture(fixturePath);
//    Assertions.assertThat(current, true).isEqual(fixture);
//    return mdo;
//  }

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
