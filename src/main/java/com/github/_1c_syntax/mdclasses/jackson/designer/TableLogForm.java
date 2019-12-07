

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TableLogForm {

    protected DataPath prop;
    protected String propName;
    protected TypeDescription tdp;
    protected DataPath rowsPicProp;
    protected String rowsPicPropName;
    protected Picture rowsPic;
    protected String bkClr;
    protected String txtClr;
    protected String brdClr;
    protected Font fnt;
    protected String titleTxtClr;
    protected Font titleFnt;
    protected ShortCutType stcut;
    protected AddProps addProps;
    protected List<Event> event;
    protected CommandsInclusion commands;
    protected List<Predefined> predefined;
    protected List<FieldLogForm> field;
    protected List<GroupLogForm> group;
    protected String title;
    protected String tooltip;
    @JsonProperty("id")
    protected BigDecimal id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("org")
    protected FormElementOrigin org;
    @JsonProperty("repres")
    protected TableRepresentation repres;
    @JsonProperty("visible")
    protected Boolean visible;
    @JsonProperty("userVisible")
    protected Boolean userVisible;
    @JsonProperty("titleLocation")
    protected FormElementTitleLocation titleLocation;
    @JsonProperty("titleRowsCount")
    protected BigDecimal titleRowsCount;
    @JsonProperty("commandBarLocation")
    protected FormElementCommandBarLocation commandBarLocation;
    @JsonProperty("autoFill")
    protected Boolean autoFill;
    @JsonProperty("enabled")
    protected Boolean enabled;
    @JsonProperty("readOnly")
    protected Boolean readOnly;
    @JsonProperty("skipOnInput")
    protected BWAValue skipOnInput;
    @JsonProperty("defItem")
    protected Boolean defItem;
    @JsonProperty("changeRowSet")
    protected Boolean changeRowSet;
    @JsonProperty("changeRowOrder")
    protected Boolean changeRowOrder;
    @JsonProperty("width")
    protected BigDecimal width;
    @JsonProperty("height")
    protected BigDecimal height;
    @JsonProperty("rowsCount")
    protected BigDecimal rowsCount;
    @JsonProperty("heightControlVariant")
    protected TableHeightControlVariant heightControlVariant;
    @JsonProperty("autoMaxRowsCount")
    protected Boolean autoMaxRowsCount;
    @JsonProperty("maxRowsCount")
    protected BigDecimal maxRowsCount;
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
    @JsonProperty("choiceMode")
    protected Boolean choiceMode;
    @JsonProperty("multipleChoice")
    protected Boolean multipleChoice;
    @JsonProperty("rowInput")
    protected TableRowInputMode rowInput;
    @JsonProperty("rowsSelection")
    protected TableSelectionMode rowsSelection;
    @JsonProperty("rowSelection")
    protected TableRowSelectionMode rowSelection;
    @JsonProperty("showHeader")
    protected Boolean showHeader;
    @JsonProperty("headerHeight")
    protected BigDecimal headerHeight;
    @JsonProperty("showFooter")
    protected Boolean showFooter;
    @JsonProperty("footerHeight")
    protected BigDecimal footerHeight;
    @JsonProperty("horScroll")
    protected TableScrollBarUse horScroll;
    @JsonProperty("verScroll")
    protected TableScrollBarUse verScroll;
    @JsonProperty("showHorLines")
    protected Boolean showHorLines;
    @JsonProperty("showVerLines")
    protected Boolean showVerLines;
    @JsonProperty("fixedLeft")
    protected BigDecimal fixedLeft;
    @JsonProperty("fixedRight")
    protected BigDecimal fixedRight;
    @JsonProperty("useAltRowColor")
    protected Boolean useAltRowColor;
    @JsonProperty("autoInsert")
    protected Boolean autoInsert;
    @JsonProperty("autoInsertNotcompleted")
    protected BWAValue autoInsertNotcompleted;
    @JsonProperty("autoMarkNotcompleted")
    protected BWAValue autoMarkNotcompleted;
    @JsonProperty("searchOnInput")
    protected SearchOnInput searchOnInput;
    @JsonProperty("initListView")
    protected TableInitialListView initListView;
    @JsonProperty("initTreeView")
    protected TableInitialTreeView initTreeView;
    @JsonProperty("useOutput")
    protected UseOutput useOutput;
    @JsonProperty("horStretch")
    protected Boolean horStretch;
    @JsonProperty("verStretch")
    protected Boolean verStretch;
    @JsonProperty("startDrag")
    protected Boolean startDrag;
    @JsonProperty("drag")
    protected Boolean drag;
    @JsonProperty("tooltipRepres")
    protected TooltipRepresentation tooltipRepres;
    @JsonProperty("searchStringLocation")
    protected SearchStringLocation searchStringLocation;
    @JsonProperty("viewStatusLocation")
    protected ViewStatusLocation viewStatusLocation;
    @JsonProperty("searchControlLocation")
    protected SearchControlLocation searchControlLocation;
    @JsonProperty("groupHAlign")
    protected ItemHorizontalAlignment groupHAlign;
    @JsonProperty("groupVAlign")
    protected ItemVerticalAlignment groupVAlign;
    @JsonProperty("clearEvents")
    protected Boolean clearEvents;
    @JsonProperty("refreshRequest")
    protected RefreshRequestMethod refreshRequest;

}
