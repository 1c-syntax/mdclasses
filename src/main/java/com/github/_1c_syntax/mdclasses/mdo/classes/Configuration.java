package com.github._1c_syntax.mdclasses.mdo.classes;

import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDO;
import com.github._1c_syntax.mdclasses.mdo.core.CompatibilityMode;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import com.github._1c_syntax.mdclasses.mdo.core.ScriptVariant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = false)
public class Configuration extends AbstractMDO {

    protected CompatibilityMode compatibilityMode;
    protected ScriptVariant scriptVariant;

    protected HashMap<MDOType, HashMap<String, AbstractMDO>> children;

    public Configuration() {
        super(MDOType.CONFIGURATION);
        children = new HashMap<>();
    }

    public AbstractMDO getChild(MDOType type, String name) {
        HashMap<String, AbstractMDO> childrenByType = children.get(type);
        if(childrenByType != null) {
            return childrenByType.get(name);
        }
        return null;
    }

    public void addChild(MDOType type, String name, AbstractMDO child) {
        HashMap<String, AbstractMDO> childrenByType = children.get(type);
        if(childrenByType == null) {
            childrenByType = new HashMap<>();
        }
        childrenByType.put(name, child);
        children.put(type, childrenByType);
    }
}
