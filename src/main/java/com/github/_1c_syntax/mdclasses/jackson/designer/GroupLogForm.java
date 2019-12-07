

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class GroupLogForm {

    @JsonProperty("anySimpleType")
    protected String titleTxtClr;
    protected Font titleFnt;
    protected ShortCutType stcut;
    protected MenuGroupData menuData;
    protected AutoMenuGroupData autoMenuData;
    protected SubmenuGroupData subMenuData;
    protected ButtonsGroupData buttonsData;
    protected ContextMenuGroupData contextMenuData;
    protected ColumnsGroupData columnsData;
    protected PagesGroupData pagesData;
    protected PageGroupData pageData;
    protected UsualGroupData usualData;
    protected List<FieldLogForm> field;
    protected List<GroupLogForm> group;
    protected List<TableLogForm> table;
    protected List<Button> button;
    protected List<Decoration> decoration;
    protected List<Addition> addition;
    protected String title;
    protected String tooltip;
    protected List<Predefined> predefined;
    @JsonProperty("id")
    protected BigDecimal id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("org")
    protected FormElementOrigin org;
    @JsonProperty("users")
    protected Boolean users;
    @JsonProperty("kind")
    protected ManagedFormGroupType kind;
    @JsonProperty("visible")
    protected Boolean visible;
    @JsonProperty("userVisible")
    protected Boolean userVisible;
    @JsonProperty("contentChange")
    protected Boolean contentChange;
    @JsonProperty("enabled")
    protected Boolean enabled;
    @JsonProperty("readOnly")
    protected Boolean readOnly;
    @JsonProperty("width")
    protected BigDecimal width;
    @JsonProperty("height")
    protected BigDecimal height;
    @JsonProperty("horStretch")
    protected BWAValue horStretch;
    @JsonProperty("verStretch")
    protected BWAValue verStretch;
    @JsonProperty("tooltipRepres")
    protected TooltipRepresentation tooltipRepres;
    @JsonProperty("groupHAlign")
    protected ItemHorizontalAlignment groupHAlign;
    @JsonProperty("groupVAlign")
    protected ItemVerticalAlignment groupVAlign;
    @JsonProperty("degeneratable")
    protected Boolean degeneratable;
}
