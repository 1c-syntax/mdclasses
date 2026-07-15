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
package com.github._1c_syntax.bsl.reader.common.xstream;

import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;

import static org.assertj.core.api.Assertions.assertThat;

class ConcurrentQNameMapTest {

  private static final QName FORM_QNAME = new QName("http://v8.1c.ru/8.3/xcf/logform", "Form");
  private static final String FORM_CLASS = "com.github._1c_syntax.bsl.mdo.storage.form.ManagedFormData";

  @Test
  void mappedQNameResolvesToRegisteredClassAndBack() {
    // given
    var map = new ConcurrentQNameMap();

    // when
    map.registerMapping(FORM_QNAME, FORM_CLASS);

    // then
    assertThat(map.getJavaClassName(FORM_QNAME)).isEqualTo(FORM_CLASS);
    assertThat(map.getQName(FORM_CLASS)).isEqualTo(FORM_QNAME);
  }

  @Test
  void unmappedQNameFallsBackToLocalPart() {
    // given
    var map = new ConcurrentQNameMap();

    // when
    var javaClassName = map.getJavaClassName(new QName("http://ns", "Catalog"));

    // then
    assertThat(javaClassName).isEqualTo("Catalog");
  }

  @Test
  void unmappedClassNameFallsBackToDefaultQName() {
    // given
    var map = new ConcurrentQNameMap();

    // when
    var qName = map.getQName("Catalog");

    // then
    assertThat(qName.getLocalPart()).isEqualTo("Catalog");
    assertThat(qName.getNamespaceURI()).isEqualTo(map.getDefaultNamespace());
  }
}
