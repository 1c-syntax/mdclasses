

package com.github._1c_syntax.mdclasses.mdosource.edt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;

import java.util.List;

@Getter
public class MDObjectBase {

  protected String name;
  @JacksonXmlElementWrapper(useWrapping = false)
  protected List<SynonymType> synonym;
  protected String comment;
  @JsonProperty("uuid")
  protected String uuid;


}
