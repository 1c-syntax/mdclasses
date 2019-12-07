

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AttributeUse {

    @JsonProperty("ForItem")
    FOR_ITEM("ForItem"),
    @JsonProperty("ForFolder")
    FOR_FOLDER("ForFolder"),
    @JsonProperty("ForFolderAndItem")
    FOR_FOLDER_AND_ITEM("ForFolderAndItem");
    private final String value;

    AttributeUse(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AttributeUse fromValue(String v) {
        for (AttributeUse c : AttributeUse.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
