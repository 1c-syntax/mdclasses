package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class CommonAttributeTest extends AbstractMDOTest {
  CommonAttributeTest() {
    super(MDOType.COMMON_ATTRIBUTE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("CommonAttributes/ОбщийРеквизит1/ОбщийРеквизит1.mdo");
    checkBaseField(mdo, CommonAttribute.class, "ОбщийРеквизит1",
      "d4f0c0ac-ed26-4085-a1b4-e52314b973ad");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("CommonAttributes/ОбщийРеквизит1.xml");
    checkBaseField(mdo, CommonAttribute.class, "ОбщийРеквизит1",
      "d4f0c0ac-ed26-4085-a1b4-e52314b973ad");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

}
