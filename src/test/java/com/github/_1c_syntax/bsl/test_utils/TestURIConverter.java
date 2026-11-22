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

import com.github._1c_syntax.utils.Absolute;
import com.thoughtworks.xstream.converters.basic.URIConverter;

import java.nio.file.Path;

/**
 * Для возможности сохранять в фикстурах пути относительно рабочего каталога
 */
public class TestURIConverter extends URIConverter {
  private final static String WOKRDIR = Path.of("", "").toUri().getPath();
  private final static String DESIGNERDIR = Path.of("ext", "designer").toString();
  private final static String EDTDIR = Path.of("ext", "edt").toString();
  private final static String DESIGNERCFDIR = Path.of("src", "cf").toString();
  private final static String EDTCFDIR = Path.of("configuration", "src").toString();
  private final static String DESIGNEREPFDIR = Path.of("src", "epf").toString();
  private final static String DESIGNERERFDIR = Path.of("src", "erf").toString();
  private final static String EDTEPFDIR = Path.of("src", "ExternalDataProcessors").toString();
  private final static String EDTERFDIR = Path.of("src", "ExternalReports").toString();

  @Override
  public String toString(Object obj) {
    return Absolute.uri(obj.toString()
        .replace("%D0%99", "_")
        .replace("%D0%B9", "_")
        .replace("%D0%98%CC%86", "_")
        .replace("%D0%B8%CC%86", "_"))
      .getPath()
      .replace(WOKRDIR, "")
      .replace(EDTDIR, "")
      .replace(DESIGNERDIR, "")
      .replace(DESIGNERCFDIR, "")
      .replace(EDTCFDIR, "")
      .replace(EDTEPFDIR, DESIGNEREPFDIR)
      .replace(EDTERFDIR, DESIGNERERFDIR)
      .replace("/Ext/", "/")
      .replace("\\Ext\\", "\\")
      .replace("/Form/", "/")
      .replace("\\Form\\", "\\")
      .replace("/Template/", "/")
      .replace("\\Template\\", "\\")
      .replace("//", "/")
      .replace("\\\\", "\\")
      .replace(".dcs", ".xml")
      .replace(".bin", ".bsl")
      ;
  }
}
