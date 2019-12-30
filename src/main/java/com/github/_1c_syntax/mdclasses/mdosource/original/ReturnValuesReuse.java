

package com.github._1c_syntax.mdclasses.mdosource.original;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum ReturnValuesReuse {

  @JsonProperty("DontUse")
  DONT_USE("DontUse"),
  @JsonProperty("DuringRequest")
  DURING_REQUEST("DuringRequest"),
  @JsonProperty("DuringSession")
  DURING_SESSION("DuringSession");
  private final String value;

  ReturnValuesReuse(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ReturnValuesReuse fromValue(String v) {
    for (ReturnValuesReuse c : ReturnValuesReuse.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
