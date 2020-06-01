package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

class CommonFormTest extends AbstractMDOTest {
  CommonFormTest() {
    super(MDOType.COMMON_FORM);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("CommonForms/Форма/Форма.mdo");
    checkBaseField(mdo, CommonForm.class, "Форма",
      "5ac59104-28a5-40b1-ab5b-2857fb41991a");
    checkNoChildren(mdo);
    checkModules(((MDObjectBSL) mdo).getModules(), 1,
      "CommonForms/Форма", ModuleType.FormModule);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("CommonForms/Форма.xml");
    checkBaseField(mdo, CommonForm.class, "Форма",
      "5ac59104-28a5-40b1-ab5b-2857fb41991a");
    checkNoChildren(mdo);
    checkModules(((MDObjectBSL) mdo).getModules(), 1,
      "CommonForms/Форма", ModuleType.FormModule);
  }
}
