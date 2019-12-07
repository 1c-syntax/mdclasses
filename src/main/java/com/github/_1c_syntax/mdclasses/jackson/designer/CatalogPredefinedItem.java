

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CatalogPredefinedItem
        extends PredefinedItemBase {

    @JsonProperty("IsFolder")
    protected boolean isFolder;
    @JsonProperty("ChildItems")
    protected List<CatalogPredefinedItems> childItems;

}
