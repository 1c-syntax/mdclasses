package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.classes.Configuration;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import com.github._1c_syntax.mdclasses.metadata.ConfigurationBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.configurations.AbstractConfiguration;
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
        assertThat(configuration.getUuid()).isEqualTo("");
        assertThat(configuration.getName()).isEqualTo("");
        assertThat(configuration.getComment()).isEqualTo("");
        assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
        assertThat(configuration.getMdoPath()).isEqualTo(Paths.get("src/test/resources/metadata/edt/src/Configuration/Configuration.mdo"));
        assertThat(configuration.getRootPath()).isEqualTo(srcPath.toPath());




//        assertThat(configuration.getScriptVariant() == ScriptVariant.RUSSIAN).isTrue();

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
