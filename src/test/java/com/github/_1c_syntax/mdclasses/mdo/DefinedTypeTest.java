package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class DefinedTypeTest extends AbstractMDOTest {
  DefinedTypeTest() {
    super(MDOType.DEFINED_TYPE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("DefinedTypes/ОпределяемыйТип1/ОпределяемыйТип1.mdo");
    checkBaseField(mdo, DefinedType.class, "ОпределяемыйТип1",
      "e8c616d9-4957-48ab-a917-afb6847f6840");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("DefinedTypes/ОпределяемыйТип1.xml");
    checkBaseField(mdo, DefinedType.class, "ОпределяемыйТип1",
      "e8c616d9-4957-48ab-a917-afb6847f6840");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
