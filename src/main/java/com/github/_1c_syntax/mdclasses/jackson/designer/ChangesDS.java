

package com.github._1c_syntax.mdclasses.jackson.designer;


import lombok.Getter;

import java.util.List;


@Getter
public class ChangesDS {

    protected SpreadsheetDocument commonPart;
    protected SpreadsheetDocument fullDocument;
    protected SpreadsheetDocument fullDocumentWithoutContent;
    protected List<ChangedRowItem> row;
    protected List<GroupDS> vg;

}
