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

import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Служебный класс для хранения контекста при "сборке" объекта при чтении из файла
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class MDCReaderContext extends AbstractReaderContext {

  private static final String UUID_FIELD_NAME = "uuid";
  private static final String MDO_REFERENCE_FIELD_NAME = "mdoReference";
  private static final String SUPPORT_VALIANT_FIELD_NAME = "SupportVariant";
  private static final String CHILD_FILED_NAME = "child";
  private static final String COMPATIBILITY_MODE_FILED_NAME = "compatibilityMode";
  private static final String CONFIGURATION_SOURCE_MODE_FILED_NAME = "configurationSource";

  /**
   * Режим совместимости
   */
  @Setter
  private CompatibilityMode compatibilityMode;

  /**
   * Режим совместимости расширения
   */
  @Setter
  private CompatibilityMode configurationExtensionCompatibilityMode;

  /**
   * Дочерние метаданные
   */
  private final List<String> childrenNames;

  public MDCReaderContext(@NonNull Class<?> clazz, @NonNull HierarchicalStreamReader reader) {
    super(reader);

    realClass = clazz;
    builder = TransformationUtils.builder(realClass);

    var uuid = reader.getAttribute(UUID_FIELD_NAME);
    supportVariant = ParseSupportData.getSupportVariantByMDO(uuid, currentPath);
    mdoType = MDOType.CONFIGURATION;

    super.setValue(UUID_FIELD_NAME, uuid);
    super.setValue(SUPPORT_VALIANT_FIELD_NAME, supportVariant);

    childrenNames = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public void setValue(String methodName, Object value) {
    if (value instanceof String string && MDOType.fromValue(methodName).isPresent()) {
      childrenNames.add(string);
    } else {
      super.setValue(methodName, value);
    }
  }

  @Override
  public Object build() {
    mdoReference = MdoReference.create(mdoType, name);
    setValue(MDO_REFERENCE_FIELD_NAME, mdoReference);

    if (compatibilityMode == null) {
      setValue(COMPATIBILITY_MODE_FILED_NAME, configurationExtensionCompatibilityMode);
    }
    setValue(CONFIGURATION_SOURCE_MODE_FILED_NAME, mdReader.getConfigurationSource());

    setValueModules();
    setValueChildren();

    return super.build();
  }

  private void setValueChildren() {
    var children = childrenNames.parallelStream()
      .map(fullName -> (MD) mdReader.read(fullName))
      .filter(Objects::nonNull)
      .collect(Collectors.toMap(md -> md.getMdoReference().getMdoRef(), md -> md));

    // после обработки порядок нарушился, необходимо его соблюсти
    childrenNames.forEach((String name) -> {
      var child = children.get(name);
      if (child != null) {
        var fieldName = child.getMdoType().getName();
        setValue(fieldName, child);
        setValue(CHILD_FILED_NAME, child);
      }
    });
  }
}
