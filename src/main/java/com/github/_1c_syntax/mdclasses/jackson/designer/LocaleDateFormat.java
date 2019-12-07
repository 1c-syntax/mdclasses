


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class LocaleDateFormat {

    protected List<String> longDayNames;
    protected List<String> shortDayNames;
    protected List<String> longMonthNames;
    protected List<String> shortMonthNames;
    protected List<String> longAloneMonthNames;
    protected List<String> shortAloneMonthNames;
    @JsonProperty("fullDatePattern")
    protected String fullDatePattern;
    @JsonProperty("longDatePattern")
    protected String longDatePattern;
    @JsonProperty("shortDatePattern")
    protected String shortDatePattern;
    @JsonProperty("timePattern")
    protected String timePattern;
    @JsonProperty("shortTimePattern")
    protected String shortTimePattern;
    @JsonProperty("longDateTimePattern")
    protected String longDateTimePattern;
    @JsonProperty("shortDateTimePattern")
    protected String shortDateTimePattern;
    @JsonProperty("beginningOfCentury")
    protected BigDecimal beginningOfCentury;
    @JsonProperty("amPmMarkers")
    protected String amPmMarkers;

}
