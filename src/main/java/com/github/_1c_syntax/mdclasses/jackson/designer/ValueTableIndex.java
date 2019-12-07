

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.tools.javac.util.List;
import lombok.Getter;

@Getter
public class ValueTableIndex {

    @JsonProperty(required = true)
    protected List<String> column;

}
