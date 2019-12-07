

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TextEncoding {

    @JsonProperty("UTF16")
    UTF_16("UTF16"),
    @JsonProperty("UTF8")
    UTF_8("UTF8"),
    ANSI("ANSI"),
    OEM("OEM"),
    @JsonProperty("System")
    SYSTEM("System");
    private final String value;

    TextEncoding(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TextEncoding fromValue(String v) {
        for (TextEncoding c: TextEncoding.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
