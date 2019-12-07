

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum AutoCapitalizationOnTextInput {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("Words")
    WORDS("Words"),
    @JsonProperty("Sentences")
    SENTENCES("Sentences"),
    @JsonProperty("AllCharacters")
    ALL_CHARACTERS("AllCharacters");
    private final String value;

    AutoCapitalizationOnTextInput(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AutoCapitalizationOnTextInput fromValue(String v) {
        for (AutoCapitalizationOnTextInput c: AutoCapitalizationOnTextInput.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
