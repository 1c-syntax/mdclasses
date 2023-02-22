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
package com.github._1c_syntax.bsl.reader.edt.converter;

import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.support.AutoRecordType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Конвертер для состава плана обмена
 */
@EDTConverter
public class ExchangePlanAutoRecordConverter implements Converter {

  private static final String MD_OBJECT_NODE_NAME = "mdObject";
  private static final String AUTO_RECORD_NODE_NAME = "autoRecord";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if (!reader.hasMoreChildren()) {
      return null;
    }

    var builder = ExchangePlan.RecordContent.builder();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      if (MD_OBJECT_NODE_NAME.equals(node)) {
        builder.metadata(MdoReference.create(reader.getValue()));
      } else if (AUTO_RECORD_NODE_NAME.equals(node)) {
        builder.autoRecord((AutoRecordType) context.convertAnother(reader.getValue(), AutoRecordType.class));
      } else {
        // no-op
      }
      reader.moveUp();
    }
    return builder.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return ExchangePlan.RecordContent.class.isAssignableFrom(type);
  }
}
