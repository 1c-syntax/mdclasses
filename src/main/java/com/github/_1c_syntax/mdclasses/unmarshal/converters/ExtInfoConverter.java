/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.mdclasses.unmarshal.converters;

import com.github._1c_syntax.mdclasses.mdo.children.form.DynamicListExtInfo;
import com.github._1c_syntax.mdclasses.mdo.children.form.ExtInfo;
import com.github._1c_syntax.mdclasses.mdo.children.form.InputFieldExtInfo;
import com.github._1c_syntax.mdclasses.unmarshal.XStreamFactory;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.Optional;

public class ExtInfoConverter implements Converter {
  private static final String TYPE_DYNAMIC_LIST = "form:DynamicListExtInfo";
  private static final String TYPE_INPUT_FIELD = "form:InputFieldExtInfo";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    ExtInfo item;
    var type = Optional.ofNullable(reader.getAttribute("type")).orElse(ExtInfo.UNKNOWN);
    // TODO: отрефакторить при добавлении нового типа ExtInfo
    if (TYPE_DYNAMIC_LIST.equals(type)) {
      item = createExtInfo(reader, context, DynamicListExtInfo.class);
    } else if (TYPE_INPUT_FIELD.equals(type)) {
      item = createExtInfo(reader, context, InputFieldExtInfo.class);
    } else {
      item = new ExtInfo();
    }
    item.setType(type);
    return item;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == ExtInfo.class;
  }

  private static ExtInfo createExtInfo(HierarchicalStreamReader reader, UnmarshallingContext context,
                                       Class<? extends ExtInfo> classTo) {
    return (ExtInfo) context.convertAnother(reader, classTo, XStreamFactory.getReflectionConverter());
  }
}
