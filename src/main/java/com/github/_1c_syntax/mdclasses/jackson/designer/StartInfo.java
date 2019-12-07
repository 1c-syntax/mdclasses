

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class StartInfo {

    @JsonProperty(required = true)
    protected NavigationPoint navStartPoint;
    protected List<SubsystemVerInfo> subsystemVer;
    @JsonProperty(required = true)
    protected DesktopInfo desktop;
    @JsonProperty(required = true)
    protected StyleEntriesInfo styleEntries;
    @JsonProperty(required = true)
    protected String fullConfName;
    protected List<String> extName;
    protected List<String> extIssue;
    @JsonProperty(required = true)
    protected String compress;
    protected InterfaceLayouter panels;
    protected LFEFChoiceHistory choiceHistory;
    protected UserFavorites favs;
    @JsonProperty(required = true)
    protected String userName;
    @JsonProperty(required = true)
    protected String fullUserName;
    @JsonProperty(required = true)
    protected String externalResourceMode;
    @JsonProperty(required = true)
    protected BigDecimal deploymentType;
    @JsonProperty("mainWindowMode")
    protected MainWindowMode mainWindowMode;
    @JsonProperty("infoBaseInstanceID")
    protected String infoBaseInstanceID;
    @JsonProperty("confName")
    protected String confName;
    @JsonProperty("subsystemsPanelVer")
    protected String subsystemsPanelVer;
    @JsonProperty("foVer")
    protected String foVer;
    @JsonProperty("mainWindowOptionsVer")
    protected String mainWindowOptionsVer;
    @JsonProperty("desktopSpliterPosVer")
    protected String desktopSpliterPosVer;
    @JsonProperty("notifyWindowOptionsVer")
    protected String notifyWindowOptionsVer;
    @JsonProperty("stateWindowOptionsVer")
    protected String stateWindowOptionsVer;
    @JsonProperty("commConfName")
    protected String commConfName;
    @JsonProperty("commConfURL")
    protected String commConfURL;
    @JsonProperty("commProvName")
    protected String commProvName;
    @JsonProperty("commProvURL")
    protected String commProvURL;
    @JsonProperty("desktopFormsWindowSettingsVerHash")
    protected String desktopFormsWindowSettingsVerHash;
    @JsonProperty("enableOutput")
    protected Boolean enableOutput;
    @JsonProperty("allowAllFunctionsMode")
    protected Boolean allowAllFunctionsMode;
    @JsonProperty("allowSaveUserData")
    protected Boolean allowSaveUserData;
    @JsonProperty("clientSettings")
    protected BigDecimal clientSettings;
    @JsonProperty("debugServerHTTP")
    protected String debugServerHTTP;
    @JsonProperty("allowFullTextSearch")
    protected Boolean allowFullTextSearch;
    @JsonProperty("taxi")
    protected Boolean taxi;
    @JsonProperty("sdi")
    protected Boolean sdi;
    @JsonProperty("enableChoiceInterface")
    protected Boolean enableChoiceInterface;
    @JsonProperty("newInterfaceByDefault")
    protected Boolean newInterfaceByDefault;
    @JsonProperty("largeFont")
    protected Boolean largeFont;


}
