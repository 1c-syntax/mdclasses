package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HTTPServiceTest extends AbstractMDOTest {
  HTTPServiceTest() {
    super(MDOType.HTTP_SERVICE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("HTTPServices/HTTPСервис1/HTTPСервис1.mdo");
    checkBaseField(mdo, HTTPService.class, "HTTPСервис1",
      "3f029e1e-5a9e-4446-b74f-cbcb79b1e2fe");
    checkNoChildren(mdo);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "HTTPServices/HTTPСервис1",
      ModuleType.HTTPServiceModule);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("HTTPServices/HTTPСервис1.xml");
    checkBaseField(mdo, HTTPService.class, "HTTPСервис1",
      "3f029e1e-5a9e-4446-b74f-cbcb79b1e2fe");
    checkNoChildren(mdo);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }
}
