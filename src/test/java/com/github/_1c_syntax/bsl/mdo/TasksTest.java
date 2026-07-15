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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class TasksTest {
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Tasks.Задача1",
    "false, mdclasses, Tasks.Задача1",
    "true, ssl_3_1, Tasks.ЗадачаИсполнителя",
    "false, ssl_3_1, Tasks.ЗадачаИсполнителя",
    "true, ssl_3_2, Tasks.ЗадачаИсполнителя",
    "false, ssl_3_2, Tasks.ЗадачаИсполнителя"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Task.class);

    var task = (Task) mdo;
    assertThat(task).isNotNull();

    // --- ModuleOwner ---
    Assertions.assertThat(task.getAllModules(), false)
      .containsAll(task.getModules(),
        task.getForms(),
        task.getCommands());

    // --- ChildrenOwner ---
    Assertions.assertThat(task.getChildren(), false)
      .containsAll(task.getAttributes(),
        task.getTabularSections(),
        task.getForms(),
        task.getCommands(),
        task.getTemplates(),
        task.getAddressingAttributes());
    Assertions.assertThat(task.getPlainChildren(), true)
      .containsAllPlain(task.getAttributes(),
        task.getTabularSections(),
        task.getForms(),
        task.getCommands(),
        task.getTemplates(),
        task.getAddressingAttributes());

    // --- AttributeOwner ---
    Assertions.assertThat(task.getAllAttributes(), false)
      .containsAll(task.getAttributes(), task.getTabularSections());
    Assertions.assertThat(task.getStorageFields(), false)
      .containsAll(task.getAttributes(), task.getTabularSections());
    Assertions.assertThat(task.getPlainStorageFields(), false)
      .containsAllPlain(task.getAttributes(), task.getTabularSections());
  }
}
