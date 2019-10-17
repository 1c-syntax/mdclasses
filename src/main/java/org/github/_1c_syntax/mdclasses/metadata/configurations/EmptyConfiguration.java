package org.github._1c_syntax.mdclasses.metadata.configurations;

import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;

import java.io.File;
import java.nio.file.Path;

public class EmptyConfiguration extends AbstractConfiguration {

  public EmptyConfiguration() {
    this(ConfigurationSource.EMPTY, null);
  }

  public EmptyConfiguration(ConfigurationSource configurationSource, Path rootPath) {
    super(configurationSource, rootPath);
  }

  @Override
  public void initialize(File xml) {

  }
}
