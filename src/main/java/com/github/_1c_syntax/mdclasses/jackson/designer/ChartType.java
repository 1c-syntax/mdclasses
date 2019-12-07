

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChartType {

    @JsonProperty("Line")
    LINE("Line"),
    @JsonProperty("Step")
    STEP("Step"),
    @JsonProperty("StackedLine")
    STACKED_LINE("StackedLine"),
    @JsonProperty("Area")
    AREA("Area"),
    @JsonProperty("StackedArea")
    STACKED_AREA("StackedArea"),
    @JsonProperty("NormalizedArea")
    NORMALIZED_AREA("NormalizedArea"),
    @JsonProperty("Column")
    COLUMN("Column"),
    @JsonProperty("StackedColumn")
    STACKED_COLUMN("StackedColumn"),
    @JsonProperty("NormalizedColumn")
    NORMALIZED_COLUMN("NormalizedColumn"),
    @JsonProperty("Column3D")
    COLUMN_3_D("Column3D"),
    @JsonProperty("StackedColumn3D")
    STACKED_COLUMN_3_D("StackedColumn3D"),
    @JsonProperty("NormalizedColumn3D")
    NORMALIZED_COLUMN_3_D("NormalizedColumn3D"),
    @JsonProperty("Bar")
    BAR("Bar"),
    @JsonProperty("StackedBar")
    STACKED_BAR("StackedBar"),
    @JsonProperty("NormalizedBar")
    NORMALIZED_BAR("NormalizedBar"),
    @JsonProperty("Bar3D")
    BAR_3_D("Bar3D"),
    @JsonProperty("StackedBar3D")
    STACKED_BAR_3_D("StackedBar3D"),
    @JsonProperty("NormalizedBar3D")
    NORMALIZED_BAR_3_D("NormalizedBar3D"),
    @JsonProperty("Pie")
    PIE("Pie"),
    @JsonProperty("Pie3D")
    PIE_3_D("Pie3D"),
    @JsonProperty("Stock")
    STOCK("Stock"),
    @JsonProperty("OpenHighLowClose")
    OPEN_HIGH_LOW_CLOSE("OpenHighLowClose"),
    @JsonProperty("BarGraph")
    BAR_GRAPH("BarGraph"),
    @JsonProperty("CeilGraph")
    CEIL_GRAPH("CeilGraph"),
    @JsonProperty("TapeGraph")
    TAPE_GRAPH("TapeGraph"),
    @JsonProperty("PyramidGraph")
    PYRAMID_GRAPH("PyramidGraph"),
    @JsonProperty("Waterfall")
    WATERFALL("Waterfall"),
    @JsonProperty("WireframeSurface")
    WIREFRAME_SURFACE("WireframeSurface"),
    @JsonProperty("Honeycomb")
    HONEYCOMB("Honeycomb"),
    @JsonProperty("Surface")
    SURFACE("Surface"),
    @JsonProperty("ConvexSurface")
    CONVEX_SURFACE("ConvexSurface"),
    @JsonProperty("ConcaveSurface")
    CONCAVE_SURFACE("ConcaveSurface"),
    @JsonProperty("ShadedSurface")
    SHADED_SURFACE("ShadedSurface"),
    @JsonProperty("RadarLine")
    RADAR_LINE("RadarLine"),
    @JsonProperty("RadarArea")
    RADAR_AREA("RadarArea"),
    @JsonProperty("RadarStackedLine")
    RADAR_STACKED_LINE("RadarStackedLine"),
    @JsonProperty("RadarStackedArea")
    RADAR_STACKED_AREA("RadarStackedArea"),
    @JsonProperty("RadarNormalizedArea")
    RADAR_NORMALIZED_AREA("RadarNormalizedArea"),
    @JsonProperty("Gauge")
    GAUGE("Gauge"),
    @JsonProperty("Funnel")
    FUNNEL("Funnel"),
    @JsonProperty("Funnel3D")
    FUNNEL_3_D("Funnel3D"),
    @JsonProperty("NormalizedFunnel")
    NORMALIZED_FUNNEL("NormalizedFunnel"),
    @JsonProperty("NormalizedFunnel3D")
    NORMALIZED_FUNNEL_3_D("NormalizedFunnel3D"),
    @JsonProperty("Scatter")
    SCATTER("Scatter"),
    @JsonProperty("Bubble")
    BUBBLE("Bubble");
    private final String value;

    ChartType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChartType fromValue(String v) {
        for (ChartType c: ChartType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
