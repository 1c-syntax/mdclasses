package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class FunctionalOptionsParameterTest extends AbstractMDOTest {
  FunctionalOptionsParameterTest() {
    super(MDOType.FUNCTIONAL_OPTIONS_PARAMETER);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("FunctionalOptionsParameters/ПараметрФункциональныхОпций/ПараметрФункциональныхОпций.mdo");
    checkBaseField(mdo, FunctionalOptionsParameter.class, "ПараметрФункциональныхОпций",
      "9fae7345-6220-4e5b-b4c1-84bb921a58b7");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("FunctionalOptionsParameters/ПараметрФункциональныхОпций1.xml");
    checkBaseField(mdo, FunctionalOptionsParameter.class, "ПараметрФункциональныхОпций1",
      "8e2f9f0c-8727-4ffc-a6ea-f510b37814eb");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

}
