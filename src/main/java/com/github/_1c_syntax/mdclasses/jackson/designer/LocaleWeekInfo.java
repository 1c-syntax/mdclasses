

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocaleWeekInfo {

    @JsonProperty("firstDayOfWeek")
    protected Integer firstDayOfWeek;
    @JsonProperty("weekendOnSet")
    protected Integer weekendOnSet;
    @JsonProperty("weekendCease")
    protected Integer weekendCease;
}
