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
package com.github._1c_syntax.bsl.reader.common.context.std_attributes;

import com.github._1c_syntax.bsl.reader.common.context.MDReaderContext;
import com.github._1c_syntax.bsl.types.AllowedLength;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.MultiName;
import com.github._1c_syntax.bsl.types.StdAttributeNames;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import com.github._1c_syntax.bsl.types.qualifiers.DateQualifiers;
import com.github._1c_syntax.bsl.types.value.PrimitiveValueType;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 * Вспомогательный класс для хранения описаний стандартных реквизитов
 */
@Value
@Slf4j
public class StdAtrInfo {
  private static final ValueTypeDescription BOOLEAN_TYPE = ValueTypeDescription.create(PrimitiveValueType.BOOLEAN);
  private static final ValueTypeDescription STRING_BIG_TYPE = ValueTypeDescription.createString(0);
  private static final ValueTypeDescription NUMBER_TYPE = ValueTypeDescription.createNumber(5);
  private static final ValueTypeDescription DATETIME_TYPE =
    ValueTypeDescription.create(PrimitiveValueType.DATE, DateQualifiers.create());

  public static final StdAtrInfo PREDEFINED_DATA_NAME
    = new StdAtrInfo(StdAttributeNames.PREDEFINED_DATA_NAME, STRING_BIG_TYPE);
  public static final StdAtrInfo PREDEFINED = new StdAtrInfo(StdAttributeNames.PREDEFINED, BOOLEAN_TYPE);
  public static final StdAtrInfo REF = new StdAtrInfo(StdAttributeNames.REF, StdAtrInfo::computeRef);
  public static final StdAtrInfo DELETION_MARK = new StdAtrInfo(StdAttributeNames.DELETION_MARK, BOOLEAN_TYPE);
  public static final StdAtrInfo IS_FOLDER = new StdAtrInfo(StdAttributeNames.IS_FOLDER, BOOLEAN_TYPE);
  public static final StdAtrInfo PARENT = new StdAtrInfo(StdAttributeNames.PARENT, StdAtrInfo::computeRef);
  public static final StdAtrInfo DESCRIPTION =
    new StdAtrInfo(StdAttributeNames.DESCRIPTION, StdAtrInfo::computeDescription);
  public static final StdAtrInfo CODE = new StdAtrInfo(StdAttributeNames.CODE, StdAtrInfo::computeCode);
  public static final StdAtrInfo OWNER = new StdAtrInfo(StdAttributeNames.OWNER, StdAtrInfo::computeOwner);
  public static final StdAtrInfo LINE_NUMBER = new StdAtrInfo(StdAttributeNames.LINE_NUMBER, NUMBER_TYPE);
  public static final StdAtrInfo ACTIVE = new StdAtrInfo(StdAttributeNames.ACTIVE, BOOLEAN_TYPE);
  public static final StdAtrInfo ORDER = new StdAtrInfo(StdAttributeNames.ORDER, NUMBER_TYPE);
  public static final StdAtrInfo PERIOD = new StdAtrInfo(StdAttributeNames.PERIOD, DATETIME_TYPE);
  public static final StdAtrInfo THIS_NODE = new StdAtrInfo(StdAttributeNames.THIS_NODE, StdAtrInfo::computeRef);
  public static final StdAtrInfo RECEIVED_NO = new StdAtrInfo(StdAttributeNames.RECEIVED_NO, NUMBER_TYPE);
  public static final StdAtrInfo SENT_NO = new StdAtrInfo(StdAttributeNames.SENT_NO, NUMBER_TYPE);
  public static final StdAtrInfo NUMBER = new StdAtrInfo(StdAttributeNames.NUMBER, StdAtrInfo::computeNumber);
  public static final StdAtrInfo END_OF_BASE_PERIOD =
    new StdAtrInfo(StdAttributeNames.END_OF_BASE_PERIOD, DATETIME_TYPE);
  public static final StdAtrInfo BEG_OF_BASE_PERIOD =
    new StdAtrInfo(StdAttributeNames.BEG_OF_BASE_PERIOD, DATETIME_TYPE);
  public static final StdAtrInfo END_OF_ACTION_PERIOD =
    new StdAtrInfo(StdAttributeNames.END_OF_ACTION_PERIOD, DATETIME_TYPE);
  public static final StdAtrInfo BEG_OF_ACTION_PERIOD =
    new StdAtrInfo(StdAttributeNames.BEG_OF_ACTION_PERIOD, DATETIME_TYPE);
  public static final StdAtrInfo ACTION_PERIOD = new StdAtrInfo(StdAttributeNames.ACTION_PERIOD, DATETIME_TYPE);

