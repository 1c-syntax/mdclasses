

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnsGroupData {

    protected Picture headerPic;
    protected String titleBkClr;
    protected DataPath hdProp;
    protected String hdPropName;
    protected TypeDescription hdTdp;
    @JsonProperty("grouping")
    protected ColumnsGroup grouping;
    @JsonProperty("showTitle")
    protected Boolean showTitle;
    @JsonProperty("showInHeader")
    protected Boolean showInHeader;
    @JsonProperty("headerHAlign")
    protected ItemHorizontalAlignment headerHAlign;
    @JsonProperty("headerFormat")
    protected String headerFormat;
    @JsonProperty("fixedInTable")
    protected FormFixedInTable fixedInTable;

}
