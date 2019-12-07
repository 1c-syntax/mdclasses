

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FormDataStructureDescription
        extends BaseDescription {

    protected List<FormDataFieldDescription> field;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("value")
    protected Boolean value;

}
