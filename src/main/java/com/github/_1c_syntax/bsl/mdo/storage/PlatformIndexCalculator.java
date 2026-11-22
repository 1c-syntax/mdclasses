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
package com.github._1c_syntax.bsl.mdo.storage;

import com.github._1c_syntax.bsl.mdclasses.CF;
import com.github._1c_syntax.bsl.mdo.AccountingRegister;
import com.github._1c_syntax.bsl.mdo.AccumulationRegister;
import com.github._1c_syntax.bsl.mdo.Attribute;
import com.github._1c_syntax.bsl.mdo.BusinessProcess;
import com.github._1c_syntax.bsl.mdo.CalculationRegister;
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.ChartOfAccounts;
import com.github._1c_syntax.bsl.mdo.ChartOfCalculationTypes;
import com.github._1c_syntax.bsl.mdo.ChartOfCharacteristicTypes;
import com.github._1c_syntax.bsl.mdo.CommonAttribute;
import com.github._1c_syntax.bsl.mdo.Document;
import com.github._1c_syntax.bsl.mdo.DocumentJournal;
import com.github._1c_syntax.bsl.mdo.Enum;
import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.InformationRegister;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Register;
import com.github._1c_syntax.bsl.mdo.Sequence;
import com.github._1c_syntax.bsl.mdo.TabularSection;
import com.github._1c_syntax.bsl.mdo.Task;
import com.github._1c_syntax.bsl.mdo.children.Dimension;
import com.github._1c_syntax.bsl.mdo.support.CommonAttributeSeparatedDataUse;
import com.github._1c_syntax.bsl.mdo.support.DataSeparation;
import com.github._1c_syntax.bsl.mdo.support.DefaultPresentation;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.InformationRegisterPeriodicity;
import com.github._1c_syntax.bsl.mdo.support.RegisterWriteMode;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.MultiName;
import com.github._1c_syntax.bsl.types.StdAttributeNames;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github._1c_syntax.bsl.mdo.storage.PlatformIndex.clusteredIndex;
import static com.github._1c_syntax.bsl.mdo.storage.PlatformIndex.createIndex;
import static com.github._1c_syntax.bsl.mdo.storage.PlatformIndex.index;

/**
 * Калькулятор платформенных индексов таблиц базы данных объектов метаданных.
 * Реализует правила из {@code docs/ru/platform-indexes.md}.
 */
public final class PlatformIndexCalculator {

  private static final String TABULAR_SECTION = ".TabularSection.";

  private PlatformIndexCalculator() {
  }

  /**
   * Рассчитывает платформенные индексы объекта метаданных: основной таблицы
   * и таблиц его табличных частей.
   *
   * @param mdo           объект метаданных
   * @param configuration конфигурация-владелец
   * @return список индексов, таблица каждого индекса указана в самом индексе
   */
  public static List<PlatformIndex> computeIndexes(MD mdo, CF configuration) {
    var result = new ArrayList<PlatformIndex>();
    switch (mdo) {
      case Catalog catalog -> result.addAll(catalogIndexes(catalog, configuration));
      case Document document -> result.addAll(documentIndexes(document, configuration));
      case DocumentJournal journal -> result.addAll(documentJournalIndexes(journal, configuration));
      case ChartOfCharacteristicTypes chartOfCharacteristicTypes ->
        result.addAll(chartOfCharacteristicTypesIndexes(chartOfCharacteristicTypes, configuration));
      case ChartOfAccounts chartOfAccounts -> result.addAll(chartOfAccountsIndexes(chartOfAccounts, configuration));
      case ChartOfCalculationTypes chartOfCalculationTypes ->
        result.addAll(referenceBasicIndexes(chartOfCalculationTypes,
          chartOfCalculationTypes.getAttributes(), configuration));
      case ExchangePlan exchangePlan ->
        result.addAll(referenceBasicIndexes(exchangePlan, exchangePlan.getAttributes(), configuration));
      case Enum enumValue -> result.addAll(enumIndexes(enumValue, configuration));
      case BusinessProcess businessProcess -> result.addAll(businessProcessIndexes(businessProcess, configuration));
      case Task task -> result.addAll(taskIndexes(task, configuration));
      case InformationRegister informationRegister ->
        result.addAll(informationRegisterIndexes(informationRegister, configuration));
      case AccumulationRegister accumulationRegister ->
        result.addAll(turnoverRegisterIndexes(accumulationRegister, configuration));
      case AccountingRegister accountingRegister ->
        result.addAll(accountingRegisterIndexes(accountingRegister, configuration));
      case CalculationRegister calculationRegister ->
        result.addAll(calculationRegisterIndexes(calculationRegister, configuration));
      case Sequence sequence -> result.addAll(sequenceIndexes(sequence, configuration));
      case TabularSection tabularSection -> configuration
        .findChild(tabularSection.getOwner())
        .ifPresent(md -> result.addAll(tabularSectionIndexes(tabularSection, md, configuration)));
      default -> {
      }
    }
    return List.copyOf(result);
  }

  // Справочник

