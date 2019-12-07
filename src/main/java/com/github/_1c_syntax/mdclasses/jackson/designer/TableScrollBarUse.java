

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TableScrollBarUse {

    @JsonProperty("DontUse")
    DONT_USE("DontUse"),
    @JsonProperty("UseAlways")
    USE_ALWAYS("UseAlways"),
    @JsonProperty("AutoUse")
    AUTO_USE("AutoUse");
    private final String value;

    TableScrollBarUse(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TableScrollBarUse fromValue(String v) {
        for (TableScrollBarUse c: TableScrollBarUse.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
