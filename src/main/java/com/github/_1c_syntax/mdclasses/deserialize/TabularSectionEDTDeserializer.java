package com.github._1c_syntax.mdclasses.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.github._1c_syntax.mdclasses.mdo.Attribute;

import java.io.IOException;
import java.util.Map;

public class TabularSectionEDTDeserializer extends AbstractDeserializer {

  private static final String ATTRIBUTE_KEY = "attributes";
  private static final String ATTRIBUTES_KEY = "tabularAttributes";

  public TabularSectionEDTDeserializer() {
    super("tabularSections");
  }

  @Override
  protected void readToken(JsonParser parser, Map<String, Object> childObjects, String name) throws IOException {
    parser.nextToken();
    if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
      if (name.equals(ATTRIBUTE_KEY)) {
        var newValue = getValueFromNode(parser.readValueAsTree(), Attribute.class);
        addProperty(childObjects, ATTRIBUTES_KEY, newValue);
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
}
