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
import com.github._1c_syntax.bsl.types.ModuleType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CommonCommandTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, CommonCommands.ОбщаяКоманда1",
      "false, mdclasses, CommonCommands.ОбщаяКоманда1",
      "true, ssl_3_1, CommonCommands.ОтправитьПисьмо",
      "false, ssl_3_1, CommonCommands.ОтправитьПисьмо",
      "true, ssl_3_2, CommonCommands.ОтправитьПисьмо",
      "false, ssl_3_2, CommonCommands.ОтправитьПисьмо"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(CommonCommand.class);

    var commonCommand = (CommonCommand) mdo;
    assertThat(commonCommand).isNotNull();

    var modules = commonCommand.getModules();
    Assertions.assertThat(commonCommand.getAllModules(), false)
      .containsAll(modules);

    assertThat(commonCommand.getModuleTypes())
      .hasSize(modules.size());
    assertThat(modules)
      .allMatch(m -> m.getOwner().equals(commonCommand.getMdoReference()));

    assertThat(commonCommand.getModuleTypes())
      .containsEntry(ModuleType.CommandModule,
        modules.stream()
          .map(Module::getUri)
          .toList());
  }
}
