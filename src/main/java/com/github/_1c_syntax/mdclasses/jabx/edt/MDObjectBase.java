

package com.github._1c_syntax.mdclasses.jabx.edt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MDObjectBase {

  protected String name;
  // fixme
//  protected List<SynonymType> synonym;
  protected String comment;
  @JsonProperty("uuid")
  protected String uuid;


}
