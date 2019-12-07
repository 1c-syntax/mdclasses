

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum ScriptVariant {

  @JsonProperty("English")
  ENGLISH("English"),
  @JsonProperty("Russian")
  RUSSIAN("Russian");
  private final String value;

  ScriptVariant(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ScriptVariant fromValue(String v) {
    for (ScriptVariant c : ScriptVariant.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
