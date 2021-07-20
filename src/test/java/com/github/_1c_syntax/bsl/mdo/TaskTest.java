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

class TaskTest extends AbstractMDObjectTest<Task> {
  TaskTest() {
    super(Task.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "Задача1,c251fcec-ec02-4ef4-8f70-4d70db6631ea,,,Task,Задача,1,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("Tasks/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.TASK, argumentsAccessor);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "Задача1,c251fcec-ec02-4ef4-8f70-4d70db6631ea,,,Task,Задача,2,1,1,1,1,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("Tasks/" + name + "/" + name);
    mdoTest(mdo, MDOType.TASK, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "Реквизит", "9ec8c522-8479-4733-b120-699dc9fe6018");

    checkChildField(mdo.getForms().get(0),
      "Форма", "d7cd2a53-f2a3-4c49-bf19-7762b0565a42");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "187ca6a7-be26-48e0-8b48-1fb79a797c0d");

    checkChildField(mdo.getCommands().get(0),
      "Команда", "7eab1b3a-f23c-4185-9b81-a5c757c5507c");

    checkChildField(mdo.getTabularSections().get(0),
      "ТабличнаяЧасть", "8b67da9e-2ce2-42db-a12a-d21f9a563afe");
  }
}
