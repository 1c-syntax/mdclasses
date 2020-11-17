package com.github._1c_syntax.mdclasses.mdo.form.attribute;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class DynamicListExtInfo extends ExtInfo {
  // TODO: MDORef
  private String mainTable = "";
  private boolean dynamicDataRead = true;
  private boolean autoFillAvailableFields = true;
  private boolean autoSaveUserSettings = true;
  private boolean getInvisibleFieldPresentations = true;
  private boolean customQuery = false;
  private String queryText = "";
}