  private static List<PlatformIndex> catalogIndexes(Catalog catalog, CF configuration) {
    var table = catalog.getMdoReference();
    var sep = separators(catalog, configuration);
    var ref = stdAttribute(catalog, StdAttributeNames.REF);
    var code = stdAttribute(catalog, StdAttributeNames.CODE);
    var description = stdAttribute(catalog, StdAttributeNames.DESCRIPTION);
    var subordinate = !catalog.getOwners().isEmpty();
    var hierarchical = catalog.isHierarchical();
    var additionalOrderField =
      additionalOrderField(catalog, catalog.getCodeLength(), catalog.getDescriptionLength(),
        catalog.getDefaultPresentation());

    var result = new ArrayList<PlatformIndex>();

    // Правило 1 - Ссылка (кластерный)
    result.add(clusteredIndex(table, sep, ref));

    // Правило 2 - Код + Ссылка
    if (catalog.getCodeLength() != 0) {
      result.add(index(table, sep, code, ref));
    }

    // Правило 3 - Наименование + Ссылка
    if (catalog.getDescriptionLength() != 0) {
      result.add(index(table, sep, description, ref));
    }

    // Правила 4-6 - реквизиты
    result.addAll(attributeIndexes(catalog.getAttributes(), table, sep, additionalOrderField, ref));

    // Правило 7 - критерии отбора
    result.addAll(filterCriterionIndexes(configuration, table, sep));

    var hierarchyTail = hierarchyTail(catalog);

    if (subordinate) {
      var ownerTail = List.of(stdAttribute(catalog, StdAttributeNames.OWNER));

      // Правила 9-10 - Владелец [+ Код] + Ссылка
      if (catalog.getCodeLength() == 0) {
        result.add(index(table, sep, withTail(ownerTail, ref)));
      } else {
        result.add(index(table, sep, withTail(ownerTail, code, ref)));
      }

      // Правило 11 - Владелец + Наименование + Ссылка
      if (catalog.getDescriptionLength() != 0) {
        result.add(index(table, sep, withTail(ownerTail, description, ref)));
      }

      // Правила 12-14 - Владелец + Реквизит [+ доп. упорядочивание] + Ссылка
      result.addAll(prefixedAttributeIndexes(catalog.getAttributes(), table, sep,
        ownerTail, additionalOrderField, ref));

      if (hierarchical) {
        var ownerHierarchyTail = withTail(ownerTail, hierarchyTail);
        // Правила 21-23
        appendLengthIndexes(result, catalog, table, sep, ownerHierarchyTail, ref, code, description);

        // Правила 24-26
        result.addAll(prefixedAttributeIndexes(catalog.getAttributes(), table, sep,
          ownerHierarchyTail, additionalOrderField, ref));
      }
    } else if (hierarchical) {
      // Правила 15-17
      appendLengthIndexes(result, catalog, table, sep, hierarchyTail, ref, code, description);

      // Правила 18-20
      result.addAll(prefixedAttributeIndexes(catalog.getAttributes(), table, sep,
        hierarchyTail, additionalOrderField, ref));
    }
    return result;
  }

  // Документ

  private static List<PlatformIndex> documentIndexes(Document document, CF configuration) {
    var table = document.getMdoReference();
    var sep = separators(document, configuration);
    var ref = stdAttribute(document, StdAttributeNames.REF);
    var date = stdAttribute(document, StdAttributeNames.DATE);
    var result = new ArrayList<PlatformIndex>();

    // Правила 1-3 - Ссылка (кластерный), Дата + Ссылка, Номер + Ссылка
    baseRefDateNumberIndexes(document, table, sep, ref, date, document.getNumberLength(), result);

    // Правила 4-5 - реквизиты
    result.addAll(attributeIndexes(document.getAttributes(), table, sep, date, ref));

    // Правило 6 - критерии отбора
    result.addAll(filterCriterionIndexes(configuration, table, sep));
    return result;
  }

  // Журнал документов

  private static List<PlatformIndex> documentJournalIndexes(DocumentJournal journal, CF configuration) {
    var table = journal.getMdoReference();
    var sep = separators(journal, configuration);
    var ref = stdAttribute(journal, StdAttributeNames.REF);
    var date = stdAttribute(journal, StdAttributeNames.DATE);
    var result = new ArrayList<PlatformIndex>();

    // Правило 1 - Ссылка (кластерный)
    result.add(clusteredIndex(table, sep, ref));

    // Правило 2 - Дата + Ссылка
    result.add(index(table, sep, date, ref));

    // Правила 3-4 - графы
    result.addAll(attributeIndexes(journal.getColumns(), table, sep, date, ref));
    return result;
  }

  // План видов характеристик

