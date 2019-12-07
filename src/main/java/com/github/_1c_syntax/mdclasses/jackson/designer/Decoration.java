

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class Decoration {

    @JsonProperty("anySimpleType")
    protected String txtClr;
    protected Font fnt;
    protected ShortCutType stcut;
    protected TextDecorationData textData;
    protected PictureDecorationData pictureData;
    protected List<Predefined> predefined;
    protected FormattedString title;
    protected String tooltip;
    @JsonProperty("id")
    protected BigDecimal id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("org")
    protected FormElementOrigin org;
    @JsonProperty("users")
    protected Boolean users;
    @JsonProperty("kind")
    protected ManagedFormDecorationType kind;
    @JsonProperty("visible")
    protected Boolean visible;
    @JsonProperty("userVisible")
    protected Boolean userVisible;
    @JsonProperty("enabled")
    protected Boolean enabled;
    @JsonProperty("width")
    protected BigDecimal width;
    @JsonProperty("height")
    protected BigDecimal height;
    @JsonProperty("horStretch")
    protected BWAValue horStretch;
    @JsonProperty("verStretch")
    protected BWAValue verStretch;
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
    @JsonProperty("skipOnInput")
    protected BWAValue skipOnInput;
    @JsonProperty("tooltipRepres")
    protected TooltipRepresentation tooltipRepres;
    @JsonProperty("groupHAlign")
    protected ItemHorizontalAlignment groupHAlign;
    @JsonProperty("groupVAlign")
    protected ItemVerticalAlignment groupVAlign;

}
