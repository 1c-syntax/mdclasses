package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MDObjectBase {

    protected String name;
    protected String comment;

    @JsonProperty("uuid")
    protected String uuid;

    @JsonProperty("Properties")
    private void unpackProperties(Map<String, Object> properties) {
        this.name = (String) properties.get("name");
        this.comment = (String) properties.get("comment");
    }

}
