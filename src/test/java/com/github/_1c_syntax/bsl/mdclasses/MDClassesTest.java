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
package com.github._1c_syntax.bsl.mdclasses;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class MDClassesTest {

  @Test
  void createConfigurations() {
    var srcPath = Paths.get("src/test/resources/ext");
    var mdcs = MDClasses.createConfigurations(srcPath);
    assertThat(mdcs).hasSize(15);

    // каталоги с обработками не читаются
    srcPath = Paths.get("src/test/resources/ext/edt/external");
    mdcs = MDClasses.createConfigurations(srcPath);
    assertThat(mdcs).isEmpty();
  }

  @Test
  void createExternalSources() {
    var srcPath = Paths.get("src/test/resources/ext");
    var mdcs = MDClasses.createExternalSources(srcPath);
    assertThat(mdcs).hasSize(4);

    // каталоги с конфигурацией не читаются
    srcPath = Paths.get("src/test/resources/ext/edt/ssl_3_1");
    mdcs = MDClasses.createExternalSources(srcPath);
    assertThat(mdcs).isEmpty();
  }

  @Test
  void create() {
    var srcPath = Paths.get("src/test/resources/ext");
    var mdcs = MDClasses.create(srcPath);
    assertThat(mdcs).hasSize(19);
  }
}
