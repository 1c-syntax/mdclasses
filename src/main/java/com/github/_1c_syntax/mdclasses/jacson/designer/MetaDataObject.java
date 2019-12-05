package com.github._1c_syntax.mdclasses.jacson.designer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

//@JacksonXmlRootElement(localName = "MetaDataObject")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class MetaDataObject {
    @JsonProperty("Configuration")
    public Configuration configuration;
}

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
class InternalInfo {
}

