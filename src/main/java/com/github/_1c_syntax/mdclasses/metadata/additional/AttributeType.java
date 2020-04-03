package com.github._1c_syntax.mdclasses.metadata.additional;

public enum AttributeType {
  ATTRIBUTE("Attribute"),
  DIMENSION("Dimension"),
  RESOURCE("Resource"),
  TABULAR_SECTION("TabularSection");

  private String shortClassName;

  AttributeType(String shortName) {
    this.shortClassName = shortName;
  }

  public String getClassName() {
    return shortClassName;
  }

}

