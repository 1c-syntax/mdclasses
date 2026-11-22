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
package com.github._1c_syntax.bsl.mdo.collect;

import com.github._1c_syntax.bsl.test_utils.Fixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Сериализация фикстур не должна зависеть от того, сериализовался ли дочерний объект раньше родителя.
 */
class FixtureSerializationStabilityTest {

  @Test
  void serializingChildDoesNotChangeParentJson() {
    var parentRef = "ExternalDataSources.ТекущаяСУБД";
    var childRef = "ExternalDataSource.ТекущаяСУБД.Table.ИнформацияОбОшибках";

    var before = Fixtures.asJson(Fixtures.get("mdclasses", parentRef, true));

    ChildFixtureComparisonTest.serializeChild("mdclasses", parentRef, childRef, true);

    assertThat(Fixtures.asJson(Fixtures.get("mdclasses", parentRef, true))).isEqualTo(before);
  }
}
