

package com.github._1c_syntax.mdclasses.mdosource.original;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ClientRunMode {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("ManagedApplication")
    MANAGED_APPLICATION("ManagedApplication"),
    @JsonProperty("OrdinaryApplication")
    ORDINARY_APPLICATION("OrdinaryApplication");
    private final String value;

    ClientRunMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClientRunMode fromValue(String v) {
        for (ClientRunMode c : ClientRunMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
