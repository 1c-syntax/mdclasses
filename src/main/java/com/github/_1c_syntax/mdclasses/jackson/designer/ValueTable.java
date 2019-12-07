

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.sun.tools.javac.util.List;
import lombok.Getter;

@Getter
public class ValueTable {

    protected List<ValueTableColumn> column;
    protected List<ValueTableIndex> index;
    protected List<ValueTableRow> row;

}
