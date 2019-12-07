

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum XCFFormat {

    @JsonProperty("Hierarchical")
    HIERARCHICAL("Hierarchical"),
    @JsonProperty("Plain")
    PLAIN("Plain");
    private final String value;

    XCFFormat(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static XCFFormat fromValue(String v) {
        for (XCFFormat c: XCFFormat.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
