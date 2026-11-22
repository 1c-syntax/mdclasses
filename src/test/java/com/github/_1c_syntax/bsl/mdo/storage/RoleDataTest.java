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
package com.github._1c_syntax.bsl.mdo.storage;

import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class RoleDataTest {

  @Test
  void testEmptyConstant() {
    assertThat(RoleData.EMPTY).isNotNull();
    assertThat(RoleData.EMPTY.objectRights()).isEmpty();
  }

  @Test
  void testBuilderWithEmptyList() {
    var roleData = RoleData.builder().build();

    assertThat(roleData).isNotNull();
    assertThat(roleData.objectRights()).isEmpty();
  }

  @Test
  void testObjectRight() {
    var objectRight = new RoleData.ObjectRight(MdoReference.create("Catalogs.TestObject"), Collections.emptyList());

    assertThat(objectRight).isNotNull();
    assertThat(objectRight.name().getMdoRef()).isEqualTo("Catalog.TestObject");
    assertThat(objectRight.rights()).isEmpty();
  }

  @Test
  void testRight() {
    var right = new RoleData.Right(RoleRight.READ, true, "");

    assertThat(right).isNotNull();
    assertThat(right.name()).isEqualTo(RoleRight.READ);
    assertThat(right.value()).isTrue();
    assertThat(right.restrictionCondition()).isEmpty();
  }

  @Test
  void testRightWithRestriction() {
    var right = new RoleData.Right(RoleRight.READ, true, "ГДЕ ИСТИНА");

    assertThat(right).isNotNull();
    assertThat(right.name()).isEqualTo(RoleRight.READ);
    assertThat(right.value()).isTrue();
    assertThat(right.restrictionCondition()).isEqualTo("ГДЕ ИСТИНА");
  }

  @Test
  void testRestrictionTemplate() {
    var template = new RoleData.RestrictionTemplate("ДляОбъекта(ПолеОбъекта)", "ГДЕ ИСТИНА");

    assertThat(template).isNotNull();
    assertThat(template.name()).isEqualTo("ДляОбъекта(ПолеОбъекта)");
    assertThat(template.condition()).isEqualTo("ГДЕ ИСТИНА");
  }

  @Test
  void testRestrictionTemplateEmpty() {
    var template = new RoleData.RestrictionTemplate("", "");

    assertThat(template).isNotNull();
    assertThat(template.name()).isEmpty();
    assertThat(template.condition()).isEmpty();
  }

  @Test
  void testBuilderWithRestrictionTemplates() {
    var template1 = new RoleData.RestrictionTemplate("ДляОбъекта(ПолеОбъекта)", "ГДЕ ИСТИНА");
    var template2 = new RoleData.RestrictionTemplate("ДляРегистра(Регистр, Поле1)", "ГДЕ ЛОЖЬ");
    var roleData = RoleData.builder()
      .restrictionTemplate(template1)
      .restrictionTemplate(template2)
      .build();

    assertThat(roleData).isNotNull();
    assertThat(roleData.restrictionTemplates()).hasSize(2);
    assertThat(roleData.restrictionTemplates().get(0).name()).isEqualTo("ДляОбъекта(ПолеОбъекта)");
    assertThat(roleData.restrictionTemplates().get(1).name()).isEqualTo("ДляРегистра(Регистр, Поле1)");
  }
}
