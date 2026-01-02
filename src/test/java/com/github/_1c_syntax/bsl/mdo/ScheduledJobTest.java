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

import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduledJobTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, ScheduledJobs.ОбновлениеАгрегатов",
      "false, ssl_3_1, ScheduledJobs.ОбновлениеАгрегатов",
    }
  )
  void testSimple(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, ScheduledJobs.РегламентноеЗадание1",
      "false, mdclasses, ScheduledJobs.РегламентноеЗадание1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    var job = (ScheduledJob) mdo;
    var handler = ((ScheduledJob) mdo).getMethodName();
    assertThat(handler.getMethodName()).isNotBlank();
    assertThat(handler.getMethodPath()).isNotBlank();
    assertThat(handler.getModuleName()).isNotBlank();

    assertThat(handler.getMethodPath()).isEqualTo("CommonModule.ПростойОбщийМодуль.РегламентноеЗадание1");
    assertThat(handler.getMethodName()).isEqualTo("РегламентноеЗадание1");
    assertThat(handler.getModuleName()).isEqualTo("ПростойОбщийМодуль");

    assertThat(job.getDescription()).isEqualTo("Описание Регламентное задание 1");
    assertThat(job.getKey()).isEqualTo("ПроверкаАктивностиСеансаУдаленияОбъектов");
    assertThat(job.isUse()).isTrue();
    assertThat(job.isPredefined()).isTrue();
    assertThat(job.getRestartCountOnFailure()).isEqualTo(3);
    assertThat(job.getRestartIntervalOnFailure()).isEqualTo(10);
  }
}