package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.mdo.CommonModule;
import com.github._1c_syntax.mdclasses.metadata.Configuration;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationOriginTest {

  @Test
  void testBuilder() {

    File srcPath = new File("src/test/resources/metadata/original");
    Configuration configuration = Configuration.newBuilder(srcPath.toPath()).build();

    assertThat(configuration).isNotNull();

    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 13))).isEqualTo(0);
    assertThat(configuration.getModulesByType().size() > 0).isTrue();

    File file = new File("src/test/resources/metadata/original/Documents/ПоступлениеТоваровУслуг/Ext/ManagerModule.bsl");
    assertThat(configuration.getModuleType(Common.getAbsoluteUri(file))).isEqualTo(ModuleType.ManagerModule);

    file = new File("src/test/resources/metadata/original/CommonModules/ПростойОбщийМодуль/Ext/Module.bsl");
    assertThat(configuration.getModuleType(Common.getAbsoluteUri(file))).isEqualTo(ModuleType.CommonModule);

    CommonModule commonModule = (CommonModule) configuration.getChild(MDOType.COMMON_MODULE, "ПростойОбщийМодуль");
    assertThat(commonModule).isNotNull();
    assertThat(commonModule.getName()).isEqualTo("ПростойОбщийМодуль");

    assertThat(configuration.getChildren(MDOType.COMMON_MODULE)).hasSize(3);
  }

  @Test
  void testErrorBuild() {

    Path srcPath = Paths.get("src/test/resources/metadata");
    Configuration configuration = Configuration.newBuilder(srcPath).build();

    assertThat(configuration).isNotNull();
  }

}
