package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountingRegisterTest extends AbstractMDOTest {
  AccountingRegisterTest() {
    super(MDOType.ACCOUNTING_REGISTER);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("AccountingRegisters/РегистрБухгалтерии1/РегистрБухгалтерии1.mdo");
    checkBaseField(mdo, AccountingRegister.class, "РегистрБухгалтерии1",
      "e5930f2f-15d9-48a1-ac69-379ad990b02a");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 2,
      "AccountingRegister.РегистрБухгалтерии1",
      AttributeType.DIMENSION, AttributeType.RESOURCE);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("AccountingRegisters/РегистрБухгалтерии1.xml");
    checkBaseField(mdo, AccountingRegister.class, "РегистрБухгалтерии1",
      "e5930f2f-15d9-48a1-ac69-379ad990b02a");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 2,
      "AccountingRegister.РегистрБухгалтерии1",
      AttributeType.DIMENSION, AttributeType.RESOURCE);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }
}
