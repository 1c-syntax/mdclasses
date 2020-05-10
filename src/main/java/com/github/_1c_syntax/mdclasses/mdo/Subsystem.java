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
import io.vavr.control.Either;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@JsonDeserialize(builder = Subsystem.SubsystemBuilderImpl.class)
@SuperBuilder
public class Subsystem extends MDObjectBase {

  @NonFinal
  @Setter
  Set<Either<String, MDObjectBase>> children;

  @NonFinal
  @Setter
  Subsystem parent;

  @Override
  public MDOType getType() {
    return MDOType.SUBSYSTEM;
  }

  @Override
  public void computeMdoRef() {
    super.computeMdoRef();
    if(parent != null) {
      this.mdoRef = this.parent.getMdoRef() + "." + this.mdoRef;
    }

  }

  public abstract static class SubsystemBuilder<C extends Subsystem, B extends Subsystem.SubsystemBuilder<C, B>> extends MDObjectBaseBuilder<C, B> {

    private void childrenAdd(String value) {
      if (children == null) {
        children = new HashSet<>();
      }
      children.add(Either.left(value.intern()));
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
          ((List<?>) value).stream()
            .filter(Objects::nonNull)
            .forEach(subsystemName -> childrenValue.add(Either.left(subsystemName.toString())));
        } else {
          childrenValue.add(Either.left(value.toString()));
        }
      }
      children(childrenValue);
    }
  }

}
