

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class TaskProperties {

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
    @JsonProperty("ObjectModule")
    protected String objectModule;
    @JsonProperty("ManagerModule")
    protected String managerModule;
    @JsonProperty("NumberType")
    protected TaskNumberType numberType;
    @JsonProperty("NumberLength")
    protected BigDecimal numberLength;
    @JsonProperty("NumberAllowedLength")
    protected AllowedLength numberAllowedLength;
    @JsonProperty("CheckUnique")
    protected boolean checkUnique;
    @JsonProperty("Autonumbering")
    protected boolean autonumbering;
    @JsonProperty("TaskNumberAutoPrefix")
    protected TaskNumberAutoPrefix taskNumberAutoPrefix;
    @JsonProperty("DescriptionLength")
    protected BigDecimal descriptionLength;
    @JsonProperty("Addressing")
    protected String addressing;
    @JsonProperty("MainAddressingAttribute")
    protected String mainAddressingAttribute;
    @JsonProperty("CurrentPerformer")
    protected String currentPerformer;
    @JsonProperty("BasedOn")
    protected MDListType basedOn;
    @JsonProperty("StandardAttributes")
    protected StandardAttributeDescriptions standardAttributes;
    @JsonProperty("Characteristics")
    protected CharacteristicsDescriptions characteristics;
    @JsonProperty("DefaultPresentation")
    protected TaskMainPresentation defaultPresentation;
    @JsonProperty("EditType")
    protected EditType editType;
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
    @JsonProperty("ChoiceHistoryOnInput")
    protected ChoiceHistoryOnInput choiceHistoryOnInput;
    @JsonProperty("IncludeHelpInContents")
    protected boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;
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
