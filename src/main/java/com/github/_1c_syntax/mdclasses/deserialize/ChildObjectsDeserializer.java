package com.github._1c_syntax.mdclasses.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.github._1c_syntax.mdclasses.mdo.Command;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;

import java.io.IOException;
import java.util.Map;

public class ChildObjectsDeserializer extends AbstractDeserializer {

  private static final String COMMAND_KEY = "Command";
  private static final String SUBSYSTEM_KEY = "Subsystem";

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
    }
  }
}
