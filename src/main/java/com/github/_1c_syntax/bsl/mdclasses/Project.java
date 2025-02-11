package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.AccountingRegister;
import com.github._1c_syntax.bsl.mdo.AccumulationRegister;
import com.github._1c_syntax.bsl.mdo.Bot;
import com.github._1c_syntax.bsl.mdo.BusinessProcess;
import com.github._1c_syntax.bsl.mdo.CalculationRegister;
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.ChartOfAccounts;
import com.github._1c_syntax.bsl.mdo.ChartOfCalculationTypes;
import com.github._1c_syntax.bsl.mdo.ChartOfCharacteristicTypes;
import com.github._1c_syntax.bsl.mdo.CommandGroup;
import com.github._1c_syntax.bsl.mdo.CommonAttribute;
import com.github._1c_syntax.bsl.mdo.CommonCommand;
import com.github._1c_syntax.bsl.mdo.CommonForm;
import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.CommonPicture;
import com.github._1c_syntax.bsl.mdo.CommonTemplate;
import com.github._1c_syntax.bsl.mdo.Constant;
import com.github._1c_syntax.bsl.mdo.DataProcessor;
import com.github._1c_syntax.bsl.mdo.DefinedType;
import com.github._1c_syntax.bsl.mdo.Document;
import com.github._1c_syntax.bsl.mdo.DocumentJournal;
import com.github._1c_syntax.bsl.mdo.DocumentNumerator;
import com.github._1c_syntax.bsl.mdo.Enum;
import com.github._1c_syntax.bsl.mdo.EventSubscription;
import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.ExternalDataSource;
import com.github._1c_syntax.bsl.mdo.FilterCriterion;
import com.github._1c_syntax.bsl.mdo.FunctionalOption;
import com.github._1c_syntax.bsl.mdo.FunctionalOptionsParameter;
import com.github._1c_syntax.bsl.mdo.HTTPService;
import com.github._1c_syntax.bsl.mdo.InformationRegister;
import com.github._1c_syntax.bsl.mdo.IntegrationService;
import com.github._1c_syntax.bsl.mdo.Interface;
import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.PaletteColor;
import com.github._1c_syntax.bsl.mdo.Report;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.ScheduledJob;
import com.github._1c_syntax.bsl.mdo.Sequence;
import com.github._1c_syntax.bsl.mdo.SessionParameter;
import com.github._1c_syntax.bsl.mdo.SettingsStorage;
import com.github._1c_syntax.bsl.mdo.Style;
import com.github._1c_syntax.bsl.mdo.StyleItem;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.Task;
import com.github._1c_syntax.bsl.mdo.WSReference;
import com.github._1c_syntax.bsl.mdo.WebService;
import com.github._1c_syntax.bsl.mdo.XDTOPackage;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Singular;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Проект решения, включающий в себя конфигурацию, расширения и внешние обработки и отчеты
 */
@Value
@Builder
public class Project implements ConfigurationTree {
  /**
   * Основная конфигурация
   */
  @Default
  CF configuration = Configuration.EMPTY;

  /**
   * Набор расширений конфигурации
   */
  @Default
  Map<String, ConfigurationExtension> extensions = new HashMap<>();

