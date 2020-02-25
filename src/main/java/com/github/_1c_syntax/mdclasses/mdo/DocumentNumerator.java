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
@JsonDeserialize(builder = DocumentNumerator.DocumentNumeratorBuilderImpl.class)
@SuperBuilder
public class DocumentNumerator extends MDObjectBase {

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class DocumentNumeratorBuilderImpl extends DocumentNumerator.DocumentNumeratorBuilder<DocumentNumerator, DocumentNumerator.DocumentNumeratorBuilderImpl> {

    @JsonProperty("Properties")
    @Override
    public DocumentNumerator.DocumentNumeratorBuilderImpl properties(Map<String, Object> properties) {
      super.properties(properties);
      return this.self();
    }
  }
}
