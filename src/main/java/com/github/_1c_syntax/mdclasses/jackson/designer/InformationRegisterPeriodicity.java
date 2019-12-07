

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum InformationRegisterPeriodicity {

    @JsonProperty("Nonperiodical")
    NONPERIODICAL("Nonperiodical"),
    @JsonProperty("RecorderPosition")
    RECORDER_POSITION("RecorderPosition"),
    @JsonProperty("Second")
    SECOND("Second"),
    @JsonProperty("Day")
    DAY("Day"),
    @JsonProperty("Month")
    MONTH("Month"),
    @JsonProperty("Quarter")
    QUARTER("Quarter"),
    @JsonProperty("Year")
    YEAR("Year");
    private final String value;

    InformationRegisterPeriodicity(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InformationRegisterPeriodicity fromValue(String v) {
        for (InformationRegisterPeriodicity c: InformationRegisterPeriodicity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
