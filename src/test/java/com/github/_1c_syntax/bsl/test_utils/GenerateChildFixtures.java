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
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.children.EnumValue;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Генератор JSON-фикстур для дочерних объектов MDO.
 * <p>
 * Загружает родительские MDO из тестовых пэков,
 * обходит детей, и сохраняет JSON-фикстуры.
 * <p>
 * Ограничения:
 * <ul>
 *   <li>атрибуты (custom + standard): не более 2 на объект на пак</li>
 *   <li>значения перечислений: не более 2 на пак</li>
 *   <li>вложенные атрибуты ТЧ: без ограничений</li>
 * </ul>
 */
@Disabled
class GenerateChildFixtures {

  private static final Path FIXTURES_BASE = Fixtures.FIXTURES_PATH;

  /**
   * Счётчики для ограничения фикстур одного типа в рамках одного (pack, parentRef).
   */
  private static final Map<String, Integer> attributeCounts = new HashMap<>();
  private static final Map<String, Integer> enumValueCounts = new HashMap<>();
  private static final int MAX_ATTRIBUTES = 2;
  private static final int MAX_ENUM_VALUES = 2;

  private static final Set<String> LIMIT_TYPES = Set.of(
    "ObjectAttribute", "StandardAttribute"
  );

  private static String limitKey(String pack, String parentRef) {
    return pack + "::" + parentRef;
  }

  /**
   * Перечень (pack, parentRef) для генерации фикстур.
   */
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
      new PackParent("mdclasses", "WebServices.WebСервис1"),
      new PackParent("mdclasses", "HTTPServices.HTTPСервис1"),
      new PackParent("mdclasses", "ExternalDataSources.ТекущаяСУБД"),
      new PackParent("mdclasses", "DataProcessors.Обработка1"),
      new PackParent("mdclasses", "Reports.Отчет1"),
      new PackParent("mdclasses", "Sequences.Последовательность1"),
      new PackParent("mdclasses", "Subsystems.ПерваяПодсистема"),
      new PackParent("mdclasses", "ExchangePlans.ПланОбмена1"),
      new PackParent("mdclasses", "BusinessProcesses.БизнесПроцесс1"),
      new PackParent("mdclasses", "Constants.Константа1"),
      new PackParent("mdclasses", "SessionParameters.ПараметрСеанса1"),
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
      new PackParent("ssl_3_2", "Documents.Анкета"),
      new PackParent("ssl_3_2", "InformationRegisters.ЭлектронныеПодписи"),
      new PackParent("ssl_3_2", "InformationRegisters.СклоненияПредставленийОбъектов"),
      new PackParent("ssl_3_2", "Enums.СтатусыОбработчиковОбновления")
    );
  }

  @Test
  @SneakyThrows
  void generateAll() {
    for (var pp : parents()) {
      var parent = Fixtures.get(pp.pack, pp.parentRef, true);
      if (parent == null) {
        System.out.println("SKIP " + pp.pack + "/" + pp.parentRef + " — parent is null");
        continue;
      }

      var children = getPlainChildren(parent);
      for (var child : children) {
        if (!shouldGenerate(pp.pack, pp.parentRef, child)) {
          continue;
        }
        writeFixture(pp.pack, child);
      }
    }
    System.out.println("Done. Generated fixtures in " + FIXTURES_BASE);
  }

  private static List<MD> getPlainChildren(MD parent) {
    if (parent instanceof ChildrenOwner owner) {
      return owner.getPlainChildren();
    }
    return List.of();
  }

  /**
   * Определяет, нужно ли генерировать фикстуру для данного ребёнка.
   */
  private static boolean shouldGenerate(String pack, String parentRef, MD child) {
    var childRef = child.getMdoReference().getMdoRef();
    if (childRef.isEmpty()) {
      return false;
    }

    var simpleName = child.getClass().getSimpleName();

    // Лимит атрибутов (custom + standard): не более MAX_ATTRIBUTES на объект на пак
    if (LIMIT_TYPES.contains(simpleName)) {
      var key = limitKey(pack, parentRef);
      var count = attributeCounts.getOrDefault(key, 0);
      if (count >= MAX_ATTRIBUTES) {
        return false;
      }
      attributeCounts.put(key, count + 1);
    }

    // Лимит значений перечислений: не более MAX_ENUM_VALUES на пак
    if (child instanceof EnumValue) {
      var key = limitKey(pack, "Enums");
      var count = enumValueCounts.getOrDefault(key, 0);
      if (count >= MAX_ENUM_VALUES) {
        return false;
      }
      enumValueCounts.put(key, count + 1);
    }

    return true;
  }

  @SneakyThrows
  private static void writeFixture(String pack, MD child) {
    var childRef = child.getMdoReference().getMdoRef();
    var dir = FIXTURES_BASE.resolve(pack).resolve("children");
    Files.createDirectories(dir);
    var filePath = dir.resolve(childRef + ".json");
    Fixtures.write(child, filePath);
    System.out.println("  " + pack + "/children/" + childRef + ".json");
  }

  private record PackParent(String pack, String parentRef) {
  }
}
