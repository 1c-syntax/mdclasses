

package com.github._1c_syntax.mdclasses.jackson.designer;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeChanges {

    protected TreeOperations operations;
    protected TreeItemChangesList items;
}
