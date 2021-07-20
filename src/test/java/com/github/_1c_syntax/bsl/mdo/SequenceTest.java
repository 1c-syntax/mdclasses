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

import java.util.List;

class SequenceTest extends AbstractMDObjectTest<Sequence> {
  SequenceTest() {
    super(Sequence.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "Последовательность1,514bbcf4-7fc4-4a3e-9245-598fad397eec,,,Sequence,Последовательность,1,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("Sequences/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.SEQUENCE, argumentsAccessor);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "Последовательность1,514bbcf4-7fc4-4a3e-9245-598fad397eec,,,Sequence,Последовательность,1,0,0,0,0,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("Sequences/" + name + "/" + name);
    mdoTest(mdo, MDOType.SEQUENCE, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "Измерение1", "763b82dd-2fdb-4a02-a50b-3eb916c02d3d",
      List.of("passwordMode", "denyIncompleteValues", "master"));
  }
}
