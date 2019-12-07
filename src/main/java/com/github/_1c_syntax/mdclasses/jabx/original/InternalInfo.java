

package com.github._1c_syntax.mdclasses.jabx.original;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InternalInfo {

  @JsonProperty("ThisNode")
  protected String thisNode;
  @JsonProperty("GeneratedType")
  protected List<GeneratedType> generatedType;
  @JsonProperty("ContainedObject")
  protected List<ContainedObject> containedObject;

}
