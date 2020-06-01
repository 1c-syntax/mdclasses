package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.AccountingRegister;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class XStreamFactoryTest {

  private static final String SRC_EDT = "src/test/resources/metadata/edt/src";
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";

  @Test
  void test() {
    var mdo = MDOFactory.readMDObject(ConfigurationSource.EDT, MDOType.ACCOUNTING_REGISTER, getMDOPathEDT("AccountingRegisters/РегистрБухгалтерии1/РегистрБухгалтерии1.mdo"));
    assertThat(mdo)
      .isPresent()
      .containsInstanceOf(AccountingRegister.class)
      .map(MDObjectBase::getName)
      .contains("РегистрБухгалтерии1");
    assertThat(mdo)
      .map(MDObjectBase::getUuid)
      .contains("e5930f2f-15d9-48a1-ac69-379ad990b02a");

    var mdo2 = MDOFactory.readMDObject(ConfigurationSource.DESIGNER, MDOType.ACCOUNTING_REGISTER, getMDOPathDesigner("AccountingRegisters/РегистрБухгалтерии1.xml"));
    assertThat(mdo2)
      .isPresent()
      .containsInstanceOf(AccountingRegister.class)
      .map(MDObjectBase::getName)
      .contains("РегистрБухгалтерии1");
    assertThat(mdo2)
      .map(MDObjectBase::getUuid)
      .contains("e5930f2f-15d9-48a1-ac69-379ad990b02a");

    assertThat(mdo).isEqualTo(mdo2);
  }

  private Path getMDOPathEDT(String path) {
    return Paths.get(SRC_EDT, path);
  }

  private Path getMDOPathDesigner(String path) {
    return Paths.get(SRC_DESIGNER, path);
  }
}