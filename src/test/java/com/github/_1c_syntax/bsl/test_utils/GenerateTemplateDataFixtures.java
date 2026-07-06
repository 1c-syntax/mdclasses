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

import com.github._1c_syntax.bsl.mdo.CommonTemplate;
import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.mdo.TemplateOwner;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Генератор JSON-фикстур для TemplateData (содержимого макетов).
 * <p>
 * Загружает макеты (ObjectTemplate из родителей + CommonTemplate напрямую),
 * обходит их TemplateData и сохраняет JSON-фикстуры.
 */
@Disabled
class GenerateTemplateDataFixtures {

  private static final Path FIXTURES_BASE = Fixtures.FIXTURES_PATH;

  private static List<PackParent> parents() {
    return List.of(
      // mdclasses
      new PackParent("mdclasses", "Catalogs.Справочник1"),
      new PackParent("mdclasses", "Reports.Отчет1"),
      // ssl_3_1
      new PackParent("ssl_3_1", "DocumentJournals.Взаимодействия"),
      // ssl_3_2
      new PackParent("ssl_3_2", "Catalogs.РассылкиОтчетов"),
      new PackParent("ssl_3_2", "Catalogs.СертификатыКлючейЭлектроннойПодписиИШифрования")
    );
  }

  private static List<PackCommonTemplate> commonTemplates() {
    return List.of(
      // mdclasses
      new PackCommonTemplate("mdclasses", "CommonTemplates.СКД"),
      new PackCommonTemplate("mdclasses", "CommonTemplates.ТабличныйДокумент"),
      new PackCommonTemplate("mdclasses", "CommonTemplates.ТекстовыйДокумент"),
      new PackCommonTemplate("mdclasses", "CommonTemplates.ДвоичныеДанные"),
      // ssl_3_1
      new PackCommonTemplate("ssl_3_1", "CommonTemplates.СтруктураПодчиненности"),
      new PackCommonTemplate("ssl_3_1", "CommonTemplates.ДанныеПечатиОбщиеРеквизиты"),
      // ssl_3_2
      new PackCommonTemplate("ssl_3_2", "CommonTemplates.СтруктураПодчиненности"),
      new PackCommonTemplate("ssl_3_2", "CommonTemplates.ДанныеПечатиОбщиеРеквизиты")
    );
  }

  @Test
  @SneakyThrows
  void generateAll() {
    // ObjectTemplate (children)
    for (var pp : parents()) {
      var parent = Fixtures.get(pp.pack, pp.parentRef, true);
      if (parent == null) {
        System.out.println("SKIP " + pp.pack + "/" + pp.parentRef + " — parent is null");
        continue;
      }

      if (!(parent instanceof TemplateOwner templateOwner)) {
        continue;
      }
      var templates = templateOwner.getTemplates();

      if (templates.isEmpty()) {
        System.out.println("  (no templates) " + pp.pack + "/" + pp.parentRef);
      }

      for (var template : templates) {
        writeTemplateDataFixture(pp.pack, template);
      }
    }

    // CommonTemplate (top-level)
    for (var ct : commonTemplates()) {
      var mdo = Fixtures.get(ct.pack, ct.commonTemplateRef, true);
      if (mdo == null) {
        System.out.println("SKIP " + ct.pack + "/" + ct.commonTemplateRef + " — mdo is null");
        continue;
      }
      writeTemplateDataFixture(ct.pack, (CommonTemplate) mdo);
    }

    System.out.println("Done. Generated templatedata fixtures in " + FIXTURES_BASE);
  }

  @SneakyThrows
  private static void writeTemplateDataFixture(String pack, Template template) {
    var templateRef = template.getMdoReference().getMdoRef();
    if (templateRef.isEmpty()) {
      System.out.println("  SKIP (empty ref)");
      return;
    }

    var data = template.getData();
    var dir = FIXTURES_BASE.resolve(pack).resolve("templatedata");
    Files.createDirectories(dir);
    var filePath = dir.resolve(templateRef + ".json");
    Fixtures.write(data, filePath);
    System.out.println("  " + pack + "/templatedata/" + templateRef + ".json");
  }

  private record PackParent(String pack, String parentRef) {
  }

  private record PackCommonTemplate(String pack, String commonTemplateRef) {
  }
}
