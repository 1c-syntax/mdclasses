package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConstantTest extends AbstractMDOTest {
  ConstantTest() {
    super(MDOType.CONSTANT);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("Constants/Константа1/Константа1.mdo");
    checkBaseField(mdo, Constant.class, "Константа1",
      "61e6a6f2-7057-4e93-96c3-7bd2559217f4");
    checkNoChildren(mdo);
    checkModules(((MDObjectBSL) mdo).getModules(), 1,
      "Constants/Константа1", ModuleType.ValueManagerModule);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Constants/Константа1.xml");
    checkBaseField(mdo, Constant.class, "Константа1",
      "61e6a6f2-7057-4e93-96c3-7bd2559217f4");
    checkNoChildren(mdo);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }
}
