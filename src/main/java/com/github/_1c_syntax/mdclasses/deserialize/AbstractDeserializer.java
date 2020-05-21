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
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.NullNode;
import com.github._1c_syntax.mdclasses.utils.ObjectMapperFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс реализация кастомной десериализации XML файлов конфигурации.
 * В дочерних классах необходимо реализовать только метод чтение свойства readToken
 */
abstract class AbstractDeserializer extends JsonDeserializer<Object> {

  protected final String rootKey;

  protected AbstractDeserializer(String key) {
    rootKey = key;
  }

  @Override
  public Map<String, Object> deserialize(
    JsonParser parser,
    DeserializationContext context
  ) throws IOException {
    Map<String, Object> childObjects = new HashMap<>();

    if (parser.getCurrentToken() != JsonToken.START_OBJECT) {
      return childObjects;
    }

    int level = 1;
    while (parser.nextToken() != null) {
      var token = parser.getCurrentToken();
      var name = parser.getCurrentName();

      // скрректируем уровень, для того, чтобы вовремя выйти
      level = correctLevel(name, token, level);
      if (level == 0) {
        break;
      }

      // обрабатываем только свойства
      if (token == JsonToken.FIELD_NAME) {
        readToken(parser, childObjects, name, context);
      }
    }
    return childObjects;
  }

  protected abstract void readToken(JsonParser parser,
                                    Map<String, Object> childObjects,
                                    String name,
                                    DeserializationContext context) throws IOException;

  private int correctLevel(String name, JsonToken token, int level) {
    var isRoot = rootKey.equals(name);
    if (isRoot && token == JsonToken.END_OBJECT) {
      level--;
    } else if (isRoot && token == JsonToken.START_OBJECT) {
      level++;
    }

    return level;
  }

  protected boolean isEndToken(String name, JsonParser parser) throws IOException {
    return parser.getCurrentToken() == JsonToken.END_OBJECT && name.equals(parser.getCurrentName());
  }

  protected String getValueFromNode(TreeNode node) {
    if (node instanceof NullNode) {
      return null;
    }
    return getValueFromNode(node, String.class);
  }

  protected <T> T getValueFromNode(TreeNode node, Class<T> classValue) {
    return ObjectMapperFactory.getXmlMapper().convertValue(node, classValue);
  }

  protected <T> T readValueByType(JsonParser parser, Class<T> classValue) throws IOException {
    return ObjectMapperFactory.getXmlMapper().readValue(parser, classValue);
  }

  protected void addProperty(Map<String, Object> childObjects, String name, Object newValue) {
    var value = childObjects.get(name);
    if (value == null) {
      childObjects.put(name, newValue);
    } else if (value instanceof List) {
      List<Object> values = new ArrayList<>((Collection<?>) value);
      values.add(newValue);
      childObjects.put(name, values);
    } else {
      List<Object> values = new ArrayList<>();
      values.add(value);
      values.add(newValue);
      childObjects.put(name, values);
    }
  }

  protected void readAndAddProperty(Map<String, Object> childObjects, String name, JsonParser parser) throws IOException {
    var newValue = getValueFromNode(parser.readValueAsTree());
    addProperty(childObjects, name, newValue);
  }

}
