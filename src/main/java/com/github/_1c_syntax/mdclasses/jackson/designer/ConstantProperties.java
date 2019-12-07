

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstantProperties {

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
    @JsonProperty("UseStandardCommands")
    protected boolean useStandardCommands;
    @JsonProperty("DefaultForm")
    protected String defaultForm;
    @JsonProperty("ExtendedPresentation")
    protected LocalStringType extendedPresentation;
    @JsonProperty("Explanation")
    protected LocalStringType explanation;
    @JsonProperty("PasswordMode")
    protected boolean passwordMode;
    @JsonProperty("Format")
    protected LocalStringType format;
    @JsonProperty("EditFormat")
    protected LocalStringType editFormat;
    @JsonProperty("ToolTip")
    protected LocalStringType toolTip;
    @JsonProperty("MarkNegatives")
    protected boolean markNegatives;
    @JsonProperty("Mask")
    protected String mask;
    @JsonProperty("MultiLine")
    protected boolean multiLine;
    @JsonProperty("ExtendedEdit")
    protected boolean extendedEdit;
    @JsonProperty("MinValue")
    protected Object minValue;
    @JsonProperty("MaxValue")
    protected Object maxValue;
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
    @JsonProperty("ChoiceForm")
    protected String choiceForm;
    @JsonProperty("LinkByType")
    protected TypeLink linkByType;
    @JsonProperty("ChoiceHistoryOnInput")
    protected ChoiceHistoryOnInput choiceHistoryOnInput;
    @JsonProperty("ValueManagerModule")
    protected String valueManagerModule;
    @JsonProperty("DataLockControlMode")
    protected DefaultDataLockControlMode dataLockControlMode;

}
