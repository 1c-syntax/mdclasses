

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChartOfCalculationTypesBaseUse {

    @JsonProperty("DontUse")
    DONT_USE("DontUse"),
    @JsonProperty("OnActionPeriod")
    ON_ACTION_PERIOD("OnActionPeriod"),
    @JsonProperty("OnRegistrationPeriod")
    ON_REGISTRATION_PERIOD("OnRegistrationPeriod");
    private final String value;

    ChartOfCalculationTypesBaseUse(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChartOfCalculationTypesBaseUse fromValue(String v) {
        for (ChartOfCalculationTypesBaseUse c: ChartOfCalculationTypesBaseUse.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
