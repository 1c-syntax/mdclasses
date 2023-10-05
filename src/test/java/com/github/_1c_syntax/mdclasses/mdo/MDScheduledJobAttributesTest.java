/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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

import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.mdo.support.Handler;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDScheduledJobAttributesTest extends AbstractMDOTest {

  MDScheduledJobAttributesTest() {
    super(MDOType.SCHEDULED_JOB);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = (MDScheduledJob) getMDObjectEDT("ScheduledJobs/РегламентноеЗадание1/РегламентноеЗадание1.mdo");
    Handler handler = mdo.getHandler();
    assertThat(handler.getMethodName()).isNotBlank();
    assertThat(handler.getMethodPath()).isNotBlank();
    assertThat(handler.getModuleName()).isNotBlank();

    assertThat(handler.getMethodPath()).isEqualTo("CommonModule.ПростойОбщийМодуль.РегламентноеЗадание1");
    assertThat(handler.getMethodName()).isEqualTo("РегламентноеЗадание1");
    assertThat(handler.getModuleName()).isEqualTo("ПростойОбщийМодуль");

    assertThat(mdo.getDescription()).isEqualTo("Описание Регламентное задание 1");
    assertThat(mdo.getKey()).isEqualTo("ПроверкаАктивностиСеансаУдаленияОбъектов");
    assertThat(mdo.isUse()).isTrue();
    assertThat(mdo.isPredefined()).isTrue();
    assertThat(mdo.getRestartCountOnFailure()).isEqualTo(3);
    assertThat(mdo.getRestartIntervalOnFailure()).isEqualTo(10);
  }

  @Override
  void testDesigner() {
    var mdo = (MDScheduledJob) getMDObjectDesigner("ScheduledJobs/РегламентноеЗадание1.xml");
    Handler handler = mdo.getHandler();
    assertThat(handler.getMethodName()).isNotBlank();
    assertThat(handler.getMethodPath()).isNotBlank();
    assertThat(handler.getModuleName()).isNotBlank();

    assertThat(handler.getMethodPath()).isEqualTo("CommonModule.ПростойОбщийМодуль.РегламентноеЗадание1");
    assertThat(handler.getMethodName()).isEqualTo("РегламентноеЗадание1");
    assertThat(handler.getModuleName()).isEqualTo("ПростойОбщийМодуль");

    assertThat(mdo.getDescription()).isEqualTo("Описание Регламентное задание 1");
    assertThat(mdo.getKey()).isEqualTo("ПроверкаАктивностиСеансаУдаленияОбъектов");
    assertThat(mdo.isUse()).isTrue();
    assertThat(mdo.isPredefined()).isTrue();
    assertThat(mdo.getRestartCountOnFailure()).isEqualTo(3);
    assertThat(mdo.getRestartIntervalOnFailure()).isEqualTo(10);
  }
}
