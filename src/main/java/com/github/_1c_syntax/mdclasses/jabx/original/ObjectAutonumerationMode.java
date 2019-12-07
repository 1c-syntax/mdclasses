

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ObjectAutonumerationMode {

  @JsonProperty("AutoFree")
  AUTO_FREE("AutoFree"),
  @JsonProperty("NotAutoFree")
  NOT_AUTO_FREE("NotAutoFree");
  private final String value;

  ObjectAutonumerationMode(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ObjectAutonumerationMode fromValue(String v) {
    for (ObjectAutonumerationMode c : ObjectAutonumerationMode.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
