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
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.support.AutoRecordType;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Конвертер для состава плана обмена
 */
@CommonConverter
public class ExchangePlanAutoRecordConverter implements ReadConverter {

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if (!reader.hasMoreChildren()) {
      return null;
    }
    if (ExtendXStream.getCurrentMDReader(reader).getConfigurationSource() == ConfigurationSource.DESIGNER) {
      List<ExchangePlan.RecordContent> content = new ArrayList<>();
      while (reader.hasMoreChildren()) { // root
        reader.moveDown();
        content.add(
          exchangePlanAutoRecord(reader, context, "Metadata", "AutoRecord")
        );
        reader.moveUp();
      }
      return content;
    } else {
      return exchangePlanAutoRecord(reader, context, "mdObject", "autoRecord");
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return ExchangePlan.RecordContent.class.isAssignableFrom(type);
  }

  private static ExchangePlan.RecordContent exchangePlanAutoRecord(HierarchicalStreamReader reader,
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
        builder.autoRecord(ExtendXStream.readValue(context, AutoRecordType.class));
      } else {
        // no-op
      }
      reader.moveUp();
    }
    return builder.build();
  }

}
