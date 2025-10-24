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
package com.github._1c_syntax.bsl.reader.common.context.std_attributes;

import com.github._1c_syntax.bsl.mdo.children.StandardAttribute;
import com.github._1c_syntax.bsl.reader.common.context.MDReaderContext;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.MultiName;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Вспомогательный класс для заполнения информации о стандартных реквизитах MD
 */
@UtilityClass
@Slf4j
public class StdAttributeFiller {
  private static final String UUID_FIELD_NAME = "uuid";
  private static final String SUPPORT_VALIANT_FIELD_NAME = "SupportVariant";
  private static final List<MDOType> EXCLUDED = List.of(
    MDOType.DATA_PROCESSOR, MDOType.REPORT, MDOType.EXTERNAL_DATA_PROCESSOR, MDOType.EXTERNAL_REPORT,
    MDOType.SEQUENCE, MDOType.EXTERNAL_DATA_SOURCE_TABLE, MDOType.EXTERNAL_DATA_SOURCE_CUBE,
    MDOType.EXTERNAL_DATA_SOURCE_FUNCTION);
  private static final Map<MDOType, List<StdAtrInfo>> REGISTRY = computeRegistry();

  public void fill(MDReaderContext parentContext) {
    if (EXCLUDED.contains(parentContext.getMdoType())) {
      return;
    }

    var stdAttributes = REGISTRY.getOrDefault(parentContext.getMdoType(), Collections.emptyList());
    if (stdAttributes.isEmpty()) {
      LOGGER.debug("Для {} нет настроенных стандартных реквизитов", parentContext.getMdoType());
    }

    Map<String, MDReaderContext> existsStdAttributes = new HashMap<>();
    parentContext.getChildrenContexts().forEach((String collectionName, List<MDReaderContext> value) -> value.stream()
      .filter(context -> StandardAttribute.class.isAssignableFrom(context.getRealClass()))
      .forEach(mdReaderContext -> existsStdAttributes.put(mdReaderContext.getName(), mdReaderContext)));

    var uuid = parentContext.getFromCache(UUID_FIELD_NAME, "");

    stdAttributes.forEach((StdAtrInfo stdAtrInfo) -> {
        var attributeContext = getOrComputeChildContext(parentContext, existsStdAttributes, stdAtrInfo.getNameEn());
        attributeContext.setValue("nameRu", stdAtrInfo.getNameRu());
        if (stdAtrInfo.getValueType() != ValueTypeDescription.EMPTY) {
          attributeContext.setValue("type", stdAtrInfo.getValueType());
        } else {
          attributeContext.setValue("type", stdAtrInfo.getComputeValueType().apply(parentContext));
        }

        attributeContext.setValue("mdoReference",
          MdoReference.create(parentContext.getMdoReference(),
            MDOType.STANDARD_ATTRIBUTE, stdAtrInfo.getNameEn(), stdAtrInfo.getNameRu()));
      }
    );

    // todo подумать об удалении ненужных

    existsStdAttributes.forEach((String name, MDReaderContext stdAttribute) -> {
      var nameRu = stdAttribute.getFromCache("nameRu", "");
      if (nameRu.isEmpty()) {
        LOGGER.debug("У {} для поля {} нет заполнения", parentContext.getMdoReference(), stdAttribute.getName());
      }
      stdAttribute.setValue("fullName", MultiName.create(stdAttribute.getName(), nameRu));
      stdAttribute.setValue(UUID_FIELD_NAME, uuid);
      stdAttribute.setValue(SUPPORT_VALIANT_FIELD_NAME, parentContext.getSupportVariant());
    });
  }

