

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FoldersAndItems {

    @JsonProperty("Items")
    ITEMS("Items"),
    @JsonProperty("Folders")
    FOLDERS("Folders"),
    @JsonProperty("FoldersAndItems")
    FOLDERS_AND_ITEMS("FoldersAndItems"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    FoldersAndItems(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FoldersAndItems fromValue(String v) {
        for (FoldersAndItems c: FoldersAndItems.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
