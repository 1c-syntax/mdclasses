

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChoiceButtonRepresentation {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("ShowInDropList")
    SHOW_IN_DROP_LIST("ShowInDropList"),
    @JsonProperty("ShowInDropListAndInInputField")
    SHOW_IN_DROP_LIST_AND_IN_INPUT_FIELD("ShowInDropListAndInInputField"),
    @JsonProperty("ShowInInputField")
    SHOW_IN_INPUT_FIELD("ShowInInputField");
    private final String value;

    ChoiceButtonRepresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChoiceButtonRepresentation fromValue(String v) {
        for (ChoiceButtonRepresentation c: ChoiceButtonRepresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
