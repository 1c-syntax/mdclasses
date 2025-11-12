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
package com.github._1c_syntax.jol.memory;

import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Slf4j
@UtilityClass
public class MemoryAnalyzer {
  /**
   * Анализирует все DTO в указанных пакетах
   */
  public List<AnalysisResult> analyzeClasses(String... basePackages) {
    var classes = findClasses(basePackages);
    LOGGER.info("Found {} classes for analysis", classes.size());

    return classes.stream()
      .map(MemoryAnalyzer::analyzeClass)
      .filter(Objects::nonNull)
      .sorted(Comparator.comparing(AnalysisResult::getWastedBytes).reversed())
      .limit(10)
      .toList();
  }

  public void printAnalysisReport(List<AnalysisResult> results) {
    if (!results.isEmpty()) {
      var rec = results.get(0);
      LOGGER.info("Class: {}", rec.getClassName());
      LOGGER.info("Count: {}", results.size());
      LOGGER.info("Instance size: {} bytes", rec.getInstanceSize());
      LOGGER.info("Total size: {} bytes", results.stream().mapToLong(AnalysisResult::getTotalSize).sum());
      LOGGER.info("Wasted bytes: {} bytes", rec.getWastedBytes());
      LOGGER.info("Alignment efficiency: {}", String.format("%.2f", rec.getAlignmentEfficiency()) + "%");
    }
//
//    for (var result : results) {
//      LOGGER.info("Class: {}", result.getClassName());
//      LOGGER.info("Instance size: {} bytes", result.getInstanceSize());
//      LOGGER.info("Total size: {} bytes", result.getTotalSize());
//      LOGGER.info("Wasted bytes: {} bytes", result.getWastedBytes());
//      LOGGER.info("Alignment efficiency: {}", String.format("%.2f", result.getAlignmentEfficiency()) + "%");
//
////      if (!result.getAlignmentIssues().isEmpty()) {
////        LOGGER.info("Alignment issues:");
////        for (FieldAlignmentIssue issue : result.getAlignmentIssues()) {
////          LOGGER.info("  - {}: {} bytes", issue.description(), issue.paddingSize());
////        }
////      }
//
////      if (!result.getOptimizationSuggestions().isEmpty()) {
////        LOGGER.info("Optimization suggestions:");
////        for (String suggestion : result.getOptimizationSuggestions()) {
////          LOGGER.info("  - {}", suggestion);
////        }
////      }
//
////      LOGGER.info("Field sizes:");
////      result.getFieldSizes().forEach(
////        (field, size) -> LOGGER.info("  {}: {} bytes", field, size)
////      );
//
//      LOGGER.info("---");
//    }
  }

  /**
   * Анализирует конкретный класс
   */
  public AnalysisResult analyzeClass(Object instance) {
    var clazz = instance.getClass();
    try {
      return analyzeInstance(clazz, instance);
    } catch (Exception e) {
      LOGGER.error("Error analyzing class {}: {}", clazz.getName(), e.getMessage());
      return AnalysisResult.error(clazz.getSimpleName(), e.getMessage());
    }
  }

  private AnalysisResult analyzeClass(Class<?> clazz) {
    var builder = TransformationUtils.builder(clazz);
    if (builder == null) {
      return null;
    }
    var instance = TransformationUtils.build(builder);
    if (instance == null) {
      return null;
    }
    try {
      return analyzeInstance(clazz, instance);
    } catch (Exception e) {
      LOGGER.error("Error analyzing class {}: {}", clazz.getName(), e.getMessage());
      return AnalysisResult.error(clazz.getSimpleName(), e.getMessage());
    }
  }

  /**
   * Анализирует экземпляр с помощью JOL
   */
  public AnalysisResult analyzeInstance(Class<?> clazz, Object instance) {
    var classLayout = ClassLayout.parseClass(clazz);
    var graphLayout = GraphLayout.parseInstance(instance);

    var instanceSize = classLayout.instanceSize();
    var totalSize = graphLayout.totalSize();

    var fieldSizes = calculateFieldSizes(classLayout);
    var alignmentIssues = findAlignmentIssues(classLayout);
    var wastedBytes = calculateWastedBytes(alignmentIssues);

    var alignmentEfficiency = calculateAlignmentEfficiency(instanceSize, wastedBytes);
    var optimizationSuggestions = generateSuggestions(clazz, alignmentIssues);

    return AnalysisResult.builder()
      .className(clazz.getName())
      .simpleClassName(clazz.getSimpleName())
      .instanceSize(instanceSize)
      .totalSize(totalSize)
      .fieldSizes(fieldSizes)
      .alignmentIssues(alignmentIssues)
      .wastedBytes(wastedBytes)
      .alignmentEfficiency(alignmentEfficiency)
      .optimizationSuggestions(optimizationSuggestions)
      .build();
  }

