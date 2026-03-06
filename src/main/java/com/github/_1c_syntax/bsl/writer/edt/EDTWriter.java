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
import java.nio.file.StandardCopyOption;

/**
 * Запись объектов метаданных в формате EDT (.mdo).
 * Поддерживаются типы: Subsystem, Catalog, Configuration.
 * Запись выполняется во временный файл с последующей атомарной заменой целевого файла.
 */
@Slf4j
public class EDTWriter {

  private final XStream xstream;
  private final MDCWriteSettings writeSettings;

  /**
   * Создаёт писатель EDT с заданными настройками.
   *
   * @param writeSettings настройки записи (кодировка и др.); null заменяется на {@link MDCWriteSettings#DEFAULT}
   */
  public EDTWriter(MDCWriteSettings writeSettings) {
    this.writeSettings = writeSettings != null ? writeSettings : MDCWriteSettings.DEFAULT;
    this.xstream = createXStream();
  }

  /**
   * Записывает объект в файл .mdo.
   *
   * @param path   Путь к файлу .mdo (родительские каталоги создаются при необходимости)
   * @param object Объект метаданных (Subsystem, Catalog или Configuration)
   * @throws IOException                  при ошибке записи
   * @throws IllegalArgumentException    если path или object равен null, либо тип object не поддерживается
   */
  public void write(Path path, Object object) throws IOException {
    if (path == null) {
      throw new IllegalArgumentException("path must not be null");
    }
    if (object == null) {
      throw new IllegalArgumentException("object must not be null");
    }
    if (!(object instanceof Subsystem) && !(object instanceof Catalog) && !(object instanceof Configuration)) {
      throw new IllegalArgumentException(
        "EDT write supports only Subsystem, Catalog, Configuration, got: " + object.getClass().getName());
    }
    Path parent = path.getParent();
    if (parent != null && !Files.exists(parent)) {
      Files.createDirectories(parent);
    }
    Path tempDir = parent != null ? parent : path.getFileSystem().getPath(".");
    Path tempFile = Files.createTempFile(tempDir, "mdw", ".mdo");
    try {
      var charset = Charset.forName(writeSettings.encoding());
      try (Writer writer = new OutputStreamWriter(Files.newOutputStream(tempFile), charset)) {
        writer.write("<?xml version=\"1.0\" encoding=\"");
        writer.write(writeSettings.encoding());
        writer.write("\"?>\n");
        var prettyWriter = new PrettyPrintWriter(writer);
        xstream.marshal(object, prettyWriter);
      }
      Files.move(tempFile, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    } finally {
      if (Files.exists(tempFile)) {
        try {
          Files.delete(tempFile);
        } catch (IOException ignored) {
          // best effort cleanup
        }
      }
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
