

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ClientConnectionSpeed {

    @JsonProperty("Normal")
    NORMAL("Normal"),
    @JsonProperty("Low")
    LOW("Low");
    private final String value;

    ClientConnectionSpeed(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClientConnectionSpeed fromValue(String v) {
        for (ClientConnectionSpeed c : ClientConnectionSpeed.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
