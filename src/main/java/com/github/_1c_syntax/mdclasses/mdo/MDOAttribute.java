package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@JsonDeserialize(builder = MDOAttribute.MDOAttributeBuilderImpl.class)
@SuperBuilder
public class MDOAttribute extends MDObjectBase {

  MDObjectBase parent;

  @Override
  public MDOType getType() {
    return MDOType.ATTRIBUTE;
  }

  public AttributeType getAttributeType() {
    return null;
  }

  @Override
  public void computeMdoRef() {
    this.mdoRef = getAttributeType().getClassName() + "." + getName();
    if (parent != null) {
      this.mdoRef = this.parent.getMdoRef() + "." + this.mdoRef;
    }
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class MDOAttributeBuilderImpl extends MDOAttribute.MDOAttributeBuilder<MDOAttribute, MDOAttribute.MDOAttributeBuilderImpl> {
  }
}
