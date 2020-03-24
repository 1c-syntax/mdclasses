package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = CommonAttribute.CommonAttributeBuilderImpl.class)
@SuperBuilder
public class CommonAttribute extends MDObjectBase {

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class CommonAttributeBuilderImpl extends CommonAttribute.CommonAttributeBuilder<CommonAttribute, CommonAttribute.CommonAttributeBuilderImpl> {
  }
}