  public static final StdAtrInfo POSTED = new StdAtrInfo(StdAttributeNames.POSTED, BOOLEAN_TYPE);
  public static final StdAtrInfo DATE = new StdAtrInfo(StdAttributeNames.DATE, DATETIME_TYPE);
  public static final StdAtrInfo ACTION_PERIOD_IS_BASIC =
    new StdAtrInfo(StdAttributeNames.ACTION_PERIOD_IS_BASIC, BOOLEAN_TYPE);
  public static final StdAtrInfo STARTED = new StdAtrInfo(StdAttributeNames.STARTED, BOOLEAN_TYPE);
  public static final StdAtrInfo COMPLETED = new StdAtrInfo(StdAttributeNames.COMPLETED, BOOLEAN_TYPE);
  public static final StdAtrInfo EXECUTED = new StdAtrInfo(StdAttributeNames.EXECUTED, BOOLEAN_TYPE);
  public static final StdAtrInfo OFF_BALANCE = new StdAtrInfo(StdAttributeNames.OFF_BALANCE, BOOLEAN_TYPE);
  public static final StdAtrInfo REGISTRATION_PERIOD
    = new StdAtrInfo(StdAttributeNames.REGISTRATION_PERIOD, DATETIME_TYPE);

  public static final StdAtrInfo RECORD_TYPE
    = new StdAtrInfo(StdAttributeNames.RECORD_TYPE, StdAtrInfo::computeRecordType);
  public static final StdAtrInfo RECORDER = new StdAtrInfo(StdAttributeNames.RECORDER, StdAtrInfo::computeRecorder);
  public static final StdAtrInfo ACCOUNT = new StdAtrInfo(StdAttributeNames.ACCOUNT, StdAtrInfo::computeAccount);
  public static final StdAtrInfo VALUE_TYPE
    = new StdAtrInfo(StdAttributeNames.VALUE_TYPE, StdAtrInfo::computeValueType);
  public static final StdAtrInfo REVERSING_ENTRY = new StdAtrInfo(StdAttributeNames.REVERSING_ENTRY, BOOLEAN_TYPE);
  public static final StdAtrInfo HEAD_TASK = new StdAtrInfo(StdAttributeNames.HEAD_TASK, StdAtrInfo::computeHeadTask);
  public static final StdAtrInfo ROUTE_POINT
    = new StdAtrInfo(StdAttributeNames.ROUTE_POINT, StdAtrInfo::computeRoutePoint);
  public static final StdAtrInfo BUSINESS_PROCESS
    = new StdAtrInfo(StdAttributeNames.BUSINESS_PROCESS, StdAtrInfo::computeBusinessProcess);
  public static final StdAtrInfo TYPE = new StdAtrInfo(StdAttributeNames.TYPE, StdAtrInfo::computeType);
  public static final StdAtrInfo CALCULATION_TYPE
    = new StdAtrInfo(StdAttributeNames.CALCULATION_TYPE, StdAtrInfo::computeCalculationType);
  public static final StdAtrInfo EXCHANGE_DATE = new StdAtrInfo(StdAttributeNames.EXCHANGE_DATE, DATETIME_TYPE);
  public static final StdAtrInfo PERIOD_ADJUSTMENT = new StdAtrInfo(StdAttributeNames.PERIOD_ADJUSTMENT, NUMBER_TYPE);

  private static final Map<String, StdAtrInfo> KEYS = computeKeys();

  MultiName name;
  ValueTypeDescription valueType;
  Function<MDReaderContext, ValueTypeDescription> computeValueType;

  private StdAtrInfo(MultiName name, ValueTypeDescription valueType) {
    this.name = name;
    this.valueType = valueType;
    this.computeValueType = StdAtrInfo::empty; // заглушка
  }

  private StdAtrInfo(MultiName name, Function<MDReaderContext, ValueTypeDescription> computeValueType) {
    this.name = name;
    this.valueType = ValueTypeDescription.EMPTY;
    this.computeValueType = computeValueType;
  }

  public static StdAtrInfo get(String name) {
    return KEYS.get(name.toLowerCase(Locale.ROOT));
  }

  private static ValueTypeDescription empty(MDReaderContext context) {
    LOGGER.warn("Ошибка в формировании описания стандартного реквизита");
    return ValueTypeDescription.EMPTY;
  }

  private static ValueTypeDescription computeRef(MDReaderContext parentContext) {
    return ValueTypeDescription.createRef(parentContext.getMdoType(), parentContext.getName());
  }

  private static ValueTypeDescription computeDescription(MDReaderContext parentContext) {
    var descriptionLength = Integer.parseInt(parentContext.getFromCache("descriptionLength", "100"));
    return ValueTypeDescription.createString(descriptionLength);
  }

