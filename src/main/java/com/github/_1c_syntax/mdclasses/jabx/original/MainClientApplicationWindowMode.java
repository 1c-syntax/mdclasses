

package com.github._1c_syntax.mdclasses.jabx.original;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum MainClientApplicationWindowMode {

  @JsonProperty("Normal")
  NORMAL("Normal"),
  @JsonProperty("Workplace")
  WORKPLACE("Workplace"),
  @JsonProperty("FullscreenWorkplace")
  FULLSCREEN_WORKPLACE("FullscreenWorkplace"),
  @JsonProperty("Kiosk")
  KIOSK("Kiosk");
  private final String value;

  MainClientApplicationWindowMode(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static MainClientApplicationWindowMode fromValue(String v) {
    for (MainClientApplicationWindowMode c : MainClientApplicationWindowMode.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
