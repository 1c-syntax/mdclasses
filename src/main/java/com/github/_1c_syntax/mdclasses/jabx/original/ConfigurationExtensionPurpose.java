

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum ConfigurationExtensionPurpose {

  @JsonProperty("Patch")
  PATCH("Patch"),
  @JsonProperty("Customization")
  CUSTOMIZATION("Customization"),
  @JsonProperty("AddOn")
  ADD_ON("AddOn");
  private final String value;

  ConfigurationExtensionPurpose(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ConfigurationExtensionPurpose fromValue(String v) {
    for (ConfigurationExtensionPurpose c : ConfigurationExtensionPurpose.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
