package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOModule;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * Базовый класс объектов метаданных, имеющих модули с исходным кодом
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class MDObjectBSL extends MDObjectBase {

  /**
   * Список модулей объекта
   */
  @NonNull
  List<MDOModule> modules = Collections.emptyList();

  public MDObjectBSL(DesignerMDO designerMDO) {
    super(designerMDO);
  }
}
