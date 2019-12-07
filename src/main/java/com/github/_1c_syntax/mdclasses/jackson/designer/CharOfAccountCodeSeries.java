

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum CharOfAccountCodeSeries {

    @JsonProperty("WholeChartOfAccounts")
    WHOLE_CHART_OF_ACCOUNTS("WholeChartOfAccounts"),
    @JsonProperty("WithinSubordination")
    WITHIN_SUBORDINATION("WithinSubordination");
    private final String value;

    CharOfAccountCodeSeries(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CharOfAccountCodeSeries fromValue(String v) {
        for (CharOfAccountCodeSeries c: CharOfAccountCodeSeries.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
