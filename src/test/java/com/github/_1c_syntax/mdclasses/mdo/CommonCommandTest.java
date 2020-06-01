package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

class CommonCommandTest extends AbstractMDOTest {
  CommonCommandTest() {
    super(MDOType.COMMON_COMMAND);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("CommonCommands/ОбщаяКоманда1/ОбщаяКоманда1.mdo");
    checkBaseField(mdo, CommonCommand.class, "ОбщаяКоманда1",
      "a608f796-f58e-4f8a-b63f-272342b32f35");
    checkNoChildren(mdo);
    checkModules(((MDObjectBSL) mdo).getModules(), 1,
      "CommonCommands/ОбщаяКоманда1", ModuleType.CommandModule);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("CommonCommands/ОбщаяКоманда1.xml");
    checkBaseField(mdo, CommonCommand.class, "ОбщаяКоманда1",
      "a608f796-f58e-4f8a-b63f-272342b32f35");
    checkNoChildren(mdo);
    checkModules(((MDObjectBSL) mdo).getModules(), 1,
      "CommonCommands/ОбщаяКоманда1", ModuleType.CommandModule);
  }
}
