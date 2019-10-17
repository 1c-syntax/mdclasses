package org.github._1c_syntax.mdclasses;

import org.github._1c_syntax.mdclasses.metadata.ConfigurationBuilder;
import org.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import org.github._1c_syntax.mdclasses.metadata.configurations.AbstractConfiguration;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationEDTTest {
  @Test
  void testBuilder() {

    File srcPath = new File("src/test/resources/metadata/edt");
    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(srcPath.toPath());
    AbstractConfiguration configuration = configurationBuilder.build();

    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
    assertThat(configuration.getScriptVariant() == ScriptVariant.RUSSIAN).isTrue();
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 10))).isEqualTo(0);
    assertThat(configuration.getModulesByType().size() > 0).isTrue();

    File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ManagerModule.bsl");
    assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.ManagerModule);

    file = new File("src/test/resources/metadata/edt/src/CommonModules/ПростойОбщийМодуль/Module.bsl");
    assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.CommonModule);

  }

  @Test
  void testErrorBuild() {

    Path srcPath = Paths.get("src/test/resources/metadata");
    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(srcPath);
    AbstractConfiguration configuration = configurationBuilder.build();

    assertThat(configuration).isNotNull();
  }

}
