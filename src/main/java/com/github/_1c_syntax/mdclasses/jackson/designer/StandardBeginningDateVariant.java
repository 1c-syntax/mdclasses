

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StandardBeginningDateVariant {

    @JsonProperty("Custom")
    CUSTOM("Custom"),
    @JsonProperty("BeginningOfThisDay")
    BEGINNING_OF_THIS_DAY("BeginningOfThisDay"),
    @JsonProperty("BeginningOfThisWeek")
    BEGINNING_OF_THIS_WEEK("BeginningOfThisWeek"),
    @JsonProperty("BeginningOfThisTenDays")
    BEGINNING_OF_THIS_TEN_DAYS("BeginningOfThisTenDays"),
    @JsonProperty("BeginningOfThisMonth")
    BEGINNING_OF_THIS_MONTH("BeginningOfThisMonth"),
    @JsonProperty("BeginningOfThisQuarter")
    BEGINNING_OF_THIS_QUARTER("BeginningOfThisQuarter"),
    @JsonProperty("BeginningOfThisHalfYear")
    BEGINNING_OF_THIS_HALF_YEAR("BeginningOfThisHalfYear"),
    @JsonProperty("BeginningOfThisYear")
    BEGINNING_OF_THIS_YEAR("BeginningOfThisYear"),
    @JsonProperty("BeginningOfLastDay")
    BEGINNING_OF_LAST_DAY("BeginningOfLastDay"),
    @JsonProperty("BeginningOfLastWeek")
    BEGINNING_OF_LAST_WEEK("BeginningOfLastWeek"),
    @JsonProperty("BeginningOfLastTenDays")
    BEGINNING_OF_LAST_TEN_DAYS("BeginningOfLastTenDays"),
    @JsonProperty("BeginningOfLastMonth")
    BEGINNING_OF_LAST_MONTH("BeginningOfLastMonth"),
    @JsonProperty("BeginningOfLastQuarter")
    BEGINNING_OF_LAST_QUARTER("BeginningOfLastQuarter"),
    @JsonProperty("BeginningOfLastHalfYear")
    BEGINNING_OF_LAST_HALF_YEAR("BeginningOfLastHalfYear"),
    @JsonProperty("BeginningOfLastYear")
    BEGINNING_OF_LAST_YEAR("BeginningOfLastYear"),
    @JsonProperty("BeginningOfNextDay")
    BEGINNING_OF_NEXT_DAY("BeginningOfNextDay"),
    @JsonProperty("BeginningOfNextWeek")
    BEGINNING_OF_NEXT_WEEK("BeginningOfNextWeek"),
    @JsonProperty("BeginningOfNextTenDays")
    BEGINNING_OF_NEXT_TEN_DAYS("BeginningOfNextTenDays"),
    @JsonProperty("BeginningOfNextMonth")
    BEGINNING_OF_NEXT_MONTH("BeginningOfNextMonth"),
    @JsonProperty("BeginningOfNextQuarter")
    BEGINNING_OF_NEXT_QUARTER("BeginningOfNextQuarter"),
    @JsonProperty("BeginningOfNextHalfYear")
    BEGINNING_OF_NEXT_HALF_YEAR("BeginningOfNextHalfYear"),
    @JsonProperty("BeginningOfNextYear")
    BEGINNING_OF_NEXT_YEAR("BeginningOfNextYear");
    private final String value;

    StandardBeginningDateVariant(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StandardBeginningDateVariant fromValue(String v) {
        for (StandardBeginningDateVariant c: StandardBeginningDateVariant.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
