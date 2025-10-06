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
package com.github._1c_syntax.bsl.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.CommonTemplate;
import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.mdo.storage.EmptyTemplateData;
import com.github._1c_syntax.bsl.mdo.storage.TemplateData;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.common.converter.AbstractReadConverter;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

@DesignerConverter
public class TemplateConverter extends AbstractReadConverter {

  private static final String DATA_FIELD = "data";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

    var name = reader.getNodeName();

    var realClass = ExtendXStream.getRealClass(reader, name);
    if (!realClass.isAssignableFrom(CommonTemplate.class)) {
      var currentPath = ExtendXStream.getCurrentPath(reader);
      if (reader.getAttributeCount() == 0) {
        var childName = ExtendXStream.readValue(context, String.class);
        return ExtendXStream.read(reader, childDataPath(currentPath, childName));
      }
    }
    var readerContext = super.read(reader, context);

    TemplateData templateData = EmptyTemplateData.getEmpty();
    if (readerContext.getTemplateType() == TemplateType.DATA_COMPOSITION_SCHEME) {
      var data = ExtendXStream.read(reader, dataPath(readerContext.getCurrentPath(), readerContext.getName()));
      if (data instanceof TemplateData templData) {
        templateData = templData;
      }
    }

    readerContext.setValue(DATA_FIELD, templateData);
    if (realClass.isAssignableFrom(CommonTemplate.class)) {
      return readerContext.build();
    } else {
      return readerContext;
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return Template.class.isAssignableFrom(type);
  }

  private static Path dataPath(Path path, String name) {
    return Paths.get(path.getParent().toString(), name, "Ext", "Template.xml");
  }

  private static Path childDataPath(Path path, String childName) {
    return Paths.get(
      path.getParent().toString(),
      FilenameUtils.getBaseName(path.toString()),
      MDOType.TEMPLATE.groupName(),
      childName + ".xml"
    );
  }
}
