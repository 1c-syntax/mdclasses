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
@JsonDeserialize(builder = Subsystem.SubsystemBuilderImpl.class)
@SuperBuilder
public class Subsystem extends MDObjectBase {

  public MDOType getType() {
    return MDOType.SUBSYSTEM;
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class SubsystemBuilderImpl extends Subsystem.SubsystemBuilder<Subsystem, Subsystem.SubsystemBuilderImpl> {
  }
}
