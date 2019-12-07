

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ChartOfAccountsProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("UseStandardCommands")
    protected boolean useStandardCommands;
    @JsonProperty("IncludeHelpInContents")
    protected boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;
    @JsonProperty("BasedOn")
    protected MDListType basedOn;
    @JsonProperty("ExtDimensionTypes")
    protected String extDimensionTypes;
    @JsonProperty("MaxExtDimensionCount")
    protected BigDecimal maxExtDimensionCount;
    @JsonProperty("CodeMask")
    protected String codeMask;
    @JsonProperty("CodeLength")
    protected BigDecimal codeLength;
    @JsonProperty("DescriptionLength")
    protected BigDecimal descriptionLength;
    @JsonProperty("CodeSeries")
    protected CharOfAccountCodeSeries codeSeries;
    @JsonProperty("CheckUnique")
    protected boolean checkUnique;
    @JsonProperty("DefaultPresentation")
    protected AccountMainPresentation defaultPresentation;
    @JsonProperty("StandardAttributes")
    protected StandardAttributeDescriptions standardAttributes;
    @JsonProperty("Characteristics")
    protected CharacteristicsDescriptions characteristics;
    @JsonProperty("StandardTabularSections")
    protected StandardTabularSectionDescriptions standardTabularSections;
    @JsonProperty("Predefined")
    protected String predefined;
    @JsonProperty("PredefinedDataUpdate")
    protected PredefinedDataUpdate predefinedDataUpdate;
    @JsonProperty("EditType")
    protected EditType editType;
    @JsonProperty("QuickChoice")
    protected boolean quickChoice;
    @JsonProperty("ChoiceMode")
    protected ChoiceMode choiceMode;
    @JsonProperty("InputByString")
    protected FieldList inputByString;
    @JsonProperty("SearchStringModeOnInputByString")
    protected SearchStringModeOnInputByString searchStringModeOnInputByString;
    @JsonProperty("FullTextSearchOnInputByString")
    protected FullTextSearchOnInputByString fullTextSearchOnInputByString;
    @JsonProperty("ChoiceDataGetModeOnInputByString")
    protected ChoiceDataGetModeOnInputByString choiceDataGetModeOnInputByString;
    @JsonProperty("CreateOnInput")
    protected CreateOnInput createOnInput;
    @JsonProperty("ChoiceHistoryOnInput")
    protected ChoiceHistoryOnInput choiceHistoryOnInput;
    @JsonProperty("DefaultObjectForm")
    protected String defaultObjectForm;
    @JsonProperty("DefaultListForm")
    protected String defaultListForm;
    @JsonProperty("DefaultChoiceForm")
    protected String defaultChoiceForm;
    @JsonProperty("AuxiliaryObjectForm")
    protected String auxiliaryObjectForm;
    @JsonProperty("AuxiliaryListForm")
    protected String auxiliaryListForm;
    @JsonProperty("AuxiliaryChoiceForm")
    protected String auxiliaryChoiceForm;
    @JsonProperty("ObjectModule")
    protected String objectModule;
    @JsonProperty("ManagerModule")
    protected String managerModule;
    @JsonProperty("AutoOrderByCode")
    protected boolean autoOrderByCode;
    @JsonProperty("OrderLength")
    protected BigDecimal orderLength;
    @JsonProperty("DataLockFields")
    protected FieldList dataLockFields;
    @JsonProperty("DataLockControlMode")
    protected DefaultDataLockControlMode dataLockControlMode;
    @JsonProperty("FullTextSearch")
    protected FullTextSearchUsing fullTextSearch;
    @JsonProperty("ObjectPresentation")
    protected LocalStringType objectPresentation;
    @JsonProperty("ExtendedObjectPresentation")
    protected LocalStringType extendedObjectPresentation;
    @JsonProperty("ListPresentation")
    protected LocalStringType listPresentation;
    @JsonProperty("ExtendedListPresentation")
    protected LocalStringType extendedListPresentation;
    @JsonProperty("Explanation")
    protected LocalStringType explanation;


}
