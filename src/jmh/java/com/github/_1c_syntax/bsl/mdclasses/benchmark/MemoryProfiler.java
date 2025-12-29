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

import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.IterationParams;
import org.openjdk.jmh.profile.InternalProfiler;
import org.openjdk.jmh.results.AggregationPolicy;
import org.openjdk.jmh.results.IterationResult;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.ScalarResult;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemoryProfiler implements InternalProfiler {

  private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
  private final List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
  private final MemoryPoolMXBean[] memoryPoolBeans = ManagementFactory.getMemoryPoolMXBeans().toArray(new MemoryPoolMXBean[0]);

  private long initialGcCount = 0;
  private long initialGcTime = 0;

  @Override
  public void beforeIteration(BenchmarkParams benchmarkParams, IterationParams iterationParams) {
    // Сохраняем начальные значения GC
    initialGcCount = getTotalGcCount();
    initialGcTime = getTotalGcTime();

    // Очищаем память перед итерацией
    System.gc();
    try {
      Thread.sleep(100); // Даем время GC завершиться
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public Collection<? extends Result> afterIteration(
    BenchmarkParams benchmarkParams,
    IterationParams iterationParams,
    IterationResult result
  ) {
    List<ScalarResult> results = new ArrayList<>();

    // Память
    var heapUsage = memoryMXBean.getHeapMemoryUsage();
    var nonHeapUsage = memoryMXBean.getNonHeapMemoryUsage();

    var usedHeap = heapUsage.getUsed();
    var usedNonHeap = nonHeapUsage.getUsed();
    var totalUsed = usedHeap + usedNonHeap;

    // GC статистика
    var gcCount = getTotalGcCount() - initialGcCount;
    var gcTime = getTotalGcTime() - initialGcTime;

    // Добавляем метрики памяти
    results.add(new ScalarResult("memory.heap.used",
      usedHeap / 1024.0 / 1024.0, "MB", AggregationPolicy.AVG));
    results.add(new ScalarResult("memory.heap.max",
      heapUsage.getMax() / 1024.0 / 1024.0, "MB", AggregationPolicy.AVG));
    results.add(new ScalarResult("memory.heap.committed",
      heapUsage.getCommitted() / 1024.0 / 1024.0, "MB", AggregationPolicy.AVG));

    results.add(new ScalarResult("memory.nonHeap.used",
      usedNonHeap / 1024.0 / 1024.0, "MB", AggregationPolicy.AVG));
    results.add(new ScalarResult("memory.nonHeap.committed",
      nonHeapUsage.getCommitted() / 1024.0 / 1024.0, "MB", AggregationPolicy.AVG));

    results.add(new ScalarResult("memory.total.used",
      totalUsed / 1024.0 / 1024.0, "MB", AggregationPolicy.AVG));

    // Добавляем метрики GC
    results.add(new ScalarResult("gc.collections",
      gcCount, "collections", AggregationPolicy.AVG));
    results.add(new ScalarResult("gc.time",
      gcTime, "ms", AggregationPolicy.AVG));

    // Метрики по memory pools
    for (MemoryPoolMXBean pool : memoryPoolBeans) {
      MemoryUsage usage = pool.getUsage();
      String poolName = sanitizePoolName(pool.getName());

      results.add(new ScalarResult("memory.pool." + poolName + ".used",
        usage.getUsed() / 1024.0 / 1024.0, "MB", AggregationPolicy.AVG));
      results.add(new ScalarResult("memory.pool." + poolName + ".max",
        usage.getMax() / 1024.0 / 1024.0, "MB", AggregationPolicy.AVG));
    }

    return results;
  }

  private long getTotalGcCount() {
    return gcBeans.stream().mapToLong(GarbageCollectorMXBean::getCollectionCount).sum();
  }

  private long getTotalGcTime() {
    return gcBeans.stream().mapToLong(GarbageCollectorMXBean::getCollectionTime).sum();
  }

  private String sanitizePoolName(String name) {
    return name.toLowerCase()
      .replace(" ", "_")
      .replace("-", "_")
      .replace(".", "_");
  }

  @Override
  public String getDescription() {
    return "Comprehensive Memory and GC Profiler";
  }
}