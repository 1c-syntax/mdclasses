

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FormChildrenGroup {

    @JsonProperty("Horizontal")
    HORIZONTAL("Horizontal"),
    @JsonProperty("Vertical")
    VERTICAL("Vertical"),
    @JsonProperty("HorizontalIfPossible")
    HORIZONTAL_IF_POSSIBLE("HorizontalIfPossible");
    private final String value;

    FormChildrenGroup(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormChildrenGroup fromValue(String v) {
        for (FormChildrenGroup c: FormChildrenGroup.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
