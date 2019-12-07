

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CubeProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("NameInDataSource")
    protected String nameInDataSource;
    @JsonProperty("Characteristics")
    protected CharacteristicsDescriptions characteristics;
    @JsonProperty("UseStandardCommands")
    protected boolean useStandardCommands;
    @JsonProperty("DefaultRecordForm")
    protected String defaultRecordForm;
    @JsonProperty("DefaultListForm")
    protected String defaultListForm;
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
    @JsonProperty("RecordSetModule")
    protected String recordSetModule;
    @JsonProperty("ManagerModule")
    protected String managerModule;
    @JsonProperty("IncludeHelpInContents")
    protected boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;

}
