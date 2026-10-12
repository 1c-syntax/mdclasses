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

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Трассировка правил {@code docs/ru/platform-indexes.md} на эталонных объектах тестовых пакетов.
 */
class PlatformIndexCalculatorTest {

  private static final String REF = "StandardAttribute.Ref";
  private static final String CODE = "StandardAttribute.Code";
  private static final String DESCRIPTION = "StandardAttribute.Description";
  private static final String DATE = "StandardAttribute.Date";
  private static final String NUMBER = "StandardAttribute.Number";
  private static final String PARENT = "StandardAttribute.Parent";
  private static final String IS_FOLDER = "StandardAttribute.IsFolder";
  private static final String OWNER = "StandardAttribute.Owner";
  private static final String ORDER = "StandardAttribute.Order";
  private static final String PERIOD = "StandardAttribute.Period";
  private static final String RECORDER = "StandardAttribute.Recorder";
  private static final String LINE_NUMBER = "StandardAttribute.LineNumber";
  private static final String REGISTRATION_PERIOD = "StandardAttribute.RegistrationPeriod";

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Catalogs.Справочник1",
    "false, mdclasses, Catalogs.Справочник1"
  })
  void testCatalog(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = "Catalog.Справочник1.";

    // Правила 1-3, 6: базовые индексы и реквизит с доп. упорядочиванием
    assertThat(indexes).extracting(PlatformIndex::getTable)
      .containsOnly(MdoReference.create("Catalog.Справочник1"));
    assertThat(indexes).extracting(PlatformIndex::isClustered)
      .containsExactly(true, false, false, false);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + REF),
        List.of(prefix + CODE, prefix + REF),
        List.of(prefix + DESCRIPTION, prefix + REF),
        List.of(prefix + "Attribute.Реквизит1", prefix + DESCRIPTION, prefix + REF)
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Documents.Документ1",
    "false, mdclasses, Documents.Документ1"
  })
  void testDocument(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = "Document.Документ1.";

    // Правила 1-3 и 6: критерии отбора по реквизитам документа
    assertThat(indexes).extracting(PlatformIndex::isClustered)
      .containsExactly(true, false, false, false, false);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + REF),
        List.of(prefix + DATE, prefix + REF),
        List.of(prefix + NUMBER, prefix + REF),
        List.of(prefix + "Attribute.Реквизит1"),
        List.of(prefix + "Attribute.Реквизит2")
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, InformationRegisters.РегистрСведений1",
    "false, mdclasses, InformationRegisters.РегистрСведений1",
    "true, mdclasses, InformationRegisters.РегистрСведений2",
    "false, mdclasses, InformationRegisters.РегистрСведений2"
  })
  void testInformationRegisters(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);

    if (mdo.getName().equals("РегистрСведений1")) {
      // Непериодический независимый: правило 1, кластерный
      assertThat(indexes).extracting(PlatformIndex::isClustered).containsExactly(true);
      assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
        .containsExactly(List.of("InformationRegister.РегистрСведений1.Dimension.Измерение1"));
    } else {
      // Непериодический подчинённый регистратору: правила 1 и доп. индекс подчинения
      assertThat(indexes).extracting(PlatformIndex::isClustered).containsExactly(false, true);
      assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
        .containsExactly(
          List.of("InformationRegister.РегистрСведений2.Dimension.Измерение1"),
          List.of("InformationRegister.РегистрСведений2." + RECORDER,
            "InformationRegister.РегистрСведений2." + LINE_NUMBER)
        );
    }
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, AccumulationRegisters.РегистрНакопления1",
    "false, mdclasses, AccumulationRegisters.РегистрНакопления1"
  })
  void testAccumulationRegister(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = "AccumulationRegister.РегистрНакопления1.";

    // Правила 1-2 основной таблицы регистра накопления
    assertThat(indexes).extracting(PlatformIndex::isClustered).containsExactly(true, false);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + PERIOD, prefix + RECORDER, prefix + LINE_NUMBER),
        List.of(prefix + RECORDER, prefix + LINE_NUMBER)
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, AccountingRegisters.РегистрБухгалтерии1",
    "false, mdclasses, AccountingRegisters.РегистрБухгалтерии1"
  })
  void testAccountingRegister(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = "AccountingRegister.РегистрБухгалтерии1.";

    // Правила 1-4 без корреспонденции: счет, измерение с индексированием
    assertThat(indexes).extracting(PlatformIndex::isClustered)
      .containsExactly(true, false, false, false);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + PERIOD, prefix + RECORDER, prefix + LINE_NUMBER),
        List.of(prefix + RECORDER, prefix + LINE_NUMBER),
        List.of(prefix + "StandardAttribute.Account", prefix + PERIOD, prefix + RECORDER),
        List.of(prefix + "Dimension.Измерение1", prefix + PERIOD, prefix + RECORDER, prefix + LINE_NUMBER)
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, CalculationRegisters.РегистрРасчета1",
    "false, mdclasses, CalculationRegisters.РегистрРасчета1"
  })
  void testCalculationRegister(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = "CalculationRegister.РегистрРасчета1.";

    // Правила 1-3: без базовых измерений и без периода действия
    assertThat(indexes).extracting(PlatformIndex::isClustered)
      .containsExactly(false, false, false);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + REGISTRATION_PERIOD, prefix + RECORDER, prefix + LINE_NUMBER),
        List.of(prefix + RECORDER, prefix + LINE_NUMBER),
        List.of(prefix + REGISTRATION_PERIOD)
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, ChartsOfAccounts.ПланСчетов1",
    "false, mdclasses, ChartsOfAccounts.ПланСчетов1"
  })
  void testChartOfAccounts(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = "ChartOfAccounts.ПланСчетов1.";

    // Правила 1-5: длина порядка равна 0, индексы по порядку не строятся
    assertThat(indexes).extracting(PlatformIndex::isClustered)
      .containsExactly(true, false, false, false, false);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + REF),
        List.of(prefix + CODE, prefix + REF),
        List.of(prefix + PARENT, prefix + CODE, prefix + REF),
        List.of(prefix + DESCRIPTION, prefix + REF),
        List.of(prefix + PARENT, prefix + DESCRIPTION, prefix + REF)
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, ChartsOfCharacteristicTypes.ПланВидовХарактеристик1",
    "false, mdclasses, ChartsOfCharacteristicTypes.ПланВидовХарактеристик1",
    "true, mdclasses, ChartsOfCalculationTypes.ПланВидовРасчета1",
    "false, mdclasses, ChartsOfCalculationTypes.ПланВидовРасчета1",
    "true, mdclasses, ExchangePlans.ПланОбмена1",
    "false, mdclasses, ExchangePlans.ПланОбмена1"
  })
  void testBasicReferenceObjects(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = mdo.getMdoReference().getMdoRef() + ".";

    // Правила 1-3 основных индексов ссылочных объектов
    assertThat(indexes).extracting(PlatformIndex::isClustered)
      .containsExactly(true, false, false);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + REF),
        List.of(prefix + CODE, prefix + REF),
        List.of(prefix + DESCRIPTION, prefix + REF)
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Enums.Перечисление1",
    "false, mdclasses, Enums.Перечисление1"
  })
  void testEnum(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = "Enum.Перечисление1.";

    // Правила 1-2 перечислений
    assertThat(indexes).extracting(PlatformIndex::isClustered).containsExactly(false, true);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + ORDER, prefix + REF),
        List.of(prefix + REF)
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, DocumentJournals.ЖурналДокументов1",
    "false, mdclasses, DocumentJournals.ЖурналДокументов1"
  })
  void testDocumentJournal(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = "DocumentJournal.ЖурналДокументов1.";

    // Правила 1-2: графа журнала без индексирования
    assertThat(indexes).extracting(PlatformIndex::isClustered).containsExactly(true, false);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + REF),
        List.of(prefix + DATE, prefix + REF)
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Tasks.Задача1",
    "false, mdclasses, Tasks.Задача1"
  })
  void testTask(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = "Task.Задача1.";
    var businessProcess = prefix + "StandardAttribute.BusinessProcess";
    var routePoint = prefix + "StandardAttribute.RoutePoint";
    var executed = prefix + "StandardAttribute.Executed";

    // Правила 1-9 задач
    assertThat(indexes).extracting(PlatformIndex::isClustered)
      .containsExactly(true, false, false, false, false, false, false, false, false);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + REF),
        List.of(prefix + DATE, prefix + REF),
        List.of(prefix + NUMBER, prefix + REF),
        List.of(prefix + DESCRIPTION, prefix + REF),
        List.of(executed, prefix + DESCRIPTION, prefix + REF),
        List.of(executed, prefix + DATE, prefix + REF),
        List.of(businessProcess, routePoint, prefix + REF),
        List.of(executed, businessProcess, routePoint, prefix + REF),
        List.of(businessProcess, prefix + DATE, prefix + REF)
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, BusinessProcesses.БизнесПроцесс1",
    "false, mdclasses, BusinessProcesses.БизнесПроцесс1"
  })
  void testBusinessProcess(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = "BusinessProcess.БизнесПроцесс1.";

    // Правила 1-6 основной таблицы бизнес-процесса
    assertThat(indexes).extracting(PlatformIndex::isClustered)
      .containsExactly(true, false, false, false, false, false);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + REF),
        List.of(prefix + DATE, prefix + REF),
        List.of(prefix + NUMBER, prefix + REF),
        List.of(prefix + "StandardAttribute.Completed", prefix + DATE, prefix + REF),
        List.of(prefix + "StandardAttribute.Started", prefix + DATE, prefix + REF),
        List.of(prefix + "StandardAttribute.HeadTask", prefix + REF)
      );
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Sequences.Последовательность1",
    "false, mdclasses, Sequences.Последовательность1"
  })
  void testSequence(boolean formatEDT, String pack, String mdoRef) {
    var mdo = Fixtures.get(pack, mdoRef, formatEDT);
    var indexes = compute(mdo, pack, formatEDT);
    var prefix = "Sequence.Последовательность1.";

    // Правила 1-2 последовательностей
    assertThat(indexes).extracting(PlatformIndex::isClustered).containsExactly(false, false);
    assertThat(indexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(prefix + RECORDER),
        List.of(prefix + "Dimension.Измерение1", prefix + PERIOD, prefix + RECORDER)
      );
  }

  @Test
  void testHierarchicalAndSubordinateCatalogs() {
    // Правила 1-7, 15-20: иерархический неподчинённый справочник с группами сверху
    var notes = Fixtures.get("ssl_3_1", "Catalogs.Заметки", false);
    var notesIndexes = compute(notes, "ssl_3_1", false);
    var notesPrefix = "Catalog.Заметки.";
    assertThat(notesIndexes).extracting(PlatformIndex::isClustered)
      .containsExactly(true, false, false, false, false, false, false, false, false, false, false);
    assertThat(notesIndexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(notesPrefix + REF),
        List.of(notesPrefix + DESCRIPTION, notesPrefix + REF),
        List.of(notesPrefix + "Attribute.Автор", notesPrefix + REF),
        List.of(notesPrefix + "Attribute.Предмет", notesPrefix + REF),
        List.of(notesPrefix + "Attribute.ДляРабочегоСтола", notesPrefix + REF),
        List.of(notesPrefix + "Attribute.Пометка", notesPrefix + DESCRIPTION, notesPrefix + REF),
        List.of(notesPrefix + PARENT, notesPrefix + IS_FOLDER, notesPrefix + DESCRIPTION, notesPrefix + REF),
        List.of(notesPrefix + PARENT, notesPrefix + IS_FOLDER, notesPrefix + "Attribute.Автор", notesPrefix + REF),
        List.of(notesPrefix + PARENT, notesPrefix + IS_FOLDER, notesPrefix + "Attribute.Предмет", notesPrefix + REF),
        List.of(notesPrefix + PARENT, notesPrefix + IS_FOLDER, notesPrefix + "Attribute.ДляРабочегоСтола",
          notesPrefix + REF),
        List.of(notesPrefix + PARENT, notesPrefix + IS_FOLDER, notesPrefix + "Attribute.Пометка",
          notesPrefix + DESCRIPTION, notesPrefix + REF)
      );

    // Правила 1-4, 9-14: подчинённый неверархический справочник
    var versions = Fixtures.get("ssl_3_1", "Catalogs.ВерсииФайлов", false);
    var versionIndexes = compute(versions, "ssl_3_1", false);
    var versionsPrefix = "Catalog.ВерсииФайлов.";
    assertThat(versionIndexes).hasSize(17);
    assertThat(versionIndexes).extracting(PlatformIndex::isClustered)
      .containsExactly(true, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false);
    assertThat(versionIndexes).extracting(PlatformIndexCalculatorTest::fields)
      .containsExactly(
        List.of(versionsPrefix + REF),
        List.of(versionsPrefix + CODE, versionsPrefix + REF),
        List.of(versionsPrefix + DESCRIPTION, versionsPrefix + REF),
        List.of(versionsPrefix + "Attribute.ДатаСоздания", versionsPrefix + REF),
        List.of(versionsPrefix + "Attribute.Размер", versionsPrefix + REF),
        List.of(versionsPrefix + "Attribute.Расширение", versionsPrefix + REF),
        List.of(versionsPrefix + "Attribute.РодительскаяВерсия", versionsPrefix + REF),
        List.of(versionsPrefix + "Attribute.СтатусИзвлеченияТекста", versionsPrefix + REF),
        List.of(versionsPrefix + "Attribute.Том", versionsPrefix + REF),
        List.of(versionsPrefix + OWNER, versionsPrefix + CODE, versionsPrefix + REF),
        List.of(versionsPrefix + OWNER, versionsPrefix + DESCRIPTION, versionsPrefix + REF),
        List.of(versionsPrefix + OWNER, versionsPrefix + "Attribute.ДатаСоздания", versionsPrefix + REF),
        List.of(versionsPrefix + OWNER, versionsPrefix + "Attribute.Размер", versionsPrefix + REF),
        List.of(versionsPrefix + OWNER, versionsPrefix + "Attribute.Расширение", versionsPrefix + REF),
        List.of(versionsPrefix + OWNER, versionsPrefix + "Attribute.РодительскаяВерсия", versionsPrefix + REF),
        List.of(versionsPrefix + OWNER, versionsPrefix + "Attribute.СтатусИзвлеченияТекста", versionsPrefix + REF),
        List.of(versionsPrefix + OWNER, versionsPrefix + "Attribute.Том", versionsPrefix + REF)
      );
  }

  private static List<PlatformIndex> compute(MD mdo, String pack, boolean formatEDT) {
    var configuration = (Configuration) Fixtures.get(pack, "Configuration", formatEDT);
    return PlatformIndexCalculator.computeIndexes(mdo, configuration);
  }

  private static List<String> fields(PlatformIndex index) {
    return index.getFields().stream()
      .map(MdoReference::getMdoRef)
      .toList();
  }
}
