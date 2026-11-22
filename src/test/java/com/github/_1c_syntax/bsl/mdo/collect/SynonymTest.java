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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SynonymTest {

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, BusinessProcesses.БизнесПроцесс1",
      "false, mdclasses, BusinessProcesses.БизнесПроцесс1",
      "true, mdclasses_3_18, IntegrationServices.СервисИнтеграции1",
      "false, mdclasses_3_18, IntegrationServices.СервисИнтеграции1"
    }
  )
  void testEmptySynonym(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);

    assertThat(mdo.getSynonym().isEmpty()).isTrue();
    assertThat(mdo.getSynonym().get("ru")).isEmpty();
    assertThat(mdo.getSynonym().get("en")).isEmpty();
    assertThat(mdo.getDescription()).isEqualTo(mdo.getName());
    assertThat(mdo.getDescription("ru")).isEqualTo(mdo.getName());
    assertThat(mdo.getDescription("en")).isEqualTo(mdo.getName());
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, BusinessProcesses.Задание",
      "false, ssl_3_1, BusinessProcesses.Задание",
      "true, ssl_3_2, BusinessProcesses.Задание",
      "false, ssl_3_2, BusinessProcesses.Задание",
      "true, ssl_3_1, Catalogs.Заметки",
      "false, ssl_3_1, Catalogs.Заметки"
    }
  )
  void testOneLanguageRU(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);

    assertThat(mdo.getSynonym().isEmpty()).isFalse();
    assertThat(mdo.getSynonym().get("ru")).isEqualTo(mdo.getName());
    assertThat(mdo.getSynonym().get("en")).isEmpty();
    assertThat(mdo.getSynonym().getAny()).isEqualTo(mdo.getName());

    assertThat(mdo.getDescription()).isEqualTo(mdo.getName());
    assertThat(mdo.getDescription("ru")).isEqualTo(mdo.getName());
    assertThat(mdo.getDescription("en")).isEqualTo(mdo.getName());
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, AccountingRegisters.РегистрБухгалтерии1",
      "false, mdclasses, AccountingRegisters.РегистрБухгалтерии1"
    }
  )
  void testTwoLanguages(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);

    assertThat(mdo.getSynonym().isEmpty()).isFalse();
    assertThat(mdo.getSynonym().get("ru")).isEqualTo("Регистр бухгалтерии");
    assertThat(mdo.getSynonym().get("en")).isEqualTo("Accounting register");
    assertThat(mdo.getSynonym().get("by")).isEmpty();

    assertThat(mdo.getDescription()).isEqualTo("Регистр бухгалтерии");
    assertThat(mdo.getDescription("ru")).isEqualTo("Регистр бухгалтерии");
    assertThat(mdo.getDescription("en")).isEqualTo("Accounting register");
  }

}
