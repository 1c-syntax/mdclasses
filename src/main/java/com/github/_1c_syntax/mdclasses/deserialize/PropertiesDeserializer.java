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
