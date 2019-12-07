

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TabularSection
        extends MDObjectBase {

    @JsonProperty("Properties")
    protected TabularSectionProperties properties;
    @JsonProperty("ChildObjects")
    protected TabularSectionChildObjects childObjects;


}
