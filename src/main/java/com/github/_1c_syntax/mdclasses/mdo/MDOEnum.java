package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = MDOEnum.MDOEnumBuilderImpl.class)
@JsonRootName(value = "Enum")
@SuperBuilder
public class MDOEnum extends MDObjectBase {

  public MDOType getType() {
    return MDOType.ENUM;
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class MDOEnumBuilderImpl extends MDOEnum.MDOEnumBuilder<MDOEnum, MDOEnum.MDOEnumBuilderImpl> {
  }
}
