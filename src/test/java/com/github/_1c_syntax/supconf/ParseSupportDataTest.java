package com.github._1c_syntax.supconf;

import com.github._1c_syntax.bsl.support.SupportVariant;
import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ParseSupportDataTest {

  @Test
  void readSimpleEdt() {
    var path = Path.of("src/test/resources/support/edt/src/Configuration/ParentConfigurations.bin");
    var pathConfiguration = Path.of("src/test/resources/support/edt/src/Configuration/Configuration.mdo");
    var result = ParseSupportData.readSimple(path);

    assertThat(result)
      .hasSize(9)
      .contains(MapEntry.entry("3c907782-1b24-440c-b0de-1d62cebde27b", SupportVariant.NOT_EDITABLE))
      .contains(MapEntry.entry("df133230-33b2-42ac-8b8b-aae801d5007f", SupportVariant.NOT_EDITABLE))
      .contains(MapEntry.entry("50791551-3395-4b3f-94e4-c4dac0be017f", SupportVariant.EDITABLE_SUPPORT_ENABLED))
    ;

    var supportVariant = ParseSupportData.getSupportVariantByMDO(
      "50791551-3395-4b3f-94e4-c4dac0be017f", pathConfiguration);
    assertThat(supportVariant).isEqualTo(SupportVariant.EDITABLE_SUPPORT_ENABLED);
  }

  @Test
  void readSimpleDesignerFullSupport() {
    var path = Path.of("src/test/resources/support/designer-full-support/Ext/ParentConfigurations.bin");
    var pathConfiguration = Path.of("src/test/resources/support/edt/src/Configuration/Configuration.mdo");
    var result = ParseSupportData.readSimple(path);

    assertThat(result)
      .hasSize(9)
      .contains(MapEntry.entry("28777e74-89cf-4993-8a0a-a5d2b9a758b9", SupportVariant.NOT_EDITABLE))
      .contains(MapEntry.entry("2b5d5d5d-3fa5-4448-a8e3-13011eb483cb", SupportVariant.NOT_EDITABLE))
      .contains(MapEntry.entry("50791551-3395-4b3f-94e4-c4dac0be017f", SupportVariant.NOT_EDITABLE))
    ;

    var supportVariant = ParseSupportData.getSupportVariantByMDO(
      "50791551-3395-4b3f-94e4-c4dac0be017f", pathConfiguration);
    assertThat(supportVariant).isEqualTo(SupportVariant.NONE);
  }

  @Test
  void readSimpleCorrectSupport() {
    var path = Path.of("src/test/resources/support/support/correct/Ext/ParentConfigurations.bin");
    var result = ParseSupportData.readSimple(path);

    assertThat(result)
      .hasSize(39784)
      .contains(MapEntry.entry("00035364-b591-4e6a-9219-e27dac18f687", SupportVariant.EDITABLE_SUPPORT_ENABLED))
    ;
  }

  @Test
  void readSimpleIncorrectSupport() {
    var path = Path.of("src/test/resources/support/support/incorrect/Ext/ParentConfigurations.bin");
    var result = ParseSupportData.readSimple(path);

    assertThat(result).isEmpty();
  }
}