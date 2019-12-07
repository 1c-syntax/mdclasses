

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OnScreenKeyboardReturnKeyText {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Return")
    RETURN("Return"),
    @JsonProperty("Go")
    GO("Go"),
    @JsonProperty("Join")
    JOIN("Join"),
    @JsonProperty("Next")
    NEXT("Next"),
    @JsonProperty("Search")
    SEARCH("Search"),
    @JsonProperty("Send")
    SEND("Send"),
    @JsonProperty("Done")
    DONE("Done"),
    @JsonProperty("Continue")
    CONTINUE("Continue");
    private final String value;

    OnScreenKeyboardReturnKeyText(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OnScreenKeyboardReturnKeyText fromValue(String v) {
        for (OnScreenKeyboardReturnKeyText c: OnScreenKeyboardReturnKeyText.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
