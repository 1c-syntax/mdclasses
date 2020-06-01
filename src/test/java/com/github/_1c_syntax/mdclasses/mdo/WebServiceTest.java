package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WebServiceTest extends AbstractMDOTest {
  WebServiceTest() {
    super(MDOType.WEB_SERVICE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("WebServices/WebСервис1/WebСервис1.mdo");
    checkBaseField(mdo, WebService.class, "WebСервис1",
      "d7f9b06b-0799-486e-adff-c45a2d5b8101");
    checkNoChildren(mdo);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "WebServices/WebСервис1",
      ModuleType.WEBServiceModule);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("WebServices/WebСервис1.xml");
    checkBaseField(mdo, WebService.class, "WebСервис1",
      "d7f9b06b-0799-486e-adff-c45a2d5b8101");
    checkNoChildren(mdo);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }
}
