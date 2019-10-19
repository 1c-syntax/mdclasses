package com.github._1c_syntax.mdclasses.mdo.core;

import com.github._1c_syntax.mdclasses.mdo.classes.Attribute;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class AbstractMDOComplex extends AbstractMDOSimple {

    protected List<Attribute> attributes;

    public AbstractMDOComplex(MDOType mdoType) {
        super(mdoType);
    }
}
