package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.metadata.configurations.AbstractConfiguration;
import com.github._1c_syntax.mdclasses.metadata.ConfigurationBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationOriginTest {

  @Test
  void testBuilder() {

    File srcPath = new File("src/test/resources/metadata/original");
    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(srcPath.toPath());
    AbstractConfiguration configuration = configurationBuilder.build();

    assertThat(configuration).isNotNull();

    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
    assertThat(configuration.getScriptVariant() == ScriptVariant.RUSSIAN).isTrue();
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 10))).isEqualTo(0);
    assertThat(configuration.getModulesByType().size() > 0).isTrue();

    File file = new File("src/test/resources/metadata/original/Documents/ПоступлениеТоваровУслуг/Ext/ManagerModule.bsl");
    assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.ManagerModule);

    file = new File("src/test/resources/metadata/original/CommonModules/ПростойОбщийМодуль/Ext/Module.bsl");
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
