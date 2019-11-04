package com.github._1c_syntax.mdclasses.mdo.builder;


import com.github._1c_syntax.mdclasses.mdo.classes.Configuration;
import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDO;
import com.github._1c_syntax.mdclasses.mdo.core.CompatibilityMode;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import com.github._1c_syntax.mdclasses.mdo.core.ScriptVariant;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationBuilderTest {
    @Test
    void testBuilder() {

        File srcPath = new File("src/test/resources/metadata/edt");
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(srcPath.toPath());
        AbstractMDO configuration = configurationBuilder.getConfiguration();
        assertThat(configuration.getMdoType()).isEqualTo(MDOType.CONFIGURATION);
        assertThat(configuration.getComment()).isEqualTo("");
        assertThat(configuration.getName()).isEqualTo("Конфигурация");
        assertThat(configuration.getUuid()).isEqualTo("46c7c1d0-b04d-4295-9b04-ae3207c18d29");
        assertThat(CompatibilityMode.compareTo(
                ((Configuration) configuration).getCompatibilityMode(),
                new CompatibilityMode("8.3.10"))).isEqualTo(0);
        assertThat(((Configuration) configuration).getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);

        // общие модули
        AbstractMDO commonModule = configurationBuilder.getChildMDO(MDOType.COMMON_MODULE, "ОбщийМодульПовтИспСеанс");
        assertThat(commonModule).isNotNull();

    }
}