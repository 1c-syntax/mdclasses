package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.metadata.Configuration;
import com.github._1c_syntax.mdclasses.metadata.additional.ParseSupportData;
import com.github._1c_syntax.mdclasses.metadata.additional.SupportVariant;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseSupportDataTest {

  private final File parentConfigurationsBin = new File("src/test/resources", "ParentConfigurations.bin");

  @Test
  public void testRead() {
    ParseSupportData parseSupportData = new ParseSupportData(parentConfigurationsBin.toPath());
    assertThat(parseSupportData.getSupportMap().size()).isNotZero();
  }

  @Test
  void testConfigurationSupportEDT() {

    final var PATH_TO_SUPPORT = "src/test/resources/support/edt";
    var srcPath = Paths.get(PATH_TO_SUPPORT).toAbsolutePath();
    var configuration = Configuration.create(srcPath);

    assertThat(configuration.getModulesBySupport().size()).isNotZero();

    var path = Paths.get(PATH_TO_SUPPORT, "src/Catalogs/ПервыйСправочник/ObjectModule.bsl").toAbsolutePath();
    assertThat(configuration.getModuleSupport(path.toUri()).containsValue(SupportVariant.NOT_EDITABLE)).isTrue();

    path = Paths.get(PATH_TO_SUPPORT, "src/Configuration/SessionModule.bsl").toAbsolutePath();
    assertThat(configuration.getModuleSupport(path.toUri()).containsValue(SupportVariant.EDITABLE_SUPPORT_ENABLED)).isTrue();

    path = Paths.get(PATH_TO_SUPPORT, "src/Documents/ПервыйДокумент/ObjectModule.bsl").toAbsolutePath();
    assertThat(configuration.getModuleSupport(path.toUri()).containsValue(SupportVariant.NOT_SUPPORTED)).isTrue();

    path = Paths.get(PATH_TO_SUPPORT, "src/Catalogs/ПервыйСправочник/Forms/ФормаЭлемента/Module.bsl").toAbsolutePath();
    assertThat(configuration.getModuleSupport(path.toUri()).containsValue(SupportVariant.NOT_EDITABLE)).isTrue();

  }

  @Test
  void testConfigurationSupportDesigner() {

    final var PATH_TO_SUPPORT = "src/test/resources/support/original";
    var srcPath = new File(PATH_TO_SUPPORT);
    var configuration = Configuration.create(srcPath.toPath());

    // пока просто проверим что там чтото есть
    assertThat(configuration.getModulesBySupport().size()).isNotZero();

    var path = Paths.get(PATH_TO_SUPPORT, "Catalogs/ПервыйСправочник/Ext/ObjectModule.bsl").toAbsolutePath();
    assertThat(configuration.getModuleSupport(path.toUri()).containsValue(SupportVariant.NOT_EDITABLE)).isTrue();

    path = Paths.get(PATH_TO_SUPPORT, "Ext/SessionModule.bsl").toAbsolutePath();
    assertThat(configuration.getModuleSupport(path.toUri()).containsValue(SupportVariant.EDITABLE_SUPPORT_ENABLED)).isTrue();

    path = Paths.get(PATH_TO_SUPPORT, "Documents/ПервыйДокумент/Ext/ObjectModule.bsl").toAbsolutePath();
    assertThat(configuration.getModuleSupport(path.toUri()).containsValue(SupportVariant.NOT_SUPPORTED)).isTrue();
    path = Paths.get(PATH_TO_SUPPORT, "Catalogs/ПервыйСправочник/Forms/ФормаЭлемента/Ext/Form/Module.bsl").toAbsolutePath();
    assertThat(configuration.getModuleSupport(path.toUri()).containsValue(SupportVariant.NOT_EDITABLE)).isTrue();
  }

}
