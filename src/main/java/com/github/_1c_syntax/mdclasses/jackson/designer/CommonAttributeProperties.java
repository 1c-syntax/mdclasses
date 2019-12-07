

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonAttributeProperties {

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
    @JsonProperty("FillFromFillingValue")
    protected boolean fillFromFillingValue;
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
    @JsonProperty("Content")
    protected CommonAttributeContent content;
    @JsonProperty("AutoUse")
    protected CommonAttributeAutoUse autoUse;
    @JsonProperty("DataSeparation")
    protected CommonAttributeDataSeparation dataSeparation;
    @JsonProperty("SeparatedDataUse")
    protected CommonAttributeSeparatedDataUse separatedDataUse;
    @JsonProperty("DataSeparationValue")
    protected String dataSeparationValue;
    @JsonProperty("DataSeparationUse")
    protected String dataSeparationUse;
    @JsonProperty("ConditionalSeparation")
    protected String conditionalSeparation;
    @JsonProperty("UsersSeparation")
    protected CommonAttributeUsersSeparation usersSeparation;
    @JsonProperty("AuthenticationSeparation")
    protected CommonAttributeAuthenticationSeparation authenticationSeparation;
    @JsonProperty("ConfigurationExtensionsSeparation")
    protected CommonAttributeConfigurationExtensionsSeparation configurationExtensionsSeparation;
    @JsonProperty("Indexing")
    protected Indexing indexing;
    @JsonProperty("FullTextSearch")
    protected FullTextSearchUsing fullTextSearch;

}
