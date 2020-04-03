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
package com.github._1c_syntax.mdclasses.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.github._1c_syntax.mdclasses.mdo.Attribute;
import com.github._1c_syntax.mdclasses.mdo.Command;
import com.github._1c_syntax.mdclasses.mdo.Dimension;
import com.github._1c_syntax.mdclasses.mdo.Resource;
import com.github._1c_syntax.mdclasses.mdo.TabularSection;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;

import java.io.IOException;
import java.util.Map;

public class ChildObjectsDeserializer extends AbstractDeserializer {

  private static final String COMMAND_KEY = "Command";
  private static final String SUBSYSTEM_KEY = "Subsystem";
  private static final String DIMENSION_KEY = "Dimension";
  private static final String RESOURCE_KEY = "Resource";
  private static final String ATTRIBUTE_KEY = "Attribute";
  private static final String TABULAR_SECTION_KEY = "TabularSection";

  public ChildObjectsDeserializer() {
    super("ChildObjects");
  }

  @Override
  protected void readToken(JsonParser parser, Map<String, Object> childObjects, String name) throws IOException {
    if (name.equals(COMMAND_KEY)) {
      parser.nextToken();
      var newValue = getValueFromNode(parser.readValueAsTree(), Command.class);
      addProperty(childObjects, name, newValue);
    } else if (name.equals(SUBSYSTEM_KEY)) {
      parser.nextToken();
      var newValue = MDOType.SUBSYSTEM.getClassName() + "." + getValueFromNode(parser.readValueAsTree());
      addProperty(childObjects, name, newValue);
    } else if (name.equals(DIMENSION_KEY)) {
      parser.nextToken();
      var newValue = getValueFromNode(parser.readValueAsTree(), Dimension.class);
      addProperty(childObjects, ATTRIBUTE_KEY, newValue);
    } else if (name.equals(RESOURCE_KEY)) {
      parser.nextToken();
      var newValue = getValueFromNode(parser.readValueAsTree(), Resource.class);
      addProperty(childObjects, ATTRIBUTE_KEY, newValue);
    } else if (name.equals(ATTRIBUTE_KEY)) {
      parser.nextToken();
      var newValue = getValueFromNode(parser.readValueAsTree(), Attribute.class);
      addProperty(childObjects, ATTRIBUTE_KEY, newValue);
    } else if (name.equals(TABULAR_SECTION_KEY)) {
      parser.nextToken();
      var newValue = getValueFromNode(parser.readValueAsTree(), TabularSection.class);
      addProperty(childObjects, ATTRIBUTE_KEY, newValue);
    }
  }
}
