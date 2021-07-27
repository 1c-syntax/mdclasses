package com.github._1c_syntax.bsl.mdo.support;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ScriptVariantTest {
  @ParameterizedTest(name = "{index}: {0}")
  @CsvSource(
    {
      "RUSSIAN,русский,ru,RussIan",
      "ENGLISH,английский,en,engLish",
      "RUSSIAN,ру,рус,eng"
    }
  )
  void testValueByString(ArgumentsAccessor argumentsAccessor) {
    var element = ScriptVariant.valueOf(argumentsAccessor.getString(0));
    assertThat(ScriptVariant.valueByString(argumentsAccessor.getString(1))).isEqualTo(element);
    assertThat(ScriptVariant.valueByString(argumentsAccessor.getString(2))).isEqualTo(element);
    assertThat(ScriptVariant.valueByString(argumentsAccessor.getString(3))).isEqualTo(element);
  }
}
