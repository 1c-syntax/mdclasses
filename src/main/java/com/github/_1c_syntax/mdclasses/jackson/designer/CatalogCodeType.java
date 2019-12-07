

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum CatalogCodeType {

    @JsonProperty("Number")
    NUMBER("Number"),
    @JsonProperty("String")
    STRING("String");
    private final String value;

    CatalogCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CatalogCodeType fromValue(String v) {
        for (CatalogCodeType c: CatalogCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
