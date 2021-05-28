/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.mdclasses.unmarshal;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.ReaderWrapper;

import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.nio.file.Path;

/**
 * Реализация враппера ридера, позволяющего читать в прикладном коде путь к файлу
 */
public class ExtendReaderWrapper extends ReaderWrapper {
  private final File file;
  private final XMLStreamReader xmlStreamReader;

  public ExtendReaderWrapper(HierarchicalStreamReader reader, File in, XMLStreamReader xmlStreamReader) {
    super(reader);
    this.file = in;
    this.xmlStreamReader = xmlStreamReader;
  }

  public Path getPath() {
    return file.toPath();
  }

  public XMLStreamReader getXMLStreamReader() {
    return xmlStreamReader;
  }
}
