

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContainedObject {

  @JsonProperty("ClassId")
  protected String classId;
  @JsonProperty("ObjectId")
  protected String objectId;
}
