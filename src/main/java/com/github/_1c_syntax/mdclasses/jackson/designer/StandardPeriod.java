

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.datatype.XMLGregorianCalendar;

@Getter
@Setter
public class StandardPeriod {

    @JsonProperty(required = true)
    protected Object variant;
    protected XMLGregorianCalendar startDate;
    protected XMLGregorianCalendar endDate;

}
