package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SettingsStorageTest extends AbstractMDOTest {
  SettingsStorageTest() {
    super(MDOType.SETTINGS_STORAGE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("SettingsStorages/ХранилищеНастроек1/ХранилищеНастроек1.mdo");
    checkBaseField(mdo, SettingsStorage.class, "ХранилищеНастроек1",
      "e7a9947d-7565-4681-b75c-c9a229b45042");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "SettingsStorages/ХранилищеНастроек1",
      ModuleType.ManagerModule);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("SettingsStorages/ХранилищеНастроек1.xml");
    checkBaseField(mdo, SettingsStorage.class, "ХранилищеНастроек1",
      "e7a9947d-7565-4681-b75c-c9a229b45042");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }
}
