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
import java.util.concurrent.ConcurrentHashMap;

/**
 * Вспомогательный класс для конвертирования значений между моделями
 */
@UtilityClass
@Slf4j
public class TransformationUtils {

  private static final Map<String, Map<String, Method>> METHODS = new ConcurrentHashMap<>();
  private static final Map<String, Map<String, Type>> TYPES = new ConcurrentHashMap<>();
  private static final Map<String, Map<String, SetterDescriptor>> SETTERS = new ConcurrentHashMap<>();

  // Сентинелы отрицательного результата. В ConcurrentHashMap нельзя хранить null, но
  // отсутствие метода/типа/сеттера тоже нужно кэшировать (иначе повторный дорогой резолв
  // на каждый промах). Отличаются по ссылочной идентичности от любого настоящего значения.
  private static final Method ABSENT_METHOD = absentMethod();
  private static final Type ABSENT_TYPE = void.class;
  private static final SetterDescriptor ABSENT_SETTER = new SetterDescriptor(ABSENT_METHOD, false, null);

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
    var perClass = perClassCache(TYPES, source.getClass().getName());
    var cached = perClass.get(methodName);
    if (cached == null) {
      cached = computeFieldType(source, methodName);
      perClass.putIfAbsent(methodName, cached);
    }
    return cached == ABSENT_TYPE ? null : cached;
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
    var perClass = perClassCache(METHODS, clazz.getName());
    var cached = perClass.get(methodName);
    if (cached == null) {
      cached = Arrays.stream(clazz.getDeclaredMethods())
        .filter(m -> methodName.equalsIgnoreCase(m.getName()))
        .findFirst()
        .orElse(ABSENT_METHOD);
      perClass.putIfAbsent(methodName, cached);
    }
    return cached == ABSENT_METHOD ? null : cached;
  }

  @Nullable
  private SetterDescriptor getSetter(Class<?> clazz, String methodName) {
    var perClass = perClassCache(SETTERS, clazz.getName());
    var cached = perClass.get(methodName);
    if (cached == null) {
      cached = computeSetter(clazz, methodName);
      perClass.putIfAbsent(methodName, cached);
    }
    return cached == ABSENT_SETTER ? null : cached;
  }

  /**
   * Возвращает (создавая при первом обращении) пер-классовый кэш по имени класса.
   * <p>
   * Лямбда {@code k -> new ConcurrentHashMap<>()} незахватывающая, поэтому компилируется в
   * синглтон и не аллоцируется на вызов; {@code computeIfAbsent} здесь безопасен по аллокациям.
   * Захватывающие лямбды (резолв метода/типа/сеттера) вынесены на {@code get()}-first в вызывающих
   * методах — иначе {@code ConcurrentHashMap.computeIfAbsent} (не встраивается JIT-ом) не даёт
   * escape-анализу их скаляризовать.
   *
   * @param cache     Двухуровневый кэш «имя класса → (имя свойства → значение)».
   * @param className Имя класса.
   * @param <V>       Тип кэшируемого значения.
   * @return Пер-классовый кэш свойств.
   */
  private static <V> Map<String, V> perClassCache(Map<String, Map<String, V>> cache, String className) {
    return cache.computeIfAbsent(className, k -> new ConcurrentHashMap<>());
  }

  private SetterDescriptor computeSetter(Class<?> clazz, String methodName) {
    var setter = getMethod(clazz, methodName);
    if (setter == null) {
      return ABSENT_SETTER;
    }
    var parameterized = setter.getGenericParameterTypes()[0] instanceof ParameterizedType;
    var singularAdder = parameterized ? getMethod(clazz, "add" + methodName) : null;
    return new SetterDescriptor(setter, parameterized, singularAdder);
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

  private static Type computeFieldType(Object source, String methodName) {
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
      return fieldClass;
    }
    return ABSENT_TYPE;
  }

  private static Method absentMethod() {
    try {
      return TransformationUtils.class.getDeclaredMethod("absentMethodTarget");
    } catch (NoSuchMethodException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  @SuppressWarnings("unused")
  private static void absentMethodTarget() {
    // Целевой метод-маркер для ABSENT_METHOD; никогда не вызывается — нужен лишь как
    // ненулевой уникальный экземпляр Method для сентинела отрицательного результата кэша.
  }
}
