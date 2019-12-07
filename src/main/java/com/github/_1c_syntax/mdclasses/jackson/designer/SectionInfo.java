
package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class SectionInfo {

    protected String text;
    protected String tooltip;
    protected Picture pic;
    protected BigDecimal picSizeCX;
    protected BigDecimal picSizeCY;
    @JsonProperty("id")
    protected String id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("helpTopic")
    protected String helpTopic;

}
