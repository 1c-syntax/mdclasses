


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccumulationRecordType {

    @JsonProperty("Receipt")
    RECEIPT("Receipt"),
    @JsonProperty("Expense")
    EXPENSE("Expense");
    private final String value;

    AccumulationRecordType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AccumulationRecordType fromValue(String v) {
        for (AccumulationRecordType c: AccumulationRecordType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
