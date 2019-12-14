

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration
  extends MDObjectBase {

  @JsonProperty("Properties")
  protected ConfigurationProperties properties;
  @JsonProperty("ChildObjects")
  protected ConfigurationChildObjects childObjects;
  @JsonProperty("formatVersion")
  protected String formatVersion;


}
