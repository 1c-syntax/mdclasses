package com.github._1c_syntax.mdclasses.mdo.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Класс для простых объектов и базовый для сложных
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractMDOSimple extends AbstractMDO {
    public AbstractMDOSimple(MDOType mdoType) {
        super(mdoType);
    }
}
