

package com.github._1c_syntax.mdclasses.jackson.designer;


import lombok.Getter;

import java.util.List;


@Getter
public class TreeOperations {

    protected List<TreeOperationAdd> add;
    protected List<TreeOperationAddRange> addRange;
    protected List<TreeOperationRemove> remove;
    protected List<TreeOperationRemoveRange> removeRange;
    protected List<TreeOperationSwap> swap;
    protected List<TreeOperationMove> move;
    protected List<TreeOperationClear> clear;

}
