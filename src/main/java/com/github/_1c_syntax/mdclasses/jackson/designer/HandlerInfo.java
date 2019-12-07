

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class HandlerInfo {

    @JsonProperty(required = true)
    protected String formId;
    protected String formName;
    protected List<ModuleInfo> moduleInfo;
    protected String name;
    protected String nameRu;
    protected boolean listFilter;
    protected String param;
    @JsonProperty(required = true)
    protected CommandParameterUseMode usageMode;
    protected boolean modifiesData;
    protected boolean createGroupCommand;
    @JsonProperty("theme")
    protected Boolean theme;
    @JsonProperty("subTheme")
    protected Boolean subTheme;
    @JsonProperty("standard")
    protected Boolean standard;
    @JsonProperty("defGroupCat")
    protected GroupCategory defGroupCat;

}
