package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class RoleTest extends AbstractMDOTest {
  RoleTest() {
    super(MDOType.ROLE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("Roles/Роль1/Роль1.mdo");
    checkBaseField(mdo, Role.class, "Роль1",
      "ecad0539-4f9f-491b-b0f2-f8f42d9a7c41");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Roles/Роль1.xml");
    checkBaseField(mdo, Role.class, "Роль1",
      "ecad0539-4f9f-491b-b0f2-f8f42d9a7c41");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
