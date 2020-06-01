package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChartOfCalculationTypesTest extends AbstractMDOTest {
  ChartOfCalculationTypesTest() {
    super(MDOType.CHART_OF_CALCULATION_TYPES);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("ChartsOfCalculationTypes/ПланВидовРасчета1/ПланВидовРасчета1.mdo");
    checkBaseField(mdo, ChartOfCalculationTypes.class, "ПланВидовРасчета1",
      "1755c534-9ccd-49c4-9f8b-2aa066424aaa");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("ChartsOfCalculationTypes/ПланВидовРасчета1.xml");
    checkBaseField(mdo, ChartOfCalculationTypes.class, "ПланВидовРасчета1",
      "1755c534-9ccd-49c4-9f8b-2aa066424aaa");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
