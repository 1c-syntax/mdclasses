package com.github._1c_syntax.mdclasses.mdo.build;


import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDO;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConfigurationBuilderTest {
    @Test
    void testBuilder() {

        File srcPath = new File("src/test/resources/metadata/edt");
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(srcPath.toPath());
        AbstractMDO configuration = configurationBuilder.getConfiguration();
    }
}