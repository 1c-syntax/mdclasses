

package com.github._1c_syntax.mdclasses.jackson.designer;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientCMIStartUpDataType {

    protected List<HandlerInfo> defaultNPHandleInfo;
    protected List<FragmentCMI> toplevelSubsystems;
    protected List<URLsByNavigationPoints> urls;
    protected List<FragmentCMI> panels;
    protected List<CommansShortCutInfoVector> commandsWithShortCuts;

}
