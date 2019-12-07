

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TableRepresentation {

    @JsonProperty("List")
    LIST("List"),
    @JsonProperty("HierarchicalList")
    HIERARCHICAL_LIST("HierarchicalList"),
    @JsonProperty("Tree")
    TREE("Tree");
    private final String value;

    TableRepresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TableRepresentation fromValue(String v) {
        for (TableRepresentation c: TableRepresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
