package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilterCriterionTest extends AbstractMDOTest {
  FilterCriterionTest() {
    super(MDOType.FILTER_CRITERION);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("FilterCriteria/КритерийОтбора1/КритерийОтбора1.mdo");
    checkBaseField(mdo, FilterCriterion.class, "КритерийОтбора1",
      "6e9d3381-0607-43df-866d-14ee5d65a294");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "FilterCriteria/КритерийОтбора1",
      ModuleType.ManagerModule);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("FilterCriteria/КритерийОтбора1.xml");
    checkBaseField(mdo, FilterCriterion.class, "КритерийОтбора1",
      "6e9d3381-0607-43df-866d-14ee5d65a294");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
