

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentJournalProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("DefaultForm")
    protected String defaultForm;
    @JsonProperty("AuxiliaryForm")
    protected String auxiliaryForm;
    @JsonProperty("UseStandardCommands")
    protected boolean useStandardCommands;
    @JsonProperty("RegisteredDocuments")
    protected MDListType registeredDocuments;
    @JsonProperty("IncludeHelpInContents")
    protected boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;
    @JsonProperty("ManagerModule")
    protected String managerModule;
    @JsonProperty("StandardAttributes")
    protected StandardAttributeDescriptions standardAttributes;
    @JsonProperty("ListPresentation")
    protected LocalStringType listPresentation;
    @JsonProperty("ExtendedListPresentation")
    protected LocalStringType extendedListPresentation;
    @JsonProperty("Explanation")
    protected LocalStringType explanation;

}
