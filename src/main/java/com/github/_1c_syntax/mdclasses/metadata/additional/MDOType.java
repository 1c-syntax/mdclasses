package com.github._1c_syntax.mdclasses.metadata.additional;

public enum MDOType {
  CONFIGURATION("Configuration", ""),
  COMMON_MODULE("CommonModule", "CommonModules");

  private String shortClassName;
  private String groupName;

  MDOType(String shortName, String groupName) {
    this.shortClassName = shortName;
    this.groupName = groupName;
  }

  public String getShortClassName() {
    return shortClassName;
  }

  public String getGroupName() {
    return groupName;
  }
}