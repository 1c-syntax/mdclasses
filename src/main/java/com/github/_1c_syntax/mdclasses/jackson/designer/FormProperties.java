

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("Form")
    protected String form;
    @JsonProperty("FormType")
    protected FormType formType;
    @JsonProperty("IncludeHelpInContents")
    protected Boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;
    @JsonProperty("UsePurposes")
    protected FixedArray usePurposes;
    @JsonProperty("ExtendedPresentation")
    protected LocalStringType extendedPresentation;

}
