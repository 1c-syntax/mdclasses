

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum CharacteristicKindCodesSeries {

    @JsonProperty("WholeCharacteristicKind")
    WHOLE_CHARACTERISTIC_KIND("WholeCharacteristicKind"),
    @JsonProperty("WithinSubordination")
    WITHIN_SUBORDINATION("WithinSubordination");
    private final String value;

    CharacteristicKindCodesSeries(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CharacteristicKindCodesSeries fromValue(String v) {
        for (CharacteristicKindCodesSeries c: CharacteristicKindCodesSeries.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
