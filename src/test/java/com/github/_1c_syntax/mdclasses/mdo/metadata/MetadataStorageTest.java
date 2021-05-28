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
package com.github._1c_syntax.mdclasses.mdo.metadata;

import com.github._1c_syntax.mdclasses.mdo.MDAccountingRegister;
import com.github._1c_syntax.mdclasses.mdo.attributes.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.children.WEBServiceOperation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MetadataStorageTest {

  @Test
  void testGetStorage() {
    var meta = MetadataStorage.getStorage();
    assertThat(meta)
      .isNotEmpty()
      .hasSize(54);

    // проверка на ошибки и дубли
    assertThat(meta.values().stream().map(Metadata::type).distinct()).hasSize(meta.size());
    assertThat(meta.values().stream().map(Metadata::name).distinct()).hasSize(meta.size());
    assertThat(meta.values().stream().map(Metadata::nameRu).distinct()).hasSize(meta.size());
    assertThat(meta.values().stream().map(Metadata::groupName).distinct()).hasSize(50);
    assertThat(meta.values().stream().map(Metadata::groupNameRu).distinct()).hasSize(50);
    assertThat(meta.values().stream().map(Metadata::xmlScope).filter(XMLScope.ALL::equals)).hasSize(53);
    assertThat(meta.values().stream().map(Metadata::xmlScope).filter(XMLScope.DESIGNER::equals)).hasSize(1);
    assertThat(meta.values().stream().map(Metadata::xmlScope).filter(XMLScope.EDT::equals)).isEmpty();
  }

  @Test
  void testGetAttributeStorage() {
    var meta = MetadataStorage.getAttributeStorage();
    assertThat(meta)
      .isNotEmpty()
      .hasSize(10);

    // проверка на ошибки и дубли
    assertThat(meta.values().stream().map(AttributeMetadata::type).distinct()).hasSize(meta.size());
    assertThat(meta.values().stream().map(AttributeMetadata::name).distinct()).hasSize(meta.size());
  }

  @Test
  void testMetadataStorageGet() {
    var metadata = MetadataStorage.get(MDAccountingRegister.class);
    assertThat(metadata).isNotNull();
    assertThat(metadata.name()).isEqualTo("AccountingRegister");
  }

  @Test
  void testClassGet() {
    var mdo = new MDAccountingRegister();
    assertThat(mdo.getMetadataName()).isEqualTo("AccountingRegister");

    var childMdo = new WEBServiceOperation();
    assertThat(childMdo.getMetadataName()).isEqualTo("Operation");

    var attribute = new TabularSection();
    assertThat(attribute.getMetadataName()).isEqualTo("TabularSection");
  }

}