package org.github._1c_syntax.mdclasses.metadata;

import org.github._1c_syntax.mdclasses.jabx.original.MetaDataObject;
import org.github._1c_syntax.mdclasses.jabx.original.ObjectFactory;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationVersion;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;

public class ConfigurationBuilder {

    private ConfigurationSource configurationSource;
    private Path pathToXML;

    public ConfigurationBuilder(){
        this.configurationSource = ConfigurationSource.Original;
    }

    public ConfigurationBuilder(ConfigurationSource configurationSource, Path pathToXML){
        this.configurationSource = configurationSource;
        this.pathToXML = pathToXML;
    }


    public Configuration build(){

        Configuration configurationMetadata = new Configuration(configurationSource);

        if (configurationSource == ConfigurationSource.Original){

            MetaDataObject MDObject = null;
            File XML = pathToXML.toFile();
            try {
                JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
                Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
                MDObject = (MetaDataObject) ((JAXBElement) jaxbUnmarshaller.unmarshal(XML)).getValue();
            } catch (JAXBException e) {
                e.printStackTrace();
            }

            org.github._1c_syntax.mdclasses.jabx.original.Configuration configurationXML = MDObject.getConfiguration();

            // режим совместимости
            ConfigurationVersion configurationVersion =
                    new ConfigurationVersion(
                            configurationXML.getProperties().getConfigurationExtensionCompatibilityMode().name());
            configurationMetadata.setCompatibilityMode(configurationVersion);

            // режим встроенного языка
            String scriptVariantString = configurationXML.getProperties().getScriptVariant().name();
            if (scriptVariantString.equals("ENGLISH")){
                configurationMetadata.setScriptVariant(ScriptVariant.English);
            }
            else {
                configurationMetadata.setScriptVariant(ScriptVariant.Russian);
            }

        }
        else {

            new Exception("Формат конфигурации не поддерживается");

        }

        return configurationMetadata;
    }
}
