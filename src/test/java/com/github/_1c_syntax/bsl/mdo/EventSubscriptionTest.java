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

import static org.assertj.core.api.Assertions.assertThat;

class EventSubscriptionTest extends AbstractMDObjectTest<EventSubscription> {
  EventSubscriptionTest() {
    super(EventSubscription.class);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "ПодпискаНаСобытие1,4da21a7b-3d07-4e6d-b91f-7e1c8ddcffcd,,,EventSubscription,ПодпискаНаСобытие,0,0,0,0,0,0"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("EventSubscriptions/" + name + "/" + name);
    mdoTest(mdo, MDOType.EVENT_SUBSCRIPTION, argumentsAccessor);
    assertThat(mdo.getHandler().getMethodName()).isEqualTo("ПодпискаНаСобытие1ПередЗаписью");
  }
}
