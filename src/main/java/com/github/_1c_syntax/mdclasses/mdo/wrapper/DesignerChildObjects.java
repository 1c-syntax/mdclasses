package com.github._1c_syntax.mdclasses.mdo.wrapper;

import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import io.vavr.control.Either;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * Враппер для дочерних элементов объекта в формате конфигуратора
 */
@Data
@NoArgsConstructor
public class DesignerChildObjects {
  @NonNull
  @XStreamImplicit(itemFieldName = "Form")
  List<String> forms = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Template")
  List<String> templates = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Command")
  List<DesignerMDO> commands = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Dimension")
  List<DesignerMDO> dimensions = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Resource")
  List<DesignerMDO> resources = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Recalculation")
  List<DesignerMDO> recalculations = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Attribute")
  List<DesignerMDO> attributes = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "TabularSection")
  List<DesignerMDO> tabularSections = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "AccountingFlag")
  List<DesignerMDO> accountingFlags = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "ExtDimensionAccountingFlag")
  List<DesignerMDO> extDimensionAccountingFlags = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "Column")
  List<DesignerMDO> columns = Collections.emptyList();

  @NonNull
  @XStreamImplicit(itemFieldName = "AddressingAttribute")
  List<DesignerMDO> addressingAttributes = Collections.emptyList();

  @NonNull
  @XStreamImplicit
  List<Either<String, MDObjectBase>> children = Collections.emptyList();

}
