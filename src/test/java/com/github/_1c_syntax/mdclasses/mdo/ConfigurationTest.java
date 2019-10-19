package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.classes.Configuration;
import com.github._1c_syntax.mdclasses.mdo.core.CompatibilityMode;
import com.github._1c_syntax.mdclasses.mdo.core.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import com.github._1c_syntax.mdclasses.mdo.core.ScriptVariant;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationTest {

    @Test
    void testEDT() {
        File srcPath = new File("src/test/resources/metadata/edt");
        Configuration configuration = new Configuration(srcPath.toPath());
        assertThat(configuration.getMdoType()).isEqualTo(MDOType.CONFIGURATION);
        assertThat(configuration.getUuid()).isEqualTo("46c7c1d0-b04d-4295-9b04-ae3207c18d29");
        assertThat(configuration.getName()).isEqualTo("Конфигурация");
        assertThat(configuration.getComment()).isNull();
        assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
        assertThat(configuration.getMdoPath().toAbsolutePath()).isEqualTo(Paths.get("src/test/resources/metadata/edt/src/Configuration/Configuration.mdo").toAbsolutePath());
        assertThat(configuration.getRootPath()).isEqualTo(srcPath.toPath());
        assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode("8.3.10"))).isEqualTo(0);
        assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);


//        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(srcPath.toPath());
//        AbstractConfiguration configuration = configurationBuilder.build();
//
//        assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
//        assertThat(configuration.getScriptVariant() == ScriptVariant.RUSSIAN).isTrue();
//        assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 10))).isEqualTo(0);
//        assertThat(configuration.getModulesByType().size() > 0).isTrue();
//
//        File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ManagerModule.bsl");
//        assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.ManagerModule);
//
//        file = new File("src/test/resources/metadata/edt/src/CommonModules/ПростойОбщийМодуль/Module.bsl");
//        assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.CommonModule);
    }

    @Test
    void testOriginal() {
        File srcPath = new File("src/test/resources/metadata/original");
        Configuration configuration = new Configuration(srcPath.toPath());
        assertThat(configuration.getMdoType()).isEqualTo(MDOType.CONFIGURATION);
        assertThat(configuration.getUuid()).isEqualTo("46c7c1d0-b04d-4295-9b04-ae3207c18d29");
        assertThat(configuration.getName()).isEqualTo("Конфигурация");
        assertThat(configuration.getComment()).isEmpty();
        assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
        assertThat(configuration.getMdoPath().toAbsolutePath()).isEqualTo(Paths.get("src/test/resources/metadata/original/Configuration.xml").toAbsolutePath());
        assertThat(configuration.getRootPath()).isEqualTo(srcPath.toPath());
        assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode("8.3.10"))).isEqualTo(0);
        assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);


//        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(srcPath.toPath());
//        AbstractConfiguration configuration = configurationBuilder.build();
//
//        assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
//        assertThat(configuration.getScriptVariant() == ScriptVariant.RUSSIAN).isTrue();
//        assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 10))).isEqualTo(0);
//        assertThat(configuration.getModulesByType().size() > 0).isTrue();
//
//        File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ManagerModule.bsl");
//        assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.ManagerModule);
//
//        file = new File("src/test/resources/metadata/edt/src/CommonModules/ПростойОбщийМодуль/Module.bsl");
//        assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.CommonModule);
    }
}
