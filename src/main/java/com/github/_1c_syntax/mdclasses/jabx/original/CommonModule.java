

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommonModule
  extends MDObjectBase {

  @JsonProperty("Properties")
  protected CommonModuleProperties properties;

}
