

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ApplicationUsePurpose {

    @JsonProperty("PersonalComputer")
    PERSONAL_COMPUTER("PersonalComputer"),
    @JsonProperty("MobileDevice")
    MOBILE_DEVICE("MobileDevice");
    private final String value;

    ApplicationUsePurpose(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ApplicationUsePurpose fromValue(String v) {
        for (ApplicationUsePurpose c : ApplicationUsePurpose.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