  private static List<PlatformIndex> chartOfCharacteristicTypesIndexes(
    ChartOfCharacteristicTypes chart, CF configuration) {
    var table = chart.getMdoReference();
    var sep = separators(chart, configuration);
    var ref = stdAttribute(chart, StdAttributeNames.REF);
    var code = stdAttribute(chart, StdAttributeNames.CODE);
    var description = stdAttribute(chart, StdAttributeNames.DESCRIPTION);
    var additionalOrderField =
      additionalOrderField(chart, chart.getCodeLength(), chart.getDescriptionLength(),
        chart.getDefaultPresentation());
    var result = new ArrayList<PlatformIndex>();

    // Правило 1 - Ссылка (кластерный)
    result.add(clusteredIndex(table, sep, ref));

    // Правило 2 - Код + Ссылка
    result.add(index(table, sep, code, ref));

    // Правило 3 - Наименование + Ссылка
    result.add(index(table, sep, description, ref));

    // Правила 4-6 - реквизиты
    result.addAll(attributeIndexes(chart.getAttributes(), table, sep, additionalOrderField, ref));

    // Правило 7 - критерии отбора
    result.addAll(filterCriterionIndexes(configuration, table, sep));

    if (chart.isHierarchical()) {
      var hierarchyTail = hierarchyTail(chart);
      // Правило 9 - Родитель [+ ЭтоГруппа] + Код + Ссылка
      result.add(index(table, sep, withTail(hierarchyTail, code, ref)));

      // Правило 10 - Родитель [+ ЭтоГруппа] + Наименование + Ссылка
      result.add(index(table, sep, withTail(hierarchyTail, description, ref)));

      // Правила 11-13
      result.addAll(prefixedAttributeIndexes(chart.getAttributes(), table, sep,
        hierarchyTail, additionalOrderField, ref));
    }
    return result;
  }

  // План счетов

  private static List<PlatformIndex> chartOfAccountsIndexes(ChartOfAccounts chart, CF configuration) {
    var table = chart.getMdoReference();
    var sep = separators(chart, configuration);
    var ref = stdAttribute(chart, StdAttributeNames.REF);
    var parent = stdAttribute(chart, StdAttributeNames.PARENT);
    var code = stdAttribute(chart, StdAttributeNames.CODE);
    var description = stdAttribute(chart, StdAttributeNames.DESCRIPTION);
    var order = stdAttribute(chart, StdAttributeNames.ORDER);
    var result = new ArrayList<PlatformIndex>();

    // Правило 1 - Ссылка (кластерный)
    result.add(clusteredIndex(table, sep, ref));

    // Правило 2 - Код + Ссылка
    result.add(index(table, sep, code, ref));

    // Правило 3 - Родитель + Код + Ссылка
    result.add(index(table, sep, parent, code, ref));

    // Правило 4 - Наименование + Ссылка
    result.add(index(table, sep, description, ref));

    // Правило 5 - Родитель + Наименование + Ссылка
    result.add(index(table, sep, parent, description, ref));

    // Правила 6-7 - Порядок
    if (chart.getOrderLength() != 0) {
      result.add(index(table, sep, order, ref));
      result.add(index(table, sep, parent, order, ref));
    }

    // Правила 8-9 - реквизиты с индексированием
    chart.getAttributes().stream()
      .filter(attribute -> attribute.getIndexing() == IndexingType.INDEX)
      .forEach((Attribute attribute) -> {
        result.add(index(table, sep, attribute.getMdoReference(), ref));
        result.add(index(table, sep, parent, attribute.getMdoReference(), ref));
      });

    // Правила 10-15 - реквизиты с доп. упорядочиванием
    for (var attribute : chart.getAttributes()) {
      if (attribute.getIndexing() != IndexingType.INDEX_WITH_ADDITIONAL_ORDER) {
        continue;
      }

      if (chart.getOrderLength() != 0) {
        result.add(index(table, sep, attribute.getMdoReference(), order, ref));
        result.add(index(table, sep, parent, attribute.getMdoReference(), order, ref));
      } else if (chart.getDefaultPresentation() == DefaultPresentation.AS_CODE) {
        result.add(index(table, sep, attribute.getMdoReference(), code, ref));
        result.add(index(table, sep, parent, attribute.getMdoReference(), code, ref));
      } else if (chart.getDefaultPresentation() == DefaultPresentation.AS_DESCRIPTION) {
        result.add(index(table, sep, attribute.getMdoReference(), description, ref));
        result.add(index(table, sep, parent, attribute.getMdoReference(), description, ref));
      }
    }

    // Правило 16 - критерии отбора
    result.addAll(filterCriterionIndexes(configuration, table, sep));
    return result;
  }

  // Основные индексы ссылочных объектов без иерархии и подчинения
  // (план видов расчета, план обмена)

  private static List<PlatformIndex> referenceBasicIndexes(
    MD mdo, List<? extends Attribute> attributes, CF configuration) {
    var table = mdo.getMdoReference();
    var sep = separators(mdo, configuration);
    var ref = stdAttribute(mdo, StdAttributeNames.REF);
    var result = new ArrayList<PlatformIndex>();

    // Правило 1 - Ссылка (кластерный)
    result.add(clusteredIndex(table, sep, ref));

    // Правило 2 - Код + Ссылка
    result.add(index(table, sep, stdAttribute(mdo, StdAttributeNames.CODE), ref));

    // Правило 3 - Наименование + Ссылка
    result.add(index(table, sep, stdAttribute(mdo, StdAttributeNames.DESCRIPTION), ref));

    // Правила 4-6 - реквизиты
    result.addAll(attributeIndexes(attributes, table, sep, null, ref));

    // Правило 7 - критерии отбора
    result.addAll(filterCriterionIndexes(configuration, table, sep));
    return result;
  }

