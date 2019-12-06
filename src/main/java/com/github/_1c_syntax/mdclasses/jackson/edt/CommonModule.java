

package com.github._1c_syntax.mdclasses.jackson.edt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonModule
        extends com.github._1c_syntax.mdclasses.jackson.edt.MDObjectBase {

    @JsonProperty(defaultValue = "false")
    protected Boolean server;
    @JsonProperty(defaultValue = "false")
    protected Boolean global;
    @JsonProperty(defaultValue = "false")
    protected Boolean clientManagedApplication;
    @JsonProperty(defaultValue = "false")
    protected Boolean externalConnection;
    @JsonProperty(defaultValue = "false")
    protected Boolean clientOrdinaryApplication;
    @JsonProperty(defaultValue = "false")
    protected Boolean serverCall;
    @JsonProperty()
    protected ReturnValueReuse returnValuesReuse;
    @JsonProperty(defaultValue = "false")
    protected Boolean privileged;
}
