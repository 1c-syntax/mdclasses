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

class SequenceTest {
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Sequences.Последовательность1",
    "false, mdclasses, Sequences.Последовательность1"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Sequence.class);

    var sequence = (Sequence) mdo;
    assertThat(sequence).isNotNull();

    // --- ModuleOwner ---
    Assertions.assertThat(sequence.getAllModules(), false)
      .containsAll(sequence.getModules());

    // --- ChildrenOwner ---
    Assertions.assertThat(sequence.getChildren(), false)
      .containsAll(sequence.getDimensions());
    Assertions.assertThat(sequence.getPlainChildren(), true)
      .containsAll(sequence.getChildren());

    // --- AttributeOwner ---
    Assertions.assertThat(sequence.getAllAttributes(), false)
      .containsAll(sequence.getDimensions());
    Assertions.assertThat(sequence.getStorageFields(), false)
      .containsAll(sequence.getDimensions());
    Assertions.assertThat(sequence.getPlainStorageFields(), false)
      .containsAll(sequence.getDimensions());
  }
}
