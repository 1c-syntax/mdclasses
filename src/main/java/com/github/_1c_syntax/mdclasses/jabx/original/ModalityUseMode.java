

package com.github._1c_syntax.mdclasses.jabx.original;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ModalityUseMode {

  @JsonProperty("Use")
  USE("Use"),
  @JsonProperty("UseWithWarnings")
  USE_WITH_WARNINGS("UseWithWarnings"),
  @JsonProperty("DontUse")
  DONT_USE("DontUse");
  private final String value;

  ModalityUseMode(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ModalityUseMode fromValue(String v) {
    for (ModalityUseMode c : ModalityUseMode.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
