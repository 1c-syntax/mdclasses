package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTest extends AbstractMDOTest {
  TaskTest() {
    super(MDOType.TASK);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("Tasks/Задача1/Задача1.mdo");
    checkBaseField(mdo, Task.class, "Задача1",
      "c251fcec-ec02-4ef4-8f70-4d70db6631ea");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 1, "Task.Задача1",
      AttributeType.ADDRESSING_ATTRIBUTE);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Tasks/Задача1.xml");
    checkBaseField(mdo, Task.class, "Задача1",
      "c251fcec-ec02-4ef4-8f70-4d70db6631ea");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }
}
