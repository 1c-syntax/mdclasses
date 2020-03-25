package com.github._1c_syntax.mdclasses.metadata.additional;

public enum ModuleType {

  CommandModule("CommandModule.bsl"),
  CommonModule("Module.bsl"),
  ObjectModule("ObjectModule.bsl"),
  ManagerModule("ManagerModule.bsl"),
  FormModule("Module.bsl"),
  RecordSetModule("RecordSetModule.bsl"),
  ValueManagerModule("ValueManagerModule.bsl"),
  ApplicationModule("ApplicationModule.bsl"),
  ManagedApplicationModule("ManagedApplicationModule.bsl"),
  SessionModule("SessionModule.bsl"),
  ExternalConnectionModule("ExternalConnectionModule.bsl"),
  OrdinaryApplicationModule("OrdinaryApplicationModule.bsl"),
  HTTPServiceModule("Module.bsl"),
  WEBServiceModule("Module.bsl"),
  Unknown("");

  private String fileName;

  ModuleType(String fileName) {
    this.fileName = fileName;
  }

  public String getFileName() {
    return this.fileName;
  }
}
