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
@JsonDeserialize(builder = FilterCriterion.FilterCriterionBuilderImpl.class)
@SuperBuilder
public class FilterCriterion extends MDObjectBase {

  public MDOType getType() {
    return MDOType.FILTER_CRITERION;
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class FilterCriterionBuilderImpl extends FilterCriterion.FilterCriterionBuilder<FilterCriterion, FilterCriterion.FilterCriterionBuilderImpl> {
  }
}
