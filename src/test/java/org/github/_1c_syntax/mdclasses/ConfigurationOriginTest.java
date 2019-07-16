package org.github._1c_syntax.mdclasses;

import org.github._1c_syntax.mdclasses.metadata.Configuration;
import org.github._1c_syntax.mdclasses.metadata.ConfigurationBuilder;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationOriginTest {

    @Test
    public void testBuilder() {

        File ConfigurationXML = new File("src/test/resources/metadata/original", "Configuration.xml");
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(ConfigurationSource.DESIGNER, ConfigurationXML.toPath());
        Configuration configuration = configurationBuilder.build();

        assertThat(configuration.getScriptVariant() == ScriptVariant.RUSSIAN).isTrue();
        assertThat(configuration.getConfigurationSource() == ConfigurationSource.DESIGNER).isTrue();
        assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 10)));

    }

}
