package com.github._1c_syntax.mdclasses;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github._1c_syntax.mdclasses.jackson.designer.MetaDataObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class JABXOriginTest {

    private String basePath = "src/test/resources/metadata/original";

    @Test
    void testLoadConfiguration() {

        File XML = new File(basePath, "Configuration.xml");

        MetaDataObject mdObject = null;
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        try {
            mdObject = xmlMapper.readValue(XML, MetaDataObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(mdObject);

        assertThat(mdObject).isNotNull();
        assertThat(mdObject.getConfiguration()).isNotNull();

    }

}
