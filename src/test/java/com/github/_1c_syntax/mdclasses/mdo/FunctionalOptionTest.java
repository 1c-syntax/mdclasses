package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class FunctionalOptionTest extends AbstractMDOTest {
  FunctionalOptionTest() {
    super(MDOType.FUNCTIONAL_OPTION);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("FunctionalOptions/ФункциональнаяОпция1/ФункциональнаяОпция1.mdo");
    checkBaseField(mdo, FunctionalOption.class, "ФункциональнаяОпция1",
      "d3b7fd71-6570-4047-91e0-b3df75dba08d");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("FunctionalOptions/ФункциональнаяОпция1.xml");
    checkBaseField(mdo, FunctionalOption.class, "ФункциональнаяОпция1",
      "d3b7fd71-6570-4047-91e0-b3df75dba08d");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

}
