package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class DocumentNumeratorTest extends AbstractMDOTest {
  DocumentNumeratorTest() {
    super(MDOType.DOCUMENT_NUMERATOR);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("DocumentNumerators/НумераторДокументов1/НумераторДокументов1.mdo");
    checkBaseField(mdo, DocumentNumerator.class, "НумераторДокументов1",
      "e401f835-6bfc-4cd4-8d87-5e6b6332a3f6");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("DocumentNumerators/НумераторДокументов1.xml");
    checkBaseField(mdo, DocumentNumerator.class, "НумераторДокументов1",
      "e401f835-6bfc-4cd4-8d87-5e6b6332a3f6");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

}
