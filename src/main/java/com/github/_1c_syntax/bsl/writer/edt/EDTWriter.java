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
package com.github._1c_syntax.bsl.writer.edt;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.MDCWriteSettings;
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Запись объектов метаданных в формате EDT (.mdo).
 */
@Slf4j
public class EDTWriter {

  private final XStream xstream;
  private final MDCWriteSettings writeSettings;

  public EDTWriter(MDCWriteSettings writeSettings) {
    this.writeSettings = writeSettings != null ? writeSettings : MDCWriteSettings.DEFAULT;
    this.xstream = createXStream();
  }

  /**
   * Записывает объект в файл .mdo.
   *
   * @param path   Путь к файлу .mdo (родительские каталоги создаются при необходимости)
   * @param object Объект метаданных (например, Subsystem)
   * @throws IOException при ошибке записи
   */
  public void write(Path path, Object object) throws IOException {
    if (object == null) {
      throw new IllegalArgumentException("object must not be null");
    }
    Path parent = path.getParent();
    if (parent != null && !Files.exists(parent)) {
      Files.createDirectories(parent);
    }
    var charset = Charset.forName(writeSettings.encoding());
    try (Writer writer = new OutputStreamWriter(Files.newOutputStream(path), charset)) {
      writer.write("<?xml version=\"1.0\" encoding=\"");
      writer.write(writeSettings.encoding());
      writer.write("\"?>\n");
      var prettyWriter = new PrettyPrintWriter(writer);
      xstream.marshal(object, prettyWriter);
    }
  }

  private XStream createXStream() {
    var driver = new StaxDriver();
    var x = new XStream(driver);
    x.alias("mdclass:Subsystem", Subsystem.class);
    x.alias("mdclass:Configuration", Configuration.class);
    x.alias("mdclass:Catalog", Catalog.class);
    x.registerConverter(new SubsystemEdtWriteConverter(), XStream.PRIORITY_VERY_HIGH);
    x.registerConverter(new ConfigurationEdtWriteConverter(), XStream.PRIORITY_VERY_HIGH);
    x.registerConverter(new CatalogEdtWriteConverter(), XStream.PRIORITY_VERY_HIGH);
    return x;
  }
}
