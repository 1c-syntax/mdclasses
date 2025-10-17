package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.BusinessProcess;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class MDClassesSolutionTest {

  @Test
  void createSolutionEmpty() {
    var solutionEmpty = MDClasses.createSolution(Path.of("src/test/resources/fixtures"));
    assertThat(solutionEmpty).isEqualTo(Configuration.EMPTY);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "src/test/resources/ext/designer/mdclasses/src/cf",
      "src/test/resources/ext/edt/mdclasses_3_27/configuration",
      "src/test/resources/ext/designer/mdclasses_ext/src/cf"
    }
  )
  void createSolutionSimple(ArgumentsAccessor argumentsAccessor) {
    var path = Path.of(argumentsAccessor.getString(0));
    var solutionCf = MDClasses.createSolution(path);
    var cf = MDClasses.createConfigurations(path).get(0);
    Assertions.assertThat(MDTestUtils.createJson(solutionCf), true)
      .isEqual(MDTestUtils.createJson(cf));
  }

  @Test
  void createSolutionCf_2_exts() {
    var solution = MDClasses.createSolution(Path.of("src/test/resources/solutions/sol1"));
    assertThat(solution).isInstanceOf(Configuration.class);
    var cf = (Configuration) solution;
    assertThat(cf.getSupportVariant()).isEqualTo(SupportVariant.NONE);
    assertThat(cf.getModules()).hasSize(3);

    assertThat(cf.getAllModules()).hasSize(5);

    assertThat(cf.getRoles()).hasSize(2);
    assertThat(cf.getLanguages()).hasSize(1);
    assertThat(cf.getConstants()).hasSize(1);
    assertThat(cf.getCatalogs()).hasSize(3);
    assertThat(cf.getDocuments()).hasSize(1);
    assertThat(cf.getEnums()).hasSize(1);
    assertThat(cf.getDataProcessors()).hasSize(1);
    assertThat(cf.getChildren()).hasSize(10);
    assertThat(cf.getPlainChildren()).hasSize(52);

    assertThat(cf.getModulesByType()).hasSize(5)
      .containsValue(ModuleType.FormModule)
    ;

    assertThat(cf.getModulesByObject())
      .hasSize(5)
      .containsValue(cf.findChild(MdoReference.create("Catalog.Расш1_Справочник3.Form.ФормаЭлемента")).get())
    ;
  }
}