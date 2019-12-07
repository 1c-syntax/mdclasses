

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum CalculationRegisterPeriodicity {

    @JsonProperty("Year")
    YEAR("Year"),
    @JsonProperty("Quarter")
    QUARTER("Quarter"),
    @JsonProperty("Month")
    MONTH("Month"),
    @JsonProperty("Day")
    DAY("Day");
    private final String value;

    CalculationRegisterPeriodicity(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CalculationRegisterPeriodicity fromValue(String v) {
        for (CalculationRegisterPeriodicity c: CalculationRegisterPeriodicity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