  // Перечисление

  private static List<PlatformIndex> enumIndexes(Enum enumValue, CF configuration) {
    var table = enumValue.getMdoReference();
    var sep = separators(enumValue, configuration);
    var ref = stdAttribute(enumValue, StdAttributeNames.REF);
    var result = new ArrayList<PlatformIndex>();

    // Правило 1 - Порядок + Ссылка
    result.add(index(table, sep, stdAttribute(enumValue, StdAttributeNames.ORDER), ref));

    // Правило 2 - Ссылка (кластерный)
    result.add(clusteredIndex(table, sep, ref));
    return result;
  }

  // Бизнес-процесс

  private static List<PlatformIndex> businessProcessIndexes(BusinessProcess process, CF configuration) {
    var table = process.getMdoReference();
    var sep = separators(process, configuration);
    var ref = stdAttribute(process, StdAttributeNames.REF);
    var date = stdAttribute(process, StdAttributeNames.DATE);
    var result = new ArrayList<PlatformIndex>();

    // Правила 1-3 - Ссылка (кластерный), Дата + Ссылка, Номер + Ссылка
    baseRefDateNumberIndexes(process, table, sep, ref, date, process.getNumberLength(), result);

    // Правило 4 - Завершен + Дата + Ссылка
    result.add(index(table, sep, stdAttribute(process, StdAttributeNames.COMPLETED), date, ref));

    // Правило 5 - Стартован + Дата + Ссылка
    result.add(index(table, sep, stdAttribute(process, StdAttributeNames.STARTED), date, ref));

    // Правило 6 - ВедущаяЗадача + Ссылка
    result.add(index(table, sep, stdAttribute(process, StdAttributeNames.HEAD_TASK), ref));

    // Правила 7-8 - реквизиты
    result.addAll(attributeIndexes(process.getAttributes(), table, sep, date, ref));

    // Правило 9 - критерии отбора
    result.addAll(filterCriterionIndexes(configuration, table, sep));
    return result;
  }

  // Задача

  private static List<PlatformIndex> taskIndexes(Task task, CF configuration) {
    var table = task.getMdoReference();
    var sep = separators(task, configuration);
    var ref = stdAttribute(task, StdAttributeNames.REF);
    var date = stdAttribute(task, StdAttributeNames.DATE);
    var description = stdAttribute(task, StdAttributeNames.DESCRIPTION);
    var executed = stdAttribute(task, StdAttributeNames.EXECUTED);
    var businessProcess = stdAttribute(task, StdAttributeNames.BUSINESS_PROCESS);
    var routePoint = stdAttribute(task, StdAttributeNames.ROUTE_POINT);
    var result = new ArrayList<PlatformIndex>();

    // Правила 1-3 - Ссылка (кластерный), Дата + Ссылка, Номер + Ссылка
    baseRefDateNumberIndexes(task, table, sep, ref, date, task.getNumberLength(), result);

    // Правило 4 - Наименование + Ссылка
    result.add(index(table, sep, description, ref));

    // Правило 5 - Выполнена + Наименование + Ссылка
    result.add(index(table, sep, executed, description, ref));

    // Правило 6 - Выполнена + Дата + Ссылка
    result.add(index(table, sep, executed, date, ref));

    // Правило 7 - БизнесПроцесс + ТочкаМаршрута + Ссылка
    result.add(index(table, sep, businessProcess, routePoint, ref));

    // Правило 8 - Выполнена + БизнесПроцесс + ТочкаМаршрута + Ссылка
    result.add(index(table, sep, Stream.of(executed, businessProcess, routePoint, ref).toList()));

    // Правило 9 - БизнесПроцесс + Дата + Ссылка
    result.add(index(table, sep, businessProcess, date, ref));

    // Правила 10-11 - реквизиты
    result.addAll(attributeIndexes(task.getAttributes(), table, sep, date, ref));

    // Правило 12 - критерии отбора
    result.addAll(filterCriterionIndexes(configuration, table, sep));
    return result;
  }

  // Регистр сведений

  private static List<PlatformIndex> informationRegisterIndexes(
    InformationRegister register, CF configuration) {
    var sep = separators(register, configuration);
    if (register.getInformationRegisterPeriodicity() == InformationRegisterPeriodicity.RECORDER_POSITION) {
      return recorderPositionInfoRegIndexes(register, sep);
    } else {
      var result = new ArrayList<>(periodicInfoRegIndexes(register, sep));

      // Дополнительный индекс подчинения регистратору
      if (register.getWriteMode() == RegisterWriteMode.RECORDER_SUBORDINATE) {
        result.add(recorderLineNumberIndex(register, sep,
          register.getInformationRegisterPeriodicity() == InformationRegisterPeriodicity.NONPERIODICAL));
      }
      return result;
    }
  }

