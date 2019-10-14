package org.github._1c_syntax.mdclasses;

import org.github._1c_syntax.mdclasses.metadata.Configuration;
import org.github._1c_syntax.mdclasses.metadata.ConfigurationBuilder;
import org.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationEDTTest {
    @Test
    void testBuilder() {
        File srcPath = new File("src/test/resources/metadata/edt");
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(ConfigurationSource.EDT, srcPath.toPath());
        Configuration configuration = configurationBuilder.build();

        assertThat(configuration.getScriptVariant() == ScriptVariant.RUSSIAN).isTrue();
        assertThat(configuration.getConfigurationSource() == ConfigurationSource.EDT).isTrue();
        assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 10))).isEqualTo(0);
        assertThat(configuration.getModulesByType().size() > 0).isTrue();

        File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ManagerModule.bsl");
        assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.ManagerModule);

        file = new File("src/test/resources/metadata/edt/src/CommonModules/ПростойОбщийМодуль/Module.bsl");
        assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.CommonModule);
    }
}
