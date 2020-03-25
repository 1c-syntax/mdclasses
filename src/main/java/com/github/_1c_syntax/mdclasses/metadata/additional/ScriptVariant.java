package com.github._1c_syntax.mdclasses.metadata.additional;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ScriptVariant {
  @JsonProperty("English")
  ENGLISH("English"),
  @JsonProperty("Russian")
  RUSSIAN("Russian");

  private final String value;

  ScriptVariant(String value) {
    this.value = value;
  }

  public static ScriptVariant fromValue(String value) {
    return valueOf(value.toUpperCase());
  }

  public String value() {
    return value;
  }
}
