

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum CatalogCodesSeries {

    @JsonProperty("WholeCatalog")
    WHOLE_CATALOG("WholeCatalog"),
    @JsonProperty("WithinSubordination")
    WITHIN_SUBORDINATION("WithinSubordination"),
    @JsonProperty("WithinOwnerSubordination")
    WITHIN_OWNER_SUBORDINATION("WithinOwnerSubordination");
    private final String value;

    CatalogCodesSeries(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CatalogCodesSeries fromValue(String v) {
        for (CatalogCodesSeries c: CatalogCodesSeries.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
