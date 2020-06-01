package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogTest extends AbstractMDOTest {
  CatalogTest() {
    super(MDOType.CATALOG);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("Catalogs/Справочник1/Справочник1.mdo");
    checkBaseField(mdo, Catalog.class, "Справочник1",
      "eeef463d-d5e7-42f2-ae53-10279661f59d");
    checkForms(mdo, 3, "Catalog.Справочник1", "ФормаЭлемента", "ФормаСписка", "ФормаВыбора");
    checkTemplates(mdo, 1, "Catalog.Справочник1", "Макет");
    checkCommands(mdo, 1, "Catalog.Справочник1", "Команда1");
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 4, "Catalog.Справочник1",
      AttributeType.ATTRIBUTE, AttributeType.TABULAR_SECTION);
    var tabularSection = (TabularSection) ((MDObjectComplex) mdo).getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION)
      .findFirst().get();
    checkAttributes(tabularSection.getAttributes(), 2,
      "Catalog.Справочник1.TabularSection.ТабличнаяЧасть1", AttributeType.ATTRIBUTE);
    checkModules(((MDObjectBSL) mdo).getModules(), 2, "Catalogs/Справочник1",
      ModuleType.ObjectModule, ModuleType.ManagerModule);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("Catalogs/Справочник1.xml");
    checkBaseField(mdo, Catalog.class, "Справочник1",
      "eeef463d-d5e7-42f2-ae53-10279661f59d");
    checkForms(mdo, 3, "Catalog.Справочник1", "ФормаЭлемента", "ФормаСписка", "ФормаВыбора");
    checkTemplates(mdo, 2, "Catalog.Справочник1", "Макет", "Макет2");
    checkCommands(mdo, 1, "Catalog.Справочник1", "Команда1");
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 4, "Catalog.Справочник1",
      AttributeType.ATTRIBUTE, AttributeType.TABULAR_SECTION);
    var tabularSection = (TabularSection) ((MDObjectComplex) mdo).getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION)
      .findFirst().get();
    checkAttributes(tabularSection.getAttributes(), 2,
      "Catalog.Справочник1.TabularSection.ТабличнаяЧасть1", AttributeType.ATTRIBUTE);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
