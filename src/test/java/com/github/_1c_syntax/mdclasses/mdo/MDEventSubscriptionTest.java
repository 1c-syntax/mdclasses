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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDEventSubscriptionTest extends AbstractMDOTest {
  MDEventSubscriptionTest() {
    super(MDOType.EVENT_SUBSCRIPTION);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("EventSubscriptions/ПодпискаНаСобытие1/ПодпискаНаСобытие1.mdo");
    checkBaseField(mdo, MDEventSubscription.class, "ПодпискаНаСобытие1",
      "4da21a7b-3d07-4e6d-b91f-7e1c8ddcffcd");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDEventSubscription) mdo).getHandler())
      .isEqualTo("CommonModule.ПростойОбщийМодуль.ПодпискаНаСобытие1ПередЗаписью");
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectEDT("EventSubscriptions/ПодпискаНаСобытие1/ПодпискаНаСобытие1.mdo");
    checkBaseField(mdo, MDEventSubscription.class, "ПодпискаНаСобытие1",
      "4da21a7b-3d07-4e6d-b91f-7e1c8ddcffcd");
    checkNoChildren(mdo);
    checkNoModules(mdo);

    assertThat(((MDEventSubscription) mdo).getHandler())
      .isEqualTo("CommonModule.ПростойОбщийМодуль.ПодпискаНаСобытие1ПередЗаписью");
  }
}