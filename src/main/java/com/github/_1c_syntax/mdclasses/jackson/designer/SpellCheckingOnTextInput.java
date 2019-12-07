

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpellCheckingOnTextInput {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Use")
    USE("Use"),
    @JsonProperty("DontUse")
    DONT_USE("DontUse");
    private final String value;

    SpellCheckingOnTextInput(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpellCheckingOnTextInput fromValue(String v) {
        for (SpellCheckingOnTextInput c: SpellCheckingOnTextInput.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
