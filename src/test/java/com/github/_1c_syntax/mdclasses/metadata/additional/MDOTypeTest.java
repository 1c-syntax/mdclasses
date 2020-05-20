package com.github._1c_syntax.mdclasses.metadata.additional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDOTypeTest {

  @Test
  void testRuEn() {
    var nameRu = "Справочник";
    var nameEn = "Catalog";

    var groupRu = "Справочники";
    var groupEn = "Catalogs";

    var wrong = "Справочнег";

    assertThat(MDOType.fromValue(nameEn)).isEqualTo(MDOType.fromValue(nameRu));
    assertThat(MDOType.fromValue(groupEn)).isEqualTo(MDOType.fromValue(groupRu));
    assertThat(MDOType.fromValue(groupEn)).isEqualTo(MDOType.fromValue(nameEn));
    assertThat(MDOType.fromValue(wrong)).isEmpty();
  }
}