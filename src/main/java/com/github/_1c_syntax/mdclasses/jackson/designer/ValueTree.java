

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.sun.tools.javac.util.List;
import lombok.Getter;

@Getter
public class ValueTree {

    protected List<ValueTreeColumn> column;
    protected List<ValueTreeRow> row;

}
