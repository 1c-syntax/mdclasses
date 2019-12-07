

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum StandardPeriodVariant {

    @JsonProperty("Custom")
    CUSTOM("Custom"),
    @JsonProperty("Today")
    TODAY("Today"),
    @JsonProperty("ThisWeek")
    THIS_WEEK("ThisWeek"),
    @JsonProperty("ThisTenDays")
    THIS_TEN_DAYS("ThisTenDays"),
    @JsonProperty("ThisMonth")
    THIS_MONTH("ThisMonth"),
    @JsonProperty("ThisQuarter")
    THIS_QUARTER("ThisQuarter"),
    @JsonProperty("ThisHalfYear")
    THIS_HALF_YEAR("ThisHalfYear"),
    @JsonProperty("ThisYear")
    THIS_YEAR("ThisYear"),
    @JsonProperty("FromBeginningOfThisWeek")
    FROM_BEGINNING_OF_THIS_WEEK("FromBeginningOfThisWeek"),
    @JsonProperty("FromBeginningOfThisTenDays")
    FROM_BEGINNING_OF_THIS_TEN_DAYS("FromBeginningOfThisTenDays"),
    @JsonProperty("FromBeginningOfThisMonth")
    FROM_BEGINNING_OF_THIS_MONTH("FromBeginningOfThisMonth"),
    @JsonProperty("FromBeginningOfThisQuarter")
    FROM_BEGINNING_OF_THIS_QUARTER("FromBeginningOfThisQuarter"),
    @JsonProperty("FromBeginningOfThisHalfYear")
    FROM_BEGINNING_OF_THIS_HALF_YEAR("FromBeginningOfThisHalfYear"),
    @JsonProperty("FromBeginningOfThisYear")
    FROM_BEGINNING_OF_THIS_YEAR("FromBeginningOfThisYear"),
    @JsonProperty("Yesterday")
    YESTERDAY("Yesterday"),
    @JsonProperty("LastWeek")
    LAST_WEEK("LastWeek"),
    @JsonProperty("LastTenDays")
    LAST_TEN_DAYS("LastTenDays"),
    @JsonProperty("LastMonth")
    LAST_MONTH("LastMonth"),
    @JsonProperty("LastQuarter")
    LAST_QUARTER("LastQuarter"),
    @JsonProperty("LastHalfYear")
    LAST_HALF_YEAR("LastHalfYear"),
    @JsonProperty("LastYear")
    LAST_YEAR("LastYear"),
    @JsonProperty("LastWeekTillSameWeekDay")
    LAST_WEEK_TILL_SAME_WEEK_DAY("LastWeekTillSameWeekDay"),
    @JsonProperty("LastTenDaysTillSameDayNumber")
    LAST_TEN_DAYS_TILL_SAME_DAY_NUMBER("LastTenDaysTillSameDayNumber"),
    @JsonProperty("LastMonthTillSameDate")
    LAST_MONTH_TILL_SAME_DATE("LastMonthTillSameDate"),
    @JsonProperty("LastQuarterTillSameDate")
    LAST_QUARTER_TILL_SAME_DATE("LastQuarterTillSameDate"),
    @JsonProperty("LastHalfYearTillSameDate")
    LAST_HALF_YEAR_TILL_SAME_DATE("LastHalfYearTillSameDate"),
    @JsonProperty("LastYearTillSameDate")
    LAST_YEAR_TILL_SAME_DATE("LastYearTillSameDate"),
    @JsonProperty("Tomorrow")
    TOMORROW("Tomorrow"),
    @JsonProperty("NextWeek")
    NEXT_WEEK("NextWeek"),
    @JsonProperty("NextTenDays")
    NEXT_TEN_DAYS("NextTenDays"),
    @JsonProperty("NextMonth")
    NEXT_MONTH("NextMonth"),
    @JsonProperty("NextQuarter")
    NEXT_QUARTER("NextQuarter"),
    @JsonProperty("NextHalfYear")
    NEXT_HALF_YEAR("NextHalfYear"),
    @JsonProperty("NextYear")
    NEXT_YEAR("NextYear"),
    @JsonProperty("NextWeekTillSameWeekDay")
    NEXT_WEEK_TILL_SAME_WEEK_DAY("NextWeekTillSameWeekDay"),
    @JsonProperty("NextTenDaysTillSameDayNumber")
    NEXT_TEN_DAYS_TILL_SAME_DAY_NUMBER("NextTenDaysTillSameDayNumber"),
    @JsonProperty("NextMonthTillSameDate")
    NEXT_MONTH_TILL_SAME_DATE("NextMonthTillSameDate"),
    @JsonProperty("NextQuarterTillSameDate")
    NEXT_QUARTER_TILL_SAME_DATE("NextQuarterTillSameDate"),
    @JsonProperty("NextHalfYearTillSameDate")
    NEXT_HALF_YEAR_TILL_SAME_DATE("NextHalfYearTillSameDate"),
    @JsonProperty("NextYearTillSameDate")
    NEXT_YEAR_TILL_SAME_DATE("NextYearTillSameDate"),
    @JsonProperty("TillEndOfThisWeek")
    TILL_END_OF_THIS_WEEK("TillEndOfThisWeek"),
    @JsonProperty("TillEndOfThisTenDays")
    TILL_END_OF_THIS_TEN_DAYS("TillEndOfThisTenDays"),
    @JsonProperty("TillEndOfThisMonth")
    TILL_END_OF_THIS_MONTH("TillEndOfThisMonth"),
    @JsonProperty("TillEndOfThisQuarter")
    TILL_END_OF_THIS_QUARTER("TillEndOfThisQuarter"),
    @JsonProperty("TillEndOfThisHalfYear")
    TILL_END_OF_THIS_HALF_YEAR("TillEndOfThisHalfYear"),
    @JsonProperty("TillEndOfThisYear")
    TILL_END_OF_THIS_YEAR("TillEndOfThisYear"),
    @JsonProperty("Last7Days")
    LAST_7_DAYS("Last7Days"),
    @JsonProperty("Next7Days")
    NEXT_7_DAYS("Next7Days"),
    @JsonProperty("Month")
    MONTH("Month");
    private final String value;

    StandardPeriodVariant(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StandardPeriodVariant fromValue(String v) {
        for (StandardPeriodVariant c: StandardPeriodVariant.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
