/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
package com.github._1c_syntax.bsl.mdclasses.benchmark;

import com.github._1c_syntax.bsl.mdclasses.MDCReadSettings;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 5, time = 3)
@Fork(2)
public class MDClassesBenchmark {

  private final Path configPathEDT = Path.of("src/test/resources/ext/edt/ssl_3_1/configuration");
  private final Path configPathDesigner = Path.of("src/test/resources/ext/designer/ssl_3_1/src/cf");

  @Setup
  public void setup() {
    // Предварительная загрузка для разогрева
    MDClasses.createConfiguration(configPathEDT);
    MDClasses.createConfiguration(configPathDesigner);
  }

  @Benchmark
  public void test_EDT_CreateConfiguration_SkipSupport_False(Blackhole blackhole) {
    var model = MDClasses.createConfiguration(configPathEDT);
    blackhole.consume(model);
  }

  @Benchmark
  public void test_EDT_CreateConfiguration_SkipSupport_True(Blackhole blackhole) {
    var model = MDClasses.createConfiguration(configPathEDT,
      MDCReadSettings.builder()
        .skipSupport(true)
        .skipRoleData(true)
        .skipFormElementItems(true)
        .skipXdtoPackage(true)
        .skipDataCompositionSchema(true)
        .build());
    blackhole.consume(model);
  }

  @Benchmark
  public void test_Designer_CreateConfiguration_SkipSupport_False(Blackhole blackhole) {
    var model = MDClasses.createConfiguration(configPathDesigner);
    blackhole.consume(model);
  }

  @Benchmark
  public void test_Designer_CreateConfiguration_SkipSupport_True(Blackhole blackhole) {
    var model = MDClasses.createConfiguration(configPathDesigner,
      MDCReadSettings.builder()
        .skipSupport(true)
        .skipRoleData(true)
        .skipFormElementItems(true)
        .skipXdtoPackage(true)
        .skipDataCompositionSchema(true)
        .build());
    blackhole.consume(model);
  }
}
