package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = CommonForm.CommonFormBuilderImpl.class)
@SuperBuilder
public class CommonForm extends MDObjectBase {

  public MDOType getType() {
    return MDOType.COMMON_FORM;
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class CommonFormBuilderImpl extends CommonForm.CommonFormBuilder<CommonForm, CommonForm.CommonFormBuilderImpl> {
  }
}
