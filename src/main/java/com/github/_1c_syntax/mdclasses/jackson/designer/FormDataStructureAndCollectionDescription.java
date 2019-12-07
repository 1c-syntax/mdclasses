

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormDataStructureAndCollectionDescription
        extends BaseDescription {

    @JsonProperty(required = true)
    protected FormDataStructureDescription structure;
    @JsonProperty(required = true)
    protected FormDataCollectionDescription collection;
    @JsonProperty("name")
    protected String name;
}

