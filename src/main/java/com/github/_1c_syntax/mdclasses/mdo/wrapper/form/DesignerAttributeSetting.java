package com.github._1c_syntax.mdclasses.mdo.wrapper.form;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DesignerAttributeSetting {
  @XStreamAlias("xsi:type")
  private String type;
  @XStreamAlias("MainTable")
  private String mainTable = "";
  @XStreamAlias("DynamicDataRead")
  private boolean dynamicDataRead = true;
  @XStreamAlias("ManualQuery")
  private boolean customQuery = false;
  @XStreamAlias("QueryText")
  private String queryText = "";
}
