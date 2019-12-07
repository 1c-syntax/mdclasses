//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.20 at 09:46:06 PM KRAT 
//


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public enum ModalityUseMode {

    @JsonProperty("Use")
    USE("Use"),
    @JsonProperty("UseWithWarnings")
    USE_WITH_WARNINGS("UseWithWarnings"),
    @JsonProperty("DontUse")
    DONT_USE("DontUse");
    private final String value;

    ModalityUseMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ModalityUseMode fromValue(String v) {
        for (ModalityUseMode c : ModalityUseMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}