package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculationRegisterTest extends AbstractMDOTest {
  CalculationRegisterTest() {
    super(MDOType.CALCULATION_REGISTER);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("CalculationRegisters/РегистрРасчета1/РегистрРасчета1.mdo");
    checkBaseField(mdo, CalculationRegister.class, "РегистрРасчета1",
      "90587c7d-b950-4476-ac14-426e4a83d9c4");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 3, "CalculationRegister.РегистрРасчета1",
      AttributeType.DIMENSION, AttributeType.RESOURCE, AttributeType.RECALCULATION);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("CalculationRegisters/РегистрРасчета1.xml");
    checkBaseField(mdo, CalculationRegister.class, "РегистрРасчета1",
      "90587c7d-b950-4476-ac14-426e4a83d9c4");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 2, "CalculationRegister.РегистрРасчета1",
      AttributeType.DIMENSION, AttributeType.RESOURCE);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
