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
        readToken(parser, childObjects, name);
      }
    }
    return childObjects;
  }

  protected abstract void readToken(JsonParser parser, Map<String, Object> childObjects, String name) throws IOException;

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
