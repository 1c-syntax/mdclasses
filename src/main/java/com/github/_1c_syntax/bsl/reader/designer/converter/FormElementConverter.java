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

import com.github._1c_syntax.bsl.mdo.storage.form.FormAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormElementType;
import com.github._1c_syntax.bsl.mdo.storage.form.FormItem;
import com.github._1c_syntax.bsl.reader.common.context.FormElementReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.extern.slf4j.Slf4j;

/**
 * Конвертор элемента формы в формате конфигуратора
 */
@DesignerConverter
@Slf4j
public class FormElementConverter implements ReadConverter {

  private static final String CONDITIONAL_APPEARANCE_TYPE_NAME = "ConditionalAppearance";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    // todo надо научится читать, пока пропускаем
    if (CONDITIONAL_APPEARANCE_TYPE_NAME.equals(reader.getNodeName())) {
      return null;
    }

    var readerContext = new FormElementReaderContext(reader.getNodeName(), reader);
    try {
      readerContext.setValue("id", Integer.parseInt(reader.getAttribute("id")));
    } catch (NumberFormatException e) {
      LOGGER.debug("Unknown type {} in file {}", reader.getNodeName(), ExtendXStream.getCurrentPath(reader).toString());
      return null;
    }
    readerContext.setValue("type", FormElementType.fromString(reader.getNodeName()));
    readerContext.setValue("name", reader.getAttribute("name"));
    Unmarshaller.unmarshal(reader, context, readerContext);
    return readerContext.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return FormItem.class.isAssignableFrom(type)
      || type == FormAttribute.class;
  }
}
