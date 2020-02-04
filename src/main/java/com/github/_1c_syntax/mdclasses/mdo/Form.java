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
@JsonDeserialize(builder = Form.FormBuilderImpl.class)
@SuperBuilder
public class Form extends MDObjectBase {

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class FormBuilderImpl extends Form.FormBuilder<Form, Form.FormBuilderImpl> {

    @JsonProperty("Properties")
    @Override
    public Form.FormBuilderImpl properties(Map<String, Object> properties) {
      super.properties(properties);
      return this.self();
    }
  }
}
