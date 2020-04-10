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
package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github._1c_syntax.mdclasses.deserialize.ChildObjectsDeserializer;
import com.github._1c_syntax.mdclasses.deserialize.PropertiesDeserializer;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.utils.ObjectMapperFactory;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.github._1c_syntax.mdclasses.utils.MapExtension.getOrEmptyString;

@Data
@NonFinal
@JsonDeserialize(builder = MDObjectBase.MDObjectBaseBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(of = {"mdoRef"})
@ToString(of = {"mdoRef"})
public class MDObjectBase {

  protected final String uuid;
  protected final String name;
  @Builder.Default
  protected String comment = "";

  protected URI mdoURI;
  protected String mdoRef;
  protected Map<URI, ModuleType> modulesByType;
  protected List<Form> forms;
  protected List<Command> commands;
  protected List<Subsystem> includedSubsystems;

  public MDOType getType() {
    return null;
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

  public void addIncludedSubsystems(Subsystem subsystem) {
    if (this.includedSubsystems == null) {
      this.includedSubsystems = new ArrayList<>();
    }
    this.includedSubsystems.add(subsystem);
  }

  public void computeMdoRef() {
    this.mdoRef = getType().getClassName() + "." + getName();
  }

  public abstract static class MDObjectBaseBuilder
    <C extends MDObjectBase, B extends MDObjectBase.MDObjectBaseBuilder<C, B>> {

    // Re-define generated method's to implement basic read of `properties` collection.
    // It's defined here (but not in Impl class) to make it callable from other SuperBuilders
    @JsonProperty("Properties")
    @JsonDeserialize(using = PropertiesDeserializer.class)
    protected MDObjectBaseBuilder<C, B> properties(Map<String, Object> properties) {
      name(getOrEmptyString(properties, "Name"));
      comment(getOrEmptyString(properties, "Comment"));
      return this.self();
    }

    @JsonProperty("ChildObjects")
    @JsonDeserialize(using = ChildObjectsDeserializer.class)
    protected MDObjectBaseBuilder<C, B> childObjects(Map<String, Object> properties) {
      if (properties != null) {
        var value = properties.get("Command");
        if (value != null) {
          if (value instanceof List) {
            List<?> values = new ArrayList<>((Collection<?>) value);
            values.forEach(command -> addCommand((Command) command));
          } else {
            addCommand((Command) value);
          }
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
        addCommand(ObjectMapperFactory.getXmlMapper().convertValue(properties, Command.class));
      }
      return this.self();
    }

    private void addCommand(Command command) {
      if (commands == null) {
        commands = new ArrayList<>();
      }
      commands.add(command);
    }
  }

  // Mark builder implementation as Jackson JSON builder with methods w/o `with-` in their names.
  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class MDObjectBaseBuilderImpl
    extends MDObjectBase.MDObjectBaseBuilder<MDObjectBase, MDObjectBase.MDObjectBaseBuilderImpl> {
  }

}
