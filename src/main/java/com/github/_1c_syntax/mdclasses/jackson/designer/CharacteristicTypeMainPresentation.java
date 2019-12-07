


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum CharacteristicTypeMainPresentation {

    @JsonProperty("AsCode")
    AS_CODE("AsCode"),
    @JsonProperty("AsDescription")
    AS_DESCRIPTION("AsDescription");
    private final String value;

    CharacteristicTypeMainPresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CharacteristicTypeMainPresentation fromValue(String v) {
        for (CharacteristicTypeMainPresentation c : CharacteristicTypeMainPresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
