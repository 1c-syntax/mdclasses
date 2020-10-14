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
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationExtensionPurpose;
import com.github._1c_syntax.mdclasses.metadata.additional.ObjectBelonging;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * Враппер над свойствами объекта в формате конфигуратора
 */
@Data
@NoArgsConstructor
public class DesignerProperties {

  @XStreamAlias("Name")
  private String name;
  @XStreamAlias("Synonym")
  private List<DesignerSynonym> synonyms = Collections.emptyList();
  @XStreamAlias("Comment")
  private String comment = "";
  @XStreamAlias("ObjectBelonging")
  protected ObjectBelonging objectBelonging = ObjectBelonging.OWN;

  @XStreamAlias("Server")
  private boolean server;
  @XStreamAlias("Global")
  private boolean global;
  @XStreamAlias("ClientManagedApplication")
  private boolean clientManagedApplication;
  @XStreamAlias("ExternalConnection")
  private boolean externalConnection;
  @XStreamAlias("ClientOrdinaryApplication")
  private boolean clientOrdinaryApplication;
  @XStreamAlias("ServerCall")
  private boolean serverCall;
  @XStreamAlias("Privileged")
  private boolean privileged;
  @XStreamAlias("ReturnValuesReuse")
  private ReturnValueReuse returnValuesReuse = ReturnValueReuse.DONT_USE;

  @XStreamAlias("Handler")
  private String handler = "";

  @XStreamAlias("Content")
  private DesignerXRItems content = new DesignerXRItems();

  @XStreamAlias("ScriptVariant")
  private ScriptVariant scriptVariant = ScriptVariant.ENGLISH;
  @XStreamAlias("CompatibilityMode")
  private CompatibilityMode compatibilityMode = new CompatibilityMode();
  @XStreamAlias("ConfigurationExtensionCompatibilityMode")
  private CompatibilityMode configurationExtensionCompatibilityMode = new CompatibilityMode();
  @XStreamAlias("ModalityUseMode")
  private UseMode modalityUseMode = UseMode.USE;
  @XStreamAlias("SynchronousExtensionAndAddInCallUseMode")
  private UseMode synchronousExtensionAndAddInCallUseMode = UseMode.USE;
  @XStreamAlias("SynchronousPlatformExtensionAndAddInCallUseMode")
  private UseMode synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;
  @XStreamAlias("DefaultRunMode")
  private String defaultRunMode = "";
  @XStreamAlias("DefaultLanguage")
  private String defaultLanguage = "";
  @XStreamAlias("DataLockControlMode")
  private String dataLockControlMode = "";
  @XStreamAlias("ObjectAutonumerationMode")
  private String objectAutonumerationMode = "";
  @XStreamAlias("ProcedureName")
  private String wsOperationProcedureName = "";
  @XStreamAlias("LanguageCode")
  private String languageCode = "";
  @XStreamAlias("ConfigurationExtensionPurpose")
  private ConfigurationExtensionPurpose configurationExtensionPurpose = ConfigurationExtensionPurpose.PATCH;
  @XStreamAlias("NamePrefix")
  private String namePrefix = "";

  @XStreamAlias("IncludeInCommandInterface")
  private boolean includeInCommandInterface;

  @XStreamAlias("MethodName")
  private String methodName = "";

}
