


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccountType {

    @JsonProperty("Active")
    ACTIVE("Active"),
    @JsonProperty("Passive")
    PASSIVE("Passive"),
    @JsonProperty("ActivePassive")
    ACTIVE_PASSIVE("ActivePassive");
    private final String value;

    AccountType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AccountType fromValue(String v) {
        for (AccountType c: AccountType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
