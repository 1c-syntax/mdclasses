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
@JsonDeserialize(builder = FilterCriterion.FilterCriterionBuilderImpl.class)
@SuperBuilder
public class FilterCriterion extends MDObjectBase {

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class FilterCriterionBuilderImpl extends FilterCriterion.FilterCriterionBuilder<FilterCriterion, FilterCriterion.FilterCriterionBuilderImpl> {

    @JsonProperty("Properties")
    @Override
    public FilterCriterion.FilterCriterionBuilderImpl properties(Map<String, Object> properties) {
      super.properties(properties);
      return this.self();
    }
  }
}
