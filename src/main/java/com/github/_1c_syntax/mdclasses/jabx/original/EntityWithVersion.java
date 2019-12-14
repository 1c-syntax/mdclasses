

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EntityWithVersion {

  @JsonProperty("version")
  protected String version;
}
