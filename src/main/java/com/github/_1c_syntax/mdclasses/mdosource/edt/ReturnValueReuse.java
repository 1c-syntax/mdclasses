

package com.github._1c_syntax.mdclasses.mdosource.edt;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum ReturnValueReuse {

  @JsonProperty("DuringRequest")
  DURING_REQUEST("DuringRequest"),
  @JsonProperty("DuringSession")
  DURING_SESSION("DuringSession");
  private final String value;

  ReturnValueReuse(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ReturnValueReuse fromValue(String v) {
    for (ReturnValueReuse c : ReturnValueReuse.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
