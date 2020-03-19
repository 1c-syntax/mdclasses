package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.mdo.CommonModule;
import com.github._1c_syntax.mdclasses.metadata.Configuration;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.github._1c_syntax.utils.Absolute;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationEDTTest {
  @Test
  void testBuilder() {

    File srcPath = new File("src/test/resources/metadata/edt");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE_WITH_WARNINGS);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 10))).isEqualTo(0);
    assertThat(configuration.getModulesByType()).hasSize(34);

    File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ValueManagerModule.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.ValueManagerModule);

    file = new File("src/test/resources/metadata/edt/src/CommonModules/ПростойОбщийМодуль/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.CommonModule);

    file = new File("src/test/resources/metadata/edt/src/Catalogs/Справочник1/Forms/ФормаЭлемента/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.FormModule);

    file = new File("src/test/resources/metadata/edt/src/CommonForms/Форма/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.FormModule);

    CommonModule commonModule = (CommonModule) configuration.getChild(MDOType.COMMON_MODULE, "ПростойОбщийМодуль");
    assertThat(commonModule).isNotNull();
    assertThat(commonModule.getName()).isEqualTo("ПростойОбщийМодуль");

    assertThat(configuration.getChildren(MDOType.COMMON_MODULE)).hasSize(6);

  }

  @Test
  void testErrorBuild() {

    Path srcPath = Paths.get("src/test/resources/metadata");
    Configuration configuration = Configuration.create(srcPath);

    assertThat(configuration).isNotNull();

    File file = new File("src/test/resources/metadata/Module.os");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.Unknown);
  }

  @Test
  void testBuilderEn() {

    File srcPath = new File("src/test/resources/metadata/edt_en");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.ENGLISH);
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 14))).isEqualTo(0);
    assertThat(configuration.getModulesByType()).hasSize(2);

    File file = new File("src/test/resources/metadata/edt_en/src/CommonModules/CommonModule/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.CommonModule);

  }
}
