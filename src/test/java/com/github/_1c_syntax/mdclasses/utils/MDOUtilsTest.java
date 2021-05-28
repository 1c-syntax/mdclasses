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
package com.github._1c_syntax.mdclasses.utils;

import com.github._1c_syntax.mdclasses.common.ConfigurationSource;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class MDOUtilsTest {

  @Test
  void getConfigurationSourceByPath() {
    var source = MDOUtils.getConfigurationSourceByPath(Paths.get("src/test/resources/metadata/edt").toAbsolutePath());
    assertThat(source).isEqualTo(ConfigurationSource.EDT);

    source = MDOUtils.getConfigurationSourceByPath(Paths.get("src/test/resources/metadata/original").toAbsolutePath());
    assertThat(source).isEqualTo(ConfigurationSource.DESIGNER);

    source = MDOUtils.getConfigurationSourceByPath(Paths.get("src/test/resources/metadata").toAbsolutePath());
    assertThat(source).isEqualTo(ConfigurationSource.EMPTY);
  }
}
