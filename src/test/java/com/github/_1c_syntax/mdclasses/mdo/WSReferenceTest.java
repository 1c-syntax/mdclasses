package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class WSReferenceTest extends AbstractMDOTest {
  WSReferenceTest() {
    super(MDOType.WS_REFERENCE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("WSReferences/WSСсылка/WSСсылка.mdo");
    checkBaseField(mdo, WSReference.class, "WSСсылка",
      "95b745f2-e1fa-4f94-b7f9-f3f0224fc8c7");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("WSReferences/WSСсылка1.xml");
    checkBaseField(mdo, WSReference.class, "WSСсылка1",
      "7b8d6924-7aa9-4699-b794-6797c79d83c7");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
