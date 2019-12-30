

package com.github._1c_syntax.mdclasses.mdosource.original;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MDObjectBase {

  //  fixme
//  @JsonProperty("InternalInfo")
//  protected InternalInfo internalInfo;
  @JsonProperty("uuid")
  protected String uuid;

}
