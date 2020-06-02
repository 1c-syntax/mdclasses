/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.AccountingFlag;
import com.github._1c_syntax.mdclasses.mdo.AddressingAttribute;
import com.github._1c_syntax.mdclasses.mdo.Attribute;
import com.github._1c_syntax.mdclasses.mdo.Column;
import com.github._1c_syntax.mdclasses.mdo.Dimension;
import com.github._1c_syntax.mdclasses.mdo.ExtDimensionAccountingFlag;
import com.github._1c_syntax.mdclasses.mdo.MDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.Recalculation;
import com.github._1c_syntax.mdclasses.mdo.Resource;
import com.github._1c_syntax.mdclasses.mdo.TabularSection;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Используется для чтения атрибутов объекта, включая табличные части и их атрибуты
 */
public class AttributeConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    Object result;
    var uuid = reader.getAttribute("uuid");
    switch (reader.getNodeName()) {
      case "dimensions":
        result = getMdoAttribute(context, uuid, Dimension.class);
        break;
      case "resources":
        result = getMdoAttribute(context, uuid, Resource.class);
        break;
      case "recalculations":
        result = getMdoAttribute(context, uuid, Recalculation.class);
        break;
      case "attributes":
        result = getMdoAttribute(context, uuid, Attribute.class);
        break;
      case "tabularSections":
        result = getMdoAttribute(context, uuid, TabularSection.class);
        break;
      case "accountingFlags":
        result = getMdoAttribute(context, uuid, AccountingFlag.class);
        break;
      case "extDimensionAccountingFlags":
        result = getMdoAttribute(context, uuid, ExtDimensionAccountingFlag.class);
        break;
      case "columns":
        result = getMdoAttribute(context, uuid, Column.class);
        break;
      case "addressingAttributes":
        result = getMdoAttribute(context, uuid, AddressingAttribute.class);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + reader.getNodeName());
    }
    return result;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == MDOAttribute.class;
  }

  private static MDOAttribute getMdoAttribute(UnmarshallingContext context, String uuid, Class<? extends MDOAttribute> clazz) {
    var tmp = new MDObjectBase();
    MDOAttribute attribute = clazz.cast(context.convertAnother(tmp, clazz));
    attribute.setUuid(uuid);
    return attribute;
  }
}
