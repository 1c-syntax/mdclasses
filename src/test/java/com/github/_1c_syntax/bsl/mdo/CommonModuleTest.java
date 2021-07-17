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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommonModuleTest extends AbstractMDObjectTest<CommonModule> {
  CommonModuleTest() {
    super(CommonModule.class);
  }

  @Test
  void test() {
    var mdo = getMDObject("CommonModules/ГлобальныйОбщийМодуль");
    checkBaseField(mdo, MDOType.COMMON_MODULE,
      "ГлобальныйОбщийМодуль", "9e9c021c-bdbd-4804-a53a-9442ba9eb18c",
      ObjectBelonging.OWN);
    assertThat(mdo.isClientManagedApplication()).isTrue();
    assertThat(mdo.isClientOrdinaryApplication()).isTrue();
    assertThat(mdo.isGlobal()).isTrue();
    assertThat(mdo.isPrivileged()).isFalse();
    assertThat(mdo.isServer()).isTrue();
    assertThat(mdo.isExternalConnection()).isTrue();
    assertThat(mdo.isServerCall()).isFalse();
    assertThat(mdo.getModuleType()).isEqualTo(ModuleType.CommonModule);
    assertThat(mdo.getUri().getPath())
      .endsWith("src/test/resources/metadata/original/CommonModules/ГлобальныйОбщийМодуль/Ext/Module.bsl");
  }

  @Test
  void test2() {
    var mdo = getMDObjectEDT("CommonModules/ОбщийМодульПолныйеПрава/ОбщийМодульПолныйеПрава");
    checkBaseField(mdo, MDOType.COMMON_MODULE,
      "ОбщийМодульПолныйеПрава", "5733cabc-1b65-48f8-9ab6-269f1b73a21c",
      ObjectBelonging.OWN);
    assertThat(mdo.isClientManagedApplication()).isFalse();
    assertThat(mdo.isClientOrdinaryApplication()).isFalse();
    assertThat(mdo.isGlobal()).isFalse();
    assertThat(mdo.isPrivileged()).isTrue();
    assertThat(mdo.isServer()).isTrue();
    assertThat(mdo.isExternalConnection()).isFalse();
    assertThat(mdo.isServerCall()).isTrue();
    assertThat(mdo.getModuleType()).isEqualTo(ModuleType.CommonModule);
    assertThat(mdo.getUri().getPath())
      .endsWith("src/test/resources/metadata/edt/src/CommonModules/ОбщийМодульПолныйеПрава/Module.bsl");
  }

  @Test
  void test3() {
    var mdo = getMDObject("CommonModules/ПростойОбщийМодуль");
    checkBaseField(mdo, MDOType.COMMON_MODULE,
      "ПростойОбщийМодуль", "1be4af7e-334e-49fa-a9f9-d80c737ff954",
      ObjectBelonging.OWN);
    assertThat(mdo.isClientManagedApplication()).isFalse();
    assertThat(mdo.isClientOrdinaryApplication()).isFalse();
    assertThat(mdo.isGlobal()).isFalse();
    assertThat(mdo.isPrivileged()).isFalse();
    assertThat(mdo.isServer()).isTrue();
    assertThat(mdo.isExternalConnection()).isFalse();
    assertThat(mdo.isServerCall()).isFalse();
    assertThat(mdo.getModuleType()).isEqualTo(ModuleType.CommonModule);
    assertThat(mdo.getUri().getPath())
      .endsWith("src/test/resources/metadata/original/CommonModules/ПростойОбщийМодуль/Ext/Module.bsl");
  }

  @Test
  void test4() {
    var mdo = getMDObject("CommonModules/ОбщийМодульПовтИспСеанс");
    checkBaseField(mdo, MDOType.COMMON_MODULE,
      "ОбщийМодульПовтИспСеанс", "a09f73b1-0058-4f3b-a22b-799ef3c2a33d",
      ObjectBelonging.OWN);
    assertThat(mdo.isClientManagedApplication()).isFalse();
    assertThat(mdo.isClientOrdinaryApplication()).isFalse();
    assertThat(mdo.isGlobal()).isFalse();
    assertThat(mdo.isPrivileged()).isFalse();
    assertThat(mdo.isServer()).isTrue();
    assertThat(mdo.isExternalConnection()).isFalse();
    assertThat(mdo.isServerCall()).isFalse();
    assertThat(mdo.getModuleType()).isEqualTo(ModuleType.CommonModule);
    assertThat(mdo.getUri().getPath())
      .endsWith("src/test/resources/metadata/original/CommonModules/ОбщийМодульПовтИспСеанс/Ext/Module.bsl");
  }
}
