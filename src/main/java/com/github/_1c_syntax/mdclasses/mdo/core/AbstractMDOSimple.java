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
//    protected ConfigurationSource configurationSource;
//    protected Path mdoPath;

    public AbstractMDOSimple(MDOType mdoType) {
        super(mdoType);
    }


//    public abstract void initialize();
}
