

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.sun.tools.javac.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ValueListType {

    protected TypeDescription valueType;
    protected ValueListType availableValues;
    protected Long lastId;
    protected List<ValueListItemType> item;

}