  private static List<PlatformIndex> periodicInfoRegIndexes(InformationRegister register, List<MdoReference> sep) {
    var table = register.getMdoReference();
    var dimRefs = refs(register.getDimensions());
    var independent = register.getWriteMode() == RegisterWriteMode.INDEPENDENT;
    var periodic = register.getInformationRegisterPeriodicity() != InformationRegisterPeriodicity.NONPERIODICAL;
    var result = new ArrayList<PlatformIndex>();

    if (!periodic) {
      // Правило 1 - все измерения (кластерный для независимого)
      if (!dimRefs.isEmpty()) {
        result.add(createIndex(table, independent, sep, dimRefs));
      }

      // Правило 2 - измерение с индексированием или ведущее первым
      dimensionFirstIndexes(register, table, sep, result, false);

      // Правила 3-4 - реквизиты и ресурсы с индексированием
      indexedEntryIndexes(register, table, sep, result, List.of());
    } else {
      var period = stdAttribute(register, StdAttributeNames.PERIOD);

      // Правило 1 - Период + измерения
      result.add(index(table, sep, withTail(List.of(period), dimRefs)));

      // Правило 2 - измерения + Период (кластерный для независимого)
      if (!dimRefs.isEmpty()) {
        result.add(createIndex(table, independent, sep, withTail(dimRefs, period)));
      }

      // Правило 3 - измерение + Период + остальные измерения
      dimensionFirstIndexes(register, table, sep, result, true);

      // Правила 4-5 - реквизиты и ресурсы с индексированием
      indexedEntryIndexes(register, table, sep, result, List.of(period));
    }
    return result;
  }

  private static List<PlatformIndex> recorderPositionInfoRegIndexes(
    InformationRegister register, List<MdoReference> sep) {
    var table = register.getMdoReference();
    var dimRefs = refs(register.getDimensions());
    var tail = List.of(stdAttribute(register, StdAttributeNames.PERIOD),
      stdAttribute(register, StdAttributeNames.RECORDER), stdAttribute(register, StdAttributeNames.LINE_NUMBER));
    var result = new ArrayList<PlatformIndex>();

    // Правило 1 - Период + Регистратор + НомерСтроки
    result.add(index(table, sep, tail));

    // Правило 2 - Регистратор + НомерСтроки
    result.add(recorderLineNumberIndex(register, sep, false));

    // Правило 3 - измерения + Период + Регистратор + НомерСтроки (кластерный)
    if (!dimRefs.isEmpty()) {
      result.add(clusteredIndex(table, sep, withTail(dimRefs, tail)));
    }

    // Правило 4 - измерение с индексированием
    register.getDimensions().stream()
      .filter(dimension -> dimension.getIndexing() == IndexingType.INDEX)
      .map(dimension -> index(table, sep, withTail(List.of(dimension.getMdoReference()), tail)))
      .forEach(result::add);

    // Правила 5-6 - реквизиты и ресурсы с индексированием
    for (var field : indexedEntryFields(register)) {
      result.add(index(table, sep, withTail(List.of(field), tail)));
    }
    return result;
  }

  // Регистр накопления

  private static List<PlatformIndex> turnoverRegisterIndexes(
    AccumulationRegister register, CF configuration) {
    var table = register.getMdoReference();
    var sep = separators(register, configuration);
    var tail = List.of(stdAttribute(register, StdAttributeNames.PERIOD),
      stdAttribute(register, StdAttributeNames.RECORDER), stdAttribute(register, StdAttributeNames.LINE_NUMBER));
    var result = new ArrayList<PlatformIndex>();

    // Правило 1 - Период + Регистратор + НомерСтроки (кластерный)
    result.add(clusteredIndex(table, sep, tail));

    // Правило 2 - Регистратор + НомерСтроки
    result.add(recorderLineNumberIndex(register, sep, false));

    // Правила 3-4 - измерения, реквизиты и ресурсы с индексированием
    indexedFieldsWithTail(register, table, sep, tail, result);
    return result;
  }

  // Регистр бухгалтерии

  private static List<PlatformIndex> accountingRegisterIndexes(
    AccountingRegister register, CF configuration) {
    var table = register.getMdoReference();
    var sep = separators(register, configuration);
    var period = stdAttribute(register, StdAttributeNames.PERIOD);
    var recorder = stdAttribute(register, StdAttributeNames.RECORDER);
    var lineNumber = stdAttribute(register, StdAttributeNames.LINE_NUMBER);
    var tail = List.of(period, recorder, lineNumber);
    var result = new ArrayList<PlatformIndex>();

    // Правило 1 - Период + Регистратор + НомерСтроки (кластерный)
    result.add(clusteredIndex(table, sep, tail));

    // Правило 2 - Регистратор + НомерСтроки
    result.add(recorderLineNumberIndex(register, sep, false));

    // Правило 3 - Счет + Период + Регистратор (без корреспонденции)
    if (!register.isCorrespondence()) {
      result.add(index(table, sep,
        stdAttribute(register, StdAttributeNames.ACCOUNT), period, recorder));
    }

    // Правила 4-5 - измерения, реквизиты и ресурсы с индексированием
    indexedFieldsWithTail(register, table, sep, tail, result);
    return result;
  }

