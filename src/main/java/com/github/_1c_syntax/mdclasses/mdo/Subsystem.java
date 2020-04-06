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
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = Subsystem.SubsystemBuilderImpl.class)
@SuperBuilder
public class Subsystem extends MDObjectBase {

  static {
    type = MDOType.SUBSYSTEM;
  }

  @Getter
  Set<Either<String, MDObjectBase>> children;

  public abstract static class SubsystemBuilder<C extends Subsystem, B extends Subsystem.SubsystemBuilder<C, B>> extends MDObjectBaseBuilder<C, B> {

    private void childrenAdd(String value) {
      if (children == null) {
        children = new HashSet<>();
      }
      children.add(Either.forLeft(value.intern()));
    }

    @JsonProperty("subsystems")
    public SubsystemBuilder<C, B> subsystems(String name) {
      if (name != null) {
        childrenAdd((MDOType.SUBSYSTEM.getClassName() + "." + name));
      }
      return this.self();
    }

    @JsonProperty("content")
    public SubsystemBuilder<C, B> content(String name) {
      if (name != null) {
        childrenAdd(name);
      }
      return this.self();
    }
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class SubsystemBuilderImpl extends Subsystem.SubsystemBuilder<Subsystem, Subsystem.SubsystemBuilderImpl> {

    private Set<Either<String, MDObjectBase>> childrenValue;

    @Override
    protected MDObjectBaseBuilder<Subsystem, SubsystemBuilderImpl> childObjects(Map<String, Object> properties) {
      super.childObjects(properties);
      if (properties != null) {
        childrenAdd(properties.get("Subsystem"));
      }
      return this.self();
    }

    @Override
    protected MDObjectBaseBuilder<Subsystem, SubsystemBuilderImpl> properties(Map<String, Object> properties) {
      super.properties(properties);
      if (properties != null) {
        childrenAdd(properties.get("Content"));
      }
      return this.self();
    }

    private void childrenAdd(Object value) {
      if (childrenValue == null) {
        childrenValue = new HashSet<>();
      }

      if (value != null) {
        if (value instanceof List) {
          List<?> values = new ArrayList<>((Collection<?>) value);
          values.forEach(subsystemName -> childrenValue.add(Either.forLeft(subsystemName.toString())));
        } else {
          childrenValue.add(Either.forLeft(value.toString()));
        }
      }
      children(childrenValue);
    }
  }
}
