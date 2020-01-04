package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MDObjectBase {

    @JsonProperty("name")
    protected String name;
    @JsonProperty("comment")
    protected String comment = "";

    @JsonProperty("uuid")
    protected String uuid;

    @JsonProperty("Properties")
    protected void unpackProperties(Map<String, Object> properties) {
        this.name = (String) properties.getOrDefault("Name", "");
        this.comment = (String) properties.getOrDefault("Comment", "");
    }

}
