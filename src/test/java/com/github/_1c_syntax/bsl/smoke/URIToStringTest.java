/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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
package com.github._1c_syntax.bsl.smoke;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class URIToStringTest {

  private static final String OS = System.getProperty("os.name").toLowerCase();
  private static final String JDKVersion = System.getProperty("java.version").toLowerCase();

  @ParameterizedTest
  @CsvSource(
    {
      "src\\Йй.file",
      "src/Йй.file"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    final var workdir = Path.of("").toUri().getPath();
    var fixture = argumentsAccessor.getString(0)
      .replace("\\", "/"); // for win paths;

    var uri = Path.of(fixture).toUri();

    assertThat(workdir)
      .isSubstringOf(uri.getPath())
      .isSubstringOf(uri.toString());

    var bytes = uri.getPath()
      .replace(workdir, "")
      .replace("\\", "/") // for win path
      .getBytes(StandardCharsets.UTF_8);
    StringBuilder sb = new StringBuilder();
    sb.append("[ ");
    for (byte b : bytes) {
      sb.append(String.format("0x%02X ", b));
    }
    sb.append("]");

    if (isMac() && !isJDKOv20()) {
      assertThat(sb)
        .hasToString("[ 0x73 0x72 0x63 0x2F 0xD0 0x98 0xCC 0x86 0xD0 0xB8 0xCC 0x86 0x2E 0x66 0x69 0x6C 0x65 ]");
    } else {
      assertThat(sb)
        .hasToString("[ 0x73 0x72 0x63 0x2F 0xD0 0x99 0xD0 0xB9 0x2E 0x66 0x69 0x6C 0x65 ]");
    }
  }

  private static boolean isMac() {
    return OS.contains("mac");
  }

  private static boolean isJDKOv20() {
    return JDKVersion.compareTo("20") > 0;
  }
}
