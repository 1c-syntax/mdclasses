

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnumProperties {

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
    @JsonProperty("StandardAttributes")
    protected StandardAttributeDescriptions standardAttributes;
    @JsonProperty("Characteristics")
    protected CharacteristicsDescriptions characteristics;
    @JsonProperty("QuickChoice")
    protected boolean quickChoice;
    @JsonProperty("ChoiceMode")
    protected ChoiceMode choiceMode;
    @JsonProperty("DefaultListForm")
    protected String defaultListForm;
    @JsonProperty("DefaultChoiceForm")
    protected String defaultChoiceForm;
    @JsonProperty("AuxiliaryListForm")
    protected String auxiliaryListForm;
    @JsonProperty("AuxiliaryChoiceForm")
    protected String auxiliaryChoiceForm;
    @JsonProperty("ManagerModule")
    protected String managerModule;
    @JsonProperty("ListPresentation")
    protected LocalStringType listPresentation;
    @JsonProperty("ExtendedListPresentation")
    protected LocalStringType extendedListPresentation;
    @JsonProperty("Explanation")
    protected LocalStringType explanation;
    @JsonProperty("ChoiceHistoryOnInput")
    protected ChoiceHistoryOnInput choiceHistoryOnInput;

}
