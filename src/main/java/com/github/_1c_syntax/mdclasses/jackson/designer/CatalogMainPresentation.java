



package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum CatalogMainPresentation {

    @JsonProperty("AsCode")
    AS_CODE("AsCode"),
    @JsonProperty("AsDescription")
    AS_DESCRIPTION("AsDescription");
    private final String value;

    CatalogMainPresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CatalogMainPresentation fromValue(String v) {
        for (CatalogMainPresentation c: CatalogMainPresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
