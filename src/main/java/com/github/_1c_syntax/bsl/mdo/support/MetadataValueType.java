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
package com.github._1c_syntax.bsl.mdo.support;

import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ValueType;
import com.github._1c_syntax.bsl.types.ValueTypeVariant;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Типы данных, построенные на метаданных
 */
public final class MetadataValueType implements ValueType {
  public static final MetadataValueType ACCOUNTING_REGISTER_MANAGER = createManager(MDOType.ACCOUNTING_REGISTER);
  public static final MetadataValueType ACCOUNTING_REGISTER_REC_SET = createRecSet(MDOType.ACCOUNTING_REGISTER);
  public static final MetadataValueType ACCUMULATION_REGISTER_MANAGER = createManager(MDOType.ACCUMULATION_REGISTER);
  public static final MetadataValueType ACCUMULATION_REGISTER_REC_SET = createRecSet(MDOType.ACCUMULATION_REGISTER);
  public static final MetadataValueType BUSINESS_PROCESS_MANAGER = createManager(MDOType.BUSINESS_PROCESS);
  public static final MetadataValueType BUSINESS_PROCESS_OBJECT = createObj(MDOType.BUSINESS_PROCESS);
  public static final MetadataValueType BUSINESS_PROCESS_REF = createRef(MDOType.BUSINESS_PROCESS);
  public static final MetadataValueType CALCULATION_REGISTER_MANAGER = createManager(MDOType.CALCULATION_REGISTER);
  public static final MetadataValueType CALCULATION_REGISTER_REC_SET = createRecSet(MDOType.CALCULATION_REGISTER);
  public static final MetadataValueType CATALOG_MANAGER = createManager(MDOType.CATALOG);
  public static final MetadataValueType CATALOG_OBJECT = createObj(MDOType.CATALOG);
  public static final MetadataValueType CATALOG_REF = createRef(MDOType.CATALOG);
  public static final MetadataValueType CHART_OF_ACCOUNTS_MANAGER = createManager(MDOType.CHART_OF_ACCOUNTS);
  public static final MetadataValueType CHART_OF_ACCOUNTS_OBJECT = createObj(MDOType.CHART_OF_ACCOUNTS);
  public static final MetadataValueType CHART_OF_ACCOUNTS_REF = createRef(MDOType.CHART_OF_ACCOUNTS);
  public static final MetadataValueType CHART_OF_CALCULATION_TYPES_MANAGER = createManager(MDOType.CHART_OF_CALCULATION_TYPES);
  public static final MetadataValueType CHART_OF_CALCULATION_TYPES_OBJECT = createObj(MDOType.CHART_OF_CALCULATION_TYPES);
  public static final MetadataValueType CHART_OF_CALCULATION_TYPES_REF = createRef(MDOType.CHART_OF_CALCULATION_TYPES);
  public static final MetadataValueType CHART_OF_CHARACTERISTIC_TYPES_MANAGER = createManager(MDOType.CHART_OF_CHARACTERISTIC_TYPES);
  public static final MetadataValueType CHART_OF_CHARACTERISTIC_TYPES_OBJECT = createObj(MDOType.CHART_OF_CHARACTERISTIC_TYPES);
  public static final MetadataValueType CHART_OF_CHARACTERISTIC_TYPES_REF = createRef(MDOType.CHART_OF_CHARACTERISTIC_TYPES);
  public static final MetadataValueType DATA_PROCESSOR_MANAGER = createManager(MDOType.DATA_PROCESSOR);
  public static final MetadataValueType DATA_PROCESSOR_OBJECT = createObj(MDOType.DATA_PROCESSOR);
  public static final MetadataValueType DOCUMENT_JOURNAL_MANAGER = createManager(MDOType.DOCUMENT_JOURNAL);
  public static final MetadataValueType DOCUMENT_MANAGER = createManager(MDOType.DOCUMENT);
  public static final MetadataValueType DOCUMENT_OBJECT = createObj(MDOType.DOCUMENT);
  public static final MetadataValueType DOCUMENT_REF = createRef(MDOType.DOCUMENT);
  public static final MetadataValueType ENUM_REF = createRef(MDOType.ENUM);
  public static final MetadataValueType EXCHANGE_PLAN_MANAGER = createManager(MDOType.EXCHANGE_PLAN);
  public static final MetadataValueType EXCHANGE_PLAN_OBJECT = createObj(MDOType.EXCHANGE_PLAN);
  public static final MetadataValueType EXCHANGE_PLAN_REF = createRef(MDOType.EXCHANGE_PLAN);
  public static final MetadataValueType EXTERNAL_DATA_PROCESSOR_OBJECT = createObj(MDOType.EXTERNAL_DATA_PROCESSOR);
  public static final MetadataValueType EXTERNAL_REPORT_OBJECT = createObj(MDOType.EXTERNAL_REPORT);
  public static final MetadataValueType INFORMATION_REGISTER_MANAGER = createManager(MDOType.INFORMATION_REGISTER);
  public static final MetadataValueType INFORMATION_REGISTER_REC_SET = createRecSet(MDOType.INFORMATION_REGISTER);
  public static final MetadataValueType RECALCULATION_REC_SET = createRecSet(MDOType.RECALCULATION);
  public static final MetadataValueType REPORT_MANAGER = createManager(MDOType.REPORT);
  public static final MetadataValueType REPORT_OBJECT = createObj(MDOType.REPORT);
  public static final MetadataValueType SEQUENCE_REC_SET = createRecSet(MDOType.SEQUENCE);
  public static final MetadataValueType TASK_MANAGER = createManager(MDOType.TASK);
  public static final MetadataValueType TASK_OBJECT = createObj(MDOType.TASK);
  public static final MetadataValueType TASK_REF = createRef(MDOType.TASK);

