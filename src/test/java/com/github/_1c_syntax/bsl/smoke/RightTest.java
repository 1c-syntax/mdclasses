/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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
package com.github._1c_syntax.bsl.smoke;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdo.AccessRightsOwner;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RightTest {

  @Test
  void posibleRights() {
    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .acceptPackages("com.github._1c_syntax.bsl")
      .scan()) {

      var classes = scanResult.getClassesImplementing(AccessRightsOwner.class);

      assertThat(classes).hasSize(43);

      var list = classes.stream()
        .filter(ci -> !ci.isInterface())
        .map(ClassInfo::getName)
        .filter(name -> getPosibleRights(name).isEmpty()).toList();
      assertThat(list).isEmpty();
    }
  }

  @SuppressWarnings("unchecked")
  private List<RoleRight> getPosibleRights(String className) {
    Class<AccessRightsOwner> clazz;
    try {
      clazz = (Class<AccessRightsOwner>) Class.forName(className);
    } catch (ClassNotFoundException e) {
      return Collections.emptyList();
    }

    var value = Arrays.stream(clazz.getDeclaredMethods())
      .filter(method -> "posibleRights".equals(method.getName()))
      .findFirst();

    if (value.isEmpty()) {
      return Collections.emptyList();
    }

    try {
      return (List<RoleRight>) value.get().invoke(clazz);
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }
}
