

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum CommandParameterUseMode {

    @JsonProperty("Single")
    SINGLE("Single"),
    @JsonProperty("Multiple")
    MULTIPLE("Multiple");
    private final String value;

    CommandParameterUseMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CommandParameterUseMode fromValue(String v) {
        for (CommandParameterUseMode c : CommandParameterUseMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
