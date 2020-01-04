package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.mdo.CommonModule;
import com.github._1c_syntax.mdclasses.metadata.Configuration;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationEDTTest {
    @Test
    void testBuilder() {

        File srcPath = new File("src/test/resources/metadata/edt");
        Configuration configuration = Configuration.newBuilder(srcPath.toPath()).build();

        assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
        assertThat(configuration.getScriptVariant() == ScriptVariant.RUSSIAN).isTrue();
        assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 10))).isEqualTo(0);
        assertThat(configuration.getModulesByType().size() > 0).isTrue();

        File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ManagerModule.bsl");
        assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.ManagerModule);

        file = new File("src/test/resources/metadata/edt/src/CommonModules/ПростойОбщийМодуль/Module.bsl");
        assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.CommonModule);

        CommonModule commonModule = (CommonModule) configuration.getChild(MDOType.COMMON_MODULE, "ПростойОбщийМодуль");
        assertThat(commonModule).isNotNull();
        assertThat(commonModule.getName()).isEqualTo("ПростойОбщийМодуль");


    }

    @Test
    void testErrorBuild() {

        Path srcPath = Paths.get("src/test/resources/metadata");
        Configuration configuration = Configuration.newBuilder(srcPath).build();

        assertThat(configuration).isNotNull();

        File file = new File("src/test/resources/metadata/Module.os");
        assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.Unknown);
    }

    @Test
    void testBuilderEn() {

        File srcPath = new File("src/test/resources/metadata/edt_en");
        Configuration configuration = Configuration.newBuilder(srcPath.toPath()).build();

        assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
        assertThat(configuration.getScriptVariant() == ScriptVariant.ENGLISH).isTrue();
        assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 14))).isEqualTo(0);
        assertThat(configuration.getModulesByType().size() > 0).isTrue();

        File file = new File("src/test/resources/metadata/edt_en/src/CommonModules/CommonModule/Module.bsl");
        assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.CommonModule);

    }

}
