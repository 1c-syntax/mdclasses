package com.github._1c_syntax.mdclasses.mdo.builder;

import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDO;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;

public interface MDOBuilder {
    AbstractMDO build(MDOType type, String path, String name);
}
