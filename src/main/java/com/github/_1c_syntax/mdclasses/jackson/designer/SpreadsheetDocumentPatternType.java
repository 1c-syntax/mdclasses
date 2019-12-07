

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentPatternType {

    @JsonProperty("WithoutPattern")
    WITHOUT_PATTERN("WithoutPattern"),
    @JsonProperty("Solid")
    SOLID("Solid"),
    @JsonProperty("Pattern1")
    PATTERN_1("Pattern1"),
    @JsonProperty("Pattern2")
    PATTERN_2("Pattern2"),
    @JsonProperty("Pattern3")
    PATTERN_3("Pattern3"),
    @JsonProperty("Pattern4")
    PATTERN_4("Pattern4"),
    @JsonProperty("Pattern5")
    PATTERN_5("Pattern5"),
    @JsonProperty("Pattern6")
    PATTERN_6("Pattern6"),
    @JsonProperty("Pattern7")
    PATTERN_7("Pattern7"),
    @JsonProperty("Pattern8")
    PATTERN_8("Pattern8"),
    @JsonProperty("Pattern9")
    PATTERN_9("Pattern9"),
    @JsonProperty("Pattern10")
    PATTERN_10("Pattern10"),
    @JsonProperty("Pattern11")
    PATTERN_11("Pattern11"),
    @JsonProperty("Pattern12")
    PATTERN_12("Pattern12"),
    @JsonProperty("Pattern13")
    PATTERN_13("Pattern13"),
    @JsonProperty("Pattern14")
    PATTERN_14("Pattern14"),
    @JsonProperty("Pattern15")
    PATTERN_15("Pattern15"),
    @JsonProperty("Pattern16")
    PATTERN_16("Pattern16"),
    @JsonProperty("Pattern17")
    PATTERN_17("Pattern17");
    private final String value;

    SpreadsheetDocumentPatternType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentPatternType fromValue(String v) {
        for (SpreadsheetDocumentPatternType c: SpreadsheetDocumentPatternType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
