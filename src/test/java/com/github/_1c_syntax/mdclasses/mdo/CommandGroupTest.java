package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommandGroupTest extends AbstractMDOTest {
  CommandGroupTest() {
    super(MDOType.COMMAND_GROUP);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("CommandGroups/ГруппаКоманд1/ГруппаКоманд1.mdo");
    checkBaseField(mdo, CommandGroup.class, "ГруппаКоманд1",
      "9bd3b0b1-b276-4b0e-9811-44a41ebb0c7c");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("CommandGroups/ГруппаКоманд1.xml");
    checkBaseField(mdo, CommandGroup.class, "ГруппаКоманд1",
      "9bd3b0b1-b276-4b0e-9811-44a41ebb0c7c");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

}
