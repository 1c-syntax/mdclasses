

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageGroupData {

    protected Picture pic;
    protected DataPath prop;
    protected String propName;
    protected TypeDescription tdp;
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
    @JsonProperty("format")
    protected String format;
    @JsonProperty("showTitle")
    protected Boolean showTitle;
    @JsonProperty("scrollOnCompress")
    protected Boolean scrollOnCompress;

}
