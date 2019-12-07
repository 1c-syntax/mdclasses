


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum FontType {

    @JsonProperty("Absolute")
    ABSOLUTE("Absolute"),
    @JsonProperty("WindowsFont")
    WINDOWS_FONT("WindowsFont"),
    @JsonProperty("StyleItem")
    STYLE_ITEM("StyleItem"),
    @JsonProperty("AutoFont")
    AUTO_FONT("AutoFont");
    private final String value;

    FontType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FontType fromValue(String v) {
        for (FontType c: FontType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
