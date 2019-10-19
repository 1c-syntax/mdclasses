package com.github._1c_syntax.mdclasses.mdo.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.file.Path;

/**
 * Класс для простых объектов и базовый для сложных
 */
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class AbstractMDOSimple extends AbstractMDO {
    protected ConfigurationSource configurationSource;
    protected Path mdoPath;

    public AbstractMDOSimple(MDOType mdoType) {
        super(mdoType);
    }

    public abstract void initialize();
}
