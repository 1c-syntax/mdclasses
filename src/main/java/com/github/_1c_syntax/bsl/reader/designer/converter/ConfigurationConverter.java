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
package com.github._1c_syntax.bsl.reader.designer.converter;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationExtension;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationTree;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@DesignerConverter
public class ConfigurationConverter implements ReadConverter {

  private static final String CHILD_FILED = "child";

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    int count;
    var fileInputStream = new FileInputStream(ExtendXStream.getCurrentPath(reader).toFile());
    try (var scanner = new Scanner(fileInputStream, StandardCharsets.UTF_8)) {
      count = (int) scanner.findAll("(<ObjectBelonging>)").count();
    }

    Class<?> realClass = Configuration.class;

    if (count > 0) {
      realClass = ConfigurationExtension.class;
    }

    var currentPath = ExtendXStream.getCurrentPath(reader);
    var readerContext = new TransformationUtils.Context(reader.getNodeName(), realClass, currentPath);
    Unmarshaller.unmarshal(reader, context, readerContext);

    if (readerContext.getCompatibilityMode() == null) {
      readerContext.setValue("compatibilityMode",
        readerContext.getConfigurationExtensionCompatibilityMode());
    }

    readChildren(readerContext);

    readerContext.setValue("configurationSource", ConfigurationSource.DESIGNER);
    return readerContext.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return ConfigurationTree.class.isAssignableFrom(type);
  }

  private static void readChildren(TransformationUtils.Context readerContext) {
    var rootPath = DesignerPaths.rootPathByConfigurationMDO(readerContext.getCurrentPath());
    var reader = MDOReader.getReader(rootPath);
    readerContext.getChildrenMetadata()
      .forEach((String fullName) -> {
        MD child = (MD) reader.read(fullName);
        if (child != null) {
          var fieldName = child.getMdoType().getName();
          readerContext.setValue(fieldName, child);
          readerContext.setValue(CHILD_FILED, child);
        }
      });
  }
}
