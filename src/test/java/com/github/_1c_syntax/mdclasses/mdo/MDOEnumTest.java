package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDOEnumTest extends AbstractMDOTest {
  MDOEnumTest() {
    super(MDOType.ENUM);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("Enums/Перечисление1/Перечисление1.mdo");
    checkBaseField(mdo, MDOEnum.class, "Перечисление1",
      "f11f3441-4b64-4344-b1a0-0e4b3e466e03");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "Enums/Перечисление1",
      ModuleType.ManagerModule);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Enums/Перечисление1.xml");
    checkBaseField(mdo, MDOEnum.class, "Перечисление1",
      "f11f3441-4b64-4344-b1a0-0e4b3e466e03");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }
}
