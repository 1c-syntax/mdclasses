package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.jabx.original.MetaDataObject;
import com.github._1c_syntax.mdclasses.jabx.original.ObjectFactory;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class JABXOriginTest {

  private String basePath = "src/test/resources/metadata/original";

  @Test
  void testLoadConfiguration() {

    MetaDataObject MDObject = null;
    File XML = new File(basePath, "Configuration.xml");
    try {
      JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
      Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
      MDObject = (MetaDataObject) ((JAXBElement) jaxbUnmarshaller.unmarshal(XML)).getValue();
      System.out.println(MDObject);
    } catch (JAXBException e) {
      e.printStackTrace();
    }

    assertThat(MDObject).isNotNull();
    assertThat(MDObject.getConfiguration()).isNotNull();

  }

}
