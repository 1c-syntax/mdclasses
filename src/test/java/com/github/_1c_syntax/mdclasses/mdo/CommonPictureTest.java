package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class CommonPictureTest extends AbstractMDOTest {
  CommonPictureTest() {
    super(MDOType.COMMON_PICTURE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("CommonPictures/ОбщаяКартинка1/ОбщаяКартинка1.mdo");
    checkBaseField(mdo, CommonPicture.class, "ОбщаяКартинка1",
      "db84513d-2535-494b-843e-6d8931cb2f82");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("CommonPictures/ОбщаяКартинка1.xml");
    checkBaseField(mdo, CommonPicture.class, "ОбщаяКартинка1",
      "db84513d-2535-494b-843e-6d8931cb2f82");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
