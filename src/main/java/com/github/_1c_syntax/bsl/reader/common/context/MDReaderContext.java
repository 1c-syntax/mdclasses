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

import com.github._1c_syntax.bsl.mdo.AttributeOwner;
import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.MDChild;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTableField;
import com.github._1c_syntax.bsl.mdo.children.StandardAttribute;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.context.std_attributes.StdAttributeFiller;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Для хранения контекста при чтении MD и ExternalSource объектов
 */
@EqualsAndHashCode(callSuper = true)
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
  @Getter
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
    if (uuid != null && !mdReader.getReadSettings().skipSupport()) {
      supportVariant = ParseSupportData.get(uuid, currentPath);
    } else {
      supportVariant = SupportVariant.NONE;
    }

    mdoType = MDOType.fromValue(realClassName).orElse(MDOType.UNKNOWN);
    if (mdoType == MDOType.UNKNOWN && realClass.isAssignableFrom(ExternalDataSourceTableField.class)) {
      realClassName = "Field";
      mdoType = MDOType.fromValue(realClassName).orElse(MDOType.UNKNOWN);
    }

    super.setValue(UUID_FIELD_NAME, uuid);
    super.setValue(SUPPORT_VALIANT_FIELD_NAME, supportVariant);

    childrenContexts = new ConcurrentHashMap<>();
  }

  public MDReaderContext(@NonNull Path currentPath,
                         @NonNull MDReader mdReader) {
    super(currentPath, mdReader);
    realClass = StandardAttribute.class;
    builder = TransformationUtils.builder(realClass);
    mdoType = MDOType.STANDARD_ATTRIBUTE;
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
    if (mdoReference.isEmpty()) {
      mdoReference = MdoReference.create(owner, mdoType, name);
      setValue(MDO_REFERENCE_FIELD_NAME, mdoReference);
    }

    if (MDChild.class.isAssignableFrom(realClass)) {
      setValue(OWNER_FIELD_NAME, owner);
    }

    if (Subsystem.class.isAssignableFrom(realClass)) {
      setValue(PARENT_SUBSYSTEM_FIELD_NAME, owner);
    }

    if (Form.class.isAssignableFrom(realClass)) {
      setValue(DATA_FIELD_NAME, mdReader.readFormData(currentPath, name, mdoType));
    }

    if (AttributeOwner.class.isAssignableFrom(realClass)) {
      StdAttributeFiller.fill(this);
    }

    if (ChildrenOwner.class.isAssignableFrom(realClass)) {
      setValueChildren();
    }

    if (ModuleOwner.class.isAssignableFrom(realClass)) {
      setValueModules();
    }

    return super.build();
  }

  private void saveChildName(String collectionName, MDReaderContext child) {
    childrenContexts.computeIfAbsent(collectionName, k -> Collections.synchronizedList(new ArrayList<>()))
      .add(child);
  }

  private void setValueChildren() {
    childrenContexts.forEach((String collectionName, List<MDReaderContext> value) -> {
      var collection = value.parallelStream()
        .map((MDReaderContext childContext) -> {
          childContext.setOwner(mdoReference);
          return childContext.build();
        }).toList();
      if (!collectionName.endsWith("s")) {
        collectionName += "s";
      }
      setValue(collectionName, collection);
    });
  }
}
