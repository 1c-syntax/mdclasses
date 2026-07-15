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
package com.github._1c_syntax.bsl.reader.common;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Вспомогательный класс для конвертирования значений между моделями
 */
@UtilityClass
@Slf4j
public class TransformationUtils {

  private static final Map<String, Map<String, Optional<Method>>> METHODS = new ConcurrentHashMap<>();
  private static final Map<String, Map<String, Optional<Type>>> TYPES = new ConcurrentHashMap<>();
  private static final Map<String, Map<String, Optional<SetterDescriptor>>> SETTERS = new ConcurrentHashMap<>();
  private static final String BUILD_METHOD_NAME = "build";
  private static final String BUILDER_METHOD_NAME = "builder";
  private static final String TO_BUILDER_METHOD_NAME = "toBuilder";
  private static final String LOGGER_MESSAGE_PREF = "Class {}, method {}";

  /**
   * Устанавливает значение в билдере объекта. Не устанавливаются значения равные null
   *
   * @param source     Билдер-источник
   * @param methodName Метод\свойство билдера
   * @param value      Устанавливаемое значение
   */
  public void setValue(Object source, String methodName, @Nullable Object value) {
    if (value == null) {
      return;
    }
    var descriptor = getSetter(source.getClass(), methodName);
    if (descriptor != null) {
      try {
        descriptor.target(value).invoke(source, value);
      } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
        LOGGER.error(LOGGER_MESSAGE_PREF, source.getClass(), methodName, e);
      }
    }
  }

  public void invoke(Object source, String methodName) {
    var method = getMethod(source.getClass(), methodName);
    if (method != null) {
      try {
        method.invoke(source);
      } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
        LOGGER.error(LOGGER_MESSAGE_PREF, source.getClass(), methodName, e);
      }
    }
  }

  /**
   * Определяет тип значения поля\метода
   *
   * @param source     Билдер-источник
   * @param methodName Имя метода\поля
   * @return Тип значения
   */
  @Nullable
  public Type fieldType(Object source, String methodName) {
    return TYPES.computeIfAbsent(source.getClass().getName(),
        k -> new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER))
      .computeIfAbsent(methodName, l -> computeFieldType(source, methodName))
      .orElse(null);
  }

  /**
   * Возвращает объект-билдер для выбранного класса
   *
   * @param clazz Класс, для которого ищется билдер
   * @return Найденный билдер
   */
  public Object builder(Class<?> clazz) {
    var method = getMethod(clazz, BUILDER_METHOD_NAME);
    if (method != null) {
      try {
        return method.invoke(clazz);
      } catch (IllegalAccessException | InvocationTargetException e) {
        LOGGER.error(LOGGER_MESSAGE_PREF, clazz, BUILDER_METHOD_NAME, e);
      }
    }
    throw new IllegalArgumentException("Incorrect class " + clazz);
  }

  /**
   * Возвращает объект-билдер для объекта
   *
   * @param object Объект, для которого ищется билдер копирования
   * @return Найденный билдер копирования
   */
  @Nullable
  public Object toBuilder(Object object) {
    var clazz = object.getClass();
    var method = getMethod(clazz, TO_BUILDER_METHOD_NAME);
    if (method != null) {
      try {
        return method.invoke(object);
      } catch (IllegalAccessException | InvocationTargetException e) {
        LOGGER.error(LOGGER_MESSAGE_PREF, clazz, TO_BUILDER_METHOD_NAME, e);
      }
    }
    return null;
  }

  /**
   * Вызывает метод сборки билдера
   *
   * @param builder Собираемый билдер
   * @param path    Файл, который собирается
   * @return Собранный билдером объект
   */
  @Nullable
  public Object build(Object builder, Path path) {
    var method = getMethod(builder.getClass(), BUILD_METHOD_NAME);
    if (method != null) {
      try {
        return method.invoke(builder);
      } catch (Exception e) {
        LOGGER.error("File {}, Class {}, method {}", path, builder.getClass(), BUILD_METHOD_NAME, e);
      }
    }
    return null;
  }

  @Nullable
  public Object build(Object builder) {
    var method = getMethod(builder.getClass(), BUILD_METHOD_NAME);
    if (method != null) {
      try {
        return method.invoke(builder);
      } catch (Exception e) {
        LOGGER.error(LOGGER_MESSAGE_PREF, builder.getClass(), BUILD_METHOD_NAME, e);
      }
    }
    return null;
  }

  @Nullable
  private Method getMethod(Class<?> clazz, String methodName) {
    return METHODS.computeIfAbsent(clazz.getName(), k -> new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER))
      .computeIfAbsent(methodName, k -> Arrays.stream(clazz.getDeclaredMethods())
        .filter(m -> methodName.equalsIgnoreCase(m.getName()))
        .findFirst())
      .orElse(null);
  }

  @Nullable
  private SetterDescriptor getSetter(Class<?> clazz, String methodName) {
    return SETTERS.computeIfAbsent(clazz.getName(),
        k -> new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER))
      .computeIfAbsent(methodName, k -> computeSetter(clazz, methodName))
      .orElse(null);
  }

  private Optional<SetterDescriptor> computeSetter(Class<?> clazz, String methodName) {
    var setter = getMethod(clazz, methodName);
    if (setter == null) {
      return Optional.empty();
    }
    var parameterized = setter.getGenericParameterTypes()[0] instanceof ParameterizedType;
    var singularAdder = parameterized ? getMethod(clazz, "add" + methodName) : null;
    return Optional.of(new SetterDescriptor(setter, parameterized, singularAdder));
  }

  /**
   * Разобранный сеттер билдера, закэшированный по паре (класс, свойство).
   * <p>
   * Раньше {@code setValue} на каждый вызов заново вычислял тип параметра
   * ({@code getGenericParameterTypes()} аллоцирует массив) и, для параметризованных
   * свойств, склеивал имя {@code "add" + methodName} и делал повторный поиск метода.
   * Дескриптор считает это один раз.
   *
   * @param setter        Метод-сеттер билдера.
   * @param parameterized Признак того, что первый параметр — параметризованный тип
   *                      (коллекция), для которого одиночное значение ставится через
   *                      {@code singularAdder}.
   * @param singularAdder Метод добавления одиночного значения ({@code add<Свойство>}) или
   *                      {@code null}, если его нет либо свойство не параметризованное.
   */
  private record SetterDescriptor(Method setter, boolean parameterized, @Nullable Method singularAdder) {

    /**
     * Выбирает целевой метод под конкретное значение: для параметризованного свойства и
     * одиночного (не {@link List}) значения — {@code singularAdder} (если есть), иначе сеттер.
     *
     * @param value Устанавливаемое значение.
     * @return Метод для вызова через рефлексию.
     */
    private Method target(Object value) {
      if (parameterized && singularAdder != null && !(value instanceof List)) {
        return singularAdder;
      }
      return setter;
    }
  }

  private static Optional<Type> computeFieldType(Object source, String methodName) {
    var method = getMethod(source.getClass(), methodName);
    if (method != null) {
      var fieldClass = method.getGenericParameterTypes()[0];
      if (fieldClass instanceof ParameterizedType parameterizedType) {
        var type = parameterizedType.getActualTypeArguments()[0];
        if (type instanceof WildcardType wildcardType) {
          fieldClass = wildcardType.getUpperBounds()[0];
        } else {
          fieldClass = type;
        }
      }
      return Optional.of(fieldClass);
    }
    return Optional.empty();
  }
}
