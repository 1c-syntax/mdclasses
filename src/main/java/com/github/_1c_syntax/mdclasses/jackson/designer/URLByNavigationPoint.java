

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class URLByNavigationPoint {

    @JsonProperty(required = true)
    protected NavigationPoint np;
    @JsonProperty(required = true)
    protected String url;
    @JsonProperty(required = true)
    protected String presentation;
    @JsonProperty(required = true)
    protected String shortPresentation;


}
