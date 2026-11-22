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
package com.github._1c_syntax.bsl.test_utils.assertions;

import org.assertj.core.api.AbstractAssert;
import org.json.JSONException;
import org.skyscreamer.jsonassert.FieldComparisonFailure;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.util.ArrayList;
import java.util.List;

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

    JSONCompareResult result;
    try {
      result = JSONCompare.compareJSON(actual, fixture, JSONCompareMode.LENIENT);
    } catch (JSONException e) {
      failWithMessage(e.getMessage());
      return this;
    }

    if (result.failed()) {
      failWithMessage(buildDiffMessage(result, fixture));
    }

    // return the current assertion for method chaining
    return this;
  }

  private String buildDiffMessage(JSONCompareResult result, String fixture) {
    var details = new ArrayList<String>();
    collectFailures(result.getFieldFailures(), "MISMATCH", details);
    collectFailures(result.getFieldMissing(), "MISSING", details);
    collectFailures(result.getFieldUnexpected(), "UNEXPECTED", details);

    var builder = new StringBuilder("JSON mismatch:");

    if (!details.isEmpty()) {
      var toShow = Math.min(details.size(), 20);
      for (int i = 0; i < toShow; i++) {
        builder.append("\n").append(details.get(i));
      }
      if (details.size() > toShow) {
        builder.append("\n  ... and ").append(details.size() - toShow).append(" more differences");
      }
    } else {
      var msg = result.getMessage();
      if (msg != null && !msg.isBlank()) {
        builder.append("\n").append(formatDiffMessage(msg));
      } else {
        builder.append("\n  (")
          .append(actual.length()).append(" chars vs ")
          .append(fixture.length()).append(" chars)");
      }
    }

    return builder.toString();
  }

  private String formatDiffMessage(String msg) {
    String trimmed = msg.trim();

    // "path Could not find match for element {...}"
    int noMatchIdx = trimmed.indexOf("Could not find match for element");
    if (noMatchIdx > 0) {
      String path = trimmed.substring(0, noMatchIdx).trim();
      return "  Cannot match element at: " + path;
    }

    // "path Missing: ..."
    int missingIdx = trimmed.indexOf("Missing:");
    if (missingIdx > 0) {
      String path = trimmed.substring(0, missingIdx).trim();
      return "  Missing: " + path + "\n  " + trimmed.substring(missingIdx);
    }

    // "path Unexpected: ..."
    int unexpectedIdx = trimmed.indexOf("Unexpected:");
    if (unexpectedIdx > 0) {
      String path = trimmed.substring(0, unexpectedIdx).trim();
      return "  Unexpected: " + path + "\n  " + trimmed.substring(unexpectedIdx);
    }

    // Default: if it's a huge message (contains big JSON dump), just show the path
    if (trimmed.length() > 800) {
      int jsonIdx = trimmed.indexOf('{');
      if (jsonIdx > 0) {
        String path = trimmed.substring(0, jsonIdx).trim();
        return "  Element mismatch at: " + path + " (element content too long to display)";
      }
    }

    return "  " + trimmed;
  }

  private void collectFailures(List<FieldComparisonFailure> failures, String kind, List<String> out) {
    if (failures == null) {
      return;
    }
    for (var f : failures) {
      var path = f.getField() != null ? f.getField() : "?";
      var expected = f.getExpected() == null ? "<missing>" : f.getExpected().toString();
      var actual = f.getActual() == null ? "<missing>" : f.getActual().toString();
      out.add("  [" + kind + "] " + path + ": expected=" + expected + "; actual=" + actual);
    }
  }
}
