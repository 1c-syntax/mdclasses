package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class StyleTest extends AbstractMDOTest {
  StyleTest() {
    super(MDOType.STYLE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("Styles/Стиль/Стиль.mdo");
    checkBaseField(mdo, Style.class, "Стиль",
      "d6aaa851-cba7-486d-92f4-ab31b1628c6b");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Styles/Стиль1.xml");
    checkBaseField(mdo, Style.class, "Стиль1",
      "2ef7f6ca-b11c-4e2d-a233-5c5b01675e9a");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
