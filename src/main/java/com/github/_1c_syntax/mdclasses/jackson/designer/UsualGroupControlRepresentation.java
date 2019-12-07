

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum UsualGroupControlRepresentation {

    @JsonProperty("TitleHyperlink")
    TITLE_HYPERLINK("TitleHyperlink"),
    @JsonProperty("Picture")
    PICTURE("Picture");
    private final String value;

    UsualGroupControlRepresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UsualGroupControlRepresentation fromValue(String v) {
        for (UsualGroupControlRepresentation c: UsualGroupControlRepresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
