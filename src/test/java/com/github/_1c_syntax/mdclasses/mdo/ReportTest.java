package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReportTest extends AbstractMDOTest {
  ReportTest() {
    super(MDOType.REPORT);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("Reports/Отчет1/Отчет1.mdo");
    checkBaseField(mdo, Report.class, "Отчет1",
      "34d3754d-298c-4786-92f6-a487db249fc7");
    checkForms(mdo);
    checkTemplates(mdo, 1, "Report.Отчет1", "МакетОтчета");
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    checkModules(((MDObjectBSL) mdo).getModules(), 2, "Reports/Отчет1",
      ModuleType.ObjectModule, ModuleType.ManagerModule);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Reports/Отчет1.xml");
    checkBaseField(mdo, Report.class, "Отчет1",
      "34d3754d-298c-4786-92f6-a487db249fc7");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }
}
