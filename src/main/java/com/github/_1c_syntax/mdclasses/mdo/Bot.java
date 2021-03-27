package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Bot extends MDObjectBSL {

    private boolean predefined;

    public Bot(DesignerMDO designerMDO) {
        super(designerMDO);
        predefined = designerMDO.getProperties().isPredefined();
    }

    @Override
    public MDOType getType() {
        return MDOType.BOT;
    }
}
