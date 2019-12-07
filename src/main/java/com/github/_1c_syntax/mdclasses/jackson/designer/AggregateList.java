

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AggregateList {

    @JsonProperty("dateTime")
    protected XMLGregorianCalendar buildDate;
    protected BigDecimal sizeLimit;
    protected BigDecimal size;
    protected BigDecimal effect;
    protected List<Aggregate> aggregate;

}
