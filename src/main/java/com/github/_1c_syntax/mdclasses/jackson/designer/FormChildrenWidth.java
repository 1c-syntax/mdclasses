

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FormChildrenWidth {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Equal")
    EQUAL("Equal"),
    @JsonProperty("LeftWide")
    LEFT_WIDE("LeftWide"),
    @JsonProperty("LeftWidest")
    LEFT_WIDEST("LeftWidest"),
    @JsonProperty("LeftNarrow")
    LEFT_NARROW("LeftNarrow"),
    @JsonProperty("LeftNarrowest")
    LEFT_NARROWEST("LeftNarrowest");
    private final String value;

    FormChildrenWidth(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormChildrenWidth fromValue(String v) {
        for (FormChildrenWidth c: FormChildrenWidth.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
