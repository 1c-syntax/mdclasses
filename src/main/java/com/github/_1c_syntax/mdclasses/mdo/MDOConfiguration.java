package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.Map;

import static com.github._1c_syntax.mdclasses.metadata.utils.MapExtension.getOrEmptyString;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonRootName(value = "Configuration")
@JsonDeserialize(builder = MDOConfiguration.MDOConfigurationBuilderImpl.class)
@SuperBuilder
public class MDOConfiguration extends MDObjectBase {

  @Builder.Default
  protected ScriptVariant scriptVariant = ScriptVariant.ENGLISH;
  protected CompatibilityMode compatibilityMode;
  protected CompatibilityMode configurationExtensionCompatibilityMode;

  protected String defaultRunMode;
  protected String defaultLanguage;
  protected String dataLockControlMode;
  protected String objectAutonumerationMode;
  protected String modalityUseMode;
  protected String synchronousPlatformExtensionAndAddInCallUseMode;

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class MDOConfigurationBuilderImpl extends MDOConfiguration.MDOConfigurationBuilder<MDOConfiguration, MDOConfiguration.MDOConfigurationBuilderImpl> {

    @JsonProperty("Properties")
    @Override
    public MDOConfigurationBuilderImpl properties(Map<String, Object> properties) {
      super.properties(properties);

      scriptVariant(ScriptVariant.fromValue((String) properties.getOrDefault("ScriptVariant", ScriptVariant.ENGLISH.value())));
      compatibilityMode(new CompatibilityMode(getOrEmptyString(properties, "CompatibilityMode")));
      configurationExtensionCompatibilityMode(new CompatibilityMode(getOrEmptyString(properties, "ConfigurationExtensionCompatibilityMode")));
      defaultRunMode(getOrEmptyString(properties, "DefaultRunMode"));
      defaultLanguage(getOrEmptyString(properties, "DefaultLanguage"));
      dataLockControlMode(getOrEmptyString(properties, "DataLockControlMode"));
      objectAutonumerationMode(getOrEmptyString(properties, "ObjectAutonumerationMode"));
      modalityUseMode(getOrEmptyString(properties, "ModalityUseMode"));
      synchronousPlatformExtensionAndAddInCallUseMode(getOrEmptyString(properties, "SynchronousPlatformExtensionAndAddInCallUseMode"));

      return this.self();
    }
  }

}
