

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Addition {

    protected AdditionSource source;
    protected SearchStringData searchStringData;
    protected ViewStatusData viewStatusData;
    protected SearchControlData searchControlData;
    protected List<Predefined> predefined;
    protected String title;
    protected String tooltip;
    protected List<Event> event;
    protected List<GroupLogForm> group;
    protected List<Button> button;
    @JsonProperty("id")
    protected BigDecimal id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("org")
    protected FormElementOrigin org;
    @JsonProperty("users")
    protected Boolean users;
    @JsonProperty("kind")
    protected LogFormElementAdditionKind kind;
    @JsonProperty("visible")
    protected Boolean visible;
    @JsonProperty("userVisible")
    protected Boolean userVisible;
    @JsonProperty("enabled")
    protected Boolean enabled;
    @JsonProperty("plcm")
    protected MenuElementPlacementArea plcm;
    @JsonProperty("tooltipRepres")
    protected TooltipRepresentation tooltipRepres;
    @JsonProperty("groupHAlign")
    protected ItemHorizontalAlignment groupHAlign;
    @JsonProperty("groupVAlign")
    protected ItemVerticalAlignment groupVAlign;

}
