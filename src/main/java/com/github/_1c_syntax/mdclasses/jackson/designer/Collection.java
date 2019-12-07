

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;


@Getter
@Setter
public class Collection {

    protected List<CollectionColumn> column;
    protected List<CollectionItem> item;
    @JsonProperty("count")
    protected long count;
    @JsonProperty("lastId")
    protected long lastId;
    @JsonProperty("ids")
    protected List<Long> ids;
    @JsonProperty("fullChanged")
    protected Boolean fullChanged;
    @JsonProperty("firstAddedLine")
    protected BigInteger firstAddedLine;


}
