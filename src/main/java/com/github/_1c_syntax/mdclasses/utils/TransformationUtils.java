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
package com.github._1c_syntax.mdclasses.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Вспомогательный класс для конвертирования значений между моделями
 */
@UtilityClass
public class TransformationUtils {

  private static final Map<Class<?>, Map<String, Method>> methods = new ConcurrentHashMap<>();
  private static final String BUILDER_METHOD_NAME = "build";

  @SneakyThrows
  public void setValue(Object source, String methodName, Object value) {
    var classMethods = methods.get(source.getClass());
    if (classMethods == null) {
      classMethods = new HashMap<>();
    }

    var method = classMethods.get(methodName);
    // ключ метода в кэше есть, но метода нет
    if (method == null) {
      // ключ метода в кэше есть, но метода нет
      if (classMethods.containsKey(methodName)) {
        return;
      }
      try {
        if (value instanceof List) {
          method = source.getClass().getDeclaredMethod(methodName, List.class);
        } else if (value instanceof Path) {
          method = source.getClass().getDeclaredMethod(methodName, Path.class);
        } else if (value instanceof Boolean) {
          method = source.getClass().getDeclaredMethod(methodName, boolean.class);
        } else {
          method = source.getClass().getDeclaredMethod(methodName, value.getClass());
        }
      } catch (NoSuchMethodException e) {
        // просто считаем, что метода нет
        method = null;
      }
      saveMethod(source, classMethods, method, methodName);

      if (method == null) {
        return;
      }
    }

    method.invoke(source, value);
  }

  @SneakyThrows
  public Object build(Object builder) {
    var classMethods = methods.get(builder.getClass());
    if (classMethods == null) {
      classMethods = new HashMap<>();
    }

    var method = classMethods.get(BUILDER_METHOD_NAME);
    if (method == null) {
      method = builder.getClass().getDeclaredMethod(BUILDER_METHOD_NAME);
      saveMethod(builder, classMethods, method, BUILDER_METHOD_NAME);
    }

    return method.invoke(builder);
  }

  private static void saveMethod(Object builder,
                                 Map<String, Method> classMethods,
                                 Method method,
                                 String builderMethodName) {
    classMethods.put(builderMethodName, method);
    methods.put(builder.getClass(), classMethods);
  }
}
