

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalStringItemType {

  @JsonProperty("lang")
  protected String lang;
  @JsonProperty("content")
  protected String content;
}
