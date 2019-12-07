


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Aggregate {

    protected Boolean use;
    protected BigDecimal periodicity;
    @JsonProperty("dateTime")
    protected XMLGregorianCalendar beginOfPeriod;
    @JsonProperty("dateTime")
    protected XMLGregorianCalendar endOfPeriod;
    protected BigDecimal size;
    protected List<String> dimension;


}
