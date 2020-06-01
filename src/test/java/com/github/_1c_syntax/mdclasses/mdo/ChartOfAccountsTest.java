package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChartOfAccountsTest extends AbstractMDOTest {
  ChartOfAccountsTest() {
    super(MDOType.CHART_OF_ACCOUNTS);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("ChartsOfAccounts/ПланСчетов1/ПланСчетов1.mdo");
    checkBaseField(mdo, ChartOfAccounts.class, "ПланСчетов1",
      "2766f353-abd2-4e7f-9a95-53f05c83f5d4");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 2, "ChartOfAccounts.ПланСчетов1",
      AttributeType.ACCOUNTING_FLAG, AttributeType.EXT_DIMENSION_ACCOUNTING_FLAG);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "ChartsOfAccounts/ПланСчетов1",
      ModuleType.ObjectModule);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("ChartsOfAccounts/ПланСчетов1.xml");
    checkBaseField(mdo, ChartOfAccounts.class, "ПланСчетов1",
      "2766f353-abd2-4e7f-9a95-53f05c83f5d4");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
