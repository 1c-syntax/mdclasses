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
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.Map;

public class PropertiesDeserializer extends AbstractDeserializer {

  private static final String CONTENT_KEY = "Content";

  protected PropertiesDeserializer() {
    super("Properties");
  }

  @Override
  protected void readToken(JsonParser parser, Map<String, Object> childObjects, String name) throws IOException {
    parser.nextToken(); // вошли во внутрь

    if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
      if (name.equals(CONTENT_KEY)) {
        readContent(parser, childObjects, name);
      } else {
        // объекты пропускаем
        while (parser.nextToken() != null) {
          if (isEndToken(name, parser)) {
            break;
          }
        }
      }
    } else {
      readAndAddProperty(childObjects, name, parser);
    }
  }

  private void readContent(JsonParser parser, Map<String, Object> childObjects, String name) throws IOException {
    while (parser.nextToken() != null) {
      if (isEndToken(name, parser)) {
        break;
      }
      parser.nextToken();
      var value = parser.readValueAsTree();
      var newValue = getValueFromNode(value.get(""));
      addProperty(childObjects, name, newValue);
    }
  }
}
