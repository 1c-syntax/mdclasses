/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
package com.github._1c_syntax.bsl.reader.common.xstream;

import com.github._1c_syntax.bsl.reader.MDReader;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Расширенная модель StaxDriver для возможности при чтении иметь доступ к файлу, который читается
 */
public class ExtendStaxDriver extends StaxDriver {

  private final MDReader mdReader;

  /**
   * Оставлен для совместимости со старой версией читателя. При разделении читателей не потребуется
   */
  public ExtendStaxDriver(MDReader reader, QNameMap qNameMap) {
    super(qNameMap);
    this.mdReader = reader;

    // Do not delete. Implementation via XMLInputFactoryImpl
    System.setProperty("javax.xml.stream.XMLInputFactory", "com.sun.xml.internal.stream.XMLInputFactoryImpl");
  }

  public ExtendStaxDriver(MDReader mdReader) {
    super();
    this.mdReader = mdReader;

    // Do not delete. Implementation via XMLInputFactoryImpl
    System.setProperty("javax.xml.stream.XMLInputFactory", "com.sun.xml.internal.stream.XMLInputFactoryImpl");
  }

  @Override
  public HierarchicalStreamReader createReader(File in) {
    final InputStream stream;
    try {
      stream = new FileInputStream(in);
      var xmlStreamReader = createParser(new StreamSource(
        stream, in.toURI().toASCIIString()));
      var reader = createStaxReader(xmlStreamReader);
      return new ExtendReaderWrapper(reader, in, xmlStreamReader, mdReader) {
        @Override
        public void close() {
          super.close();
          try {
            stream.close();
          } catch (IOException e) {
            // ignore
          }
        }
      };
    } catch (XMLStreamException | FileNotFoundException e) {
      throw new StreamException(e);
    }
  }
}
