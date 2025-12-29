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
package com.github._1c_syntax.bsl.reader.common.context;

import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.children.ObjectModule;
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.converter.ProtectedModuleInfo;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Сохраняемый контекст при чтении файла
 */
public abstract class AbstractReaderContext {

  protected static final String MODULES_FIELD_NAME = "modules";

  /**
   * Путь к текущему, читаемому файлу
   */
  @Getter
  protected final Path currentPath;

  /**
   * Ридер файла
   */
  @Getter
  protected final MDReader mdReader;

  /**
   * Класс будущего объекта
   */
  @Getter
  protected Class<?> realClass;

  /**
   * Билдер объекта
   */
  @Getter
  protected Object builder;

  /**
   * Режим поддержки
   */
  @Getter
  protected SupportVariant supportVariant;

  /**
   * Тип объекта ссылки
   */
  @Getter
  protected MDOType mdoType;

  /**
   * Ссылка на текущий объект
   */
  @Getter
  protected MdoReference mdoReference = MdoReference.EMPTY;

  /**
   * Имя прочитанного объекта
   */
  @Setter
  @Getter
  protected String name;

  /**
   * Последнее прочитанное поле
   */
  @Setter
  @Getter
  private String lastName;

  /**
   * Значение последнего прочитанного поля
   */
  @Setter
  @Getter
  @Nullable
  private Object lastValue;

  /**
   * Всякие прочитанные атрибуты
   */
  @Getter
  private final Map<String, Object> cache;

  protected AbstractReaderContext(HierarchicalStreamReader reader) {
    currentPath = ExtendXStream.getCurrentPath(reader);
    mdReader = ExtendXStream.getCurrentMDReader(reader);

    cache = new ConcurrentHashMap<>();
  }

  protected AbstractReaderContext(Path currentPath, MDReader mdReader) {
    this.currentPath = currentPath;
    this.mdReader = mdReader;

    cache = new ConcurrentHashMap<>();
  }

  /**
   * Для установки значения поля собираемого объекта
   *
   * @param methodName Имя поля\метода
   * @param value      устанавливаемое значение
   */
  public void setValue(String methodName, @Nullable Object value) {
    if (value != null) {
      TransformationUtils.setValue(builder, methodName, value);
      var key = methodName.toLowerCase(Locale.ROOT);
      cache.compute(key, (String k, Object existing) -> {
        if (existing instanceof List<?> existingList) {
          @SuppressWarnings("unchecked")
          var list = (List<Object>) existingList;
          if (value instanceof List<?> valueList) {
            list.addAll(valueList);
          } else {
            list.add(value);
          }
          return list;
        } else {
          if (value instanceof List<?>) { // список при добавлении сделаем синхронизируемым
            return Collections.synchronizedList(new ArrayList<>((List<?>) value));
          }
          return value;
        }
      });
    }
  }

  /**
   * Получение класса типа поля
   *
   * @param fieldName Имя поля\метода
   * @return Определенный класс, если не найден, тогда null
   */
  @Nullable
  public Class<?> fieldType(String fieldName) {
    return (Class<?>) TransformationUtils.fieldType(builder, fieldName);
  }

  /**
   * Сборка контекста в объект
   */
  public Object build() {
    var result = TransformationUtils.build(builder, currentPath);
    assert result != null;
    return result;
  }

  @SuppressWarnings("unchecked")
  public <T> T getFromCache(String key, T defaultValue) {
    return (T) cache.getOrDefault(key.toLowerCase(Locale.ROOT), defaultValue);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public <T> T getFromCache(String key) {
    return (T) cache.get(key.toLowerCase(Locale.ROOT));
  }

  protected void setValueModules() {
    var modules = readModules();
    if (!modules.isEmpty()) {
      setValue(MODULES_FIELD_NAME, modules);
    }
  }

  private List<Module> readModules() {
    var folder = mdReader.moduleFolder(currentPath, mdoType);
    if (!folder.toFile().exists()) {
      return Collections.emptyList();
    }

    var moduleTypes = ModuleType.byMDOType(mdoType);
    if (moduleTypes.isEmpty()) {
      return Collections.emptyList();
    }

    var isDesigner = mdReader.getConfigurationSource() == ConfigurationSource.DESIGNER;
    List<Module> modules = new ArrayList<>();
    moduleTypes.forEach((ModuleType moduleType) -> {
        var protectedModuleInfo = new ProtectedModuleInfo(mdReader.modulePath(folder, name, moduleType), isDesigner);
        if (protectedModuleInfo.isExist()) {
          modules.add(ObjectModule.builder()
            .moduleType(moduleType)
            .uri(protectedModuleInfo.getModulePath().toUri())
            .owner(mdoReference)
            .supportVariant(supportVariant)
            .isProtected(protectedModuleInfo.isProtected())
            .build());
        }
      }
    );
    modules.sort(Comparator.comparing(Module::toString));
    return modules;
  }
}
