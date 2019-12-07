

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalStringType {

  @JsonProperty("item")
  protected List<LocalStringItemType> item;


}
