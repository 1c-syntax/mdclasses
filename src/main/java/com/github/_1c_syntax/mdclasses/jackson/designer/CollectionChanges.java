

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;


@Getter
@Setter
public class CollectionChanges {

    protected CollectionOperations operations;
    protected CollectionItemChangesList items;
    protected List<CollectionColumn> column;
    @JsonProperty("count")
    protected long count;
    @JsonProperty("lastId")
    protected long lastId;
    @JsonProperty("fullChanged")
    protected Boolean fullChanged;
    @JsonProperty("firstAddedLine")
    protected BigInteger firstAddedLine;
    @JsonProperty("dropOld")
    protected Boolean dropOld;

}
