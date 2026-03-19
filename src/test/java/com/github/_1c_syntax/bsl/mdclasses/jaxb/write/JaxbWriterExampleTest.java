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
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.MDCReadSettings;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Пример полной записи конфигурации в папку для ручной проверки в Конфигураторе 1С.
 * Результат в {@code build/jaxb-example-output/}: Configuration.xml, Languages/, Subsystems/ (в т.ч. вложенные),
 * Catalogs/, и прочие объекты по дереву конфигурации.
 * <p>
 * Формы справочников записываются в отдельные файлы Catalogs/Имя/Forms/ИмяФормы.xml.
 * <p>
 * Запуск: {@code ./gradlew test --tests "com.github._1c_syntax.bsl.mdclasses.jaxb.write.JaxbWriterExampleTest.writeExampleToBuildDir"}
 */
class JaxbWriterExampleTest {

  private static final Path DESIGNER_CF = Path.of("src/test/resources/ext/designer/mdclasses/src/cf");
  private static final Path OUTPUT_DIR = Path.of("build/jaxb-example-output");

  @Test
  void writeExampleToBuildDir() throws Exception {
    Configuration cf = (Configuration) MDClasses.createConfiguration(DESIGNER_CF, MDCReadSettings.DEFAULT);
    MDClassesJaxbWriter.writeConfigurationToFolder(OUTPUT_DIR, cf);

    Path configPath = OUTPUT_DIR.resolve("Configuration.xml");
    assert Files.exists(configPath) : "Configuration.xml";
    assert Files.exists(OUTPUT_DIR.resolve("Languages").resolve("Русский.xml")) : "Languages/Русский.xml";
    assert Files.exists(OUTPUT_DIR.resolve("Subsystems").resolve("ПерваяПодсистема.xml")) : "Subsystems/ПерваяПодсистема.xml";
    assert Files.exists(OUTPUT_DIR.resolve("Subsystems").resolve("ВтораяПодсистема.xml")) : "Subsystems/ВтораяПодсистема.xml";
    Path nestedSub = OUTPUT_DIR.resolve("Subsystems").resolve("ПерваяПодсистема").resolve("Subsystems").resolve("ПодчиненнаяПодсистема.xml");
    assert Files.exists(nestedSub) : "Subsystems/ПерваяПодсистема/Subsystems/ПодчиненнаяПодсистема.xml";
    assert Files.exists(OUTPUT_DIR.resolve("Catalogs").resolve("Справочник1.xml")) : "Catalogs/Справочник1.xml";
    Path formsDir = OUTPUT_DIR.resolve("Catalogs").resolve("Справочник1").resolve("Forms");
    assert Files.exists(formsDir.resolve("ФормаЭлемента.xml")) : "Catalogs/Справочник1/Forms/ФормаЭлемента.xml";
    assert Files.exists(formsDir.resolve("ФормаСписка.xml")) : "Catalogs/Справочник1/Forms/ФормаСписка.xml";
    assert Files.exists(formsDir.resolve("ФормаВыбора.xml")) : "Catalogs/Справочник1/Forms/ФормаВыбора.xml";
  }
}
