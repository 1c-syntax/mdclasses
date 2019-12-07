

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ApplicationInfo {

    @JsonProperty(required = true)
    protected String infoBaseInstanceID;
    @JsonProperty(required = true)
    protected String infoBaseAlias;
    protected LocaleInfo locale;
    @JsonProperty(required = true)
    protected LangSettings langs;
    @JsonProperty(required = true)
    protected String config;
    @JsonProperty(required = true)
    protected String types;
    @JsonProperty(required = true)
    protected String user;
    @JsonProperty(required = true)
    protected BigDecimal rolesID;
    @JsonProperty(required = true)
    protected String seance;
    @JsonProperty(required = true)
    protected BigDecimal seanceNo;
    @JsonProperty(required = true)
    protected BigDecimal userRunMode;
    @JsonProperty(required = true)
    protected BigDecimal configRunMode;
    protected SplashInfo splash;
    protected MngLocale fullLocale;
    protected BigDecimal compatibilityMode;
    protected BigDecimal compatibilityModeLive;
    protected boolean userSeparatorsEmpty;
    protected boolean training;
    @JsonProperty(required = true)
    protected BigDecimal modalityUseMode;
    @JsonProperty(required = true)
    protected BigDecimal rigthClientsStart;
    @JsonProperty(required = true)
    protected BigDecimal syncCallsUseMode;
    @JsonProperty(required = true)
    protected String assemblyVersion;
    protected boolean interactiveSecurity;
    @JsonProperty(required = true)
    protected BigDecimal defaultPaperWidth;
    @JsonProperty(required = true)
    protected BigDecimal defaultPaperHeight;
    @JsonProperty("confNotSaved")
    protected Boolean confNotSaved;
    @JsonProperty("noFontsOnServer")
    protected Boolean noFontsOnServer;
    @JsonProperty("noFontConfigOnServer")
    protected Boolean noFontConfigOnServer;

}