  // Регистр расчета

  private static List<PlatformIndex> calculationRegisterIndexes(
    CalculationRegister register, CF configuration) {
    var table = register.getMdoReference();
    var sep = separators(register, configuration);
    var registrationPeriod = stdAttribute(register, StdAttributeNames.REGISTRATION_PERIOD);
    var recorder = stdAttribute(register, StdAttributeNames.RECORDER);
    var lineNumber = stdAttribute(register, StdAttributeNames.LINE_NUMBER);
    var baseDims = refs(register.getDimensions().stream()
      .filter(Dimension::isBaseDimension).toList());
    var result = new ArrayList<PlatformIndex>();

    // Правило 1 - ПериодРегистрации + Регистратор + НомерСтроки
    result.add(index(table, sep, registrationPeriod, recorder, lineNumber));

    // Правило 2 - Регистратор + НомерСтроки
    result.add(recorderLineNumberIndex(register, sep, false));

    // Правило 3 - ПериодРегистрации + базовые измерения
    result.add(index(table, sep, withTail(List.of(registrationPeriod), baseDims)));

    // Правило 4 - базовые измерения + ПериодРегистрации
    if (!baseDims.isEmpty()) {
      result.add(index(table, sep, withTail(baseDims, registrationPeriod)));
    }

    // Правила 5-6 - ПериодДействия
    if (register.isActionPeriod()) {
      var actionPeriod = stdAttribute(register, StdAttributeNames.ACTION_PERIOD);
      result.add(index(table, sep, withTail(List.of(actionPeriod), baseDims)));
      if (!baseDims.isEmpty()) {
        result.add(index(table, sep, withTail(baseDims, actionPeriod)));
      }
    }

    // Правила 7-8 - измерения, реквизиты и ресурсы с индексированием
    indexedFieldsWithTail(register, table, sep, List.of(registrationPeriod, recorder, lineNumber), result);
    return result;
  }

  // Последовательность

  private static List<PlatformIndex> sequenceIndexes(Sequence sequence, CF configuration) {
    var table = sequence.getMdoReference();
    var sep = separators(sequence, configuration);
    var recorder = stdAttribute(sequence, StdAttributeNames.RECORDER);
    var result = new ArrayList<PlatformIndex>();

    // Правило 1 - Регистратор
    result.add(index(table, sep, recorder));

    // Правило 2 - измерения + Период + Регистратор
    result.add(
      index(table, sep, Stream.concat(
        refs(sequence.getDimensions()).stream(),
        Stream.of(stdAttribute(sequence, StdAttributeNames.PERIOD), recorder)).toList()
      )
    );
    return result;
  }

  // Табличная часть

  private static List<PlatformIndex> tabularSectionIndexes(
    TabularSection tabularSection, MD owner, CF configuration) {
    var table = tabularSection.getMdoReference();
    var sep = separators(owner, configuration);
    var ownerRef = stdAttribute(owner, StdAttributeNames.REF);
    var criterionFields = filterCriterionFields(configuration, table.getMdoRef(), false);

    // Правило 2 - Реквизит + Ссылка
    return tabularSection.getAttributes().stream()
      .filter(attribute -> attribute.getIndexing() == IndexingType.INDEX
        || criterionFields.contains(attribute.getMdoReference()))
      .map(attribute -> index(table, sep, attribute.getMdoReference(), ownerRef))
      .collect(Collectors.toCollection(ArrayList::new));
  }

  // Разделители

  /**
   * Независимые разделители объекта: общие реквизиты с разделением "Разделять",
   * использованием разделяемых данных "Независимо" и данным объектом в составе использования.
   */
  private static List<MdoReference> separators(MD mdo, CF configuration) {
    return configuration.getCommonAttributes().stream()
      .filter(attribute -> attribute.getDataSeparation() == DataSeparation.SEPARATE
        && attribute.getDataSeparationUse() == CommonAttributeSeparatedDataUse.INDEPENDENTLY
        && usesObject(attribute, mdo.getMdoReference()))
      .map(CommonAttribute::getMdoReference)
      .toList();
  }

  private static boolean usesObject(CommonAttribute attribute, MdoReference mdoRef) {
    var explicit = attribute.getContent().stream()
      .filter(useContent -> useContent.getMetadata().equals(mdoRef))
      .findFirst();
    return explicit.map(useContent -> useContent.getUse() != UseMode.DONT_USE)
      .orElseGet(() -> switch (attribute.getAutoUse()) {
        case USE, AUTO, USE_WITH_WARNINGS -> true;
        default -> false;
      });
  }

  // Вспомогательные методы

  private static MdoReference stdAttribute(MD mdo, MultiName name) {
    return MdoReference.create(mdo.getMdoReference(), MDOType.STANDARD_ATTRIBUTE, name.getEn());
  }

