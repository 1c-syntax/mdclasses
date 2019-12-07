

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FullTextSearchOnInputByString {

    @JsonProperty("Use")
    USE("Use"),
    @JsonProperty("DontUse")
    DONT_USE("DontUse");
    private final String value;

    FullTextSearchOnInputByString(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FullTextSearchOnInputByString fromValue(String v) {
        for (FullTextSearchOnInputByString c: FullTextSearchOnInputByString.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
