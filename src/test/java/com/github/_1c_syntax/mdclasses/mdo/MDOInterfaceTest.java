package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class MDOInterfaceTest extends AbstractMDOTest {
  MDOInterfaceTest() {
    super(MDOType.INTERFACE);
  }

  @Override
  @Test
  protected void testEDT() {
    // TODO интерфейсов в едт нет
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Interfaces/Интерфейс1.xml");
    checkBaseField(mdo, MDOInterface.class, "Интерфейс1",
      "874d641c-12f7-4db7-bde2-dd72c3d5b522");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

}
