package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccumulationRegisterTest extends AbstractMDOTest {
  AccumulationRegisterTest() {
    super(MDOType.ACCUMULATION_REGISTER);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("AccumulationRegisters/РегистрНакопления1/РегистрНакопления1.mdo");
    checkBaseField(mdo, AccumulationRegister.class, "РегистрНакопления1",
      "8ea07f36-d671-4649-bc7a-94daa939e77f");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 2,
      "AccumulationRegister.РегистрНакопления1",
      AttributeType.DIMENSION, AttributeType.RESOURCE);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("AccumulationRegisters/РегистрНакопления1.xml");
    checkBaseField(mdo, AccumulationRegister.class, "РегистрНакопления1",
      "8ea07f36-d671-4649-bc7a-94daa939e77f");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 2,
      "AccumulationRegister.РегистрНакопления1",
      AttributeType.DIMENSION, AttributeType.RESOURCE);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }
}