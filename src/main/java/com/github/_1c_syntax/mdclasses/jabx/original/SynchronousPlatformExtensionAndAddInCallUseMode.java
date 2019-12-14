

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum SynchronousPlatformExtensionAndAddInCallUseMode {

  @JsonProperty("Use")
  USE("Use"),
  @JsonProperty("UseWithWarnings")
  USE_WITH_WARNINGS("UseWithWarnings"),
  @JsonProperty("DontUse")
  DONT_USE("DontUse");
  private final String value;

  SynchronousPlatformExtensionAndAddInCallUseMode(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static SynchronousPlatformExtensionAndAddInCallUseMode fromValue(String v) {
    for (SynchronousPlatformExtensionAndAddInCallUseMode c : SynchronousPlatformExtensionAndAddInCallUseMode.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
