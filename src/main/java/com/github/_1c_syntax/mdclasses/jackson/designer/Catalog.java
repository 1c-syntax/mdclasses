

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Catalog
    extends MDObjectBase
{

    @JsonProperty("Properties")
    protected CatalogProperties properties;
    @JsonProperty("ChildObjects")
    protected CatalogChildObjects childObjects;

}
