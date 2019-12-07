

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocaleInfo {

    protected BoolFormat bool;
    protected DateFormat date;
    protected NumericFormat numeric;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("useSeanceFormats")
    protected Boolean useSeanceFormats;
    @JsonProperty("firstDayOfWeek")
    protected Integer firstDayOfWeek;

}
