

package com.github._1c_syntax.mdclasses.mdosource.original;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum InterfaceCompatibilityMode {

  @JsonProperty("Taxi")
  TAXI("Taxi"),
  @JsonProperty("TaxiEnableVersion8_2")
  TAXI_ENABLE_VERSION_8_2("TaxiEnableVersion8_2"),
  @JsonProperty("Version8_2EnableTaxi")
  VERSION_8_2_ENABLE_TAXI("Version8_2EnableTaxi"),
  @JsonProperty("Version8_2")
  VERSION_8_2("Version8_2");
  private final String value;

  InterfaceCompatibilityMode(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static InterfaceCompatibilityMode fromValue(String v) {
    for (InterfaceCompatibilityMode c : InterfaceCompatibilityMode.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
