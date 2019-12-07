

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ChartOfCharacteristicTypesProperties {

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
    @JsonProperty("CharacteristicExtValues")
    protected String characteristicExtValues;
    @JsonProperty("Type")
    protected TypeDescription type;
    @JsonProperty("Hierarchical")
    protected boolean hierarchical;
    @JsonProperty("FoldersOnTop")
    protected boolean foldersOnTop;
    @JsonProperty("CodeLength")
    protected BigDecimal codeLength;
    @JsonProperty("CodeAllowedLength")
    protected AllowedLength codeAllowedLength;
    @JsonProperty("DescriptionLength")
    protected BigDecimal descriptionLength;
    @JsonProperty("CodeSeries")
    protected CharacteristicKindCodesSeries codeSeries;
    @JsonProperty("CheckUnique")
    protected boolean checkUnique;
    @JsonProperty("Autonumbering")
    protected boolean autonumbering;
    @JsonProperty("DefaultPresentation")
    protected CharacteristicTypeMainPresentation defaultPresentation;
    @JsonProperty("StandardAttributes")
    protected StandardAttributeDescriptions standardAttributes;
    @JsonProperty("Characteristics")
    protected CharacteristicsDescriptions characteristics;
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
    @JsonProperty("CreateOnInput")
    protected CreateOnInput createOnInput;
    @JsonProperty("SearchStringModeOnInputByString")
    protected SearchStringModeOnInputByString searchStringModeOnInputByString;
    @JsonProperty("ChoiceDataGetModeOnInputByString")
    protected ChoiceDataGetModeOnInputByString choiceDataGetModeOnInputByString;
    @JsonProperty("FullTextSearchOnInputByString")
    protected FullTextSearchOnInputByString fullTextSearchOnInputByString;
    @JsonProperty("ChoiceHistoryOnInput")
    protected ChoiceHistoryOnInput choiceHistoryOnInput;
    @JsonProperty("DefaultObjectForm")
    protected String defaultObjectForm;
    @JsonProperty("DefaultFolderForm")
    protected String defaultFolderForm;
    @JsonProperty("DefaultListForm")
    protected String defaultListForm;
    @JsonProperty("DefaultChoiceForm")
    protected String defaultChoiceForm;
    @JsonProperty("DefaultFolderChoiceForm")
    protected String defaultFolderChoiceForm;
    @JsonProperty("AuxiliaryObjectForm")
    protected String auxiliaryObjectForm;
    @JsonProperty("AuxiliaryFolderForm")
    protected String auxiliaryFolderForm;
    @JsonProperty("AuxiliaryListForm")
    protected String auxiliaryListForm;
    @JsonProperty("AuxiliaryChoiceForm")
    protected String auxiliaryChoiceForm;
    @JsonProperty("AuxiliaryFolderChoiceForm")
    protected String auxiliaryFolderChoiceForm;
    @JsonProperty("ObjectModule")
    protected String objectModule;
    @JsonProperty("ManagerModule")
    protected String managerModule;
    @JsonProperty("BasedOn")
    protected MDListType basedOn;
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
