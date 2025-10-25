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

import lombok.Builder;
import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Value
@Builder
public class AnalysisResult {
  String className;
  String simpleClassName;
  long instanceSize;
  long totalSize;
  Map<String, Long> fieldSizes;
  List<FieldAlignmentIssue> alignmentIssues;
  int wastedBytes;
  double alignmentEfficiency;
  List<String> optimizationSuggestions;
  String error;

  public static AnalysisResult error(String className, String error) {
    return builder()
      .className(className)
      .error(error)
      .alignmentIssues(Collections.emptyList())
      .optimizationSuggestions(Collections.emptyList())
      .fieldSizes(Collections.emptyMap())
      .build();
  }
}
