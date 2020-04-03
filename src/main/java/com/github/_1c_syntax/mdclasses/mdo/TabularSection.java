package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@JsonDeserialize(builder = TabularSection.TabularSectionBuilderImpl.class)
@SuperBuilder
public class TabularSection extends MDOAttribute {

  @Override
  public AttributeType getAttributeType() {
    return AttributeType.TABULAR_SECTION;
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class TabularSectionBuilderImpl extends TabularSection.TabularSectionBuilder<TabularSection, TabularSection.TabularSectionBuilderImpl> {
  }
}
