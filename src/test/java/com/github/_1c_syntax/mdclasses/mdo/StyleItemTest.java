package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class StyleItemTest extends AbstractMDOTest {
  StyleItemTest() {
    super(MDOType.STYLE_ITEM);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("StyleItems/ЭлементСтиля1/ЭлементСтиля1.mdo");
    checkBaseField(mdo, StyleItem.class, "ЭлементСтиля1",
      "68047ae8-62aa-4696-9780-d364feb3cc10");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("StyleItems/ЭлементСтиля1.xml");
    checkBaseField(mdo, StyleItem.class, "ЭлементСтиля1",
      "68047ae8-62aa-4696-9780-d364feb3cc10");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
