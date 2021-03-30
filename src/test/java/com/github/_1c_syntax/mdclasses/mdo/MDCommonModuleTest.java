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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import com.github._1c_syntax.mdclasses.mdo.support.ReturnValueReuse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDCommonModuleTest extends AbstractMDOTest {

  MDCommonModuleTest() {
    super(MDOType.COMMON_MODULE);
  }

  @Override
  @Test
  void testEDT() {

    var mdo = getMDObjectEDT("CommonModules/ГлобальныйОбщийМодуль/ГлобальныйОбщийМодуль.mdo");
    checkBaseField(mdo, MDCommonModule.class, "ГлобальныйОбщийМодуль",
      "9e9c021c-bdbd-4804-a53a-9442ba9eb18c");
    checkNoChildren(mdo);
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1,
      "CommonModules/Глобальны", ModuleType.CommonModule);

    var commonModule = (MDCommonModule) mdo;
    assertThat(commonModule.isClientManagedApplication()).isTrue();
    assertThat(commonModule.isClientOrdinaryApplication()).isTrue();
    assertThat(commonModule.isExternalConnection()).isTrue();
    assertThat(commonModule.isGlobal()).isTrue();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);

    commonModule = (MDCommonModule) getMDObjectEDT("CommonModules/ПростойОбщийМодуль/ПростойОбщийМодуль.mdo");
    assertThat(commonModule.isClientManagedApplication()).isFalse();
    assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
    assertThat(commonModule.isExternalConnection()).isFalse();
    assertThat(commonModule.isGlobal()).isFalse();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);

    commonModule = (MDCommonModule) getMDObjectEDT("CommonModules/ОбщийМодульПовтИспСеанс/ОбщийМодульПовтИспСеанс.mdo");
    assertThat(commonModule.isClientManagedApplication()).isFalse();
    assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
    assertThat(commonModule.isExternalConnection()).isFalse();
    assertThat(commonModule.isGlobal()).isFalse();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DURING_SESSION);

  }

  @Override
  @Test
  void testDesigner() {

    var mdo = getMDObjectDesigner("CommonModules/ГлобальныйОбщийМодуль.xml");
    checkBaseField(mdo, MDCommonModule.class, "ГлобальныйОбщийМодуль",
      "9e9c021c-bdbd-4804-a53a-9442ba9eb18c");
    checkNoChildren(mdo);
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1,
      "CommonModules/Глобальны", ModuleType.CommonModule);

    var commonModule = (MDCommonModule) mdo;
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
    assertThat(commonModule.isClientManagedApplication()).isTrue();
    assertThat(commonModule.isClientOrdinaryApplication()).isTrue();
    assertThat(commonModule.isExternalConnection()).isTrue();
    assertThat(commonModule.isGlobal()).isTrue();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();

    commonModule = (MDCommonModule) getMDObjectDesigner("CommonModules/ПростойОбщийМодуль.xml");
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
    assertThat(commonModule.isClientManagedApplication()).isFalse();
    assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
    assertThat(commonModule.isExternalConnection()).isFalse();
    assertThat(commonModule.isGlobal()).isFalse();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();
  }
}