  public static final MetadataValueType CONSTANT_VALUE_MANAGER = create(MDOType.CONSTANT, "ValueManager", "МенеджерЗначения");
  public static final MetadataValueType CONSTANTS_SET =
    new MetadataValueType(MDOType.CONSTANT,
      MDOType.CONSTANT.getGroupName() + "Set",
      MDOType.CONSTANT.getGroupNameRu() + "Набор",
      true);

  public static final MetadataValueType BUSINESS_PROCESS_ROUTE_POINT_REF =
    new MetadataValueType(MDOType.BUSINESS_PROCESS,
      MDOType.BUSINESS_PROCESS.getName() + "RoutePointRef",
      "ТочкаМаршрутаБизнесПроцессаСсылка",
      true);

  private static final List<ValueType> BUILTIN_TYPES = List.of(
    ACCOUNTING_REGISTER_MANAGER,
    ACCOUNTING_REGISTER_REC_SET,
    ACCUMULATION_REGISTER_MANAGER,
    ACCUMULATION_REGISTER_REC_SET,
    BUSINESS_PROCESS_MANAGER,
    BUSINESS_PROCESS_OBJECT,
    BUSINESS_PROCESS_REF,
    BUSINESS_PROCESS_ROUTE_POINT_REF,
    CALCULATION_REGISTER_MANAGER,
    CALCULATION_REGISTER_REC_SET,
    CATALOG_MANAGER,
    CATALOG_OBJECT,
    CATALOG_REF,
    CHART_OF_ACCOUNTS_MANAGER,
    CHART_OF_ACCOUNTS_OBJECT,
    CHART_OF_ACCOUNTS_REF,
    CHART_OF_CALCULATION_TYPES_MANAGER,
    CHART_OF_CALCULATION_TYPES_OBJECT,
    CHART_OF_CALCULATION_TYPES_REF,
    CHART_OF_CHARACTERISTIC_TYPES_MANAGER,
    CHART_OF_CHARACTERISTIC_TYPES_OBJECT,
    CHART_OF_CHARACTERISTIC_TYPES_REF,
    CONSTANT_VALUE_MANAGER,
    CONSTANTS_SET,
    DATA_PROCESSOR_MANAGER,
    DATA_PROCESSOR_OBJECT,
    DOCUMENT_JOURNAL_MANAGER,
    DOCUMENT_MANAGER,
    DOCUMENT_OBJECT,
    DOCUMENT_REF,
    ENUM_REF,
    EXCHANGE_PLAN_MANAGER,
    EXCHANGE_PLAN_OBJECT,
    EXCHANGE_PLAN_REF,
    EXTERNAL_DATA_PROCESSOR_OBJECT,
    EXTERNAL_REPORT_OBJECT,
    INFORMATION_REGISTER_MANAGER,
    INFORMATION_REGISTER_REC_SET,
    RECALCULATION_REC_SET,
    REPORT_MANAGER,
    REPORT_OBJECT,
    SEQUENCE_REC_SET,
    TASK_MANAGER,
    TASK_OBJECT,
    TASK_REF
  );

