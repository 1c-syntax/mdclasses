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
package com.github._1c_syntax.bsl.smoke;

import com.github._1c_syntax.bsl.mdo.AdditionalIndexOwner;
import com.github._1c_syntax.bsl.mdo.BasedOnOwner;
import com.github._1c_syntax.bsl.mdo.DataLockFieldsOwner;
import com.github._1c_syntax.bsl.mdo.FullTextSearchOwner;
import com.github._1c_syntax.bsl.mdo.InputByStringOwner;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerInterfacesTest {

  private static final String MDO_PACKAGE = "com.github._1c_syntax.bsl.mdo";

  @ParameterizedTest
  @MethodSource("properties")
  void declaringClassImplementsOwnerInterface(String fieldName, Class<?> ownerInterface, int expectedCount) {
    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .acceptPackages(MDO_PACKAGE)
      .scan()) {

      var violatingClasses = scanResult.getAllClasses().stream()
        .filter(ci -> !ci.isInterface())
        .filter(ci -> !ci.isInnerClass())
        .filter(ci -> MDO_PACKAGE.equals(ci.getPackageName()))
        .map(ClassInfo::loadClass)
        .filter(clazz -> hasField(clazz, fieldName))
        .filter(clazz -> !ownerInterface.isAssignableFrom(clazz))
        .map(Class::getName)
        .toList();

      assertThat(violatingClasses).isEmpty();
    }
  }

  @ParameterizedTest
  @MethodSource("properties")
  void ownerInterfaceHasExpectedImplementors(String fieldName, Class<?> ownerInterface, int expectedCount) {
    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .acceptPackages(MDO_PACKAGE)
      .scan()) {

      var implementors = scanResult.getClassesImplementing(ownerInterface).stream()
        .filter(ci -> !ci.isInterface())
        .filter(ci -> !ci.isInnerClass())
        .filter(ci -> MDO_PACKAGE.equals(ci.getPackageName()))
        .count();

      assertThat(implementors).isEqualTo(expectedCount);
    }
  }

  private static boolean hasField(Class<?> clazz, String fieldName) {
    try {
      clazz.getDeclaredField(fieldName);
      return true;
    } catch (NoSuchFieldException e) {
      return false;
    }
  }

  private static Stream<Arguments> properties() {
    return Stream.of(
      Arguments.of("basedOn", BasedOnOwner.class, 8),
      Arguments.of("additionalIndexes", AdditionalIndexOwner.class, 14),
      Arguments.of("dataLockFields", DataLockFieldsOwner.class, 8),
      Arguments.of("inputByString", InputByStringOwner.class, 8),
      Arguments.of("fullTextSearch", FullTextSearchOwner.class, 13)
    );
  }
}