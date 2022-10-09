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

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.Function;

@UtilityClass
@Slf4j
public class XStreamUtils {
  /**
   * Регистрирует конверторы нужного типа, фильтруя по пакету и аннотации
   *
   * @param xStream               объект xStream
   * @param convertersPackageName полное имя пакета, где расположены конверторы
   * @param annotation            аннотация, которой помечены конверторы
   */
  public void registerConverters(ExtendXStream xStream, String convertersPackageName, Class<?> annotation) {
    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .enableAnnotationInfo()
      .acceptPackages(convertersPackageName)
      .scan()) {

      var classes = scanResult.getClassesWithAnnotation(annotation.getName());
      classes.stream()
        .map(getObjectsFromInfoClass())
        .filter(Objects::nonNull)
        .forEach(converter -> xStream.registerConverter(converter, false));
    }
  }

  private Function<ClassInfo, Object> getObjectsFromInfoClass() {
    return (ClassInfo classInfo) -> {
      try {
        var clazz = Class.forName(classInfo.getName());
        return clazz.getDeclaredConstructors()[0].newInstance();
      } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
        LOGGER.error("Cannot resolve class {}\n{}", classInfo.getName(), e);
        return null;
      }
    };
  }

}
