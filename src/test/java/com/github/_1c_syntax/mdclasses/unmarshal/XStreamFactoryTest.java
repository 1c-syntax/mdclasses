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

import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.thoughtworks.xstream.converters.ConversionException;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;

class XStreamFactoryTest {

  private static final String SRC_EDT = "src/test/resources/metadata/edt/src";
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";


  @Test
  void testBrokenXml() {
    var path = Paths.get("src/test/resources/metadata/original_broken/Configuration.xml");
    try {
      MDOFactory.readMDObject(path);
      fail("Expected an ConversionException to be thrown");
    } catch (ConversionException e) {
      // noop
    }
  }

}
