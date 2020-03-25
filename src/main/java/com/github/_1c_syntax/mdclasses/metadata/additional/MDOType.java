package com.github._1c_syntax.mdclasses.metadata.additional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MDOType {
  ACCOUNTING_REGISTER("AccountingRegister", "AccountingRegisters"),
  ACCUMULATION_REGISTER("AccumulationRegister", "AccumulationRegisters"),
  BUSINESS_PROCESS("BusinessProcess", "BusinessProcesses"),
  CALCULATION_REGISTER("CalculationRegister", "CalculationRegisters"),
  CATALOG("Catalog", "Catalogs"),
  CHART_OF_ACCOUNTS("ChartOfAccounts", "ChartsOfAccounts"),
  CHART_OF_CALCULATION_TYPES("ChartOfCalculationTypes", "ChartsOfCalculationTypes"),
  CHART_OF_CHARACTERISTIC_TYPES("ChartOfCharacteristicTypes", "ChartsOfCharacteristicTypes"),
  COMMAND_GROUP("CommandGroup", "CommandGroups"),
  COMMON_ATTRIBUTE("CommonAttribute", "CommonAttributes"),
  COMMON_COMMAND("CommonCommand", "CommonCommands"),
  COMMON_FORM("CommonForm", "CommonForms"),
  COMMON_MODULE("CommonModule", "CommonModules"),
  COMMON_PICTURE("CommonPicture", "CommonPictures"),
  COMMON_TEMPLATE("CommonTemplate", "CommonTemplates"),
  CONFIGURATION("Configuration", ""),
  CONSTANT("Constant", "Constants"),
  DATA_PROCESSOR("DataProcessor", "DataProcessors"),
  DEFINED_TYPE("DefinedType", "DefinedTypes"),
  DOCUMENT_JOURNAL("DocumentJournal", "DocumentJournals"),
  DOCUMENT_NUMERATOR("DocumentNumerator", "DocumentNumerators"),
  DOCUMENT("Document", "Documents"),
  ENUM("Enum", "Enums"),
  EVENT_SUBSCRIPTION("EventSubscription", "EventSubscriptions"),
  EXCHANGE_PLAN("ExchangePlan", "ExchangePlans"),
  FILTER_CRITERION("FilterCriterion", "FilterCriteria"),
  FUNCTIONAL_OPTION("FunctionalOption", "FunctionalOptions"),
  FUNCTIONAL_OPTIONS_PARAMETER("FunctionalOptionsParameter", "FunctionalOptionsParameters"),
  HTTP_SERVICE("HTTPService", "HTTPServices"),
  INFORMATION_REGISTER("InformationRegister", "InformationRegisters"),
  INTERFACE("Interface", "Interfaces"),
  LANGUAGE("Language", "Languages"),
  REPORT("Report", "Reports"),
  ROLE("Role", "Roles"),
  SCHEDULED_JOB("ScheduledJob", "ScheduledJobs"),
  SEQUENCE("Sequence", "Sequences"),
  SESSION_PARAMETER("SessionParameter", "SessionParameters"),
  SETTINGS_STORAGE("SettingsStorage", "SettingsStorages"),
  STYLE_ITEM("StyleItem", "StyleItems"),
  STYLE("Style", "Styles"),
  SUBSYSTEM("Subsystem", "Subsystems"),
  TASK("Task", "Tasks"),
  WEB_SERVICE("WebService", "WebServices"),
  WS_REFERENCE("WSReference", "WSReferences"),
  XDTO_PACKAGE("XDTOPackage", "XDTOPackages"),

  FORM("Form", "Forms"),
  COMMAND("Command", "Commands"),
  ;

  private String shortClassName;
  private String groupName;

  MDOType(String shortName, String groupName) {
    this.shortClassName = shortName;
    this.groupName = groupName;
  }

  public String getShortClassName() {
    if (this == CONFIGURATION
      || this == ENUM
      || this == INTERFACE) {
      return "MDO" + shortClassName;
    }
    return shortClassName;
  }

  public static List<MDOType> values(boolean withoutChildren) {
    if (withoutChildren) {
      return Arrays.stream(values()).filter(mdoType -> mdoType != FORM && mdoType != COMMAND)
        .collect(Collectors.toList());
    }
    return Arrays.asList(values());
  }

  public String getGroupName() {
    return groupName;
  }

}