  private static Map<MDOType, List<StdAtrInfo>> computeRegistry() {
    Map<MDOType, List<StdAtrInfo>> registry = new ConcurrentHashMap<>();

    registry.put(MDOType.ACCOUNTING_REGISTER,
      List.of(
        StdAtrInfo.ACTIVE,
        StdAtrInfo.LINE_NUMBER,
        StdAtrInfo.PERIOD,
        StdAtrInfo.RECORDER,
        StdAtrInfo.ACCOUNT,
        StdAtrInfo.RECORD_TYPE
      )
    );

    registry.put(MDOType.ACCUMULATION_REGISTER,
      List.of(
        StdAtrInfo.ACTIVE,
        StdAtrInfo.PERIOD,
        StdAtrInfo.LINE_NUMBER,
        StdAtrInfo.RECORDER,
        StdAtrInfo.RECORD_TYPE
      )
    );

    registry.put(MDOType.CALCULATION_REGISTER,
      List.of(
        StdAtrInfo.BEG_OF_ACTION_PERIOD,
        StdAtrInfo.END_OF_ACTION_PERIOD,
        StdAtrInfo.BEG_OF_BASE_PERIOD,
        StdAtrInfo.END_OF_BASE_PERIOD,
        StdAtrInfo.ACTIVE,
        StdAtrInfo.ACTION_PERIOD,
        StdAtrInfo.REGISTRATION_PERIOD,
        StdAtrInfo.LINE_NUMBER,
        StdAtrInfo.RECORDER,
        StdAtrInfo.REVERSING_ENTRY,
        StdAtrInfo.RECORD_TYPE,
        StdAtrInfo.CALCULATION_TYPE
      )
    );

    registry.put(MDOType.INFORMATION_REGISTER,
      List.of(
        StdAtrInfo.ACTIVE,
        StdAtrInfo.PERIOD,
        StdAtrInfo.LINE_NUMBER,
        StdAtrInfo.RECORDER
      )
    );

    registry.put(MDOType.BUSINESS_PROCESS,
      List.of(
        StdAtrInfo.REF,
        StdAtrInfo.DELETION_MARK,
        StdAtrInfo.DATE,
        StdAtrInfo.NUMBER,
        StdAtrInfo.STARTED,
        StdAtrInfo.COMPLETED,
        StdAtrInfo.HEAD_TASK
      )
    );

    registry.put(MDOType.CATALOG,
      List.of(
        StdAtrInfo.PREDEFINED_DATA_NAME,
        StdAtrInfo.PREDEFINED,
        StdAtrInfo.REF,
        StdAtrInfo.DELETION_MARK,
        StdAtrInfo.IS_FOLDER,
        StdAtrInfo.PARENT,
        StdAtrInfo.DESCRIPTION,
        StdAtrInfo.CODE,
        StdAtrInfo.OWNER
      )
    );

    registry.put(MDOType.CHART_OF_ACCOUNTS,
      List.of(
        StdAtrInfo.REF,
        StdAtrInfo.DELETION_MARK,
        StdAtrInfo.DESCRIPTION,
        StdAtrInfo.ORDER,
        StdAtrInfo.PARENT,
        StdAtrInfo.PREDEFINED,
        StdAtrInfo.PREDEFINED_DATA_NAME,
        StdAtrInfo.CODE,
        StdAtrInfo.OFF_BALANCE,
        StdAtrInfo.TYPE
      )
    );

    registry.put(MDOType.CHART_OF_CALCULATION_TYPES,
      List.of(
        StdAtrInfo.REF,
        StdAtrInfo.DELETION_MARK,
        StdAtrInfo.DESCRIPTION,
        StdAtrInfo.ACTION_PERIOD_IS_BASIC,
        StdAtrInfo.PREDEFINED,
        StdAtrInfo.PREDEFINED_DATA_NAME,
        StdAtrInfo.CODE
      )
    );

    registry.put(MDOType.CHART_OF_CHARACTERISTIC_TYPES,
      List.of(
        StdAtrInfo.REF,
        StdAtrInfo.DELETION_MARK,
        StdAtrInfo.DESCRIPTION,
        StdAtrInfo.IS_FOLDER,
        StdAtrInfo.PARENT,
        StdAtrInfo.PREDEFINED,
        StdAtrInfo.PREDEFINED_DATA_NAME,
        StdAtrInfo.CODE,
        StdAtrInfo.VALUE_TYPE
      )
    );

    registry.put(MDOType.DOCUMENT,
      List.of(
        StdAtrInfo.REF,
        StdAtrInfo.DELETION_MARK,
        StdAtrInfo.NUMBER,
        StdAtrInfo.POSTED,
        StdAtrInfo.DATE
      )
    );

    registry.put(MDOType.DOCUMENT_JOURNAL,
      List.of(
        StdAtrInfo.REF,
        StdAtrInfo.DELETION_MARK,
        StdAtrInfo.NUMBER,
        StdAtrInfo.POSTED,
        StdAtrInfo.DATE,
        StdAtrInfo.TYPE
      )
    );

    registry.put(MDOType.EXCHANGE_PLAN,
      List.of(
        StdAtrInfo.REF,
        StdAtrInfo.DELETION_MARK,
        StdAtrInfo.THIS_NODE,
        StdAtrInfo.RECEIVED_NO,
        StdAtrInfo.SENT_NO,
        StdAtrInfo.DESCRIPTION,
        StdAtrInfo.CODE,
        StdAtrInfo.EXCHANGE_DATE
      )
    );

    registry.put(MDOType.ENUM,
      List.of(
        StdAtrInfo.REF,
        StdAtrInfo.ORDER
      )
    );

    registry.put(MDOType.TASK,
      List.of(
        StdAtrInfo.REF,
        StdAtrInfo.EXECUTED,
        StdAtrInfo.DESCRIPTION,
        StdAtrInfo.DELETION_MARK,
        StdAtrInfo.DATE,
        StdAtrInfo.NUMBER,
        StdAtrInfo.ROUTE_POINT,
        StdAtrInfo.BUSINESS_PROCESS
      )
    );

    registry.put(MDOType.TABULAR_SECTION, List.of(StdAtrInfo.LINE_NUMBER));
    return registry;
  }

  private MDReaderContext getOrComputeChildContext(MDReaderContext parentContext, Map<String, MDReaderContext> stdAttributes, String name) {
    var childContext = stdAttributes.get(name);
    if (childContext == null) {
      var collectionName = "attributes";
      var contexts = parentContext.getChildrenContexts().get(collectionName);
      if (contexts == null) {
        collectionName = "Attribute";
        contexts = parentContext.getChildrenContexts().get(collectionName);
        if (contexts == null) {
          contexts = Collections.synchronizedList(new ArrayList<>());
        }
      }

      childContext = new MDReaderContext(parentContext.getCurrentPath(), parentContext.getMdReader());
      childContext.setName(name);
      childContext.setValue("name", name);
      stdAttributes.put(name, childContext);
      contexts.add(childContext);
      parentContext.getChildrenContexts().put(collectionName, contexts);
    }
    return childContext;
  }
}
