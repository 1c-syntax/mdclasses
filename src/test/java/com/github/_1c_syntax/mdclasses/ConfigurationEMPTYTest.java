package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.metadata.Configuration;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationEMPTYTest {
    @Test
    void testBuilder() {

        Configuration configuration = Configuration.newBuilder(null).build();

        assertThat(configuration).isNotNull();
        assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EMPTY);

        File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ManagerModule.bsl");
        assertThat(configuration.getModuleType(Common.getAbsoluteUri(file))).isEqualTo(ModuleType.Unknown);

        Configuration configuration2 = Configuration.newBuilder().build();

        assertThat(configuration2).isNotNull();
        assertThat(configuration2.getConfigurationSource()).isEqualTo(ConfigurationSource.EMPTY);
        assertThat(configuration2.getChild(MDOType.COMMON_MODULE, "НесуществущийМодуль")).isNull();
    }
}
