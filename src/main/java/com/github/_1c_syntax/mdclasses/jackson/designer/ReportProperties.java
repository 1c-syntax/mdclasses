

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportProperties {

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
    @JsonProperty("DefaultForm")
    protected String defaultForm;
    @JsonProperty("AuxiliaryForm")
    protected String auxiliaryForm;
    @JsonProperty("MainDataCompositionSchema")
    protected String mainDataCompositionSchema;
    @JsonProperty("DefaultSettingsForm")
    protected String defaultSettingsForm;
    @JsonProperty("AuxiliarySettingsForm")
    protected String auxiliarySettingsForm;
    @JsonProperty("DefaultVariantForm")
    protected String defaultVariantForm;
    @JsonProperty("VariantsStorage")
    protected String variantsStorage;
    @JsonProperty("SettingsStorage")
    protected String settingsStorage;
    @JsonProperty("ObjectModule")
    protected String objectModule;
    @JsonProperty("ManagerModule")
    protected String managerModule;
    @JsonProperty("IncludeHelpInContents")
    protected boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;
    @JsonProperty("ExtendedPresentation")
    protected LocalStringType extendedPresentation;
    @JsonProperty("Explanation")
    protected LocalStringType explanation;

}
