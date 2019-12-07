

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Drawing {

    @JsonProperty(required = true)
    protected SpreadsheetDocumentDrawingType drawingType;
    @JsonProperty(required = true)
    protected BigDecimal id;
    protected BigDecimal formatIndex;
    protected LocalStringType text;
    protected String textString;
    protected String parameter;
    protected Object value;
    protected String detailParameter;
    protected Object detailValue;
    @JsonProperty(required = true)
    protected BigDecimal beginRow;
    @JsonProperty(required = true)
    protected BigDecimal beginRowOffset;
    @JsonProperty(required = true)
    protected BigDecimal endRow;
    @JsonProperty(required = true)
    protected BigDecimal endRowOffset;
    @JsonProperty(required = true)
    protected BigDecimal beginColumn;
    @JsonProperty(required = true)
    protected BigDecimal beginColumnOffset;
    @JsonProperty(required = true)
    protected BigDecimal endColumn;
    @JsonProperty(required = true)
    protected BigDecimal endColumnOffset;
    protected Boolean autoSize;
    protected PictureSize pictureSize;
    protected BigDecimal zOrder;
    protected List<Drawing> nestedDrawing;
    protected Object object;
    protected BigDecimal pictureIndex;
    protected Object picture;
}
