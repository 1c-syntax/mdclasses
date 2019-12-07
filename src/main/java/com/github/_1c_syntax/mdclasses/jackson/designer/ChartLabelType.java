

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChartLabelType {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("Series")
    SERIES("Series"),
    @JsonProperty("Value")
    VALUE("Value"),
    @JsonProperty("Percent")
    PERCENT("Percent"),
    @JsonProperty("SeriesPercent")
    SERIES_PERCENT("SeriesPercent"),
    @JsonProperty("SeriesValue")
    SERIES_VALUE("SeriesValue"),
    @JsonProperty("SeriesValuePercent")
    SERIES_VALUE_PERCENT("SeriesValuePercent"),
    @JsonProperty("ValuePercent")
    VALUE_PERCENT("ValuePercent"),
    @JsonProperty("Point")
    POINT("Point"),
    @JsonProperty("PointPercent")
    POINT_PERCENT("PointPercent"),
    @JsonProperty("PointValue")
    POINT_VALUE("PointValue"),
    @JsonProperty("PointValuePercent")
    POINT_VALUE_PERCENT("PointValuePercent"),
    @JsonProperty("SeriesPoint")
    SERIES_POINT("SeriesPoint"),
    @JsonProperty("SeriesPointPercent")
    SERIES_POINT_PERCENT("SeriesPointPercent"),
    @JsonProperty("SeriesPointValue")
    SERIES_POINT_VALUE("SeriesPointValue"),
    @JsonProperty("SeriesPointValuePercent")
    SERIES_POINT_VALUE_PERCENT("SeriesPointValuePercent"),
    @JsonProperty("ValueSize")
    VALUE_SIZE("ValueSize"),
    @JsonProperty("SeriesSize")
    SERIES_SIZE("SeriesSize"),
    @JsonProperty("SeriesValueSize")
    SERIES_VALUE_SIZE("SeriesValueSize"),
    @JsonProperty("SeriesPointSize")
    SERIES_POINT_SIZE("SeriesPointSize"),
    @JsonProperty("SeriesPointValueSize")
    SERIES_POINT_VALUE_SIZE("SeriesPointValueSize"),
    @JsonProperty("PointSize")
    POINT_SIZE("PointSize"),
    @JsonProperty("PointValueSize")
    POINT_VALUE_SIZE("PointValueSize");
    private final String value;

    ChartLabelType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChartLabelType fromValue(String v) {
        for (ChartLabelType c: ChartLabelType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