  private static List<MdoReference> withTail(List<MdoReference> prefix, MdoReference... tail) {
    return withTail(prefix, List.of(tail));
  }

  private static List<MdoReference> withTail(List<MdoReference> prefix, List<MdoReference> tail) {
    return Stream.concat(prefix.stream(), tail.stream()).toList();
  }

  private static List<MdoReference> refs(List<? extends MD> children) {
    return children.stream().map(MD::getMdoReference).toList();
  }

  /**
   * Поле дополнительного упорядочивания реквизита: Код или Наименование
   * в зависимости от основного представления и ненулевой длины.
   */
  private static @Nullable MdoReference additionalOrderField(
    MD mdo, int codeLength, int descriptionLength, DefaultPresentation defaultPresentation) {
    if (defaultPresentation == DefaultPresentation.AS_CODE && codeLength != 0) {
      return stdAttribute(mdo, StdAttributeNames.CODE);
    }
    if (defaultPresentation == DefaultPresentation.AS_DESCRIPTION && descriptionLength != 0) {
      return stdAttribute(mdo, StdAttributeNames.DESCRIPTION);
    }
    return null;
  }

  /**
   * Хвост иерархического справочника или плана видов характеристик:
   * Родитель [+ ЭтоГруппа при размещении групп сверху].
   */
  private static List<MdoReference> hierarchyTail(Catalog catalog) {
    return hierarchyTail(catalog.isHierarchical(), catalog.isFoldersOnTop(), catalog);
  }

  private static List<MdoReference> hierarchyTail(ChartOfCharacteristicTypes chart) {
    return hierarchyTail(chart.isHierarchical(), chart.isFoldersOnTop(), chart);
  }

  private static List<MdoReference> hierarchyTail(boolean hierarchical, boolean foldersOnTop, MD mdo) {
    if (!hierarchical) {
      return List.of();
    }
    var parent = stdAttribute(mdo, StdAttributeNames.PARENT);
    if (foldersOnTop) {
      return List.of(parent, stdAttribute(mdo, StdAttributeNames.IS_FOLDER));
    }
    return List.of(parent);
  }

  /**
   * Индексы по длинам кода и наименования для иерархических справочников
   * (правила 15-17 и 21-23).
   */
  private static void appendLengthIndexes(List<PlatformIndex> result, Catalog catalog, MdoReference table,
                                          List<MdoReference> sep, List<MdoReference> tail, MdoReference ref,
                                          MdoReference code, MdoReference description) {
    if (catalog.getCodeLength() == 0 && catalog.getDescriptionLength() == 0) {
      result.add(index(table, sep, withTail(tail, ref)));
    }
    if (catalog.getCodeLength() != 0) {
      result.add(index(table, sep, withTail(tail, code, ref)));
    }
    if (catalog.getDescriptionLength() != 0) {
      result.add(index(table, sep, withTail(tail, description, ref)));
    }
  }

  /**
   * Индексы по реквизитам: с индексированием - простой состав,
   * с доп. упорядочиванием - с полем упорядочивания между реквизитом и хвостом.
   */
  private static List<PlatformIndex> attributeIndexes(List<? extends Attribute> attributes,
                                                      MdoReference table,
                                                      List<MdoReference> sep,
                                                      @Nullable MdoReference additionalOrderField,
                                                      MdoReference... tail) {
    return attributeIndexes(attributes, table, sep, additionalOrderField, List.of(tail));
  }

  private static List<PlatformIndex> attributeIndexes(List<? extends Attribute> attributes,
                                                      MdoReference table,
                                                      List<MdoReference> sep,
                                                      @Nullable MdoReference additionalOrderField,
                                                      List<MdoReference> tail) {
    var result = new ArrayList<PlatformIndex>();
    for (var attribute : attributes) {
      if (attribute.getIndexing() == IndexingType.INDEX) {
        result.add(index(table, sep, withTail(List.of(attribute.getMdoReference()), tail)));
      } else if (attribute.getIndexing() == IndexingType.INDEX_WITH_ADDITIONAL_ORDER && additionalOrderField != null) {
        result.add(index(table, sep, withTail(List.of(attribute.getMdoReference(), additionalOrderField), tail)));
      }
    }
    return result;
  }

  /**
   * Индексы по реквизитам с префиксом полей: префикс + реквизит [+ поле упорядочивания] + Ссылка
   * (правила 12-14, 18-20, 24-26 справочника и 11-13 плана видов характеристик).
   */
  private static List<PlatformIndex> prefixedAttributeIndexes(List<? extends Attribute> attributes,
                                                              MdoReference table,
                                                              List<MdoReference> sep,
                                                              List<MdoReference> prefix,
                                                              @Nullable MdoReference additionalOrderField,
                                                              MdoReference ref) {
    var result = new ArrayList<PlatformIndex>();
    for (var attribute : attributes) {
      if (attribute.getIndexing() == IndexingType.INDEX) {
        result.add(index(table, sep, withTail(prefix, attribute.getMdoReference(), ref)));
      } else if (attribute.getIndexing() == IndexingType.INDEX_WITH_ADDITIONAL_ORDER && additionalOrderField != null) {
        result.add(index(table, sep, withTail(prefix, attribute.getMdoReference(), additionalOrderField, ref)));
      }
    }
    return result;
  }

