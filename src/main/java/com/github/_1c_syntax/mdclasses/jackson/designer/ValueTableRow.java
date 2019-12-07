

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.tools.javac.util.List;
import lombok.Getter;


@Getter
public class ValueTableRow {

    @JsonProperty("Value")
    protected List<Object> value;

}
