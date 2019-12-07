

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ScheduledJobProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("MethodName")
    protected String methodName;
    @JsonProperty("Description")
    protected String description;
    @JsonProperty("Key")
    protected String key;
    @JsonProperty("Schedule")
    protected String schedule;
    @JsonProperty("Use")
    protected boolean use;
    @JsonProperty("Predefined")
    protected boolean predefined;
    @JsonProperty("RestartCountOnFailure")
    protected BigDecimal restartCountOnFailure;
    @JsonProperty("RestartIntervalOnFailure")
    protected BigDecimal restartIntervalOnFailure;

}
