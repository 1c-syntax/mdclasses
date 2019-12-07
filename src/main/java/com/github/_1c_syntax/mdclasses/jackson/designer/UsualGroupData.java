

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsualGroupData {

    protected DataPath prop;
    protected String propName;
    protected TypeDescription tdp;
    @JsonProperty("anySimpleType")
    protected String bkClr;
    @JsonProperty("grouping")
    protected FormChildrenGroup grouping;
    @JsonProperty("align")
    protected FormChildrenAlign align;
    @JsonProperty("horizontalSpacing")
    protected FormItemSpacing horizontalSpacing;
    @JsonProperty("verticalSpacing")
    protected FormItemSpacing verticalSpacing;
    @JsonProperty("hAlign")
    protected ItemHorizontalAlignment hAlign;
    @JsonProperty("vAlign")
    protected ItemVerticalAlignment vAlign;
    @JsonProperty("childrenWidth")
    protected FormChildrenWidth childrenWidth;
    @JsonProperty("repres")
    protected UsualGroupRepresentation repres;
    @JsonProperty("bhv")
    protected UsualGroupBehavior bhv;
    @JsonProperty("mngRepres")
    protected UsualGroupControlRepresentation mngRepres;
    @JsonProperty("throughAlign")
    protected UsualGroupThroughAlign throughAlign;
    @JsonProperty("showTitle")
    protected Boolean showTitle;
    @JsonProperty("collapsed")
    protected Boolean collapsed;
    @JsonProperty("leftMargin")
    protected Boolean leftMargin;
    @JsonProperty("united")
    protected Boolean united;
    @JsonProperty("format")
    protected String format;
    @JsonProperty("clsTitle")
    protected String clsTitle;
}
