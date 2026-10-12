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
package com.github._1c_syntax.bsl.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.storage.form.FormElement;
import com.github._1c_syntax.bsl.mdo.storage.form.FormElementType;
import com.github._1c_syntax.bsl.reader.common.context.FormElementReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Конвертор элемента формы в формате конфигуратора
 */
@DesignerConverter
@Slf4j
public class FormElementConverter implements ReadConverter {
  @Override
  @Nullable
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if (ExtendXStream.getCurrentMDReader(reader).getReadSettings().skipFormElementItems()) {
      return null;
    }

    var nodeName = reader.getNodeName();
    var elementType = FormElementType.valueByName(nodeName);

    if (elementType == FormElementType.UNKNOWN) {
      LOGGER.warn("Unknown form element type: {}. Please create issue: " +
          "https://github.com/1c-syntax/mdclasses/issues/new?labels=enhancement" +
          "&title=%5BFEAT%5D%20Add%20form%20element%20type%20%5B{}%5D",
        nodeName, nodeName);
    }

    var readerContext = new FormElementReaderContext(elementType, nodeName, reader);

    readerContext.setValue("id", Integer.parseInt(reader.getAttribute("id")));
    readerContext.setValue("name", reader.getAttribute("name"));
    Unmarshaller.unmarshal(reader, context, readerContext);

    // поправим тип, иногда он определяется по тегу, иногда по параметру...
    var type = readerContext.getFromCache("type");
    if (type instanceof List<?> list) {
      list.remove(elementType);
      if (!list.isEmpty()) {
        var otherElementType = list.getFirst();
        if (otherElementType != elementType) {
          readerContext.setValue("elementType", otherElementType);
        }
      }
    }
    return readerContext.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return FormElement.class.isAssignableFrom(type);
  }
}
