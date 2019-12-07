

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class InformationRegisterProperties {

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
    @JsonProperty("EditType")
    protected EditType editType;
    @JsonProperty("DefaultRecordForm")
    protected String defaultRecordForm;
    @JsonProperty("DefaultListForm")
    protected String defaultListForm;
    @JsonProperty("AuxiliaryRecordForm")
    protected String auxiliaryRecordForm;
    @JsonProperty("AuxiliaryListForm")
    protected String auxiliaryListForm;
    @JsonProperty("StandardAttributes")
    protected StandardAttributeDescriptions standardAttributes;
    @JsonProperty("InformationRegisterPeriodicity")
    protected InformationRegisterPeriodicity informationRegisterPeriodicity;
    @JsonProperty("WriteMode")
    protected RegisterWriteMode writeMode;
    @JsonProperty("MainFilterOnPeriod")
    protected boolean mainFilterOnPeriod;
    @JsonProperty("IncludeHelpInContents")
    protected boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;
    @JsonProperty("RecordSetModule")
    protected String recordSetModule;
    @JsonProperty("ManagerModule")
    protected String managerModule;
    @JsonProperty("DataLockControlMode")
    protected DefaultDataLockControlMode dataLockControlMode;
    @JsonProperty("FullTextSearch")
    protected FullTextSearchUsing fullTextSearch;
    @JsonProperty("EnableTotalsSliceFirst")
    protected boolean enableTotalsSliceFirst;
    @JsonProperty("EnableTotalsSliceLast")
    protected boolean enableTotalsSliceLast;
    @JsonProperty("RecordPresentation")
    protected LocalStringType recordPresentation;
    @JsonProperty("ExtendedRecordPresentation")
    protected LocalStringType extendedRecordPresentation;
    @JsonProperty("ListPresentation")
    protected LocalStringType listPresentation;
    @JsonProperty("ExtendedListPresentation")
    protected LocalStringType extendedListPresentation;
    @JsonProperty("Explanation")
    protected LocalStringType explanation;

}
