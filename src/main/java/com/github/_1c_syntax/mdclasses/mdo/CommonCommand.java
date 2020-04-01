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
@JsonDeserialize(builder = CommonCommand.CommonCommandBuilderImpl.class)
@SuperBuilder
public class CommonCommand extends MDObjectBase {

  public MDOType getType() {
    return MDOType.COMMON_COMMAND;
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class CommonCommandBuilderImpl extends CommonCommand.CommonCommandBuilder<CommonCommand, CommonCommand.CommonCommandBuilderImpl> {
  }
}
