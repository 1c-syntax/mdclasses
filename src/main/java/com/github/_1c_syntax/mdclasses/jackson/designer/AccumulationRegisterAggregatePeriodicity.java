


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccumulationRegisterAggregatePeriodicity {

    @JsonProperty("Nonperiodical")
    NONPERIODICAL("Nonperiodical"),
    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Day")
    DAY("Day"),
    @JsonProperty("Month")
    MONTH("Month"),
    @JsonProperty("Quarter")
    QUARTER("Quarter"),
    @JsonProperty("HalfYear")
    HALF_YEAR("HalfYear"),
    @JsonProperty("Year")
    YEAR("Year");
    private final String value;

    AccumulationRegisterAggregatePeriodicity(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AccumulationRegisterAggregatePeriodicity fromValue(String v) {
        for (AccumulationRegisterAggregatePeriodicity c: AccumulationRegisterAggregatePeriodicity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
