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

import static org.assertj.core.api.Assertions.assertThat;

class ObjectProvenanceTest {

  @Test
  void testDefaultValues() {
    var provenance = ObjectProvenance.builder().build();
    assertThat(provenance.getOwnerRef()).isEqualTo(MdoReference.EMPTY);
    assertThat(provenance.getModifiedByExtensionRefs()).isEmpty();
    assertThat(provenance.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
  }

  @Test
  void testFilledValues() {
    var ownerRef = MdoReference.create("Configuration.МояКонфигурация");
    var extRef = MdoReference.create("Catalog.Расширение1");
    var provenance = ObjectProvenance.builder()
      .ownerRef(ownerRef)
      .modifiedByExtensionRef(extRef)
      .objectBelonging(ObjectBelonging.ADOPTED)
      .build();
    assertThat(provenance.getOwnerRef()).isEqualTo(ownerRef);
    assertThat(provenance.getModifiedByExtensionRefs()).containsExactly(extRef);
    assertThat(provenance.getObjectBelonging()).isEqualTo(ObjectBelonging.ADOPTED);
  }

  @Test
  void testEquality() {
    var ref = MdoReference.create("Configuration.Obj");
    var a = ObjectProvenance.builder().ownerRef(ref).build();
    var b = ObjectProvenance.builder().ownerRef(ref).build();
    assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
  }

  @Test
  void testToString() {
    var provenance = ObjectProvenance.builder().build();
    assertThat(provenance.toString()).contains("ObjectProvenance(");
  }
}
