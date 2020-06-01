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
package com.github._1c_syntax.mdclasses.mdo.wrapper;

import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Враппер над свойствами объекта в формате конфигуратора
 */
@Data
@NoArgsConstructor
public class DesignerProperties {

  @NonNull
  @XStreamAlias("Name")
  String name;
  @NonNull
  @XStreamAlias("Comment")
  String comment = "";

  @XStreamAlias("Server")
  boolean server = false;
  @XStreamAlias("Global")
  boolean global = false;
  @XStreamAlias("ClientManagedApplication")
  boolean clientManagedApplication = false;
  @XStreamAlias("ExternalConnection")
  boolean externalConnection = false;
  @XStreamAlias("ClientOrdinaryApplication")
  boolean clientOrdinaryApplication = false;
  @XStreamAlias("ServerCall")
  boolean serverCall = false;
  @XStreamAlias("Privileged")
  boolean privileged = false;
  @NonNull
  @XStreamAlias("ReturnValuesReuse")
  ReturnValueReuse returnValuesReuse = ReturnValueReuse.DONT_USE;

  @NonNull
  @XStreamAlias("Handler")
  String handler = "";

  @NonNull
  @XStreamAlias("Content")
  DesignerXRItems content = new DesignerXRItems();

  @NonNull
  @XStreamAlias("ScriptVariant")
  ScriptVariant scriptVariant = ScriptVariant.ENGLISH;
  @NonNull
  @XStreamAlias("CompatibilityMode")
  CompatibilityMode compatibilityMode = new CompatibilityMode();
  @NonNull
  @XStreamAlias("ConfigurationExtensionCompatibilityMode")
  CompatibilityMode configurationExtensionCompatibilityMode = new CompatibilityMode();
  @NonNull
  @XStreamAlias("ModalityUseMode")
  UseMode modalityUseMode = UseMode.USE;
  @NonNull
  @XStreamAlias("SynchronousExtensionAndAddInCallUseMode")
  UseMode synchronousExtensionAndAddInCallUseMode = UseMode.USE;
  @NonNull
  @XStreamAlias("SynchronousPlatformExtensionAndAddInCallUseMode")
  UseMode synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;
  @NonNull
  @XStreamAlias("DefaultRunMode")
  String defaultRunMode = "";
  @NonNull
  @XStreamAlias("DefaultLanguage")
  String defaultLanguage = "";
  @NonNull
  @XStreamAlias("DataLockControlMode")
  String dataLockControlMode = "";
  @NonNull
  @XStreamAlias("ObjectAutonumerationMode")
  String objectAutonumerationMode = "";

}
