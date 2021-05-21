/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.mdclasses.unmarshal.wrapper;

import com.github._1c_syntax.mdclasses.common.CompatibilityMode;
import com.github._1c_syntax.mdclasses.mdo.children.template.TemplateType;
import com.github._1c_syntax.mdclasses.mdo.support.ConfigurationExtensionPurpose;
import com.github._1c_syntax.mdclasses.mdo.support.DataLockControlMode;
import com.github._1c_syntax.mdclasses.mdo.support.FormType;
import com.github._1c_syntax.mdclasses.mdo.support.MessageDirection;
import com.github._1c_syntax.mdclasses.mdo.support.ObjectBelonging;
import com.github._1c_syntax.mdclasses.mdo.support.ReturnValueReuse;
import com.github._1c_syntax.mdclasses.mdo.support.ScriptVariant;
import com.github._1c_syntax.mdclasses.mdo.support.UseMode;
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

  @XStreamAlias("Predefined")
  private boolean predefined;

  @XStreamAlias("ExternalIntegrationServiceAddress")
  private String externalIntegrationServiceAddress = "";
  @XStreamAlias("MessageDirection")
  private MessageDirection messageDirection = MessageDirection.SEND;
  @XStreamAlias("ReceiveMessageProcessing")
  private String receiveMessageProcessing = "";
  @XStreamAlias("ExternalIntegrationServiceChannelName")
  private String externalIntegrationServiceChannelName = "";

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

  @XStreamAlias("RegisterRecords")
  private DesignerXRItems registerRecords = new DesignerXRItems();

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
  private DataLockControlMode dataLockControlMode = DataLockControlMode.AUTOMATIC;
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

  @XStreamAlias("UseManagedFormInOrdinaryApplication")
  private boolean useManagedFormInOrdinaryApplication;
  @XStreamAlias("UseOrdinaryFormInManagedApplication")
  private boolean useOrdinaryFormInManagedApplication;

  @XStreamAlias("IncludeInCommandInterface")
  private boolean includeInCommandInterface;

  @XStreamAlias("MethodName")
  private String methodName = "";

  @XStreamAlias("TemplateType")
  private TemplateType templateType = TemplateType.SPREADSHEET_DOCUMENT;
  @XStreamAlias("FormType")
  private FormType formType = FormType.ORDINARY;

}
