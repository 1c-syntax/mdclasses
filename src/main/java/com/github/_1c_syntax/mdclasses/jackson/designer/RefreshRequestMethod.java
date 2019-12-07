

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum RefreshRequestMethod {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("PullFromTop")
    PULL_FROM_TOP("PullFromTop"),
    @JsonProperty("PullFromBottom")
    PULL_FROM_BOTTOM("PullFromBottom"),
    @JsonProperty("PullFromTopOrBottom")
    PULL_FROM_TOP_OR_BOTTOM("PullFromTopOrBottom");
    private final String value;

    RefreshRequestMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RefreshRequestMethod fromValue(String v) {
        for (RefreshRequestMethod c: RefreshRequestMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