  @Singular
  List<Subsystem> subsystems;
  @Singular
  List<CommonModule> commonModules;
  @Singular
  List<SessionParameter> sessionParameters;
  @Singular
  List<Role> roles;
  @Singular
  List<CommonAttribute> commonAttributes;
  @Singular
  List<ExchangePlan> exchangePlans;
  @Singular("filterCriterion")
  List<FilterCriterion> filterCriteria;
  @Singular
  List<EventSubscription> eventSubscriptions;
  @Singular
  List<ScheduledJob> scheduledJobs;
  @Singular
  List<Bot> bots;
  @Singular
  List<FunctionalOption> functionalOptions;
  @Singular
  List<FunctionalOptionsParameter> functionalOptionsParameters;
  @Singular
  List<DefinedType> definedTypes;
  @Singular
  List<SettingsStorage> settingsStorages;
  @Singular
  List<CommonForm> commonForms;
  @Singular
  List<CommonCommand> commonCommands;
  @Singular
  List<CommandGroup> commandGroups;
  @Singular
  List<CommonTemplate> commonTemplates;
  @Singular
  List<CommonPicture> commonPictures;
  @Singular("Interface")
  List<Interface> interfaces;
  @Singular
  List<XDTOPackage> xDTOPackages;
  @Singular
  List<WebService> webServices;
  @Singular
  List<HTTPService> httpServices;
  @Singular
  List<WSReference> wsReferences;
  @Singular
  List<IntegrationService> integrationServices;
  @Singular
  List<StyleItem> styleItems;
  @Singular
  List<PaletteColor> paletteColors;
  @Singular
  List<Style> styles;
  @Singular
  List<Language> languages;
  @Singular
  List<Constant> constants;
  @Singular
  List<Catalog> catalogs;
  @Singular
  List<Document> documents;
  @Singular
  List<DocumentNumerator> documentNumerators;
  @Singular
  List<Sequence> sequences;
  @Singular
  List<DocumentJournal> documentJournals;
  @Singular("Enum")
  List<Enum> enums;
  @Singular
  List<Report> reports;
  @Singular
  List<DataProcessor> dataProcessors;
  @Singular("chartOfCharacteristicTypes")
  List<ChartOfCharacteristicTypes> chartsOfCharacteristicTypes;
  @Singular("chartOfAccounts")
  List<ChartOfAccounts> chartsOfAccounts;
  @Singular("chartOfCalculationTypes")
  List<ChartOfCalculationTypes> chartsOfCalculationTypes;
  @Singular
  List<InformationRegister> informationRegisters;
  @Singular
  List<AccumulationRegister> accumulationRegisters;
  @Singular
  List<AccountingRegister> accountingRegisters;
  @Singular
  List<CalculationRegister> calculationRegisters;
  @Singular
  List<BusinessProcess> businessProcesses;
  @Singular
  List<Task> tasks;
  @Singular
  List<ExternalDataSource> externalDataSources;

  public static Project create(CF configuration) {
    if (configuration.isEmpty()) {
      return Project.builder().build();
    }

    var builder = Project.builder();
    builder.configuration(configuration);
    builder.subsystems(configuration.getSubsystems());
    builder.commonModules(configuration.getCommonModules());
    builder.sessionParameters(configuration.getSessionParameters());
    builder.roles(configuration.getRoles());
    builder.commonAttributes(configuration.getCommonAttributes());
    builder.exchangePlans(configuration.getExchangePlans());
    builder.filterCriteria(configuration.getFilterCriteria());
    builder.eventSubscriptions(configuration.getEventSubscriptions());
    builder.scheduledJobs(configuration.getScheduledJobs());
    builder.bots(configuration.getBots());
    builder.functionalOptions(configuration.getFunctionalOptions());
    builder.functionalOptionsParameters(configuration.getFunctionalOptionsParameters());
    builder.definedTypes(configuration.getDefinedTypes());
    builder.settingsStorages(configuration.getSettingsStorages());
    builder.commonForms(configuration.getCommonForms());
    builder.commonCommands(configuration.getCommonCommands());
    builder.commandGroups(configuration.getCommandGroups());
    builder.commonTemplates(configuration.getCommonTemplates());
    builder.commonPictures(configuration.getCommonPictures());
    builder.interfaces(configuration.getInterfaces());
    builder.xDTOPackages(configuration.getXDTOPackages());
    builder.webServices(configuration.getWebServices());
    builder.httpServices(configuration.getHttpServices());
    builder.wsReferences(configuration.getWsReferences());
    builder.integrationServices(configuration.getIntegrationServices());
    builder.styleItems(configuration.getStyleItems());
    builder.paletteColors(configuration.getPaletteColors());
    builder.styles(configuration.getStyles());
    builder.languages(configuration.getLanguages());
    builder.constants(configuration.getConstants());
    builder.catalogs(configuration.getCatalogs());
    builder.documents(configuration.getDocuments());
    builder.documentNumerators(configuration.getDocumentNumerators());
    builder.sequences(configuration.getSequences());
    builder.documentJournals(configuration.getDocumentJournals());
    builder.enums(configuration.getEnums());
    builder.reports(configuration.getReports());
    builder.dataProcessors(configuration.getDataProcessors());
    builder.chartsOfCharacteristicTypes(configuration.getChartsOfCharacteristicTypes());
    builder.chartsOfAccounts(configuration.getChartsOfAccounts());
    builder.chartsOfCalculationTypes(configuration.getChartsOfCalculationTypes());
    builder.informationRegisters(configuration.getInformationRegisters());
    builder.accumulationRegisters(configuration.getAccumulationRegisters());
    builder.accountingRegisters(configuration.getAccountingRegisters());
    builder.calculationRegisters(configuration.getCalculationRegisters());
    builder.businessProcesses(configuration.getBusinessProcesses());
    builder.tasks(configuration.getTasks());
    builder.externalDataSources(configuration.getExternalDataSources());
    return builder.build();
  }

  public void addExtension(ConfigurationExtension extension) {

  }
}
