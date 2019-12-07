

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingsStorageProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("DefaultSaveForm")
    protected String defaultSaveForm;
    @JsonProperty("DefaultLoadForm")
    protected String defaultLoadForm;
    @JsonProperty("AuxiliarySaveForm")
    protected String auxiliarySaveForm;
    @JsonProperty("AuxiliaryLoadForm")
    protected String auxiliaryLoadForm;
    @JsonProperty("ManagerModule")
    protected String managerModule;


}
