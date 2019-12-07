

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalDataProcessorProperties {

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
    @JsonProperty("ObjectModule")
    protected String objectModule;
    @JsonProperty("Help")
    protected String help;


}
