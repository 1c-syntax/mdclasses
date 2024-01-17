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
package com.github._1c_syntax.bsl.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.CommonTemplate;
import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.mdo.storage.EmptyTemplateData;
import com.github._1c_syntax.bsl.mdo.storage.TemplateData;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.common.ReaderUtils;
import com.github._1c_syntax.bsl.reader.common.converter.AbstractReadConverter;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.reader.designer.DesignerReader;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.nio.file.Paths;

@DesignerConverter
public class TemplateConverter extends AbstractReadConverter {

  private static final String DATA_FIELD = "data";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

    var name = reader.getNodeName();

    var realClass = DesignerReader.getXstream().getRealClass(name);
    if (!realClass.isAssignableFrom(CommonTemplate.class)) {
      var currentPath = ExtendXStream.getCurrentPath(reader);
      if (reader.getAttributeCount() == 0) {
        var childrenFolder = DesignerPaths.childrenFolder(currentPath, MDOType.TEMPLATE);
        var childName = ReaderUtils.readValue(context, String.class);
        var childPath = Paths.get(childrenFolder.toString(), childName + DesignerPaths.EXTENSION_DOT);
        return MDOReader.read(childPath);
      }
    }
    var readerContext = super.read(reader, context);

    TemplateData templateData = EmptyTemplateData.getEmpty();
    if (readerContext.getTemplateType() == TemplateType.DATA_COMPOSITION_SCHEME) {
      var path = DesignerPaths.templateDataPath(readerContext.getCurrentPath(), readerContext.getName());
      var data = MDOReader.read(path);
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
}
