/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.mdo.CommonAttribute;
import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.support.AutoRecordType;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.reader.common.ReaderUtils;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.experimental.UtilityClass;

import java.util.AbstractMap;

/**
 * Содержит методы с общими частями методов конвертирования
 */
@UtilityClass
public class ConverterParts {
  private static final String METADATA_NODE_NAME = "Metadata";
  private static final String USE_NODE_NAME = "Use";

  public CommonAttribute.UseContent commonAttributeUseContent(HierarchicalStreamReader reader,
                                                              UnmarshallingContext context) {
    if (!reader.hasMoreChildren()) {
      return null;
    }

    var builder = CommonAttribute.UseContent.builder();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      if (METADATA_NODE_NAME.equalsIgnoreCase(node)) {
        builder.metadata(MdoReference.create(reader.getValue()));
      } else if (USE_NODE_NAME.equalsIgnoreCase(node)) {
        builder.use(ReaderUtils.readValue(context, UseMode.class));
      } else {
        // no-op
      }
      reader.moveUp();
    }
    return builder.build();
  }

  public static ExchangePlan.RecordContent exchangePlanAutoRecord(HierarchicalStreamReader reader,
                                                                  UnmarshallingContext context,
                                                                  String mdoNodeName,
                                                                  String autoRecordNodeName) {
    if (!reader.hasMoreChildren()) {
      return null;
    }

    var builder = ExchangePlan.RecordContent.builder();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      if (mdoNodeName.equals(node)) {
        builder.metadata(MdoReference.create(reader.getValue()));
      } else if (autoRecordNodeName.equals(node)) {
        builder.autoRecord(ReaderUtils.readValue(context, AutoRecordType.class));
      } else {
        // no-op
      }
      reader.moveUp();
    }
    return builder.build();
  }

  public AbstractMap.SimpleEntry<String, String> multiLanguageString(HierarchicalStreamReader reader,
                                                                     String langNodeName,
                                                                     String contentNodeName) {
    var lang = "";
    var content = "";
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      if (langNodeName.equals(node)) {
        lang = reader.getValue();
      } else if (contentNodeName.equals(node)) {
        content = reader.getValue();
      } else {
        // no-op
      }
      reader.moveUp();
    }
    return new AbstractMap.SimpleEntry<>(lang, content);
  }
}
