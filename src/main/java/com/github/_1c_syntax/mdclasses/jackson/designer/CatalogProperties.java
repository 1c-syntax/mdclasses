

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class CatalogProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("Hierarchical")
    protected boolean hierarchical;
    @JsonProperty("HierarchyType")
    protected HierarchyType hierarchyType;
    @JsonProperty("LimitLevelCount")
    protected boolean limitLevelCount;
    @JsonProperty("LevelCount")
    protected BigDecimal levelCount;
    @JsonProperty("FoldersOnTop")
    protected boolean foldersOnTop;
    @JsonProperty("UseStandardCommands")
    protected boolean useStandardCommands;
    @JsonProperty("Owners")
    protected MDListType owners;
    @JsonProperty("SubordinationUse")
    protected SubordinationUse subordinationUse;
    @JsonProperty("CodeLength")
    protected BigDecimal codeLength;
    @JsonProperty("DescriptionLength")
    protected BigDecimal descriptionLength;
    @JsonProperty("CodeType")
    protected CatalogCodeType codeType;
    @JsonProperty("CodeAllowedLength")
    protected AllowedLength codeAllowedLength;
    @JsonProperty("CodeSeries")
    protected CatalogCodesSeries codeSeries;
    @JsonProperty("CheckUnique")
    protected boolean checkUnique;
    @JsonProperty("Autonumbering")
    protected boolean autonumbering;
    @JsonProperty("DefaultPresentation")
    protected CatalogMainPresentation defaultPresentation;
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
    @JsonProperty("SearchStringModeOnInputByString")
    protected SearchStringModeOnInputByString searchStringModeOnInputByString;
    @JsonProperty("FullTextSearchOnInputByString")
    protected FullTextSearchOnInputByString fullTextSearchOnInputByString;
    @JsonProperty("ChoiceDataGetModeOnInputByString")
    protected ChoiceDataGetModeOnInputByString choiceDataGetModeOnInputByString;
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
    @JsonProperty("IncludeHelpInContents")
    protected boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;
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
    @JsonProperty("CreateOnInput")
    protected CreateOnInput createOnInput;
    @JsonProperty("ChoiceHistoryOnInput")
    protected ChoiceHistoryOnInput choiceHistoryOnInput;

}
