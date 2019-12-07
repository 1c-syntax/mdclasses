

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MngLocale {

    @JsonProperty(required = true)
    protected LocaleBoolFormat boolFormat;
    @JsonProperty(required = true)
    protected LocaleNumericFormat numericFormat;
    @JsonProperty(required = true)
    protected LocaleDateFormat dateFormat;
    @JsonProperty(required = true)
    protected LocaleWeekInfo weekInfo;
    @JsonProperty("localeCode")
    protected String localeCode;
    @JsonProperty("displayName")
    protected String displayName;
    @JsonProperty("cdisplayName")
    protected String cdisplayName;
    @JsonProperty("localeName")
    protected String localeName;

}
