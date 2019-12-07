

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateFormat {

    @JsonProperty("shortDate")
    protected String shortDate;
    @JsonProperty("time")
    protected String time;

}
