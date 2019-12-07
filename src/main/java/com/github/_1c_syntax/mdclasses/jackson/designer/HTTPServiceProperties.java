

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class HTTPServiceProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("RootURL")
    protected String rootURL;
    @JsonProperty("Module")
    protected String module;
    @JsonProperty("ReuseSessions")
    protected SessionReuseMode reuseSessions;
    @JsonProperty("SessionMaxAge")
    protected BigDecimal sessionMaxAge;

}
