package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = BusinessProcess.BusinessProcessBuilderImpl.class)
@SuperBuilder
public class BusinessProcess extends MDObjectBase {

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class BusinessProcessBuilderImpl extends BusinessProcess.BusinessProcessBuilder<BusinessProcess, BusinessProcess.BusinessProcessBuilderImpl> {

    @JsonProperty("Properties")
    @Override
    public BusinessProcess.BusinessProcessBuilderImpl properties(Map<String, Object> properties) {
      super.properties(properties);
      return this.self();
    }
  }
}
