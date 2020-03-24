package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github._1c_syntax.mdclasses.metadata.utils.MapExtension.getOrEmptyString;

@Data
@NonFinal
@JsonDeserialize(builder = MDObjectBase.MDObjectBaseBuilderImpl.class)
@SuperBuilder
@EqualsAndHashCode(exclude = {"mdoURI", "modulesByType", "forms"})
@ToString(exclude = {"mdoURI", "modulesByType", "forms"})
public class MDObjectBase {

  protected final String uuid;
  protected final String name;
  @Builder.Default
  protected String comment = "";

  protected URI mdoURI;
  protected Map<URI, ModuleType> modulesByType;
  protected List<Form> forms;

  public abstract static class MDObjectBaseBuilder
    <C extends MDObjectBase, B extends MDObjectBase.MDObjectBaseBuilder<C, B>> {

    // Re-define generated method to implement basic read of `properties` collection.
    // It's defined here (but not in Impl class) to make it callable from other SuperBuilders
    @JsonProperty("Properties")
    protected MDObjectBaseBuilder<C, B> properties(Map<String, Object> properties) {
      name(getOrEmptyString(properties, "Name"));
      comment(getOrEmptyString(properties, "Comment"));

      return this.self();
    }

    @JsonProperty("forms")
    protected MDObjectBaseBuilder<C, B> forms(Map<String, Object> properties) {

      if (forms == null) {
        forms = new ArrayList<>();
      }
      forms.add(
        Form.builder()
          .comment(getOrEmptyString(properties, "comment"))
          .uuid(getOrEmptyString(properties, "uuid"))
          .name(getOrEmptyString(properties, "name"))
          .build());

      return this.self();
    }

  }

  public void setMdoURI(URI uri) {
    if (this.mdoURI == null) {
      this.mdoURI = uri;
    }
  }

  public void setModulesByType(Map<URI, ModuleType> modulesByType) {
    if (this.modulesByType == null) {
      this.modulesByType = modulesByType;
      // todo реализовать заполнение для форм
    }
  }

  public void setForms(List<Form> forms) {
    if (this.forms == null) {
      this.forms = forms;
    }
  }

  // Mark builder implementation as Jackson JSON builder with methods w/o `with-` in their names.
  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class MDObjectBaseBuilderImpl
    extends MDObjectBase.MDObjectBaseBuilder<MDObjectBase, MDObjectBase.MDObjectBaseBuilderImpl> {
  }
}
