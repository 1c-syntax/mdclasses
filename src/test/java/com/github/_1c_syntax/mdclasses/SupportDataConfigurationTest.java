package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.metadata.SupportDataConfiguration;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportDataConfigurationTest {

  private final File parentConfigurationsBin = new File("src/test/resources", "ParentConfigurations.bin");

  @Test
  public void testRead() {
    SupportDataConfiguration supportDataConfiguration = new SupportDataConfiguration(parentConfigurationsBin.toPath());
    assertThat(supportDataConfiguration.getSupportMap().size()).isNotZero();
  }

}
