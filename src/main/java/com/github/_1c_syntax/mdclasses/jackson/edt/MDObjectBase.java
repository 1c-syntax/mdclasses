
package com.github._1c_syntax.mdclasses.jackson.edt;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class MDObjectBase {

    protected String name;
//    protected List<SynonymType> synonym;
    protected String comment;
    @JsonProperty("uuid")
    protected String uuid;
}
