package com.github._1c_syntax.bsl.mdclasses;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class MDClassesTest {
  @Test
  void createConfigurations() {
    var srcPath = Paths.get("src/test/resources/ext");
    var mdcs = MDClasses.createConfigurations(srcPath);
    assertThat(mdcs).hasSize(8);

    // каталоги с обработками не читаются
    srcPath = Paths.get("src/test/resources/ext/edt/external");
    mdcs = MDClasses.createConfigurations(srcPath);
    assertThat(mdcs).isEmpty();
  }

  @Test
  void createExternalSources() {
    var srcPath = Paths.get("src/test/resources/ext");
    var mdcs = MDClasses.createExternalSources(srcPath);
    assertThat(mdcs).hasSize(4);

    // каталоги с конфигурацией не читаются
    srcPath = Paths.get("src/test/resources/ext/edt/ssl_3_1");
    mdcs = MDClasses.createExternalSources(srcPath);
    assertThat(mdcs).isEmpty();
  }

  @Test
  void create() {
    var srcPath = Paths.get("src/test/resources/ext");
    var mdcs = MDClasses.create(srcPath);
    assertThat(mdcs).hasSize(12);
  }
}
