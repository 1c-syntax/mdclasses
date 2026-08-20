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
package com.github._1c_syntax.bsl.test_utils;

import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.CommonForm;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Генератор JSON-фикстур для FormData (содержимого форм).
 * <p>
 * Загружает формы (ObjectForm из родителей + CommonForm напрямую),
 * обходит их FormData и сохраняет JSON-фикстуры.
 */
@Disabled
class GenerateFormDataFixtures {

  private static final Path FIXTURES_BASE = Fixtures.FIXTURES_PATH;
  private static List<PackParent> parents() {
    return List.of(
      // mdclasses
      new PackParent("mdclasses", "Catalogs.Справочник1"),
      new PackParent("mdclasses", "Documents.Документ1"),
      new PackParent("mdclasses", "InformationRegisters.РегистрСведений1"),
      new PackParent("mdclasses", "InformationRegisters.РегистрСведений2"),
      new PackParent("mdclasses", "AccumulationRegisters.РегистрНакопления1"),
      new PackParent("mdclasses", "AccumulationRegisters.РегистрНакопления2"),
      new PackParent("mdclasses", "AccountingRegisters.РегистрБухгалтерии1"),
      new PackParent("mdclasses", "CalculationRegisters.РегистрРасчета1"),
      new PackParent("mdclasses", "ChartsOfAccounts.ПланСчетов1"),
      new PackParent("mdclasses", "ChartsOfCalculationTypes.ПланВидовРасчета1"),
      new PackParent("mdclasses", "ChartsOfCharacteristicTypes.ПланВидовХарактеристик1"),
      new PackParent("mdclasses", "Enums.Перечисление1"),
      new PackParent("mdclasses", "DocumentJournals.ЖурналДокументов1"),
      new PackParent("mdclasses", "Tasks.Задача1"),
      new PackParent("mdclasses", "ExternalDataSources.ТекущаяСУБД"),
      new PackParent("mdclasses", "DataProcessors.Обработка1"),
      new PackParent("mdclasses", "Reports.Отчет1"),
      new PackParent("mdclasses", "Sequences.Последовательность1"),
      new PackParent("mdclasses", "ExchangePlans.ПланОбмена1"),
      new PackParent("mdclasses", "BusinessProcesses.БизнесПроцесс1"),
      // ssl_3_1
      new PackParent("ssl_3_1", "Catalogs.ВерсииФайлов"),
      new PackParent("ssl_3_1", "Catalogs.Заметки"),
      new PackParent("ssl_3_1", "Documents.Анкета"),
      new PackParent("ssl_3_1", "InformationRegisters.ЭлектронныеПодписи"),
      new PackParent("ssl_3_1", "InformationRegisters.СклоненияПредставленийОбъектов"),
      new PackParent("ssl_3_1", "Enums.СтатусыОбработчиковОбновления"),
      // ssl_3_2
      new PackParent("ssl_3_2", "Catalogs.ВерсииФайлов"),
      new PackParent("ssl_3_2", "Catalogs.Заметки"),
      new PackParent("ssl_3_2", "Catalogs.РассылкиОтчетов"),
      new PackParent("ssl_3_2", "Documents.Анкета"),
      new PackParent("ssl_3_2", "DocumentJournals.Взаимодействия"),
      new PackParent("ssl_3_2", "InformationRegisters.ЭлектронныеПодписи"),
      new PackParent("ssl_3_2", "InformationRegisters.СклоненияПредставленийОбъектов"),
      new PackParent("ssl_3_2", "Enums.СтатусыОбработчиковОбновления")
    );
  }

  private static List<PackCommonForm> commonForms() {
    return List.of(
      new PackCommonForm("mdclasses", "CommonForms.Форма"),
      new PackCommonForm("ssl_3_1", "CommonForms.Вопрос"),
      new PackCommonForm("ssl_3_2", "CommonForms.Вопрос"),
      new PackCommonForm("ssl_3_2", "CommonForms.ФормаНастроекОтчета"),
      new PackCommonForm("ssl_3_2", "CommonForms.ФормаОтчета")
    );
  }

  @Test
  @SneakyThrows
  void generateAll() {
    // ObjectForm (children)
    for (var pp : parents()) {
      var parent = Fixtures.get(pp.pack, pp.parentRef, true);
      if (parent == null) {
        System.out.println("SKIP " + pp.pack + "/" + pp.parentRef + " — parent is null");
        continue;
      }

      if (!(parent instanceof ChildrenOwner childrenOwner)) {
        continue;
      }
      var forms = childrenOwner.getPlainChildren().stream()
        .filter(ObjectForm.class::isInstance)
        .map(ObjectForm.class::cast)
        .toList();

      for (var form : forms) {
        writeFormDataFixture(pp.pack, form);
      }
    }

    // CommonForm (top-level)
    for (var cf : commonForms()) {
      var mdo = Fixtures.get(cf.pack, cf.commonFormRef, true);
      if (mdo == null) {
        System.out.println("SKIP " + cf.pack + "/" + cf.commonFormRef + " — mdo is null");
        continue;
      }
      writeFormDataFixture(cf.pack, (CommonForm) mdo);
    }

    System.out.println("Done. Generated formdata fixtures in " + FIXTURES_BASE);
  }

  @SneakyThrows
  private static void writeFormDataFixture(String pack, Form form) {
    var formRef = form.getMdoReference().getMdoRef();
    if (formRef.isEmpty()) {
      System.out.println("  SKIP (empty ref)");
      return;
    }

    var data = form.getData();
    var dir = FIXTURES_BASE.resolve(pack).resolve("formdata");
    Files.createDirectories(dir);
    var filePath = dir.resolve(formRef + ".json");
    Fixtures.write(data, filePath);
    System.out.println("  " + pack + "/formdata/" + formRef + ".json");
  }

  private record PackParent(String pack, String parentRef) {
  }

  private record PackCommonForm(String pack, String commonFormRef) {
  }
}
