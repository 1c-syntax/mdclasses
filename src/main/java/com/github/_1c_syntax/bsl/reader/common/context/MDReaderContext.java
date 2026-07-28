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
package com.github._1c_syntax.bsl.reader.common.context;

import com.github._1c_syntax.bsl.mdo.Attribute;
import com.github._1c_syntax.bsl.mdo.AttributeOwner;
import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.MDChild;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.PredefinedDataOwner;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.children.Dimension;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTableField;
import com.github._1c_syntax.bsl.mdo.children.PredefinedValue;
import com.github._1c_syntax.bsl.mdo.children.RecalculationDimension;
import com.github._1c_syntax.bsl.mdo.children.StandardAttribute;
import com.github._1c_syntax.bsl.mdo.storage.AdditionalIndex;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.context.std_attributes.StdAtrInfo;
import com.github._1c_syntax.bsl.reader.common.context.std_attributes.StdAttributeFiller;
import com.github._1c_syntax.bsl.reader.common.converter.AdditionalIndexesWrapper;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendReaderWrapper;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Для хранения контекста при чтении MD и ExternalSource объектов
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Slf4j
public class MDReaderContext extends AbstractReaderContext {

  private static final String MDO_REFERENCE_FIELD_NAME = "mdoReference";
  private static final String OWNER_FIELD_NAME = "owner";
  private static final String PARENT_SUBSYSTEM_FIELD_NAME = "parentSubsystem";
  private static final String UUID_FIELD_NAME = "uuid";
  private static final String SUPPORT_VALIANT_FIELD_NAME = "SupportVariant";
  private static final String DATA_FIELD_NAME = "data";
  private static final String PREDEFINED_VALUES_FIELD_NAME = "predefinedValues";

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

  /**
   * Для доинициализации стандартного атрибута
   */
  @Nullable
  private StdAtrInfo stdAtrInfo;

  /**
   * Построенные атрибуты (стандартные + пользовательские), собранные при setValueChildren
   */
  @Nullable
  private Map<String, MdoReference> builtAttributes;

  public MDReaderContext(HierarchicalStreamReader reader) {
    super(reader);

    var realClassName = reader.getNodeName();
    var computeRealClass = mdReader.getXstream().getRealClass(realClassName);
    if (computeRealClass == null) {
      throw new IllegalArgumentException("Unknown class name " + realClassName);
    }

    realClass = computeRealClass;
    if (Dimension.class.isAssignableFrom(realClass)) {
      if (ConfigurationSource.DESIGNER.equals(mdReader.getConfigurationSource())) {
        var parentFolder = currentPath.getParent().getFileName().toString();
        if (MDOType.fromValue(parentFolder).isPresent()
          && MDOType.fromValue(parentFolder).get() == MDOType.RECALCULATION) {
          realClass = RecalculationDimension.class;
          realClassName = realClass.getSimpleName();
        }
      } else {
        var lastParent = ((ExtendReaderWrapper) reader).getLastParentPath();
        if (lastParent.isPresent()
          && MDOType.fromValue(lastParent.get()).isPresent()
          && MDOType.fromValue(lastParent.get()).get() == MDOType.RECALCULATION) {
          realClass = RecalculationDimension.class;
          realClassName = realClass.getSimpleName();
        }
      }
    }

    builder = TransformationUtils.builder(realClass);

    var uuid = reader.getAttribute(UUID_FIELD_NAME);
    if (uuid != null && !mdReader.getReadSettings().skipSupport()) {
      supportVariant = ParseSupportData.get(uuid, currentPath);
    } else {
      supportVariant = SupportVariant.NONE;
    }

    mdoType = MDOType.fromValue(realClassName).orElse(MDOType.UNKNOWN);
    if (mdoType == MDOType.UNKNOWN) {
      if (ExternalDataSourceTableField.class.isAssignableFrom(realClass)) {
        realClassName = "Field";
        mdoType = MDOType.fromValue(realClassName).orElse(MDOType.UNKNOWN);
      } else if (RecalculationDimension.class.isAssignableFrom(realClass)) {
        mdoType = MDOType.RECALCULATION_DIMENSION;
      }
    }

    templateType = TemplateType.UNKNOWN;
    super.setValue(UUID_FIELD_NAME, uuid);
    super.setValue(SUPPORT_VALIANT_FIELD_NAME, supportVariant);

    childrenContexts = new ConcurrentHashMap<>();
  }

