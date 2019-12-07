

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class UserMessage {

    @JsonProperty("Text")
    protected String text;
    @JsonProperty("Ref")
    protected String ref;
    protected Object pic;
    @JsonProperty("MessageMarker")
    protected BigDecimal messageMarker;
    @JsonProperty("DataPath")
    protected String dataPath;
    @JsonProperty("FormAttributePath")
    protected String formAttributePath;
    @JsonProperty("FormID")
    protected String formID;
    @JsonProperty("notification")
    protected Boolean notification;
    @JsonProperty("caption")
    protected String caption;
    @JsonProperty("url")
    protected String url;

}
