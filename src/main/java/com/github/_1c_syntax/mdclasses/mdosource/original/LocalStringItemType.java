

package com.github._1c_syntax.mdclasses.mdosource.original;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalStringItemType {

  @JsonProperty("v8:lang")
  protected String lang;
  @JsonProperty("v8:content")
  protected String content;
}
