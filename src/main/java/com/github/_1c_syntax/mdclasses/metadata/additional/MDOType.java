package com.github._1c_syntax.mdclasses.metadata.additional;

public enum MDOType {
  ACCOUNTING_REGISTER("AccountingRegister", "AccountingRegisters", true, true),
  ACCUMULATION_REGISTER("AccumulationRegister", "AccumulationRegisters", true, true),
  BUSINESS_PROCESS("BusinessProcess", "BusinessProcesses", true, true),
  CALCULATION_REGISTER("CalculationRegister", "CalculationRegisters", true, true),
  CATALOG("Catalog", "Catalogs", true, true),
  CHART_OF_ACCOUNTS("ChartOfAccounts", "ChartsOfAccounts", true, true),
  CHART_OF_CALCULATION_TYPES("ChartOfCalculationTypes", "ChartsOfCalculationTypes", true, true),
  CHART_OF_CHARACTERISTIC_TYPES("ChartOfCharacteristicTypes", "ChartsOfCharacteristicTypes", true, true),
  COMMAND_GROUP("CommandGroup", "CommandGroups", false, false),
  COMMON_ATTRIBUTE("CommonAttribute", "CommonAttributes", false, false),
  COMMON_COMMAND("CommonCommand", "CommonCommands", false, false),
  COMMON_FORM("CommonForm", "CommonForms", false, false),
  COMMON_MODULE("CommonModule", "CommonModules", false, false),
  COMMON_PICTURE("CommonPicture", "CommonPictures", false, false),
  COMMON_TEMPLATE("CommonTemplate", "CommonTemplates", false, false),
  CONFIGURATION("Configuration", "", false, false),
  CONSTANT("Constant", "Constants", false, false),
  DATA_PROCESSOR("DataProcessor", "DataProcessors", true, true),
  DEFINED_TYPE("DefinedType", "DefinedTypes", false, false),
  DOCUMENT_JOURNAL("DocumentJournal", "DocumentJournals", true, true),
  DOCUMENT_NUMERATOR("DocumentNumerator", "DocumentNumerators", false, false),
  DOCUMENT("Document", "Documents", true, true),
  ENUM("Enum", "Enums", true, true),
  EVENT_SUBSCRIPTION("EventSubscription", "EventSubscriptions", false, false),
  EXCHANGE_PLAN("ExchangePlan", "ExchangePlans", true, true),
  FILTER_CRITERION("FilterCriterion", "FilterCriteria", true, true),
  FUNCTIONAL_OPTION("FunctionalOption", "FunctionalOptions", false, false),
  FUNCTIONAL_OPTIONS_PARAMETER("FunctionalOptionsParameter", "FunctionalOptionsParameters", false, false),
  HTTP_SERVICE("HTTPService", "HTTPServices", false, false),
  INFORMATION_REGISTER("InformationRegister", "InformationRegisters", true, true),
  REPORT("Report", "Reports", true, true),
  ROLE("Role", "Roles", false, false),
  SCHEDULED_JOB("ScheduledJob", "ScheduledJobs", false, false),
  SEQUENCE("Sequence", "Sequences", true, true),
  SESSION_PARAMETER("SessionParameter", "SessionParameters", false, false),
  SETTINGS_STORAGE("SettingsStorage", "SettingsStorages", true, true),
  STYLE_ITEM("StyleItem", "StyleItems", false, false),
  STYLE("Style", "Styles", false, false),
  SUBSYSTEM("Subsystem", "Subsystems", false, false),
  TASK("Task", "Tasks", true, true),
  WEB_SERVICE("WebService", "WebServices", false, false),
  WS_REFERENCE("WSReference", "WSReferences", false, false),
  XDTO_PACKAGE("XDTOPackage", "XDTOPackages", false, false);

  private String shortClassName;
  private String groupName;
  private boolean mayHaveForm;
  private boolean mayHaveCommand;

  MDOType(String shortName, String groupName, boolean mayHaveForm, boolean mayHaveCommand) {
    this.shortClassName = shortName;
    this.groupName = groupName;
    this.mayHaveForm = mayHaveForm;
    this.mayHaveCommand = mayHaveCommand;
  }

  public String getShortClassName() {
    if (this.equals(CONFIGURATION) || this.equals(ENUM)) {
      return "MDO" + shortClassName;
    }
    return shortClassName;
  }

  public String getGroupName() {
    return groupName;
  }

  public boolean isMayHaveForm() {
    return mayHaveForm;
  }

  public boolean isMayHaveCommand() {
    return mayHaveCommand;
  }
}