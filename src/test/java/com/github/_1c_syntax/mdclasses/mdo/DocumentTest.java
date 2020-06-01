package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentTest extends AbstractMDOTest {
  DocumentTest() {
    super(MDOType.DOCUMENT);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("Documents/Документ1/Документ1.mdo");
    checkBaseField(mdo, Document.class, "Документ1",
      "ce4fb46b-4af7-493e-9fcb-76ad8c4f8acd");
    checkForms(mdo, 3, "Document.Документ1", "ФормаДокумента", "ФормаСписка", "ФормаВыбора");
    checkTemplates(mdo);
    checkCommands(mdo, 2, "Document.Документ1", "Команда", "Команда2");
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 4, "Document.Документ1",
      AttributeType.ATTRIBUTE, AttributeType.TABULAR_SECTION);
    var tabularSection = (TabularSection) ((MDObjectComplex) mdo).getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION)
      .findFirst().get();
    checkAttributes(tabularSection.getAttributes(), 2,
      "Document.Документ1.TabularSection.ТабличнаяЧасть1", AttributeType.ATTRIBUTE);
    checkModules(((MDObjectBSL) mdo).getModules(), 2, "Documents/Документ1",
      ModuleType.ObjectModule, ModuleType.ManagerModule);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Documents/Документ1.xml");
    checkBaseField(mdo, Document.class, "Документ1",
      "ce4fb46b-4af7-493e-9fcb-76ad8c4f8acd");
    checkForms(mdo, 3, "Document.Документ1", "ФормаДокумента", "ФормаСписка", "ФормаВыбора");
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 4, "Document.Документ1",
      AttributeType.ATTRIBUTE, AttributeType.TABULAR_SECTION);
    var tabularSection = (TabularSection) ((MDObjectComplex) mdo).getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION)
      .findFirst().get();
    checkAttributes(tabularSection.getAttributes(), 2,
      "Document.Документ1.TabularSection.ТабличнаяЧасть1", AttributeType.ATTRIBUTE);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
