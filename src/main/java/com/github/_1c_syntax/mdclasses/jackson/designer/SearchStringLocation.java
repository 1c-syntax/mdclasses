

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SearchStringLocation {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("CommandBar")
    COMMAND_BAR("CommandBar"),
    @JsonProperty("Top")
    TOP("Top"),
    @JsonProperty("Bottom")
    BOTTOM("Bottom"),
    @JsonProperty("FormCaption")
    FORM_CAPTION("FormCaption"),
    @JsonProperty("PullFromTop")
    PULL_FROM_TOP("PullFromTop");
    private final String value;

    SearchStringLocation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SearchStringLocation fromValue(String v) {
        for (SearchStringLocation c: SearchStringLocation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
