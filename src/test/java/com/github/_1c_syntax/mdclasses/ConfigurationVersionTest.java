/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationVersionTest {

  @Test
  void testClass() {

    String version8_3_10 = "Version_8_3_10";
    String versionDontUse = "DontUse";

    CompatibilityMode version;

    version = new CompatibilityMode(3, 99);
    assertThat(version.getMajor()).isEqualTo(8);
    assertThat(version.getMinor()).isEqualTo(3);
    assertThat(version.getVersion()).isEqualTo(99);

    version = new CompatibilityMode(versionDontUse);
    assertThat(version.getMajor()).isEqualTo(8);
    assertThat(version.getMinor()).isEqualTo(3);
    assertThat(version.getVersion()).isEqualTo(99);

    version = new CompatibilityMode(version8_3_10);
    assertThat(version.getMajor()).isEqualTo(8);
    assertThat(version.getMinor()).isEqualTo(3);
    assertThat(version.getVersion()).isEqualTo(10);

  }

  @Test
  void test_compareTo() {

    CompatibilityMode versionA = new CompatibilityMode(3, 10);
    CompatibilityMode versionB = new CompatibilityMode(3, 11);

    assertThat(CompatibilityMode.compareTo(versionA, versionB)).isEqualTo(1);
    assertThat(CompatibilityMode.compareTo(versionB, versionA)).isEqualTo(-1);
    assertThat(CompatibilityMode.compareTo(versionA, new CompatibilityMode(3, 10))).isEqualTo(0);
    assertThat(CompatibilityMode.compareTo(versionA, "Version_8_3_10")).isEqualTo(0);

  }

}