  private static ValueTypeDescription computeCode(MDReaderContext parentContext) {
    if ("String".equals(parentContext.getFromCache("codeType", "Number"))) {
      var codeAllowedLength = AllowedLength.valueByName(
        parentContext.getFromCache("codeAllowedLength", "Fixed"));
      var codeLength = Integer.parseInt(parentContext.getFromCache("codeLength", "0"));
      return ValueTypeDescription.createString(codeLength, codeAllowedLength);
    } else {
      var codeLength = Integer.parseInt(parentContext.getFromCache("codeLength", "0"));
      return ValueTypeDescription.createNumber(codeLength);
    }
  }

  private static ValueTypeDescription computeNumber(MDReaderContext parentContext) {
    if ("String".equals(parentContext.getFromCache("numberType", "Number"))) {
      var numberAllowedLength = AllowedLength.valueByName(
        parentContext.getFromCache("numberAllowedLength", "Fixed"));
      var numberLength = Integer.parseInt(parentContext.getFromCache("numberLength", "0"));
      return ValueTypeDescription.createString(numberLength, numberAllowedLength);
    } else {
      var numberLength = Integer.parseInt(parentContext.getFromCache("numberLength", "0"));
      return ValueTypeDescription.createNumber(numberLength);
    }
  }

  private static ValueTypeDescription computeOwner(MDReaderContext parentContext) {
    var owners = parentContext.getFromCache("owners");
    if (owners instanceof MdoReference mdoReference) {
      return ValueTypeDescription.createRef(mdoReference);
    } else if (owners instanceof List<?> list) {
      var mdoRefs = list.stream()
        .filter(MdoReference.class::isInstance)
        .map(MdoReference.class::cast)
        .toList();
      return ValueTypeDescription.createRef(mdoRefs);
    }

    return ValueTypeDescription.EMPTY;
  }

  private static ValueTypeDescription computeRecorder(MDReaderContext parentContext) {
    // todo надо соединить чтение регистратора с объектами, содержащими ссылки на регистры
    return ValueTypeDescription.EMPTY;
  }

  private static ValueTypeDescription computeRecordType(MDReaderContext parentContext) {
    // todo надо понять, что это такое
    return ValueTypeDescription.EMPTY;
  }

  private static ValueTypeDescription computeAccount(MDReaderContext parentContext) {
    var chartOfAccounts = parentContext.getFromCache("ChartOfAccounts", "");
    if (!chartOfAccounts.isEmpty()) {
      var mdoRef = MdoReference.create(chartOfAccounts);
      return ValueTypeDescription.createRef(mdoRef);
    }
    return ValueTypeDescription.EMPTY;
  }

  private static ValueTypeDescription computeValueType(MDReaderContext parentContext) {
    return parentContext.getFromCache("type", ValueTypeDescription.EMPTY);
  }

  private static ValueTypeDescription computeHeadTask(MDReaderContext parentContext) {
    var task = parentContext.getFromCache("Task", MdoReference.EMPTY);
    if (!task.isEmpty()) {
      return ValueTypeDescription.createRef(task);
    }
    return ValueTypeDescription.EMPTY;
  }

  private static ValueTypeDescription computeRoutePoint(MDReaderContext parentContext) {
    // todo поднять, как хранится, вроде как ссылка на схему бизнес-процесса
    return ValueTypeDescription.EMPTY;
  }

  private static ValueTypeDescription computeBusinessProcess(MDReaderContext parentContext) {
    // todo поднять, как хранится, вроде как ссылка на схему бизнес-процесса
    return ValueTypeDescription.EMPTY;
  }

  private static ValueTypeDescription computeType(MDReaderContext parentContext) {
    // todo надо понять, что это такое
    return ValueTypeDescription.EMPTY;
  }

  private static ValueTypeDescription computeCalculationType(MDReaderContext parentContext) {
    // todo надо понять, что это такое
    return ValueTypeDescription.EMPTY;
  }

