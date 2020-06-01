package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SequenceTest extends AbstractMDOTest {
  SequenceTest() {
    super(MDOType.SEQUENCE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("Sequences/Последовательность1/Последовательность1.mdo");
    checkBaseField(mdo, Sequence.class, "Последовательность1",
      "514bbcf4-7fc4-4a3e-9245-598fad397eec");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 1, "Sequence.Последовательность1",
      AttributeType.DIMENSION);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "Sequences/Последовательность1",
      ModuleType.RecordSetModule);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Sequences/Последовательность1.xml");
    checkBaseField(mdo, Sequence.class, "Последовательность1",
      "514bbcf4-7fc4-4a3e-9245-598fad397eec");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 1, "Sequence.Последовательность1",
      AttributeType.DIMENSION);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