  public MDReaderContext(Path currentPath,
                         MDReader mdReader) {
    super(currentPath, mdReader);
    realClass = StandardAttribute.class;
    builder = TransformationUtils.builder(realClass);
    mdoType = MDOType.STANDARD_ATTRIBUTE;
    templateType = TemplateType.UNKNOWN;
    childrenContexts = new ConcurrentHashMap<>();
  }

  @Override
  public final void setValue(String methodName, @Nullable Object value) {
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

    if (PredefinedDataOwner.class.isAssignableFrom(realClass)) {
      setupPredefinedValues();
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

    if (AttributeOwner.class.isAssignableFrom(realClass)) {
      setupAdditionalIndexes();
    }

    if (ModuleOwner.class.isAssignableFrom(realClass)) {
      setValueModules();
    }

    if (StandardAttribute.class.isAssignableFrom(realClass)) {
      stdAtrInfo = StdAtrInfo.get(name);
      setValue("fullName", stdAtrInfo.getName());
    }

    return super.build();
  }

  private void saveChildName(String collectionName, MDReaderContext child) {
    childrenContexts.computeIfAbsent(collectionName, k -> Collections.synchronizedList(new ArrayList<>()))
      .add(child);
  }

  private void setupPredefinedValues() {
    // EDT хранит предопределённые внутри файла объекта - они уже прочитаны и лежат в кэше;
    // Конфигуратор хранит их в отдельном файле Ext/Predefined.xml - дочитываем через ридер
    List<PredefinedValue> raw = getFromCache(PREDEFINED_VALUES_FIELD_NAME, Collections.emptyList());
    if (raw.isEmpty()) {
      raw = mdReader.readPredefinedData(currentPath, name, mdoType);
    }

    if (raw.isEmpty()) {
      return;
    }

    var withRefs = raw.stream()
      .map(value -> predefinedValueWithRefs(value, mdoReference, supportVariant))
      .toList();

    TransformationUtils.invoke(builder, "clearPredefinedValues");
    setValue(PREDEFINED_VALUES_FIELD_NAME, withRefs);
  }

  private static PredefinedValue predefinedValueWithRefs(PredefinedValue value,
                                                         MdoReference owner,
                                                         SupportVariant supportVariant) {
    var reference = MdoReference.create(owner, MDOType.PREDEFINED_VALUE, value.getName());
    var valueBuilder = value.toBuilder()
      .owner(owner)
      .mdoReference(reference)
      .supportVariant(supportVariant)
      .clearChildItems();
    value.getChildItems()
      .forEach(child -> valueBuilder.childItem(predefinedValueWithRefs(child, reference, supportVariant)));
    return valueBuilder.build();
  }

  private void setValueChildren() {
    var allChildren = new HashMap<String, MdoReference>();
    childrenContexts.forEach((String collectionName, List<MDReaderContext> value) -> {
      var collection = value.parallelStream()
        .map((MDReaderContext childContext) -> {
          childContext.setOwner(mdoReference);
          return childContext.build();
        }).toList();
      for (var obj : collection) {
        if (obj instanceof Attribute attr) {
          allChildren.put(attr.getName(), attr.getMdoReference());
        }
      }
      if (!collectionName.endsWith("s")) {
        collectionName += "s";
      }
      setValue(collectionName, collection);
    });
    builtAttributes = allChildren;
  }

  private void setupAdditionalIndexes() {
    var cached = getFromCache("additionalIndexes", Collections.emptyList());
    if (!cached.isEmpty() || builtAttributes == null) {
      return;
    }

    var raw = mdReader.readAdditionalIndexes(currentPath, name, mdoType);
    if (raw == AdditionalIndexesWrapper.EMPTY || raw.getIndexes().isEmpty()) {
      return;
    }

    var resolved = raw.getIndexes().stream().map((AdditionalIndexesWrapper.AdditionalIndexItem item) -> {
      var tableRef = MdoReference.create(item.getTable());
      return AdditionalIndex.builder()
        .uuid(item.getId())
        .name(item.getName())
        .table(builtAttributes.getOrDefault(item.getTable(), tableRef))
        .indexedFields(item.getIndexedFields().stream()
          .map(fieldName -> builtAttributes.get(fieldName))
          .filter(Objects::nonNull)
          .toList())
        .additionalFields(item.getAdditionalFields().stream()
          .map(fieldName -> builtAttributes.get(fieldName))
          .filter(Objects::nonNull)
          .toList())
        .build();
    }).toList();
    setValue("additionalIndexes", resolved);
  }
}