  private static Map<String, StdAtrInfo> computeKeys() {
    return Map.ofEntries(Map.entry(PREDEFINED_DATA_NAME.name.getEn().toLowerCase(Locale.ROOT), PREDEFINED_DATA_NAME),
      Map.entry(PREDEFINED.name.getEn().toLowerCase(Locale.ROOT), PREDEFINED),
      Map.entry(REF.name.getEn().toLowerCase(Locale.ROOT), REF),
      Map.entry(DELETION_MARK.name.getEn().toLowerCase(Locale.ROOT), DELETION_MARK),
      Map.entry(IS_FOLDER.name.getEn().toLowerCase(Locale.ROOT), IS_FOLDER),
      Map.entry(PARENT.name.getEn().toLowerCase(Locale.ROOT), PARENT),
      Map.entry(DESCRIPTION.name.getEn().toLowerCase(Locale.ROOT), DESCRIPTION),
      Map.entry(CODE.name.getEn().toLowerCase(Locale.ROOT), CODE),
      Map.entry(OWNER.name.getEn().toLowerCase(Locale.ROOT), OWNER),
      Map.entry(LINE_NUMBER.name.getEn().toLowerCase(Locale.ROOT), LINE_NUMBER),
      Map.entry(ACTIVE.name.getEn().toLowerCase(Locale.ROOT), ACTIVE),
      Map.entry(ORDER.name.getEn().toLowerCase(Locale.ROOT), ORDER),
      Map.entry(PERIOD.name.getEn().toLowerCase(Locale.ROOT), PERIOD),
      Map.entry(THIS_NODE.name.getEn().toLowerCase(Locale.ROOT), THIS_NODE),
      Map.entry(RECEIVED_NO.name.getEn().toLowerCase(Locale.ROOT), RECEIVED_NO),
      Map.entry(SENT_NO.name.getEn().toLowerCase(Locale.ROOT), SENT_NO),
      Map.entry(NUMBER.name.getEn().toLowerCase(Locale.ROOT), NUMBER),
      Map.entry(END_OF_BASE_PERIOD.name.getEn().toLowerCase(Locale.ROOT), END_OF_BASE_PERIOD),
      Map.entry(BEG_OF_BASE_PERIOD.name.getEn().toLowerCase(Locale.ROOT), BEG_OF_BASE_PERIOD),
      Map.entry(END_OF_ACTION_PERIOD.name.getEn().toLowerCase(Locale.ROOT), END_OF_ACTION_PERIOD),
      Map.entry(BEG_OF_ACTION_PERIOD.name.getEn().toLowerCase(Locale.ROOT), BEG_OF_ACTION_PERIOD),
      Map.entry(ACTION_PERIOD.name.getEn().toLowerCase(Locale.ROOT), ACTION_PERIOD),
      Map.entry(POSTED.name.getEn().toLowerCase(Locale.ROOT), POSTED),
      Map.entry(DATE.name.getEn().toLowerCase(Locale.ROOT), DATE),
      Map.entry(ACTION_PERIOD_IS_BASIC.name.getEn().toLowerCase(Locale.ROOT), ACTION_PERIOD_IS_BASIC),
      Map.entry(STARTED.name.getEn().toLowerCase(Locale.ROOT), STARTED),
      Map.entry(COMPLETED.name.getEn().toLowerCase(Locale.ROOT), COMPLETED),
      Map.entry(EXECUTED.name.getEn().toLowerCase(Locale.ROOT), EXECUTED),
      Map.entry(OFF_BALANCE.name.getEn().toLowerCase(Locale.ROOT), OFF_BALANCE),
      Map.entry(REGISTRATION_PERIOD.name.getEn().toLowerCase(Locale.ROOT), REGISTRATION_PERIOD),
      Map.entry(RECORD_TYPE.name.getEn().toLowerCase(Locale.ROOT), RECORD_TYPE),
      Map.entry(RECORDER.name.getEn().toLowerCase(Locale.ROOT), RECORDER),
      Map.entry(ACCOUNT.name.getEn().toLowerCase(Locale.ROOT), ACCOUNT),
      Map.entry(VALUE_TYPE.name.getEn().toLowerCase(Locale.ROOT), VALUE_TYPE),
      Map.entry(REVERSING_ENTRY.name.getEn().toLowerCase(Locale.ROOT), REVERSING_ENTRY),
      Map.entry(HEAD_TASK.name.getEn().toLowerCase(Locale.ROOT), HEAD_TASK),
      Map.entry(ROUTE_POINT.name.getEn().toLowerCase(Locale.ROOT), ROUTE_POINT),
      Map.entry(BUSINESS_PROCESS.name.getEn().toLowerCase(Locale.ROOT), BUSINESS_PROCESS),
      Map.entry(TYPE.name.getEn().toLowerCase(Locale.ROOT), TYPE),
      Map.entry(CALCULATION_TYPE.name.getEn().toLowerCase(Locale.ROOT), CALCULATION_TYPE),
      Map.entry(EXCHANGE_DATE.name.getEn().toLowerCase(Locale.ROOT), EXCHANGE_DATE),
      Map.entry(PERIOD_ADJUSTMENT.name.getEn().toLowerCase(Locale.ROOT), PERIOD_ADJUSTMENT));
  }
}
