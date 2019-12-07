

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum AccountingRecordType {

    @JsonProperty("Debit")
    DEBIT("Debit"),
    @JsonProperty("Credit")
    CREDIT("Credit");
    private final String value;

    AccountingRecordType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AccountingRecordType fromValue(String v) {
        for (AccountingRecordType c : AccountingRecordType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
