package com.github._1c_syntax.mdclasses.utils;

import com.github._1c_syntax.mdclasses.mdo.MDOConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class MDOUtilsTest {

  private static Map<URI, ModuleType> correctFilesAndTypesEDT(String srcPath) {
    Map<URI, ModuleType> correct = new HashMap<>();
    correct.put(Paths.get(srcPath, "src", "Documents", "Документ1", "Commands", "Команда", "CommandModule.bsl").toUri(), ModuleType.CommandModule);
    correct.put(Paths.get(srcPath, "src", "Documents", "Документ1", "Commands", "Команда2", "CommandModule.bsl").toUri(), ModuleType.CommandModule);
    correct.put(Paths.get(srcPath, "src", "CommonCommands", "ОбщаяКоманда1", "CommandModule.bsl").toUri(), ModuleType.CommandModule);
    correct.put(Paths.get(srcPath, "src", "Catalogs", "Справочник1", "Commands", "Команда1", "CommandModule.bsl").toUri(), ModuleType.CommandModule);
    correct.put(Paths.get(srcPath, "src", "Configuration", "ExternalConnectionModule.bsl").toUri(), ModuleType.ExternalConnectionModule);
    correct.put(Paths.get(srcPath, "src", "Configuration", "ManagedApplicationModule.bsl").toUri(), ModuleType.ManagedApplicationModule);
    correct.put(Paths.get(srcPath, "src", "SettingsStorages", "ХранилищеНастроек1", "ManagerModule.bsl").toUri(), ModuleType.ManagerModule);
    correct.put(Paths.get(srcPath, "src", "Reports", "Отчет1", "ManagerModule.bsl").toUri(), ModuleType.ManagerModule);
    correct.put(Paths.get(srcPath, "src", "FilterCriteria", "КритерийОтбора1", "ManagerModule.bsl").toUri(), ModuleType.ManagerModule);
    correct.put(Paths.get(srcPath, "src", "Enums", "Перечисление1", "ManagerModule.bsl").toUri(), ModuleType.ManagerModule);
    correct.put(Paths.get(srcPath, "src", "Documents", "Документ1", "ManagerModule.bsl").toUri(), ModuleType.ManagerModule);
    correct.put(Paths.get(srcPath, "src", "DocumentJournals", "ЖурналДокументов1", "ManagerModule.bsl").toUri(), ModuleType.ManagerModule);
    correct.put(Paths.get(srcPath, "src", "ChartsOfCharacteristicTypes", "ПланВидовХарактеристик1", "ManagerModule.bsl").toUri(), ModuleType.ManagerModule);
    correct.put(Paths.get(srcPath, "src", "Catalogs", "Справочник1", "ManagerModule.bsl").toUri(), ModuleType.ManagerModule);
    correct.put(Paths.get(srcPath, "src", "WebServices", "WebСервис1", "Module.bsl").toUri(), ModuleType.WEBServiceModule);
    correct.put(Paths.get(srcPath, "src", "HTTPServices", "HTTPСервис1", "Module.bsl").toUri(), ModuleType.HTTPServiceModule);
    correct.put(Paths.get(srcPath, "src", "Documents", "Документ1", "Forms", "ФормаДокумента", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "src", "CommonModules", "ПростойОбщийМодуль", "Module.bsl").toUri(), ModuleType.CommonModule);
    correct.put(Paths.get(srcPath, "src", "CommonModules", "ОбщийМодульПовтИспСеанс", "Module.bsl").toUri(), ModuleType.CommonModule);
    correct.put(Paths.get(srcPath, "src", "CommonModules", "ГлобальныйОбщийМодуль", "Module.bsl").toUri(), ModuleType.CommonModule);
    correct.put(Paths.get(srcPath, "src", "CommonForms", "Форма", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "src", "Catalogs", "Справочник1", "Forms", "ФормаЭлемента", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "src", "Catalogs", "Справочник1", "Forms", "ФормаСписка", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "src", "Catalogs", "Справочник1", "Forms", "ФормаВыбора", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "src", "Reports", "Отчет1", "ObjectModule.bsl").toUri(), ModuleType.ObjectModule);
    correct.put(Paths.get(srcPath, "src", "ExchangePlans", "ПланОбмена1", "ObjectModule.bsl").toUri(), ModuleType.ObjectModule);
    correct.put(Paths.get(srcPath, "src", "Documents", "Документ1", "ObjectModule.bsl").toUri(), ModuleType.ObjectModule);
    correct.put(Paths.get(srcPath, "src", "ChartsOfCharacteristicTypes", "ПланВидовХарактеристик1", "ObjectModule.bsl").toUri(), ModuleType.ObjectModule);
    correct.put(Paths.get(srcPath, "src", "ChartsOfAccounts", "ПланСчетов1", "ObjectModule.bsl").toUri(), ModuleType.ObjectModule);
    correct.put(Paths.get(srcPath, "src", "Catalogs", "Справочник1", "ObjectModule.bsl").toUri(), ModuleType.ObjectModule);
    correct.put(Paths.get(srcPath, "src", "BusinessProcesses", "БизнесПроцесс1", "ObjectModule.bsl").toUri(), ModuleType.ObjectModule);
    correct.put(Paths.get(srcPath, "src", "Sequences", "Последовательность1", "RecordSetModule.bsl").toUri(), ModuleType.RecordSetModule);
    correct.put(Paths.get(srcPath, "src", "InformationRegisters", "РегистрСведений1", "RecordSetModule.bsl").toUri(), ModuleType.RecordSetModule);
    correct.put(Paths.get(srcPath, "src", "Configuration", "SessionModule.bsl").toUri(), ModuleType.SessionModule);
    correct.put(Paths.get(srcPath, "src", "Constants", "Константа1", "ValueManagerModule.bsl").toUri(), ModuleType.ValueManagerModule);

    return correct;
  }

  private static Map<URI, ModuleType> correctFilesAndTypesDESIGNER(String srcPath) {
    Map<URI, ModuleType> correct = new HashMap<>();
    correct.put(Paths.get(srcPath, "Documents", "ПоступлениеТоваровУслуг", "Commands", "ПоступлениеТоваровУслуг", "Ext", "CommandModule.bsl").toUri(), ModuleType.CommandModule);
    correct.put(Paths.get(srcPath, "Documents", "ПоступлениеТоваровУслуг", "Commands", "ДокументыПоступления", "Ext", "CommandModule.bsl").toUri(), ModuleType.CommandModule);
    correct.put(Paths.get(srcPath, "CommonCommands", "ОбщаяКоманда1", "Ext", "CommandModule.bsl").toUri(), ModuleType.CommandModule);
    correct.put(Paths.get(srcPath, "Catalogs", "Справочник1", "Commands", "Команда1", "Ext", "CommandModule.bsl").toUri(), ModuleType.CommandModule);
    correct.put(Paths.get(srcPath, "Catalogs", "Справочник1", "Forms", "ФормаВыбора", "Ext", "Form", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "Documents", "ПоступлениеТоваровУслуг", "Ext", "ManagerModule.bsl").toUri(), ModuleType.ManagerModule);
    correct.put(Paths.get(srcPath, "Documents", "ПоступлениеТоваровУслуг", "Forms", "ФормаСпискаДокументов", "Ext", "Form", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "Documents", "ПоступлениеТоваровУслуг", "Forms", "ФормаСписка", "Ext", "Form", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "Documents", "ПоступлениеТоваровУслуг", "Forms", "ФормаПодбораТоваровИзЗаказа", "Ext", "Form", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "Documents", "ПоступлениеТоваровУслуг", "Forms", "ФормаДокумента", "Ext", "Form", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "Documents", "ПоступлениеТоваровУслуг", "Forms", "ФормаВыбораРаспоряжения", "Ext", "Form", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "Documents", "ПоступлениеТоваровУслуг", "Forms", "ФормаВыбора", "Ext", "Form", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "CommonModules", "ПростойОбщийМодуль", "Ext", "Module.bsl").toUri(), ModuleType.CommonModule);
    correct.put(Paths.get(srcPath, "CommonModules", "ОбщийМодульПовтИспСеанс", "Ext", "Module.bsl").toUri(), ModuleType.CommonModule);
    correct.put(Paths.get(srcPath, "CommonModules", "ОбщегоНазначенияПовторногоИспользования", "Ext", "Module.bsl").toUri(), ModuleType.CommonModule);
    correct.put(Paths.get(srcPath, "CommonModules", "ГлобальныйОбщийМодуль", "Ext", "Module.bsl").toUri(), ModuleType.CommonModule);
    correct.put(Paths.get(srcPath, "CommonModules", "ГлобальныйКлиент", "Ext", "Module.bsl").toUri(), ModuleType.CommonModule);
    correct.put(Paths.get(srcPath, "CommonForms", "Форма", "Ext", "Form", "Module.bsl").toUri(), ModuleType.FormModule);
    correct.put(Paths.get(srcPath, "Documents", "ПоступлениеТоваровУслуг", "Ext", "ObjectModule.bsl").toUri(), ModuleType.ObjectModule);
    correct.put(Paths.get(srcPath, "Ext", "ExternalConnectionModule.bsl").toUri(), ModuleType.ExternalConnectionModule);
    correct.put(Paths.get(srcPath, "Ext", "ManagedApplicationModule.bsl").toUri(), ModuleType.ManagedApplicationModule);
    correct.put(Paths.get(srcPath, "Ext", "SessionModule.bsl").toUri(), ModuleType.SessionModule);

    return correct;
  }

  @Test
  void getPathsTest() {
    Path rootPath = Paths.get("src/test/resources/metadata").toAbsolutePath();

    assertThat(MDOPathUtils.getMDOTypeFolder(rootPath, MDOType.SUBSYSTEM)).isNull();
    assertThat(MDOPathUtils.getMDOPath(ConfigurationSource.EDT, rootPath, MDOType.SUBSYSTEM, "Подсистема")).isEqualTo(
      Paths.get(rootPath.toString(), "src", "Subsystems", "Подсистема", "Подсистема.mdo"));
    assertThat(MDOPathUtils.getMDOPath(ConfigurationSource.DESIGNER, rootPath, MDOType.SUBSYSTEM, "Подсистема")).isEqualTo(
      Paths.get(rootPath.toString(), "Subsystems", "Подсистема.xml"));
    assertThat(MDOPathUtils.getMDOTypeFolder(ConfigurationSource.DESIGNER, rootPath, MDOType.SUBSYSTEM)).isEqualTo(
      Paths.get(rootPath.toString(), "Subsystems"));

    assertThat(MDOPathUtils.getMDOPath(ConfigurationSource.DESIGNER, rootPath, MDOType.SUBSYSTEM, "Подсистема3"))
      .isNotNull();
    assertThat(MDOPathUtils.getMDOPath(ConfigurationSource.DESIGNER, rootPath, MDOType.SUBSYSTEM, "Подсистема3")
      .toFile().exists()).isFalse();

    rootPath = Paths.get("src/test/resources/metadata", "edt", "Catalog");
    var mdoCatalog = MDOPathUtils.getMDOPath(ConfigurationSource.EDT, rootPath, "Справочник1");
    assertThat(mdoCatalog).isNotNull();
  }

  @Test
  void getMDOObjectTest() {

    // is edt configuration
    File srcPath = new File("src/test/resources/metadata/edt");
    MDObjectBase mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, srcPath.toPath(), MDOType.CONFIGURATION, "Configuration");
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof MDOConfiguration).isTrue();
    assertThat(mdo.getName()).isEqualTo("Конфигурация");

    mdo = MDOUtils.getMDObject(srcPath.toPath(), MDOType.CONFIGURATION, "Configuration");
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof MDOConfiguration).isTrue();
    assertThat(mdo.getName()).isEqualTo("Конфигурация");

    MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.CONFIGURATION, Paths.get(srcPath.toString(),
      "src", "Configuration", "Configuration.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof MDOConfiguration).isTrue();
    assertThat(mdo.getName()).isEqualTo("Конфигурация");

    // not exist designer subsystem
    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, srcPath.toPath(), MDOType.SUBSYSTEM, "ПерваяПодсистема");
    assertThat(mdo).isNull();

    // is't designer configuration
    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, srcPath.toPath(), MDOType.CONFIGURATION, "Configuration");
    assertThat(mdo).isNull();

  }

  @Test
  void getConfigurationSourceTest() {

    var rootPath = Paths.get("src/test/resources/metadata");
    var rootPathEDT = Paths.get("src/test/resources/metadata/edt");
    var rootPathDesigner = Paths.get("src/test/resources/metadata/original");

    assertThat(MDOUtils.getConfigurationSourceByPath(rootPath)).isEqualTo(ConfigurationSource.EMPTY);
    assertThat(MDOUtils.getConfigurationSourceByPath(rootPathDesigner)).isEqualTo(ConfigurationSource.DESIGNER);
    assertThat(MDOUtils.getConfigurationSourceByPath(rootPathEDT)).isEqualTo(ConfigurationSource.EDT);
  }

  @Test
  void getModuleTypesByPathEDT() {
    File srcPath = new File("src/test/resources/metadata/edt");
    Map<URI, ModuleType> correct = correctFilesAndTypesEDT(srcPath.toString());
    var moduleTypesByPath = MDOUtils.getModuleTypesByPath(srcPath.toPath());
    assertThat(moduleTypesByPath).isNotNull();
    assertThat(moduleTypesByPath).isNotEmpty();
    correct.forEach((uri, moduleType) -> {
      var value = moduleTypesByPath.get(uri);
      LOGGER.debug(uri.toString() + " = " + moduleType.toString());
      assertThat(value).isNotNull();
      assertThat(value).isEqualTo(moduleType);
    });

    assertThat(moduleTypesByPath.size()).isEqualTo(correct.size());
  }

  @Test
  void getModuleTypesByPathDESIGNER() {
    File srcPath = new File("src/test/resources/metadata/original");
    Map<URI, ModuleType> correct = correctFilesAndTypesDESIGNER(srcPath.toString());
    var moduleTypesByPath = MDOUtils.getModuleTypesByPath(srcPath.toPath());
    assertThat(moduleTypesByPath).isNotNull();
    assertThat(moduleTypesByPath).isNotEmpty();
    correct.forEach((uri, moduleType) -> {
      var value = moduleTypesByPath.get(uri);
      LOGGER.debug(uri.toString() + " = " + moduleType.toString());
      assertThat(value).isNotNull();
      assertThat(value).isEqualTo(moduleType);
    });

    assertThat(moduleTypesByPath.size()).isEqualTo(correct.size());
  }
}
