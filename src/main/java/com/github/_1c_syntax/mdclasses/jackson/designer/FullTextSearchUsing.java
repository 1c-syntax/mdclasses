

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FullTextSearchUsing {

    @JsonProperty("DontUse")
    DONT_USE("DontUse"),
    @JsonProperty("Use")
    USE("Use");
    private final String value;

    FullTextSearchUsing(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FullTextSearchUsing fromValue(String v) {
        for (FullTextSearchUsing c : FullTextSearchUsing.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
