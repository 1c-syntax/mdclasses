package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonModule
        extends MDObjectBase {

    protected Boolean server = false;
    protected Boolean global = false;
    protected Boolean clientManagedApplication = false;
    protected Boolean externalConnection = false;
    protected Boolean clientOrdinaryApplication = false;
    protected Boolean serverCall = false;
    protected ReturnValueReuse returnValuesReuse = ReturnValueReuse.DONT_USE;
    protected Boolean privileged = false;

    @JsonProperty("Properties")
    protected void unpackProperties(Map<String, Object> properties) {
        this.server = Boolean.valueOf((String) properties.getOrDefault("Server", false));
        this.global = Boolean.valueOf((String) properties.getOrDefault("Global", false));
        this.clientManagedApplication = Boolean.valueOf((String) properties.getOrDefault("ClientManagedApplication", false));
        this.externalConnection = Boolean.valueOf((String) properties.getOrDefault("ExternalConnection", false));
        this.clientOrdinaryApplication = Boolean.valueOf((String) properties.getOrDefault("ClientOrdinaryApplication", false));
        this.serverCall = Boolean.valueOf((String) properties.getOrDefault("ServerCall", false));
        this.returnValuesReuse = ReturnValueReuse.fromValue((String) properties.getOrDefault("ReturnValuesReuse", false));
        this.privileged = Boolean.valueOf((String) properties.getOrDefault("Privileged", false));
        this.name = (String) properties.getOrDefault("Name", "");
        this.comment = (String) properties.getOrDefault("Comment", "");
    }
}
