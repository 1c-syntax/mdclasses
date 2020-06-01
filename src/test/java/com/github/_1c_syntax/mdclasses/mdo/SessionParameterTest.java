package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class SessionParameterTest extends AbstractMDOTest {
  SessionParameterTest() {
    super(MDOType.SESSION_PARAMETER);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("SessionParameters/ПараметрСеанса1/ПараметрСеанса1.mdo");
    checkBaseField(mdo, SessionParameter.class, "ПараметрСеанса1",
      "66844df5-823b-40f1-ab8a-b07c1cb7462f");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("SessionParameters/ПараметрСеанса1.xml");
    checkBaseField(mdo, SessionParameter.class, "ПараметрСеанса1",
      "66844df5-823b-40f1-ab8a-b07c1cb7462f");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}
