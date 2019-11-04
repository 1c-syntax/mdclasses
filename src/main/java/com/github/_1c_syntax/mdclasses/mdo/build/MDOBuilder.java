package com.github._1c_syntax.mdclasses.mdo.build;

import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDO;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;

import java.lang.reflect.InvocationTargetException;

public interface MDOBuilder {
    AbstractMDO build(MDOType type, String path) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
