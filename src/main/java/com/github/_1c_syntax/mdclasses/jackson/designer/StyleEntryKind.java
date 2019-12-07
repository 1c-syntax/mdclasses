

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum StyleEntryKind {

    @JsonProperty("color")
    COLOR("color"),
    @JsonProperty("font")
    FONT("font"),
    @JsonProperty("border")
    BORDER("border"),
    @JsonProperty("line")
    LINE("line");
    private final String value;

    StyleEntryKind(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StyleEntryKind fromValue(String v) {
        for (StyleEntryKind c: StyleEntryKind.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
