package com.github._1c_syntax.mdclasses.mdo.form.attribute;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtInfo {
  public static final String UNKNOWN = "unknown";
  private String type = UNKNOWN;
}
