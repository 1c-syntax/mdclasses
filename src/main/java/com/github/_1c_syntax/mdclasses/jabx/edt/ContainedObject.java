

package com.github._1c_syntax.mdclasses.jabx.edt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ContainedObject {

  @JsonProperty("classId")
  protected String classId;
  @JsonProperty("objectId")
  protected String objectId;

}
