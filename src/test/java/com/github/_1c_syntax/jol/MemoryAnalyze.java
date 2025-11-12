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
package com.github._1c_syntax.jol;

import com.github._1c_syntax.bsl.mdclasses.CF;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.storage.FormData;
import com.github._1c_syntax.bsl.mdo.storage.RoleData;
import com.github._1c_syntax.jol.memory.AnalysisResult;
import com.github._1c_syntax.jol.memory.MemoryAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.vm.VM;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class MemoryAnalyze {

  private static final Path CF = Path.of("/home/vmaksimov@dellin.local/reps/onec/erp/src");

  public static void main(String[] args) {
    LOGGER.info("=== MEMORY ANALYSIS CLASSES REPORT ===");
    LOGGER.info("JVM details: {}", VM.current().details());
    LOGGER.info("");

    if (CF.toFile().exists()) {
      analyzeClassesByCF();
    } else {
      analyzeClasses();
    }
  }

  private static void analyzeClasses() {
    LOGGER.info("Without real data");
    LOGGER.info("");

    var results = MemoryAnalyzer.analyzeClasses(
      "com.github._1c_syntax.bsl.mdo",
      "com.github._1c_syntax.bsl.mdclasses"
    );
    MemoryAnalyzer.printAnalysisReport(results);
  }

  private static void analyzeClassesByCF() {
    LOGGER.info("For {}", CF);
    LOGGER.info("");

    var mdc = (CF) MDClasses.createSolution(CF);
    mdc.getPlainChildren();
    mdc.getModuleTypes();
    mdc.getAllModules();
    mdc.getChildrenByMdoRef();
    mdc.getModulesByURI();
    mdc.getModulesByObject();


    // дочерние
    var map = mdc.getPlainChildren().stream()
      .collect(Collectors.groupingBy(Object::getClass, Collectors.toList()))
      ;
    analyzeAndPrint(map);

    // части формы
    var form = mdc.getPlainChildren().stream()
      .filter(Form.class::isInstance)
      .map(Form.class::cast)
      .map(Form::getData)
      .collect(Collectors.groupingBy(Object::getClass, Collectors.toList()));
    analyzeAndPrint(form);

    var formElements = mdc.getPlainChildren().stream()
      .filter(Form.class::isInstance)
      .map(Form.class::cast)
      .map(Form::getData)
      .map(FormData::getPlainItems)
      .flatMap(Collection::stream)
      .collect(Collectors.groupingBy(Object::getClass, Collectors.toList()));
    analyzeAndPrint(formElements);

    var formAttributes = mdc.getPlainChildren().stream()
      .filter(Form.class::isInstance)
      .map(Form.class::cast)
      .map(Form::getData)
      .map(FormData::getAttributes)
      .flatMap(Collection::stream)
      .collect(Collectors.groupingBy(Object::getClass, Collectors.toList()));
    analyzeAndPrint(formAttributes);

    var formHandles = mdc.getPlainChildren().stream()
      .filter(Form.class::isInstance)
      .map(Form.class::cast)
      .map(Form::getData)
      .map(FormData::getHandlers)
      .flatMap(Collection::stream)
      .collect(Collectors.groupingBy(Object::getClass, Collectors.toList()));
    analyzeAndPrint(formHandles);

    // роли
    var role = mdc.getPlainChildren().stream()
      .filter(Role.class::isInstance)
      .map(Role.class::cast)
      .map(Role::getData)
      .collect(Collectors.groupingBy(Object::getClass, Collectors.toList()));
    analyzeAndPrint(role);

    var roleRight = mdc.getPlainChildren().stream()
      .filter(Role.class::isInstance)
      .map(Role.class::cast)
      .map(Role::getData)
      .map(RoleData::objectRights)
      .flatMap(Collection::stream)
      .collect(Collectors.groupingBy(Object::getClass, Collectors.toList()));
    analyzeAndPrint(roleRight);

    var roleRights = mdc.getPlainChildren().stream()
      .filter(Role.class::isInstance)
      .map(Role.class::cast)
      .map(Role::getData)
      .map(RoleData::objectRights)
      .flatMap(Collection::stream)
      .map(RoleData.ObjectRight::rights)
      .flatMap(Collection::stream)
      .collect(Collectors.groupingBy(Object::getClass, Collectors.toList()));
    analyzeAndPrint(roleRights);
  }

  private static <T> void analyzeAndPrint(Map<? extends Class<?>, List<T>> map) {
    map.forEach((aClass, elements) -> {
        List<AnalysisResult> results = new ArrayList<>();
//        AtomicInteger count = new AtomicInteger();
        elements.forEach(md -> {
          results.add(MemoryAnalyzer.analyzeClass(md));
//          count.getAndIncrement();
        });

//        LOGGER.info("Count {}", count);
//        var sorted = results.stream()
//          .sorted(Comparator.comparing(AnalysisResult::getTotalSize).reversed())
////          .limit(1)
//          .toList();
        MemoryAnalyzer.printAnalysisReport(results);
      }
    );
  }
}
