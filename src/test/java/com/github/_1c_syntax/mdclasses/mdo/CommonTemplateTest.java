package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class CommonTemplateTest extends AbstractMDOTest {
  CommonTemplateTest() {
    super(MDOType.COMMON_TEMPLATE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("CommonTemplates/Макет/Макет.mdo");
    checkBaseField(mdo, CommonTemplate.class, "Макет",
      "799e0ae7-f5ea-4b50-8853-e2c58ef5d9cd");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("CommonTemplates/Макет.xml");
    checkBaseField(mdo, CommonTemplate.class, "Макет",
      "799e0ae7-f5ea-4b50-8853-e2c58ef5d9cd");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
