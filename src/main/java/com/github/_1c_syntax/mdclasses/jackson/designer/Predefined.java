


package com.github._1c_syntax.mdclasses.jackson.designer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Predefined {

    protected GroupLogForm contextMenu;
    protected ItemRef contextMenuRef;
    protected GroupLogForm autoCommandBar;
    protected ItemRef autoCommandBarRef;
    protected Decoration extTooltip;
    protected ItemRef extTooltipRef;
    protected Addition searchStringRepresentation;
    protected ItemRef searchStringRepresentationRef;
    protected Addition viewStatusRepresentation;
    protected ItemRef viewStatusRepresentationRef;
    protected Addition searchControl;
    protected ItemRef searchControlRef;
    protected List<Predefined> predefined;
    protected List<FieldLogForm> field;
    protected List<GroupLogForm> group;
    protected List<TableLogForm> table;
    protected List<Button> button;
    protected List<Decoration> decoration;
    protected List<Addition> addition;

}
