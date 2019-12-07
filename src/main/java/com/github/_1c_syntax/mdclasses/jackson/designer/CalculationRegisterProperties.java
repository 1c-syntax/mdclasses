

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CalculationRegisterProperties {

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
    @JsonProperty("DefaultListForm")
    protected String defaultListForm;
    @JsonProperty("AuxiliaryListForm")
    protected String auxiliaryListForm;
    @JsonProperty("Periodicity")
    protected CalculationRegisterPeriodicity periodicity;
    @JsonProperty("ActionPeriod")
    protected boolean actionPeriod;
    @JsonProperty("BasePeriod")
    protected boolean basePeriod;
    @JsonProperty("Schedule")
    protected String schedule;
    @JsonProperty("ScheduleValue")
    protected String scheduleValue;
    @JsonProperty("ScheduleDate")
    protected String scheduleDate;
    @JsonProperty("ChartOfCalculationTypes")
    protected String chartOfCalculationTypes;
    @JsonProperty("RecordSetModule")
    protected String recordSetModule;
    @JsonProperty("ManagerModule")
    protected String managerModule;
    @JsonProperty("IncludeHelpInContents")
    protected boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;
    @JsonProperty("StandardAttributes")
    protected StandardAttributeDescriptions standardAttributes;
    @JsonProperty("DataLockControlMode")
    protected DefaultDataLockControlMode dataLockControlMode;
    @JsonProperty("FullTextSearch")
    protected FullTextSearchUsing fullTextSearch;
    @JsonProperty("ListPresentation")
    protected LocalStringType listPresentation;
    @JsonProperty("ExtendedListPresentation")
    protected LocalStringType extendedListPresentation;
    @JsonProperty("Explanation")
    protected LocalStringType explanation;


}
