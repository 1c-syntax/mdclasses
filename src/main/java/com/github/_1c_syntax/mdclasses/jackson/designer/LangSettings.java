

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LangSettings {

    @JsonProperty(required = true)
    protected LanguageInfo lang;
    @JsonProperty("current")
    protected String current;
    @JsonProperty("default")
    protected String _default;
    @JsonProperty("alias")
    protected Alias alias;

}
