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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

  MdoReference baseRef = MdoReference.create("Configuration.МояКонфигурация");
  MdoReference extRef = MdoReference.create("Catalog.Расширение1");
  MdoReference objRef = MdoReference.create("Catalog.Товары");

  @Test
  void testDelegationToEffective() {
    var base = Configuration.EMPTY;
    var solution = Solution.builder()
      .baseConfiguration(base)
      .extensions(List.of())
      .mergedConfiguration(base)
      .provenance(Map.of())
      .build();
    assertThat(solution.getBaseConfiguration()).isEqualTo(base);
    assertThat(solution.isEmpty()).isTrue();
  }

  @Test
  void testGetProvenance() {
    var base = Configuration.EMPTY;
    var prov = ObjectProvenance.builder()
      .ownerRef(baseRef)
      .objectBelonging(ObjectBelonging.OWN)
      .build();
    var solution = Solution.builder()
      .baseConfiguration(base)
      .mergedConfiguration(base)
      .provenance(Map.of(objRef, prov))
      .build();
    assertThat(solution.getProvenance(objRef)).isEqualTo(prov);
    assertThat(solution.getOwnerRef(objRef)).isEqualTo(baseRef);
  }

  @Test
  void testGetProvenanceMissingReturnsNull() {
    var solution = Solution.builder()
      .mergedConfiguration(Configuration.EMPTY)
      .provenance(Map.of())
      .build();
    assertThat(solution.getProvenance(objRef)).isNull();
  }

  @Test
  void testGetOwnerReturnsConfiguration() {
    var base = Configuration.builder()
      .mdoReference(baseRef)
      .name("МояКонфигурация")
      .build();
    var prov = ObjectProvenance.builder().ownerRef(baseRef).build();
    var solution = Solution.builder()
      .baseConfiguration(base)
      .mergedConfiguration(base)
      .provenance(Map.of(objRef, prov))
      .build();
    assertThat(solution.getOwner(objRef)).isSameAs(base);
  }

  @Test
  void testGetOwnerReturnsExtension() {
    var base = Configuration.EMPTY;
    var ext = ConfigurationExtension.builder()
      .mdoReference(extRef)
      .name("Расширение1")
      .build();
    var prov = ObjectProvenance.builder().ownerRef(extRef).build();
    var solution = Solution.builder()
      .baseConfiguration(base)
      .extensions(List.of(ext))
      .mergedConfiguration(base)
      .provenance(Map.of(objRef, prov))
      .build();
    assertThat(solution.getOwner(objRef)).isSameAs(ext);
  }
}
