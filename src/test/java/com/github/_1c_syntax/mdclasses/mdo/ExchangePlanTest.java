package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExchangePlanTest extends AbstractMDOTest {
  ExchangePlanTest() {
    super(MDOType.EXCHANGE_PLAN);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("ExchangePlans/ПланОбмена1/ПланОбмена1.mdo");
    checkBaseField(mdo, ExchangePlan.class, "ПланОбмена1",
      "242cb07d-3d2b-4689-b590-d3ed23ac9d10");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "ExchangePlans/ПланОбмена1",
      ModuleType.ObjectModule);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("ExchangePlans/ПланОбмена1.xml");
    checkBaseField(mdo, ExchangePlan.class, "ПланОбмена1",
      "242cb07d-3d2b-4689-b590-d3ed23ac9d10");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
