

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum HierarchyType {

    @JsonProperty("HierarchyFoldersAndItems")
    HIERARCHY_FOLDERS_AND_ITEMS("HierarchyFoldersAndItems"),
    @JsonProperty("HierarchyOfItems")
    HIERARCHY_OF_ITEMS("HierarchyOfItems");
    private final String value;

    HierarchyType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HierarchyType fromValue(String v) {
        for (HierarchyType c: HierarchyType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
