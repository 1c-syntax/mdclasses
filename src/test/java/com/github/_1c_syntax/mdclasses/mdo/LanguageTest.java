package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class LanguageTest extends AbstractMDOTest {
  LanguageTest() {
    super(MDOType.LANGUAGE);
  }

  @Override
  @Test
  protected void testEDT() {
    // TODO язык входит в состав конфигурации и отдельно не существует
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Languages/Русский.xml");
    checkBaseField(mdo, Language.class, "Русский",
      "1b5f5cd6-14b2-422e-ab6c-1103fd375982");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

}
