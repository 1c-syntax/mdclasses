

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FormChildrenAlign {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("ItemsLeftTitlesLeft")
    ITEMS_LEFT_TITLES_LEFT("ItemsLeftTitlesLeft"),
    @JsonProperty("ItemsRightTitlesLeft")
    ITEMS_RIGHT_TITLES_LEFT("ItemsRightTitlesLeft"),
    @JsonProperty("ItemsLeftTitlesRight")
    ITEMS_LEFT_TITLES_RIGHT("ItemsLeftTitlesRight"),
    @JsonProperty("ItemsRightTitlesRight")
    ITEMS_RIGHT_TITLES_RIGHT("ItemsRightTitlesRight"),
    @JsonProperty("TitlesLeftDataLeft")
    TITLES_LEFT_DATA_LEFT("TitlesLeftDataLeft"),
    @JsonProperty("TitlesLeftDataRight")
    TITLES_LEFT_DATA_RIGHT("TitlesLeftDataRight"),
    @JsonProperty("TitlesRightDataLeft")
    TITLES_RIGHT_DATA_LEFT("TitlesRightDataLeft"),
    @JsonProperty("TitlesRightDataRight")
    TITLES_RIGHT_DATA_RIGHT("TitlesRightDataRight"),
    @JsonProperty("TitlesLeftDataAuto")
    TITLES_LEFT_DATA_AUTO("TitlesLeftDataAuto");
    private final String value;

    FormChildrenAlign(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormChildrenAlign fromValue(String v) {
        for (FormChildrenAlign c: FormChildrenAlign.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
