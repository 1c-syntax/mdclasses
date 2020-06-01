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
