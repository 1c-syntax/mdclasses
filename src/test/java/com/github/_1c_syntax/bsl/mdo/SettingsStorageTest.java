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

import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

class SettingsStorageTest extends AbstractMDObjectTest<SettingsStorage> {
  SettingsStorageTest() {
    super(SettingsStorage.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "ХранилищеНастроек1,e7a9947d-7565-4681-b75c-c9a229b45042,,,SettingsStorage,ХранилищеНастроек,0,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("SettingsStorages/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.SETTINGS_STORAGE, argumentsAccessor);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "ХранилищеНастроек1,e7a9947d-7565-4681-b75c-c9a229b45042,,,SettingsStorage,ХранилищеНастроек,0,0,1,0,1,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("SettingsStorages/" + name + "/" + name);
    mdoTest(mdo, MDOType.SETTINGS_STORAGE, argumentsAccessor);

    checkChildField(mdo.getForms().get(0),
      "ФормаСохраненияНастроек", "d4f043bf-0ebb-4666-aac5-e7172071b5cd");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "76d7af6c-0f2e-4ebf-a051-7df236802ef9");
  }
}
