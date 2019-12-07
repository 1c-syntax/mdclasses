

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ClientApplicationInterfaceVariant {

    @JsonProperty("Version8_2")
    VERSION_8_2("Version8_2"),
    @JsonProperty("Taxi")
    TAXI("Taxi");
    private final String value;

    ClientApplicationInterfaceVariant(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClientApplicationInterfaceVariant fromValue(String v) {
        for (ClientApplicationInterfaceVariant c: ClientApplicationInterfaceVariant.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
