


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccumulationRegisterAggregateUse {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Always")
    ALWAYS("Always");
    private final String value;

    AccumulationRegisterAggregateUse(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AccumulationRegisterAggregateUse fromValue(String v) {
        for (AccumulationRegisterAggregateUse c : AccumulationRegisterAggregateUse.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
