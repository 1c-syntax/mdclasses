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
package com.github._1c_syntax.bsl.reader.edt.converter;

import com.github._1c_syntax.bsl.mdclasses.StandaloneConfigurationContent;
import com.github._1c_syntax.bsl.reader.common.converter.AbstractStandaloneContentConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Читает файл автономного состава проекта EDT {@code src/Configuration/MobileApplicationContent.scc}.
 */
@EDTConverter
public class StandaloneContentConverter extends AbstractStandaloneContentConverter {

  @Override
  protected void readItem(HierarchicalStreamReader reader, UnmarshallingContext context,
                          StandaloneConfigurationContent.StandaloneConfigurationContentBuilder builder) {
    var metadata = attribute(reader, "metadata");
    switch (reader.getNodeName()) {
      case "usedItem" -> addUsed(builder, metadata);
      case "unusedItem" -> addUnused(builder, metadata);
      case "priorityItem" -> addPriority(builder, metadata, attribute(reader, "priority"));
      case "dataExchangeSettings" -> readDataExchangeSettings(reader, builder);
      default -> {
        // skip
      }
    }
  }

  private static void readDataExchangeSettings(
    HierarchicalStreamReader reader,
    StandaloneConfigurationContent.StandaloneConfigurationContentBuilder builder) {

    var exchangeOnChangeData = attribute(reader, "exchangeOnChangeData");
    if (!exchangeOnChangeData.isEmpty()) {
      builder.exchangeOnChangeData(Boolean.parseBoolean(exchangeOnChangeData));
    }
    var exchangePeriod = attribute(reader, "exchangePeriod");
    if (!exchangePeriod.isEmpty()) {
      builder.exchangePeriod(Integer.parseInt(exchangePeriod));
    }
    var transactionCount = attribute(reader, "transactionCount");
    if (!transactionCount.isEmpty()) {
      builder.transactionCount(Integer.parseInt(transactionCount));
    }
    var inactiveNodesCleanupTimeout = attribute(reader, "inactiveNodesCleanupTimeout");
    if (!inactiveNodesCleanupTimeout.isEmpty()) {
      builder.inactiveNodesCleanupTimeout(Integer.parseInt(inactiveNodesCleanupTimeout));
    }
  }

  private static String attribute(HierarchicalStreamReader reader, String name) {
    var value = reader.getAttribute(name);
    return value == null ? "" : value;
  }
}
