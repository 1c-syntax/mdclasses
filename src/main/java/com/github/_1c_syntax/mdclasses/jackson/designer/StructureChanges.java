

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StructureChanges {

    protected List<Object> v;
    @JsonProperty("fields")
    protected List<Long> fields;
    @JsonProperty("fixedFields")
    protected List<Long> fixedFields;

}
