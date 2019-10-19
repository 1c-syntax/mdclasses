package com.github._1c_syntax.mdclasses.mdo.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;

/**
 * Класс для простых объектов и базовый для сложных
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractMDOSimple extends AbstractMDO {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMDOSimple.class.getSimpleName());

    protected ConfigurationSource configurationSource;
    protected Path mdoPath;

    public AbstractMDOSimple(MDOType mdoType) {
        super(mdoType);
    }

    public static <T, O> T unmarshalMDO(File xml, Class<T> typeMDO, Class<O> objectFactoryClass) {
        T mdoObj;
        try {
            JAXBContext context = JAXBContext.newInstance(objectFactoryClass);
            Unmarshaller jaxbUnpacked = context.createUnmarshaller();
            mdoObj = typeMDO.cast(((JAXBElement) jaxbUnpacked.unmarshal(xml)).getValue());
        } catch (JAXBException e) {
            mdoObj = null;
            LOGGER.error(e.getMessage(), e);
        }
        return mdoObj;
    }

    public abstract void initialize();
}