  /**
   * Находит классы с использованием ClassGraph
   */
  private List<? extends Class<?>> findClasses(String... basePackages) {
    if (basePackages == null || basePackages.length == 0) {
      basePackages = new String[]{"com.github._1c_syntax.bsl"};
    }

    try (ScanResult scanResult = new ClassGraph()
      .enableAllInfo()
      .acceptPackages(basePackages)
      .scan()) {

      return scanResult.getAllClasses().stream()
        .filter(MemoryAnalyzer::isDTOLikeClass)
        .map(ClassInfo::loadClass)
        .filter(Objects::nonNull)
        .filter(clazz ->
          !clazz.isInterface()
            && !Modifier.isAbstract(clazz.getModifiers())
            && !clazz.isEnum()
        )
        .toList();

    } catch (Exception e) {
      throw new RuntimeException("Failed to scan classes with ClassGraph", e);
    }
  }

  /**
   * Определяет, является ли класс DTO-подобным
   */
  private boolean isDTOLikeClass(ClassInfo classInfo) {
    var className = classInfo.getName().toLowerCase(Locale.getDefault());
    var packageName = classInfo.getPackageName().toLowerCase(Locale.getDefault());

    // Исключаем тестовые, внутренние и служебные классы
    if (classInfo.isInnerClass()
      || classInfo.isStatic()
      || packageName.contains(".test")
      || className.contains("test")
      || className.contains("builder")
      || className.contains("$")) {
      return false;
    }

    // Фильтр по пакетам
    return packageName.contains(".mdo") || packageName.contains(".mdclasses");
  }

  private Map<String, Long> calculateFieldSizes(ClassLayout classLayout) {
    Map<String, Long> fieldSizes = new LinkedHashMap<>();
    classLayout.fields().forEach(field -> {
      fieldSizes.put(field.name(), field.size());
    });
    return fieldSizes;
  }

  private List<FieldAlignmentIssue> findAlignmentIssues(ClassLayout classLayout) {
    List<FieldAlignmentIssue> issues = new ArrayList<>();
    var layout = classLayout.toPrintable();

    var lines = layout.split("\n");
    for (int i = 0; i < lines.length; i++) {
      var line = lines[i].trim();
      if (line.contains("alignment/padding gap")) {
        var parts = line.split("\\s+");
        if (parts.length >= 2) {
          try {
            var paddingSize = Long.parseLong(parts[1]);
            issues.add(new FieldAlignmentIssue(i, paddingSize, "Padding between fields"));
          } catch (NumberFormatException e) {
            // ignore
          }
        }
      }
    }
    return issues;
  }

  private int calculateWastedBytes(List<FieldAlignmentIssue> alignmentIssues) {
    return alignmentIssues.stream()
      .mapToInt(issue -> (int) issue.paddingSize())
      .sum();
  }

  private double calculateAlignmentEfficiency(long instanceSize, long wastedBytes) {
    if (instanceSize == 0) return 0;
    return ((double) (instanceSize - wastedBytes) / instanceSize) * 100;
  }

  private List<String> generateSuggestions(Class<?> dtoClass,
                                           List<FieldAlignmentIssue> alignmentIssues) {
    List<String> suggestions = new ArrayList<>();

    if (!alignmentIssues.isEmpty()) {
      suggestions.add("Переупорядочьте поля по убыванию размера " +
        "(long/double -> int/float -> short/char -> byte/boolean -> references)");
    }

    // Проверка использования wrapper типов
    var fields = dtoClass.getDeclaredFields();
    var hasWrappers = Arrays.stream(fields)
      .anyMatch(field -> isWrapperType(field.getType()) && !Modifier.isTransient(field.getModifiers()));

    if (hasWrappers) {
      suggestions.add("Замените обертки (Integer, Long, Boolean) на примитивы где допустимо null значения");
    }

    // Анализ наследования
    if (dtoClass.getSuperclass() != null && dtoClass.getSuperclass() != Object.class) {
      suggestions.add("Проверьте иерархию класса - наследование добавляет overhead");
    }

    // Проверка на final поля
    var hasNonFinalFields = Arrays.stream(fields)
      .anyMatch(field ->
        !Modifier.isFinal(field.getModifiers())
          && !Modifier.isTransient(field.getModifiers())
          && !Modifier.isStatic(field.getModifiers())
      );

    if (hasNonFinalFields) {
      suggestions.add("Сделайте поля final где возможно для оптимизации JVM");
    }

    return suggestions;
  }

  private boolean isWrapperType(Class<?> type) {
    return type == Integer.class
      || type == Long.class
      || type == Boolean.class
      || type == Double.class
      || type == Float.class
      || type == Byte.class
      || type == Short.class
      || type == Character.class;
  }
}
