

package com.github._1c_syntax.mdclasses.jackson.designer;


import lombok.Getter;

import java.util.List;

@Getter
public class CollectionOperations {

    protected List<CollectionOperationAdd> add;
    protected List<CollectionOperationAddRange> addRange;
    protected List<CollectionOperationRemove> remove;
    protected List<CollectionOperationRemoveRange> removeRange;
    protected List<CollectionOperationSwap> swap;
    protected List<CollectionOperationMove> move;
    protected List<CollectionOperationClear> clear;


}
