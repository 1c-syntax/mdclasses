

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class Button {

    @JsonProperty(required = true)
    protected String command;
    @JsonProperty(required = true)
    protected Object commandParam;
    protected DataPath prop;
    protected String propName;
    protected TypeDescription tdp;
    @JsonProperty("anySimpleType")
    protected String bkClr;
    @JsonProperty("anySimpleType")
    protected String txtClr;
    @JsonProperty("anySimpleType")
    protected String brdClr;
    protected Font fnt;
    protected ShortCutType stcut;
    protected Picture pic;
    protected String title;
    @JsonProperty("string")
    protected RepresentationInContextMenu ricm;
    protected List<Predefined> predefined;
    @JsonProperty("id")
    protected BigDecimal id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("org")
    protected FormElementOrigin org;
    @JsonProperty("kind")
    protected ManagedFormButtonType kind;
    @JsonProperty("visible")
    protected Boolean visible;
    @JsonProperty("userVisible")
    protected Boolean userVisible;
    @JsonProperty("titleRowsCount")
    protected BigDecimal titleRowsCount;
    @JsonProperty("repres")
    protected ButtonRepresentation repres;
    @JsonProperty("defButton")
    protected Boolean defButton;
    @JsonProperty("skipOnInput")
    protected BWAValue skipOnInput;
    @JsonProperty("enabled")
    protected Boolean enabled;
    @JsonProperty("defItem")
    protected Boolean defItem;
    @JsonProperty("important")
    protected BWAValue important;
    @JsonProperty("width")
    protected BigDecimal width;
    @JsonProperty("height")
    protected BigDecimal height;
    @JsonProperty("horStretch")
    protected Boolean horStretch;
    @JsonProperty("verStretch")
    protected Boolean verStretch;
    @JsonProperty("autoMaxWidth")
    protected Boolean autoMaxWidth;
    @JsonProperty("maxWidth")
    protected BigDecimal maxWidth;
    @JsonProperty("minWidth")
    protected BigDecimal minWidth;
    @JsonProperty("autoMaxHeight")
    protected Boolean autoMaxHeight;
    @JsonProperty("maxHeight")
    protected BigDecimal maxHeight;
    @JsonProperty("plcm")
    protected MenuElementPlacementArea plcm;
    @JsonProperty("check")
    protected Boolean check;
    @JsonProperty("tooltipRepres")
    protected TooltipRepresentation tooltipRepres;
    @JsonProperty("groupHAlign")
    protected ItemHorizontalAlignment groupHAlign;
    @JsonProperty("groupVAlign")
    protected ItemVerticalAlignment groupVAlign;
    @JsonProperty("shape")
    protected ButtonShape shape;
    @JsonProperty("shapeRepres")
    protected ButtonShapeRepresentation shapeRepres;
    @JsonProperty("picLocation")
    protected FormButtonPictureLocation picLocation;


}
