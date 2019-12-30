

package com.github._1c_syntax.mdclasses.mdosource.original;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum DefaultDataLockControlMode {

  @JsonProperty("Automatic")
  AUTOMATIC("Automatic"),
  @JsonProperty("Managed")
  MANAGED("Managed"),
  @JsonProperty("AutomaticAndManaged")
  AUTOMATIC_AND_MANAGED("AutomaticAndManaged");
  private final String value;

  DefaultDataLockControlMode(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static DefaultDataLockControlMode fromValue(String v) {
    for (DefaultDataLockControlMode c : DefaultDataLockControlMode.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
