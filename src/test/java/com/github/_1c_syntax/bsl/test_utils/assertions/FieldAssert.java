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
package com.github._1c_syntax.bsl.test_utils.assertions;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdo.MDObject;
import org.assertj.core.api.AbstractAssert;

import java.lang.reflect.Field;
import java.util.Objects;

public class FieldAssert extends AbstractAssert<FieldAssert, Field> {

  public FieldAssert(Field actual) {
    super(actual, FieldAssert.class);
  }

  public static FieldAssert assertThat(Field actual) {
    return new FieldAssert(actual);
  }

  public FieldAssert isNotNull(MDObject mdObject) {
    // check that actual is not null.
    isNotNull();

    // check condition
    try {
      actual.setAccessible(true);
      if (Objects.equals(actual.get(mdObject), null)) {
        failWithMessage("<%s>:\nExpected field <%s> is null", mdObject, actual.getName());
      }
    } catch (IllegalAccessException e) {
      failWithMessage("<%s>:\nExpected field <%s> is missing", mdObject, actual.getName());
    }

    // return the current assertion for method chaining
    return this;
  }

  public FieldAssert isNotNull(MDClass mdClass) {
    // check that actual is not null.
    isNotNull();

    // check condition
    try {
      actual.setAccessible(true);
      if (Objects.equals(actual.get(mdClass), null)) {
        failWithMessage("<%s>:\nExpected field <%s> is null", mdClass, actual.getName());
      }
    } catch (IllegalAccessException e) {
      failWithMessage("<%s>:\nExpected field <%s> is missing", mdClass, actual.getName());
    }

    // return the current assertion for method chaining
    return this;
  }
}
