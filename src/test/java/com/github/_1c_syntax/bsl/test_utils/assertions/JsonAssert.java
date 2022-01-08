/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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

import org.assertj.core.api.AbstractAssert;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * Для единообразного Assertions сравнения двух json строк
 */
public class JsonAssert extends AbstractAssert<JsonAssert, String> {

  public JsonAssert(String actual) {
    super(actual, JsonAssert.class);
  }

  public static JsonAssert assertThat(String actual) {
    return new JsonAssert(actual);
  }

  public JsonAssert isEqual(String fixture) {
    // check that actual is not null.
    isNotNull();

    try {
      JSONAssert.assertEquals(actual, fixture, JSONCompareMode.LENIENT);
    } catch (JSONException e) {
      failWithMessage(e.getMessage());
    }

    // return the current assertion for method chaining
    return this;
  }
}
