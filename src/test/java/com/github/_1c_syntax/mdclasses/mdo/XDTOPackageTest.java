package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class XDTOPackageTest extends AbstractMDOTest {
  XDTOPackageTest() {
    super(MDOType.XDTO_PACKAGE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("XDTOPackages/ПакетXDTO1/ПакетXDTO1.mdo");
    checkBaseField(mdo, XDTOPackage.class, "ПакетXDTO1",
      "b8a93cce-56e4-4507-b281-5c525a466a0f");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("XDTOPackages/ПакетXDTO1.xml");
    checkBaseField(mdo, XDTOPackage.class, "ПакетXDTO1",
      "b8a93cce-56e4-4507-b281-5c525a466a0f");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
