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
package com.github._1c_syntax.bsl.writer;

import com.github._1c_syntax.bsl.mdclasses.MDCWriteSettings;
import com.github._1c_syntax.bsl.writer.designer.DesignerWriter;
import com.github._1c_syntax.bsl.writer.edt.EDTWriter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Фасад записи объектов метаданных в файлы (EDT .mdo, в перспективе Designer .xml).
 */
@UtilityClass
@Slf4j
public class MDOWriter {

  /**
   * Записывает объект метаданных в файл.
   * Формат определяется по расширению пути: .mdo — EDT.
   *
   * @param path   Путь к файлу (например, .../Subsystems/Name/Name.mdo)
   * @param object Объект метаданных (поддерживается Subsystem для EDT)
   * @throws IOException          при ошибке записи
   * @throws UnsupportedOperationException если формат или тип объекта не поддерживается
   */
  public void writeObject(Path path, Object object) throws IOException {
    writeObject(path, object, MDCWriteSettings.DEFAULT);
  }

  /**
   * Записывает объект метаданных в файл с настройками.
   *
   * @param path         Путь к файлу
   * @param object       Объект метаданных
   * @param writeSettings Настройки записи
   * @throws IOException          при ошибке записи
   * @throws UnsupportedOperationException если формат или тип объекта не поддерживается
   */
  public void writeObject(Path path, Object object, MDCWriteSettings writeSettings) throws IOException {
    if (path == null || object == null) {
      throw new IllegalArgumentException("path and object must not be null");
    }
    if (FilenameUtils.isExtension(path.toString(), "mdo")) {
      var writer = new EDTWriter(writeSettings);
      writer.write(path, object);
    } else if (FilenameUtils.isExtension(path.toString(), "xml")) {
      var writer = new DesignerWriter(writeSettings);
      writer.write(path, object);
    } else {
      throw new UnsupportedOperationException("Write is supported only for EDT (.mdo) or Designer (.xml) format, got: " + path);
    }
  }
}
