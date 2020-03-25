package com.github._1c_syntax.mdclasses.metadata.additional;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReturnValueReuse {

  @JsonProperty("DontUse")
  DONT_USE("DontUse"),
  @JsonProperty("DuringRequest")
  DURING_REQUEST("DuringRequest"),
  @JsonProperty("DuringSession")
  DURING_SESSION("DuringSession");

  private final String value;

  ReturnValueReuse(String value) {
    this.value = value;
  }

  public static ReturnValueReuse fromValue(String value) {
    for (ReturnValueReuse returnValueReuse : ReturnValueReuse.values()) {
      if (returnValueReuse.value.equals(value)) {
        return returnValueReuse;
      }
    }
    throw new IllegalArgumentException(value);
  }

  public String value() {
    return this.value;
  }
}
