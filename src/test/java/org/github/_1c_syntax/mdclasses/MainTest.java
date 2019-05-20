package org.github._1c_syntax.mdclasses;

import org.github._1c_syntax.mdclasses.jabx.original.MetaDataObject;
import org.github._1c_syntax.mdclasses.jabx.original.ObjectFactory;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    String basePath = "src/test/resources/metadata/original";

    @Test
    public void testParseConfigurationFile() {

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

    @Test
    public void testParseEnumFiles() {

        MetaDataObject MDObject = null;

        File dir = new File(basePath, "Enums");
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".xml"));

        for (File XML:files) {

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

}
