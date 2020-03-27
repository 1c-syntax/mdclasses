package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class SubsystemTest {

  private static final String SRC_EDT = "src/test/resources/metadata/edt/src";
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";

  @Test
  void testEDT() {
    Subsystem mdo = (Subsystem) MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.SUBSYSTEM, getMDOPathEDT("Subsystems/ПерваяПодсистема/ПерваяПодсистема.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo.getChildren()).isNotNull();
    assertThat(mdo.getChildren()).hasSize(2);

    mdo = (Subsystem) MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.SUBSYSTEM, getMDOPathEDT("Subsystems/ПерваяПодсистема/Subsystems/ПочиненнаяСистема2/ПочиненнаяСистема2.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo.getChildren()).isNotNull();
    assertThat(mdo.getChildren()).hasSize(2);
  }

  @Test
  void testDesigner() {
    Subsystem mdo = (Subsystem) MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.SUBSYSTEM, getMDOPathDesigner("Subsystems/ПерваяПодсистема.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo.getChildren()).isNotNull();
    assertThat(mdo.getChildren()).hasSize(4);

    mdo = (Subsystem) MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.SUBSYSTEM, getMDOPathDesigner("Subsystems/ПерваяПодсистема/Subsystems/ПочиненнаяСистема2.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo.getChildren()).isNotNull();
    assertThat(mdo.getChildren()).hasSize(2);
  }

  private Path getMDOPathEDT(String path) {
    return Paths.get(SRC_EDT, path);
  }

  private Path getMDOPathDesigner(String path) {
    return Paths.get(SRC_DESIGNER, path);
  }

}