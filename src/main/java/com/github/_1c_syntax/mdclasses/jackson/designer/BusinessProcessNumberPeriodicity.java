

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum BusinessProcessNumberPeriodicity {

    @JsonProperty("Nonperiodical")
    NONPERIODICAL("Nonperiodical"),
    @JsonProperty("Year")
    YEAR("Year"),
    @JsonProperty("Quarter")
    QUARTER("Quarter"),
    @JsonProperty("Month")
    MONTH("Month"),
    @JsonProperty("Day")
    DAY("Day");
    private final String value;

    BusinessProcessNumberPeriodicity(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BusinessProcessNumberPeriodicity fromValue(String v) {
        for (BusinessProcessNumberPeriodicity c: BusinessProcessNumberPeriodicity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
