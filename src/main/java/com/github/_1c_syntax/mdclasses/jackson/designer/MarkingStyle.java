//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.20 at 09:46:06 PM KRAT 
//


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MarkingStyle {

    @JsonProperty("DontShow")
    DONT_SHOW("DontShow"),
    @JsonProperty("TopLeft")
    TOP_LEFT("TopLeft"),
    @JsonProperty("BottomRight")
    BOTTOM_RIGHT("BottomRight"),
    @JsonProperty("BothSides")
    BOTH_SIDES("BothSides");
    private final String value;

    MarkingStyle(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MarkingStyle fromValue(String v) {
        for (MarkingStyle c: MarkingStyle.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
