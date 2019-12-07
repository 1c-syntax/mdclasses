

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.namespace.QName;

@Getter
@Setter
public class ParameterProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("XDTOValueType")
    protected QName xdtoValueType;
    @JsonProperty("Nillable")
    protected boolean nillable;
    @JsonProperty("TransferDirection")
    protected TransferDirection transferDirection;

}
