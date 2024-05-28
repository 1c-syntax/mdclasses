/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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

import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.MDChild;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Для хранения контекста при чтении MD и ExternalSource объектов
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@ToString
public class MDReaderContext extends AbstractReaderContext {

  private static final String MDO_REFERENCE_FIELD_NAME = "mdoReference";
  private static final String OWNER_FIELD_NAME = "owner";
  private static final String PARENT_SUBSYSTEM_FIELD_NAME = "parentSubsystem";
  private static final String UUID_FIELD_NAME = "uuid";
  private static final String SUPPORT_VALIANT_FIELD_NAME = "SupportVariant";
  private static final String DATA_FIELD_NAME = "data";

  /**
   * Коллекция билдеров для дочерних объектов, которые надо доделать
   */
  private final Map<String, List<MDReaderContext>> childrenContexts;

  /**
   * Тип макета
   */
  @Setter
  @Getter
  private TemplateType templateType;

  /**
   * Ссылка на родительский объект
   */
  @Setter
  private MdoReference owner = MdoReference.EMPTY;

  public MDReaderContext(@NonNull HierarchicalStreamReader reader) {
    super(reader);

    var realClassName = reader.getNodeName();
    realClass = mdReader.getXstream().getRealClass(realClassName);
    builder = TransformationUtils.builder(realClass);

    var uuid = reader.getAttribute(UUID_FIELD_NAME);
    supportVariant = ParseSupportData.getSupportVariantByMDO(uuid, currentPath);
    mdoType = MDOType.fromValue(realClassName).orElse(MDOType.UNKNOWN);

    super.setValue(UUID_FIELD_NAME, uuid);
    super.setValue(SUPPORT_VALIANT_FIELD_NAME, supportVariant);

    childrenContexts = new ConcurrentHashMap<>();
  }

  @Override
  public final void setValue(String methodName, Object value) {
    if (value instanceof MDReaderContext child) {
      saveChildName(methodName, child);
    } else {
      super.setValue(methodName, value);
    }
  }

  @Override
  public Object build() {
    try {
      mdoReference = MdoReference.create(owner, mdoType, name);
      setValue(MDO_REFERENCE_FIELD_NAME, mdoReference);

      if (MDChild.class.isAssignableFrom(realClass)) {
        setValue(OWNER_FIELD_NAME, owner);
      }

      if (Subsystem.class.isAssignableFrom(realClass)) {
        setValue(PARENT_SUBSYSTEM_FIELD_NAME, owner);
      }

      if (Form.class.isAssignableFrom(realClass)) {
        setValue(DATA_FIELD_NAME, mdReader.readFormData(currentPath, name, mdoType));
      }

      if (ChildrenOwner.class.isAssignableFrom(realClass)) {
        setValueChildren();
      }

      if (ModuleOwner.class.isAssignableFrom(realClass)) {
        setValueModules();
      }

      return super.build();
    } catch (Exception e) {
      LOGGER.warn("Can't read file '{}' - it's broken (object skipped) \n: ", currentPath, e);
      LOGGER.warn("Reader context\n: '{}'", this);
      LOGGER.warn("Builder content\n: '{}'", builder);
    }
    return null;
  }

  private void saveChildName(String collectionName, MDReaderContext child) {
    var collection = childrenContexts.get(collectionName);
    if (collection == null) {
      collection = Collections.synchronizedList(new ArrayList<>());
    }
    collection.add(child);
    childrenContexts.put(collectionName, collection);
  }

  private void setValueChildren() {
    childrenContexts.forEach((String collectionName, List<MDReaderContext> collectionSource) -> {
      if (collectionName.endsWith("s")) {
        var collection = collectionSource.stream()
          .map((MDReaderContext childContext) -> {
            childContext.setOwner(mdoReference);
            return childContext.build();
          }).toList();
        setValue(collectionName, collection);
      } else {
        collectionSource.stream()
          .filter(Objects::nonNull) // исключаем не прочитанное
          .filter(childContext -> childContext.name != null) // битые
          .forEach((MDReaderContext childContext) -> {
            childContext.setOwner(mdoReference);
            setValue(collectionName, childContext.build());
          });
      }
    });
  }
}
