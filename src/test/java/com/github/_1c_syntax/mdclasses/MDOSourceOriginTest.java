package com.github._1c_syntax.mdclasses;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github._1c_syntax.mdclasses.mdo.MetaDataObject;
import com.github._1c_syntax.mdclasses.utils.ObjectMapperFactory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class MDOSourceOriginTest {

  @Test
  void testLoadConfiguration() {

    MetaDataObject MDObject = null;
    String basePath = "src/test/resources/metadata/original";
    File XML = new File(basePath, "Configuration.xml");

    XmlMapper xmlMapper = ObjectMapperFactory.getXmlMapper();

    try {
      MDObject = xmlMapper.readValue(XML, MetaDataObject.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertThat(MDObject).isNotNull();
    assertThat(MDObject.getConfiguration()).isNotNull();

  }

}
