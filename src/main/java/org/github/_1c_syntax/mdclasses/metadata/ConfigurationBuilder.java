package org.github._1c_syntax.mdclasses.metadata;

import org.github._1c_syntax.mdclasses.jabx.original.MetaDataObject;
import org.github._1c_syntax.mdclasses.jabx.original.ObjectFactory;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;

public class ConfigurationBuilder {

    private ConfigurationSource configurationSource;
    private Path pathToRoot;

    private Configuration configurationMetadata;

    public ConfigurationBuilder(ConfigurationSource configurationSource, Path pathToRoot){
        this.configurationSource = configurationSource;
        this.pathToRoot = pathToRoot;
    }


    public Configuration build(){

        configurationMetadata = new Configuration(configurationSource);

        if (configurationSource == ConfigurationSource.Designer){

            MetaDataObject MDObject = null;
            File XML = pathToRoot.toFile();
            try {
                JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
                Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
                MDObject = (MetaDataObject) ((JAXBElement) jaxbUnmarshaller.unmarshal(XML)).getValue();
            } catch (JAXBException e) {
                e.printStackTrace();
                return null; // TODO: пока так, переделать
            }

            org.github._1c_syntax.mdclasses.jabx.original.Configuration configurationXML = MDObject.getConfiguration();

            // режим совместимости
            setCompatibilityMode(configurationXML);

            // режим встроенного языка
            setScriptVariant(configurationXML);

        }
        else {

            // в разработке EDT

        }

        return configurationMetadata;
    }

    private void setCompatibilityMode(org.github._1c_syntax.mdclasses.jabx.original.Configuration configurationXML){

        CompatibilityMode compatibilityMode =
                new CompatibilityMode(
                        configurationXML.getProperties().getConfigurationExtensionCompatibilityMode().name());
        configurationMetadata.setCompatibilityMode(compatibilityMode);

    }

    private void setScriptVariant(org.github._1c_syntax.mdclasses.jabx.original.Configuration configurationXML){

        String scriptVariantString = configurationXML.getProperties().getScriptVariant().name().toUpperCase();
        configurationMetadata.setScriptVariant(ScriptVariant.valueOf(scriptVariantString));

    }
}
