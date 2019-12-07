

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DocumentProperties {

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
    @JsonProperty("Numerator")
    protected String numerator;
    @JsonProperty("NumberType")
    protected DocumentNumberType numberType;
    @JsonProperty("NumberLength")
    protected BigDecimal numberLength;
    @JsonProperty("NumberAllowedLength")
    protected AllowedLength numberAllowedLength;
    @JsonProperty("NumberPeriodicity")
    protected DocumentNumberPeriodicity numberPeriodicity;
    @JsonProperty("CheckUnique")
    protected boolean checkUnique;
    @JsonProperty("Autonumbering")
    protected boolean autonumbering;
    @JsonProperty("StandardAttributes")
    protected StandardAttributeDescriptions standardAttributes;
    @JsonProperty("Characteristics")
    protected CharacteristicsDescriptions characteristics;
    @JsonProperty("BasedOn")
    protected MDListType basedOn;
    @JsonProperty("InputByString")
    protected FieldList inputByString;
    @JsonProperty("CreateOnInput")
    protected CreateOnInput createOnInput;
    @JsonProperty("SearchStringModeOnInputByString")
    protected SearchStringModeOnInputByString searchStringModeOnInputByString;
    @JsonProperty("FullTextSearchOnInputByString")
    protected FullTextSearchOnInputByString fullTextSearchOnInputByString;
    @JsonProperty("ChoiceDataGetModeOnInputByString")
    protected ChoiceDataGetModeOnInputByString choiceDataGetModeOnInputByString;
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
    @JsonProperty("Posting")
    protected Posting posting;
    @JsonProperty("RealTimePosting")
    protected RealTimePosting realTimePosting;
    @JsonProperty("RegisterRecordsDeletion")
    protected RegisterRecordsDeletion registerRecordsDeletion;
    @JsonProperty("RegisterRecordsWritingOnPost")
    protected RegisterRecordsWritingOnPost registerRecordsWritingOnPost;
    @JsonProperty("SequenceFilling")
    protected SequenceFilling sequenceFilling;
    @JsonProperty("RegisterRecords")
    protected MDListType registerRecords;
    @JsonProperty("PostInPrivilegedMode")
    protected boolean postInPrivilegedMode;
    @JsonProperty("UnpostInPrivilegedMode")
    protected boolean unpostInPrivilegedMode;
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
    @JsonProperty("ChoiceHistoryOnInput")
    protected ChoiceHistoryOnInput choiceHistoryOnInput;

}