  private static final Map<String, Variant> ALL_VARIANTS = computeAllProvidedTypesVariants();
  private static final Map<String, MetadataValueType> PROVIDED_TYPES = new ConcurrentHashMap<>();

  @Getter
  private final String name;
  @Getter
  private final String nameRu;

  /**
   * Признак составного типа (например DocumentRef)
   */
  @Getter
  private final boolean composite;

  /**
   * Вид метаданных, к которому относится тип значения
   */
  @Getter
  private final MDOType kind;

  private MetadataValueType(MDOType kind, String name, String nameRu, boolean composite) {
    this.kind = kind;
    this.name = name;
    this.nameRu = nameRu;
    this.composite = composite;
  }

  /**
   * Производит определение типа по переданной строке
   *
   * @param name Строковое представление типа
   * @return Определенный тип
   */
  @Nullable
  public static MetadataValueType fromString(String name) {
    var type = PROVIDED_TYPES.get(name.toLowerCase(Locale.ROOT));
    if (type != null) {
      return type;
    }

    var posDot = name.indexOf(".");
    if (posDot > 0) {
      var key = name.substring(0, posDot);
      var variant = ALL_VARIANTS.get(key.toLowerCase(Locale.ROOT));
      if (variant != null) {
        type = new MetadataValueType(variant.kind(), name, variant.nameRu() + name.substring(posDot), false);
        PROVIDED_TYPES.put(name.toLowerCase(Locale.ROOT), type);
      }
    }
    return type;
  }

  @Override
  @NonNull
  public ValueTypeVariant getVariant() {
    return ValueTypeVariant.METADATA;
  }

  /**
   * Коллекция встроенных типов
   *
   * @return Список встроенных типов
   */
  public static List<? extends ValueType> builtinTypes() {
    return BUILTIN_TYPES;
  }

  private static MetadataValueType createRef(MDOType mdoType) {
    return create(mdoType, "Ref", "Ссылка");
  }

  private static MetadataValueType createObj(MDOType mdoType) {
    return create(mdoType, "Object", "Объект");
  }

  private static MetadataValueType createRecSet(MDOType mdoType) {
    return create(mdoType, "RecordSet", "НаборЗаписей");
  }

  private static MetadataValueType createManager(MDOType mdoType) {
    return create(mdoType, "Manager", "Менеджер");
  }

  private static MetadataValueType create(MDOType mdoType, String name, String nameRu) {
    return new MetadataValueType(mdoType, mdoType.getName() + name, mdoType.getNameRu() + nameRu, true);
  }

  private static Map<String, Variant> computeAllProvidedTypesVariants() {
    Map<String, Variant> variants = new ConcurrentHashMap<>();
    builtinTypes().forEach((ValueType valueType) ->
      variants.put(valueType.getName().toLowerCase(Locale.ROOT),
        new Variant(valueType.getNameRu(), ((MetadataValueType) valueType).getKind()
        ))
    );

    variants.put(MDOType.DEFINED_TYPE.getName().toLowerCase(Locale.ROOT),
      new Variant(MDOType.DEFINED_TYPE.getNameRu(), MDOType.DEFINED_TYPE));
    variants.put("Characteristic".toLowerCase(Locale.ROOT),
      new Variant("Характеристика", MDOType.CHART_OF_CHARACTERISTIC_TYPES));

    addVariantRecManager(variants, MDOType.INFORMATION_REGISTER);
    addVariantList(variants, MDOType.INFORMATION_REGISTER);
    addVariantList(variants, MDOType.ENUM);

    var mdoType = MDOType.EXTERNAL_DATA_SOURCE;
    addVariant(variants, mdoType, "TableRef", "ТаблицаСсылка");
    addVariant(variants, mdoType, "TableObject", "ТаблицаОбъект");
    addVariant(variants, mdoType, "TableRecordManager", "ТаблицаМенеджерЗаписи");

    return variants;
  }

  private static void addVariantRecManager(Map<String, Variant> variants, MDOType mdoType) {
    addVariant(variants, mdoType, "RecordManager", "МенеджерЗаписи");
  }

  private static void addVariantList(Map<String, Variant> variants, MDOType mdoType) {
    addVariant(variants, mdoType, "List", "Список");
  }

  private static void addVariant(Map<String, Variant> variants, MDOType mdoType, String name, String nameRu) {
    variants.put((mdoType.getName() + name).toLowerCase(Locale.ROOT),
      new Variant(mdoType.getNameRu() + nameRu, mdoType));
  }

  private record Variant(String nameRu, MDOType kind) {
  }
}
