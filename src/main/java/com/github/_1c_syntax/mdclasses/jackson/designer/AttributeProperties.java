


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AttributeProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("Type")
    protected TypeDescription type;
    @JsonProperty("PasswordMode")
    protected Boolean passwordMode;
    @JsonProperty("Format")
    protected LocalStringType format;
    @JsonProperty("EditFormat")
    protected LocalStringType editFormat;
    @JsonProperty("ToolTip")
    protected LocalStringType toolTip;
    @JsonProperty("MarkNegatives")
    protected Boolean markNegatives;
    @JsonProperty("Mask")
    protected String mask;
    @JsonProperty("MultiLine")
    protected Boolean multiLine;
    @JsonProperty("ExtendedEdit")
    protected Boolean extendedEdit;
    @JsonProperty("MinValue")
    protected Object minValue;
    @JsonProperty("MaxValue")
    protected Object maxValue;
    @JsonProperty("FillFromFillingValue")
    protected Boolean fillFromFillingValue;
    @JsonProperty("FillValue")
    protected Object fillValue;
    @JsonProperty("FillChecking")
    protected FillChecking fillChecking;
    @JsonProperty("ChoiceFoldersAndItems")
    protected FoldersAndItemsUse choiceFoldersAndItems;
    @JsonProperty("ChoiceParameterLinks")
    protected ChoiceParameterLinks choiceParameterLinks;
    @JsonProperty("ChoiceParameters")
    protected ChoiceParameters choiceParameters;
    @JsonProperty("QuickChoice")
    protected UseQuickChoice quickChoice;
    @JsonProperty("CreateOnInput")
    protected CreateOnInput createOnInput;
    @JsonProperty("ChoiceForm")
    protected String choiceForm;
    @JsonProperty("LinkByType")
    protected TypeLink linkByType;
    @JsonProperty("ChoiceHistoryOnInput")
    protected ChoiceHistoryOnInput choiceHistoryOnInput;
    @JsonProperty("Indexing")
    protected Indexing indexing;
    @JsonProperty("FullTextSearch")
    protected FullTextSearchUsing fullTextSearch;
    @JsonProperty("Use")
    protected AttributeUse use;
    @JsonProperty("ScheduleLink")
    protected String scheduleLink;


}
