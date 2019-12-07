

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransactionsIsolationLevel {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("ReadUncommitted")
    READ_UNCOMMITTED("ReadUncommitted"),
    @JsonProperty("ReadCommitted")
    READ_COMMITTED("ReadCommitted"),
    @JsonProperty("RepeatableRead")
    REPEATABLE_READ("RepeatableRead"),
    @JsonProperty("Serializable")
    SERIALIZABLE("Serializable");
    private final String value;

    TransactionsIsolationLevel(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransactionsIsolationLevel fromValue(String v) {
        for (TransactionsIsolationLevel c: TransactionsIsolationLevel.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
