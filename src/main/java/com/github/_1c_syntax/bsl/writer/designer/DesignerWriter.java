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
package com.github._1c_syntax.bsl.writer.designer;

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
 * Запись объектов метаданных в формате Конфигуратора (Designer .xml).
 * Поддерживаются только типы: Subsystem, Catalog, Configuration.
 * ConfigurationExtension и другие реализации CF в данной версии не поддерживаются.
 */
@Slf4j
public class DesignerWriter {

  private static final String MD_NS = "http://v8.1c.ru/8.3/MDClasses";
  private static final String V8_NS = "http://v8.1c.ru/8.1/data/core";

  private final XStream xstream;
  private final MDCWriteSettings writeSettings;

  /**
   * Создаёт писатель Designer XML с заданными настройками.
   *
   * @param writeSettings настройки записи (кодировка и др.); null заменяется на {@link MDCWriteSettings#DEFAULT}
   */
  public DesignerWriter(MDCWriteSettings writeSettings) {
    this.writeSettings = writeSettings != null ? writeSettings : MDCWriteSettings.DEFAULT;
    this.xstream = createXStream();
  }

  /**
   * Записывает объект в файл .xml (формат Конфигуратора).
   *
   * @param path   Путь к файлу .xml (родительские каталоги создаются при необходимости)
   * @param object Объект метаданных (поддерживаются только Subsystem, Catalog, Configuration)
   * @throws IOException при ошибке записи
   * @throws UnsupportedOperationException если тип object не Subsystem, Catalog или Configuration
   */
  public void write(Path path, Object object) throws IOException {
    if (object == null) {
      throw new IllegalArgumentException("object must not be null");
    }
    if (!(object instanceof Subsystem) && !(object instanceof Catalog) && !(object instanceof Configuration)) {
      throw new UnsupportedOperationException(
        "Designer write supports only Subsystem, Catalog, Configuration, got: " + object.getClass().getName());
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
      writer.write("<MetaDataObject xmlns=\"" + MD_NS + "\" xmlns:v8=\"" + V8_NS + "\">\n");
      var prettyWriter = new PrettyPrintWriter(writer);
      xstream.marshal(object, prettyWriter);
      writer.write("\n</MetaDataObject>");
    }
  }

  private XStream createXStream() {
    var driver = new StaxDriver();
    var x = new XStream(driver);
    x.alias("Subsystem", Subsystem.class);
    x.alias("Catalog", Catalog.class);
    x.alias("Configuration", Configuration.class);
    x.registerConverter(new SubsystemDesignerWriteConverter(), XStream.PRIORITY_VERY_HIGH);
    x.registerConverter(new CatalogDesignerWriteConverter(), XStream.PRIORITY_VERY_HIGH);
    x.registerConverter(new ConfigurationDesignerWriteConverter(), XStream.PRIORITY_VERY_HIGH);
    return x;
  }
}
