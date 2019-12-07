

package com.github._1c_syntax.mdclasses.jackson.designer;


import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class ViewSettings {

    protected BigDecimal currentColumn;
    protected BigDecimal currentRow;
    protected BigDecimal scale;
    protected Boolean showGrid;
    protected Boolean showHeaders;
    protected Boolean showNames;
    protected Boolean showGroups;
    protected Boolean showGroupsText;
    protected Boolean showPageBreaks;
    protected Boolean blackAndWhite;
    protected BigDecimal fixedColumn;
    protected BigDecimal fixedColumnRow;
    protected BigDecimal fixedRow;
    protected BigDecimal fixationPointColumn;
    protected BigDecimal fixationPointRow;
    protected BigDecimal topFixationPointColumn;
    protected BigDecimal topFixationPointRow;
    protected BigDecimal screenBeginPointColumn;
    protected BigDecimal screenBeginPointRow;
    protected Boolean showNamedRowsAndColumns;
    protected Boolean showNamedRowsAndColumnsText;
    protected String currentLanguage;
    protected Boolean showComments;
    protected List<Area> selection;
    protected List<BigDecimal> selectedDrawing;

}
