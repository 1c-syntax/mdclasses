//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.20 at 09:46:06 PM KRAT 
//


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TableRowInputMode {

    @JsonProperty("EndOfList")
    END_OF_LIST("EndOfList"),
    @JsonProperty("EndOfWindow")
    END_OF_WINDOW("EndOfWindow"),
    @JsonProperty("AfterCurrentRow")
    AFTER_CURRENT_ROW("AfterCurrentRow"),
    @JsonProperty("BeforeCurrentRow")
    BEFORE_CURRENT_ROW("BeforeCurrentRow");
    private final String value;

    TableRowInputMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TableRowInputMode fromValue(String v) {
        for (TableRowInputMode c: TableRowInputMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}