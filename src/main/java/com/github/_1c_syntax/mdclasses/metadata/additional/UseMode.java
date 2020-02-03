package com.github._1c_syntax.mdclasses.metadata.additional;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UseMode {
  @JsonProperty("DontUse")
  DONT_USE("DontUse"),
  @JsonProperty("Use")
  USE("Use"),
  @JsonProperty("UseWithWarnings")
  USE_WITH_WARNINGS("UseWithWarnings");

  private final String value;

  UseMode(String v) {
    this.value = v;
  }

  public static UseMode fromValue(String v) {
    for (UseMode c : UseMode.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

  public String value() {
    return this.value;
  }
}