  /**
   * Индексы "измерение первым" регистра сведений: для каждого измерения
   * с индексированием или ведущего (кроме единственного) - состав,
   * начинающийся этим измерением (для периодического - с полем Периода вторым).
   */
  private static void dimensionFirstIndexes(InformationRegister register, MdoReference table,
                                            List<MdoReference> sep, List<PlatformIndex> result, boolean periodic) {
    var dimensions = register.getDimensions();
    if (dimensions.size() < 2) {
      return;
    }
    var period = periodic ? stdAttribute(register, StdAttributeNames.PERIOD) : null;
    for (var i = 0; i < dimensions.size(); i++) {
      var dimension = dimensions.get(i);
      if (dimension.getIndexing() != IndexingType.INDEX && !dimension.isMaster()) {
        continue;
      }
      var head = new ArrayList<MdoReference>();
      head.add(dimension.getMdoReference());
      if (periodic) {
        head.add(period);
      }
      for (var j = 0; j < dimensions.size(); j++) {
        if (j != i) {
          head.add(dimensions.get(j).getMdoReference());
        }
      }
      result.add(index(table, sep, head));
    }
  }

  /**
   * Индексы по реквизитам и ресурсам с индексированием:
   * поле + голова состава + все измерения по порядку.
   */
  private static void indexedEntryIndexes(Register register, MdoReference table, List<MdoReference> sep,
                                          List<PlatformIndex> result, List<MdoReference> head) {
    var dimRefs = refs(register.getDimensions());
    indexedEntryFields(register).stream()
      .map(field ->
        index(table, sep, Stream.concat(head.stream(), Stream.concat(Stream.of(field), dimRefs.stream())).toList()))
      .forEach(result::add);
  }

  private static List<MdoReference> indexedEntryFields(Register register) {
    return Stream.concat(register.getAttributes().stream(), register.getResources().stream())
      .filter(entry -> entry.getIndexing() == IndexingType.INDEX)
      .map(Attribute::getMdoReference)
      .toList();
  }

  /**
   * Индексы по измерениям, реквизитам и ресурсам с индексированием:
   * поле + хвост стандартных полей регистра.
   */
  private static void indexedFieldsWithTail(Register register, MdoReference table,
                                            List<MdoReference> sep, List<MdoReference> tail,
                                            List<PlatformIndex> result) {
    var fields = Stream.concat(indexedEntryFields(register).stream(),
      register.getDimensions().stream()
        .filter(dimension -> dimension.getIndexing() == IndexingType.INDEX)
        .map(Dimension::getMdoReference)).toList();
    fields.stream()
      .map(field -> index(table, sep, withTail(List.of(field), tail)))
      .forEach(result::add);
  }

  private static PlatformIndex recorderLineNumberIndex(Register register, List<MdoReference> sep, boolean clustered) {
    return createIndex(register.getMdoReference(), clustered, sep,
      stdAttribute(register, StdAttributeNames.RECORDER), stdAttribute(register, StdAttributeNames.LINE_NUMBER));
  }

  /**
   * Базовые индексы объектов с датой и номером: Ссылка (кластерный),
   * Дата + Ссылка и Номер + Ссылка при ненулевой длине номера.
   */
  private static void baseRefDateNumberIndexes(MD mdo, MdoReference table,
                                               List<MdoReference> sep, MdoReference ref, MdoReference date,
                                               long numberLength, List<PlatformIndex> result) {
    // Правило 1 - Ссылка (кластерный)
    result.add(clusteredIndex(table, sep, ref));
    // Правило 2 - Дата + Ссылка
    result.add(index(table, sep, date, ref));
    // Правило 3 - Номер + Ссылка
    if (numberLength != 0) {
      result.add(index(table, sep, stdAttribute(mdo, StdAttributeNames.NUMBER), ref));
    }
  }

  /**
   * Поля объекта, входящие в состав критериев отбора конфигурации.
   *
   * @param excludeTabularSections исключить ли поля табличных частей
   */
  private static List<MdoReference> filterCriterionFields(
    CF configuration, String ownerRef, boolean excludeTabularSections) {
    return configuration.getFilterCriteria().stream()
      .flatMap(criterion -> criterion.getContent().stream())
      .map(MdoReference::getMdoRef)
      .filter(ref -> ref.startsWith(ownerRef + ".") && (!excludeTabularSections || !ref.contains(TABULAR_SECTION)))
      .distinct()
      .map(MdoReference::create)
      .toList();
  }

  private static List<PlatformIndex> filterCriterionIndexes(
    CF configuration, MdoReference table, List<MdoReference> sep) {
    return filterCriterionFields(configuration, table.getMdoRef(), true).stream()
      .map(field -> index(table, sep, field))
      .collect(Collectors.toCollection(ArrayList::new));
  }
}
