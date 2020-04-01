package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.utils.ObjectMapperFactory;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github._1c_syntax.mdclasses.utils.MapExtension.getOrEmptyString;

@Data
@NonFinal
@JsonDeserialize(builder = MDObjectBase.MDObjectBaseBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(exclude = {"mdoURI", "modulesByType", "forms", "commands"})
@ToString(exclude = {"mdoURI", "modulesByType", "forms", "commands"})
public class MDObjectBase {

  protected final String uuid;
  protected final String name;
  @Builder.Default
  protected String comment = "";

  protected URI mdoURI;
  protected Map<URI, ModuleType> modulesByType;
  protected List<Form> forms;
  protected List<Command> commands;

  public abstract static class MDObjectBaseBuilder
    <C extends MDObjectBase, B extends MDObjectBase.MDObjectBaseBuilder<C, B>> {

    static class CommandsDeserializer extends JsonDeserializer<Object> {
      @Override
      public Map<String, List<MDObjectBase>> deserialize(
        JsonParser parser,
        DeserializationContext context
      ) throws IOException {

        Map<String, List<MDObjectBase>> childObjects = new HashMap<>();
        if (parser.getCurrentToken() != JsonToken.START_OBJECT) {
          return childObjects;
        }

        var commandKey = "Command";
        int level = 1; // текущий уровень ChildObjects
        while (parser.nextToken() != null) {
          var token = parser.getCurrentToken();
          var name = parser.getCurrentName();

          // скрректируем уровень, для того, чтобы вовремя выйти
          var isChildObjects = "ChildObjects".equals(name);
          if (isChildObjects && token == JsonToken.END_OBJECT) {
            level--;
          } else if (isChildObjects && token == JsonToken.START_OBJECT) {
            level++;
          }
          if (level == 0) {
            break;
          }

          // находим свойство с нужным именем
          if (token == JsonToken.FIELD_NAME && commandKey.equals(name)) {
            // переходим к значению свойсва, чтобы не читать лишнее и не потерять данные
            // т.к. jackson просто схлопнет в мапу
            parser.nextToken();
            // получаем дерево ноды
            var nodeTree = parser.readValueAsTree();
            // читаем в объект
            var command = ObjectMapperFactory.getXmlMapper().convertValue(nodeTree, Command.class);
            var commands = childObjects.getOrDefault(commandKey, new ArrayList<>());
            commands.add(command);
            childObjects.put(commandKey, commands);
          }
        }

        return childObjects;
      }
    }

    // Re-define generated method's to implement basic read of `properties` collection.
    // It's defined here (but not in Impl class) to make it callable from other SuperBuilders
    @JsonProperty("Properties")
    protected MDObjectBaseBuilder<C, B> properties(Map<String, Object> properties) {
      name(getOrEmptyString(properties, "Name"));
      comment(getOrEmptyString(properties, "Comment"));

      return this.self();
    }

    @JsonProperty("ChildObjects")
    @JsonDeserialize(using = CommandsDeserializer.class)
    protected MDObjectBaseBuilder<C, B> childObjects(Map<String, List<MDObjectBase>> properties) {
      if (properties != null) {
        if (commands == null) {
          commands = new ArrayList<>();
        }
        List<MDObjectBase> values = properties.get("Command");
        if (values != null) {
          values.forEach(command -> commands.add((Command) command));
        }
      }
      return this.self();
    }

    @JsonProperty("forms")
    protected MDObjectBaseBuilder<C, B> forms(Map<String, Object> properties) {
      if (properties != null) {
        if (forms == null) {
          forms = new ArrayList<>();
        }
        forms.add(ObjectMapperFactory.getXmlMapper().convertValue(properties, Form.class));
      }
      return this.self();
    }

    @JsonProperty("commands")
    protected MDObjectBaseBuilder<C, B> commands(Map<String, Object> properties) {
      if (properties != null) {
        if (commands == null) {
          commands = new ArrayList<>();
        }
        commands.add(ObjectMapperFactory.getXmlMapper().convertValue(properties, Command.class));
      }
      return this.self();
    }

  }

  public MDOType getType() {
    return MDOType.CONFIGURATION;
  }

  public void setMdoURI(URI uri) {
    if (this.mdoURI == null) {
      this.mdoURI = uri;
    }
  }

  public void setModulesByType(Map<URI, ModuleType> modulesByType) {
    if (this.modulesByType == null) {
      this.modulesByType = modulesByType;
    }
  }

  public void setForms(List<Form> forms) {
    if (this.forms == null) {
      this.forms = new ArrayList<>();
    }
    this.forms.addAll(forms);
  }

  public void setCommands(List<Command> commands) {
    if (this.commands == null) {
      this.commands = new ArrayList<>();
    }
    this.commands.addAll(commands);
  }

  // Mark builder implementation as Jackson JSON builder with methods w/o `with-` in their names.
  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class MDObjectBaseBuilderImpl
    extends MDObjectBase.MDObjectBaseBuilder<MDObjectBase, MDObjectBase.MDObjectBaseBuilderImpl> {
  }

}
