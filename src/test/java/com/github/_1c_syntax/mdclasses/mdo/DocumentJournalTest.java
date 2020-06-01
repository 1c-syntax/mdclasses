package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentJournalTest extends AbstractMDOTest {
  DocumentJournalTest() {
    super(MDOType.DOCUMENT_JOURNAL);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("DocumentJournals/ЖурналДокументов1/ЖурналДокументов1.mdo");
    checkBaseField(mdo, DocumentJournal.class, "ЖурналДокументов1",
      "c6743657-4787-40de-9a45-2493c630f626");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 1, "DocumentJournal.ЖурналДокументов1",
      AttributeType.COLUMN);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "DocumentJournals/ЖурналДокументов1",
      ModuleType.ManagerModule);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("DocumentJournals/ЖурналДокументов1.xml");
    checkBaseField(mdo, DocumentJournal.class, "ЖурналДокументов1",
      "c6743657-4787-40de-9a45-2493c630f626");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
