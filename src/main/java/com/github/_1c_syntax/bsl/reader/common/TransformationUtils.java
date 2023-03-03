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
package com.github._1c_syntax.bsl.reader.common;

import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.MDChild;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.children.ObjectModule;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.reader.edt.EDTPaths;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Вспомогательный класс для конвертирования значений между моделями
 */
@UtilityClass
@Slf4j
public class TransformationUtils {

  private static final Map<Class<?>, Map<String, Method>> methods = new ConcurrentHashMap<>();
  private static final String BUILD_METHOD_NAME = "build";
  private static final String BUILDER_METHOD_NAME = "builder";
  private static final String LOGGER_MESSAGE_PREF = "Class {}, method {}";

  public void setValue(Object source, String methodName, Object value) {
    var method = getMethod(source.getClass(), methodName);
    if (method != null && value != null) {
      try {
        var parameterType = method.getGenericParameterTypes()[0];
        if (parameterType instanceof ParameterizedType && !(value instanceof List)) {
          var singular = getMethod(source.getClass(), "add" + methodName);
          Objects.requireNonNullElse(singular, method).invoke(source, value);
        } else {
          method.invoke(source, value);
        }
      } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
        LOGGER.error(LOGGER_MESSAGE_PREF, source.getClass(), methodName, e);
      }
    }
  }

  public Type fieldType(Object source, String methodName) {
    var method = getMethod(source.getClass(), methodName);
    if (method != null) {
      return method.getGenericParameterTypes()[0];
    }
    return null;
  }

  public Object builder(Class<?> clazz) {
    var method = getMethod(clazz, BUILDER_METHOD_NAME);
    if (method != null) {
      try {
        return method.invoke(clazz);
      } catch (IllegalAccessException | InvocationTargetException e) {
        LOGGER.error(LOGGER_MESSAGE_PREF, clazz, BUILDER_METHOD_NAME, e);
      }
    }
    return null;
  }

  public Object build(Object builder) {
    var method = getMethod(builder.getClass(), BUILD_METHOD_NAME);
    if (method != null) {
      try {
        return method.invoke(builder);
      } catch (IllegalAccessException | InvocationTargetException e) {
        LOGGER.error(LOGGER_MESSAGE_PREF, builder.getClass(), BUILD_METHOD_NAME, e);
      }
    }
    return null;
  }

  /**
   * Вычисляет класс типа значения из типа коллекции (для списков)
   *
   * @param fieldClass Тип поля-коллекции
   * @return тип класса
   */
  public Class<?> computeType(ParameterizedType fieldClass) {
    var type = (fieldClass).getActualTypeArguments()[0];
    if (type instanceof WildcardType) {
      return (Class<?>) ((WildcardType) type).getUpperBounds()[0];
    } else {
      return (Class<?>) type;
    }
  }

  private Method getMethod(Class<?> clazz, String methodName) {
    var classMethods = methods.get(clazz);
    if (classMethods == null) {
      classMethods = new CaseInsensitiveMap<>();
    }

    var method = classMethods.get(methodName);
    // ключ метода в кэше есть, но метода нет
    if (method == null) {
      // ключ метода в кэше есть, но метода нет
      if (classMethods.containsKey(methodName)) {
        return null;
      }
      method = Arrays.stream(clazz.getDeclaredMethods())
        .filter(classMethod -> methodName.equalsIgnoreCase(classMethod.getName()))
        .findFirst()
        .orElse(null);
      if (method != null) {
        saveMethod(clazz, classMethods, method, methodName);
      }
    }
    return method;
  }

  private static void saveMethod(Class<?> builderClass,
                                 Map<String, Method> classMethods,
                                 Method method,
                                 String builderMethodName) {
    classMethods.put(builderMethodName, method);
    methods.put(builderClass, classMethods);
  }

  /**
   * Хранит вспомогательные данные, необходимые для дозаполнения моделей
   */
  @Data
  public static class Context {

    /**
     * Имя прочитанного объекта
     */
    String name;

    /**
     * Список подсистем
     */
    List<String> subsystems = new ArrayList<>();

    /**
     * Ссылка на текущий объект
     */
    MdoReference mdoReference = MdoReference.EMPTY;

    /**
     * Ссылка на родительский объект
     */
    MdoReference owner = MdoReference.EMPTY;

    /**
     * Класс будущего объекта
     */
    Class<?> realClass;

    /**
     * Строковое имя объекта
     */
    String realClassName;

    /**
     * Билдер объекта
     */
    Object builder;

    /**
     * Тип объекта ссылки
     */
    MDOType mdoType;

    /**
     * Коллекция билдеров для дочерних объектов, которые надо доделать
     */
    Map<String, List<TransformationUtils.Context>> children;

    /**
     * Режим поддержки
     */
    SupportVariant supportVariant = SupportVariant.NONE;

    /**
     * Путь к текущему, читаемому файлу
     */
    Path currentPath;

    /**
     * Тип макета
     */
    TemplateType templateType;

    /**
     * Режим совместимости
     */
    CompatibilityMode compatibilityMode;

    /**
     * Режим совместимости расширения
     */
    CompatibilityMode configurationExtensionCompatibilityMode;

    /**
     * Дочерние метаданные
     */
    List<String> childrenMetadata = new ArrayList<>();

    public Context(@NonNull String name, @NonNull Class<?> clazz, @NonNull Path path) {
      realClassName = name;
      realClass = clazz;
      builder = TransformationUtils.builder(realClass);
      requireNonNull(builder);
      mdoType = MDOType.fromValue(realClassName).orElse(MDOType.UNKNOWN);
      children = new HashMap<>();
      currentPath = path;
    }

    public void setValue(String methodName, Object value) {
      TransformationUtils.setValue(builder, methodName, value);
    }

    public Class<?> fieldType(String methodName) {
      var fieldClass = TransformationUtils.fieldType(builder, methodName);
      if (fieldClass instanceof ParameterizedType) {
        fieldClass = TransformationUtils.computeType((ParameterizedType) fieldClass);
      }
      return (Class<?>) fieldClass;
    }

    public void addChild(String collectionName, TransformationUtils.Context child) {
      var collection = children.get(collectionName);
      if (collection == null) {
        collection = new ArrayList<>();
      }
      collection.add(child);
      children.put(collectionName, collection);
    }

    public void addChildMetadata(String groupName, String childName) {
      childrenMetadata.add(groupName + "." + childName);
    }

    public void addChildMetadata(String fullName) {
      childrenMetadata.add(fullName);
    }

    public MD build() {

      if (owner != MdoReference.EMPTY) {
        mdoReference = MdoReference.create(owner, mdoType, name);
      } else {
        mdoReference = MdoReference.create(mdoType, name);
      }
      setValue("mdoReference", mdoReference);

      if (MDChild.class.isAssignableFrom(realClass)) {
        setValue("owner", owner);
      }

      if (Subsystem.class.isAssignableFrom(realClass)) {
        setValue("parentSubsystem", owner);
      }

      if (ChildrenOwner.class.isAssignableFrom(realClass)) {
        children.forEach((String collectionName, List<Context> collectionSource) -> {
          if (collectionName.endsWith("s")) {
            var collection = collectionSource.parallelStream().map((Context childContext) -> {
              childContext.setOwner(mdoReference);
              return childContext.build();
            }).collect(Collectors.toList());
            setValue(collectionName, collection);
          } else {
            collectionSource.stream()
              .filter(Objects::nonNull) // исключаем не прочитанное
              .forEach((Context childContext) -> {
                childContext.setOwner(mdoReference);
                setValue(collectionName, childContext.build());
              });
          }
        });
      }

      if (ModuleOwner.class.isAssignableFrom(realClass)) {
        addModules();
      }

      return (MD) TransformationUtils.build(builder);
    }

    private void addModules() {
      var moduleTypes = ModuleType.byMDOType(mdoType);
      if (moduleTypes.isEmpty()) {
        return;
      }

      // todo переделать
      Path folder;
      boolean isDesigner = MDOReader.getConfigurationSourceByMDOPath(currentPath) == ConfigurationSource.DESIGNER;
      if (isDesigner) {
        folder = DesignerPaths.moduleFolder(currentPath, mdoType);
      } else {
        folder = EDTPaths.moduleFolder(currentPath, mdoType);
      }

      if (!folder.toFile().exists()) {
        return;
      }

      List<Module> modules = new ArrayList<>();
      moduleTypes.forEach((ModuleType moduleType) -> {
          Path modulePath;
          // todo переделать
          if (isDesigner) {
            modulePath = DesignerPaths.modulePath(folder, name, moduleType);
          } else if (mdoType == MDOType.CONFIGURATION) {
            modulePath = EDTPaths.modulePath(folder, MDOType.CONFIGURATION.getName(), moduleType);
          } else {
            modulePath = EDTPaths.modulePath(folder, name, moduleType);
          }

          if (modulePath.toFile().exists()) {
            modules.add(ObjectModule.builder()
              .moduleType(moduleType)
              .uri(modulePath.toUri())
              .owner(mdoReference)
              .supportVariant(supportVariant)
              .build());
          }
        }
      );
      setValue("modules", modules);
    }


  }
}
