

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccountMainPresentation {

    @JsonProperty("AsCode")
    AS_CODE("AsCode"),
    @JsonProperty("AsDescription")
    AS_DESCRIPTION("AsDescription");
    private final String value;

    AccountMainPresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AccountMainPresentation fromValue(String v) {
        for (AccountMainPresentation c: AccountMainPresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
