//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.20 at 09:46:06 PM KRAT 
//


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;


//@XmlType("CompatibilityMode", namespace = "http://v8.1c.ru/8.3/xcf/enums")
//@XmlEnum
public enum CompatibilityMode {

    @JsonProperty("DontUse")
    DONT_USE("DontUse"),
    @JsonProperty("Version8_3_17")
    VERSION_8_3_17("Version8_3_17"),
    @JsonProperty("Version8_3_16")
    VERSION_8_3_16("Version8_3_16"),
    @JsonProperty("Version8_3_15")
    VERSION_8_3_15("Version8_3_15"),
    @JsonProperty("Version8_3_14")
    VERSION_8_3_14("Version8_3_14"),
    @JsonProperty("Version8_3_13")
    VERSION_8_3_13("Version8_3_13"),
    @JsonProperty("Version8_3_12")
    VERSION_8_3_12("Version8_3_12"),
    @JsonProperty("Version8_3_11")
    VERSION_8_3_11("Version8_3_11"),
    @JsonProperty("Version8_3_10")
    VERSION_8_3_10("Version8_3_10"),
    @JsonProperty("Version8_3_9")
    VERSION_8_3_9("Version8_3_9"),
    @JsonProperty("Version8_3_8")
    VERSION_8_3_8("Version8_3_8"),
    @JsonProperty("Version8_3_7")
    VERSION_8_3_7("Version8_3_7"),
    @JsonProperty("Version8_3_6")
    VERSION_8_3_6("Version8_3_6"),
    @JsonProperty("Version8_3_5")
    VERSION_8_3_5("Version8_3_5"),
    @JsonProperty("Version8_3_4")
    VERSION_8_3_4("Version8_3_4"),
    @JsonProperty("Version8_3_3")
    VERSION_8_3_3("Version8_3_3"),
    @JsonProperty("Version8_3_2")
    VERSION_8_3_2("Version8_3_2"),
    @JsonProperty("Version8_3_1")
    VERSION_8_3_1("Version8_3_1"),
    @JsonProperty("Version8_2_16")
    VERSION_8_2_16("Version8_2_16"),
    @JsonProperty("Version8_2_13")
    VERSION_8_2_13("Version8_2_13"),
    @JsonProperty("Version8_1")
    VERSION_8_1("Version8_1");
    private final String value;

    CompatibilityMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CompatibilityMode fromValue(String v) {
        for (CompatibilityMode c : CompatibilityMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}