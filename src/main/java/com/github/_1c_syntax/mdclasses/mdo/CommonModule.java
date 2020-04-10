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
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.Map;

import static com.github._1c_syntax.mdclasses.utils.MapExtension.getOrEmptyString;
import static com.github._1c_syntax.mdclasses.utils.MapExtension.getOrFalse;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@JsonDeserialize(builder = CommonModule.CommonModuleBuilderImpl.class)
@SuperBuilder
public class CommonModule extends MDObjectBase {

  boolean server;
  boolean global;
  boolean clientManagedApplication;
  boolean externalConnection;
  boolean clientOrdinaryApplication;
  boolean serverCall;
  @Builder.Default
  ReturnValueReuse returnValuesReuse = ReturnValueReuse.DONT_USE;
  boolean privileged;

  @Override
  public MDOType getType() {
    return MDOType.COMMON_MODULE;
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class CommonModuleBuilderImpl extends CommonModule.CommonModuleBuilder<CommonModule, CommonModule.CommonModuleBuilderImpl> {

    @JsonProperty("Properties")
    @Override
    public CommonModule.CommonModuleBuilderImpl properties(Map<String, Object> properties) {
      super.properties(properties);

      server(getOrFalse(properties, "Server"));
      global(getOrFalse(properties, "Global"));
      clientManagedApplication(getOrFalse(properties, "ClientManagedApplication"));
      externalConnection(getOrFalse(properties, "ExternalConnection"));
      clientOrdinaryApplication(getOrFalse(properties, "ClientOrdinaryApplication"));
      serverCall(getOrFalse(properties, "ServerCall"));
      returnValuesReuse(ReturnValueReuse.fromValue(getOrEmptyString(properties, "ReturnValuesReuse")));
      privileged(getOrFalse(properties, "Privileged"));

      return this.self();
    }
  }
}
