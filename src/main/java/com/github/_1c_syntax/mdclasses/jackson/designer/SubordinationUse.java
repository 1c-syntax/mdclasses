

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SubordinationUse {

    @JsonProperty("ToItems")
    TO_ITEMS("ToItems"),
    @JsonProperty("ToFolders")
    TO_FOLDERS("ToFolders"),
    @JsonProperty("ToFoldersAndItems")
    TO_FOLDERS_AND_ITEMS("ToFoldersAndItems");
    private final String value;

    SubordinationUse(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SubordinationUse fromValue(String v) {
        for (SubordinationUse c: SubordinationUse.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
