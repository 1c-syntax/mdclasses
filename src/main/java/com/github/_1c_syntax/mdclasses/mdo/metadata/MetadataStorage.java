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
package com.github._1c_syntax.mdclasses.mdo.metadata;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Используется для хранения кэша метаинформации по MD классам
 */
@Slf4j
public final class MetadataStorage {

  private static final Map<Class<?>, Metadata> STORAGE = computeStorage(Metadata.class);

  private static final Map<Class<?>, AttributeMetadata> ATTRIBUTE_STORAGE = computeStorage(AttributeMetadata.class);

  private MetadataStorage() {
    // noop
  }

  public static Map<Class<?>, Metadata> getStorage() {
    return STORAGE;
  }

  public static Map<Class<?>, AttributeMetadata> getAttributeStorage() {
    return ATTRIBUTE_STORAGE;
  }

  /**
   * Используется для получения метаинформации по MD классу из кэша
   *
   * @param clazz MD класс
   * @return Метаданные класса
   */
  public static Metadata get(Class<?> clazz) {
    return STORAGE.get(clazz);
  }

  /**
   * Используется для получения метаинформации по MD классу из кэша
   *
   * @param clazz MD класс
   * @return Метаданные класса
   */
  public static AttributeMetadata getAttribute(Class<?> clazz) {
    return ATTRIBUTE_STORAGE.get(clazz);
  }

  private static <T extends Annotation> Map<Class<?>, T> computeStorage(Class<T> annotation) {
    try (var scanResult = new ClassGraph()
            .enableClassInfo()
            .enableAnnotationInfo()
            .acceptPackages("com.github._1c_syntax.mdclasses")
            .scan()) {

      Map<Class<?>, T> localStorage = new HashMap<>();
      var classes = scanResult.getClassesWithAnnotation(annotation.getName());
      classes.stream()
              .map(getClassFromInfoClass())
              .filter(Objects::nonNull)
              .forEach(aClass -> localStorage.put(aClass, aClass.getAnnotation(annotation)));

      return Collections.unmodifiableMap(localStorage);
    }
  }

  private static Function<ClassInfo, ? extends Class<?>> getClassFromInfoClass() {
    return classInfo -> {
      try {
        return Class.forName(classInfo.getName());
      } catch (ClassNotFoundException e) {
        LOGGER.error("Cannot resolve class: " + classInfo.getName());
        return null;
      }
    };
  }
}
