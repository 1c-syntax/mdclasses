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
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import com.github._1c_syntax.bsl.types.qualifiers.DateQualifiers;
import com.github._1c_syntax.bsl.types.value.PrimitiveValueType;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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

  public static final StdAtrInfo PREDEFINED_DATA_NAME =
    new StdAtrInfo("PredefinedDataName", "ИмяПредопределенныхДанных", STRING_BIG_TYPE);
  public static final StdAtrInfo PREDEFINED = new StdAtrInfo("Predefined", "Предопределенный", BOOLEAN_TYPE);
  public static final StdAtrInfo REF = new StdAtrInfo("Ref", "Ссылка", StdAtrInfo::computeRef);
  public static final StdAtrInfo DELETION_MARK = new StdAtrInfo("DeletionMark", "ПометкаУдаления", BOOLEAN_TYPE);
  public static final StdAtrInfo IS_FOLDER = new StdAtrInfo("IsFolder", "ЭтоГруппа", BOOLEAN_TYPE);
  public static final StdAtrInfo PARENT = new StdAtrInfo("Parent", "Родитель", StdAtrInfo::computeRef);
  public static final StdAtrInfo DESCRIPTION =
    new StdAtrInfo("Description", "Наименование", StdAtrInfo::computeDescription);
  public static final StdAtrInfo CODE = new StdAtrInfo("Code", "Код", StdAtrInfo::computeCode);
  public static final StdAtrInfo OWNER = new StdAtrInfo("Owner", "Владелец", StdAtrInfo::computeOwner);
  public static final StdAtrInfo LINE_NUMBER = new StdAtrInfo("LineNumber", "НомерСтроки", NUMBER_TYPE);
  public static final StdAtrInfo ACTIVE = new StdAtrInfo("Active", "Активность", BOOLEAN_TYPE);
  public static final StdAtrInfo ORDER = new StdAtrInfo("Order", "Порядок", NUMBER_TYPE);
  public static final StdAtrInfo PERIOD = new StdAtrInfo("Period", "Период", DATETIME_TYPE);
  public static final StdAtrInfo THIS_NODE = new StdAtrInfo("ThisNode", "ЭтотУзел", StdAtrInfo::computeRef);
  public static final StdAtrInfo RECEIVED_NO = new StdAtrInfo("ReceivedNo", "НомерПринятого", NUMBER_TYPE);
  public static final StdAtrInfo SENT_NO = new StdAtrInfo("SentNo", "НомерОтправленного", NUMBER_TYPE);
  public static final StdAtrInfo NUMBER = new StdAtrInfo("Number", "Номер", StdAtrInfo::computeNumber);
  public static final StdAtrInfo END_OF_BASE_PERIOD =
    new StdAtrInfo("EndOfBasePeriod", "БазовыйПериодКонец", DATETIME_TYPE);
  public static final StdAtrInfo BEG_OF_BASE_PERIOD =
    new StdAtrInfo("BegOfBasePeriod", "БазовыйПериодНачало", DATETIME_TYPE);
  public static final StdAtrInfo END_OF_ACTION_PERIOD =
    new StdAtrInfo("EndOfActionPeriod", "ПериодДействияКонец", DATETIME_TYPE);
  public static final StdAtrInfo BEG_OF_ACTION_PERIOD =
    new StdAtrInfo("BegOfActionPeriod", "ПериодДействияНачало", DATETIME_TYPE);
  public static final StdAtrInfo ACTION_PERIOD = new StdAtrInfo("ActionPeriod", "ПериодДействия", DATETIME_TYPE);

  public static final StdAtrInfo POSTED = new StdAtrInfo("Posted", "Проведен", BOOLEAN_TYPE);
  public static final StdAtrInfo DATE = new StdAtrInfo("Date", "Дата", DATETIME_TYPE);
  public static final StdAtrInfo ACTION_PERIOD_IS_BASIC =
    new StdAtrInfo("ActionPeriodIsBasic", "ПериодДействияБазовый", BOOLEAN_TYPE);
  public static final StdAtrInfo STARTED = new StdAtrInfo("Started", "Стартован", BOOLEAN_TYPE);
  public static final StdAtrInfo COMPLETED = new StdAtrInfo("Completed", "Завершен", BOOLEAN_TYPE);
  public static final StdAtrInfo EXECUTED = new StdAtrInfo("Executed", "Выполнена", BOOLEAN_TYPE);
  public static final StdAtrInfo OFF_BALANCE = new StdAtrInfo("OffBalance", "Забалансовый", BOOLEAN_TYPE);
  public static final StdAtrInfo REGISTRATION_PERIOD = new StdAtrInfo("RegistrationPeriod", "ПериодРегистрации", DATETIME_TYPE);

  public static final StdAtrInfo RECORD_TYPE = new StdAtrInfo("RecordType", "ВидДвижения", StdAtrInfo::computeRecordType);
  public static final StdAtrInfo RECORDER = new StdAtrInfo("Recorder", "Регистратор", StdAtrInfo::computeRecorder);
  public static final StdAtrInfo ACCOUNT = new StdAtrInfo("Account", "Счет", StdAtrInfo::computeAccount);
  public static final StdAtrInfo VALUE_TYPE = new StdAtrInfo("ValueType", "ТипЗначения", StdAtrInfo::computeValueType);
  public static final StdAtrInfo REVERSING_ENTRY = new StdAtrInfo("ReversingEntry", "Сторно", BOOLEAN_TYPE);
  public static final StdAtrInfo HEAD_TASK = new StdAtrInfo("HeadTask", "ВедущаяЗадача", StdAtrInfo::computeHeadTask);
  public static final StdAtrInfo ROUTE_POINT = new StdAtrInfo("RoutePoint", "ТочкаМаршрута", StdAtrInfo::computeRoutePoint);
  public static final StdAtrInfo BUSINESS_PROCESS = new StdAtrInfo("BusinessProcess", "БизнесПроцесс", StdAtrInfo::computeBusinessProcess);
  public static final StdAtrInfo TYPE = new StdAtrInfo("Type", "Тип", StdAtrInfo::computeType);
  public static final StdAtrInfo CALCULATION_TYPE = new StdAtrInfo("CalculationType", "ВидРасчета", StdAtrInfo::computeCalculationType);
  public static final StdAtrInfo EXCHANGE_DATE = new StdAtrInfo("ExchangeDate", "ДатаОбмена", DATETIME_TYPE);
  public static final StdAtrInfo PERIOD_ADJUSTMENT = new StdAtrInfo("PeriodAdjustment", "УточнениеПериода", NUMBER_TYPE);

  String nameEn;
  String nameRu;
  ValueTypeDescription valueType;
  Function<MDReaderContext, ValueTypeDescription> computeValueType;

  private StdAtrInfo(String nameEn, String nameRu, ValueTypeDescription valueType) {
    this.nameEn = nameEn;
    this.nameRu = nameRu;
    this.valueType = valueType;
    this.computeValueType = StdAtrInfo::empty; // заглушка
  }

  private StdAtrInfo(String nameEn,
                     String nameRu,
                     Function<MDReaderContext, ValueTypeDescription> computeValueType) {
    this.nameEn = nameEn;
    this.nameRu = nameRu;
    this.valueType = ValueTypeDescription.EMPTY;
    this.computeValueType = computeValueType;
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
        parentContext.getFromCache("codeAllowedLength", "Variable"));
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
        parentContext.getFromCache("numberAllowedLength", "Variable"));
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
}
