

package com.github._1c_syntax.mdclasses.mdosource.original;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum ObjectBelonging {

  @JsonProperty("Native")
  NATIVE("Native"),
  @JsonProperty("Adopted")
  ADOPTED("Adopted");
  private final String value;

  ObjectBelonging(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ObjectBelonging fromValue(String v) {
    for (ObjectBelonging c : ObjectBelonging.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
