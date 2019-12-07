
package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FormDataStructureChanges {

    protected List<FormDataFieldDescription> added;
    protected List<String> removed;
    protected List<FormDataFieldDescription> changed;
    @JsonProperty("name")
    protected String name;

}
