

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Changes {

    protected StructureChanges structure;
    protected CollectionChanges collection;
    protected StructureAndCollectionChanges structureAndCollection;
    protected TreeChanges tree;
    @JsonProperty("sinDesc")
    protected Long sinDesc;
    @JsonProperty("sin")
    protected Long sin;
    @JsonProperty("seq")
    protected Long seq;


}
