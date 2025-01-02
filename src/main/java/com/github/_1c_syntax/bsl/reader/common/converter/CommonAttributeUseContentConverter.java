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
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.mdo.CommonAttribute;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Конвертер для состава общего реквизита
 */
@CommonConverter
public class CommonAttributeUseContentConverter implements ReadConverter {

  private static final String METADATA_NODE_NAME = "Metadata";
  private static final String USE_NODE_NAME = "Use";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if (ExtendXStream.getCurrentMDReader(reader).getConfigurationSource() == ConfigurationSource.DESIGNER) {
      List<CommonAttribute.UseContent> contents = new ArrayList<>();
      while (reader.hasMoreChildren()) {
        reader.moveDown();
        contents.add(commonAttributeUseContent(reader, context));
        reader.moveUp();
      }
      return contents;
    } else {
      return commonAttributeUseContent(reader, context);
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return CommonAttribute.UseContent.class.isAssignableFrom(type);
  }

  private static CommonAttribute.UseContent commonAttributeUseContent(HierarchicalStreamReader reader,
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
        builder.use(ExtendXStream.readValue(context, UseMode.class));
      } else {
        // no-op
      }
      reader.moveUp();
    }
    return builder.build();
  }
}
