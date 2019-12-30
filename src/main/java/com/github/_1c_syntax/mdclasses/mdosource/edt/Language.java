

package com.github._1c_syntax.mdclasses.mdosource.edt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Language extends MDObjectBase {
  protected String name;
  protected String languageCode;

}
