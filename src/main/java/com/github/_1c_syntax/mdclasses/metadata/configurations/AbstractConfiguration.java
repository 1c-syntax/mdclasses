package com.github._1c_syntax.mdclasses.metadata.configurations;

import lombok.Data;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class AbstractConfiguration {

  protected final ConfigurationSource configurationSource;
  protected CompatibilityMode compatibilityMode;
  protected ScriptVariant scriptVariant;
  protected Map<URI, ModuleType> modulesByType = new HashMap<>();

  protected final Path rootPath;

  public AbstractConfiguration(ConfigurationSource configurationSource, Path rootPath) {
    this.configurationSource = configurationSource;
    this.rootPath = rootPath;
  }

  public abstract void initialize(File xml);

  public ModuleType getModuleType(URI uri) {
    return modulesByType.get(uri);
  }

}
