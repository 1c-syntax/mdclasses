

package com.github._1c_syntax.mdclasses.mdosource.original;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurationChildObjects {

  @JsonProperty("Language")
  protected List<String> language;
  @JsonProperty("Subsystem")
  protected List<String> subsystem;
  @JsonProperty("StyleItem")
  protected List<String> styleItem;
  @JsonProperty("Style")
  protected List<String> style;
  @JsonProperty("CommonPicture")
  protected List<String> commonPicture;
  @JsonProperty("Interface")
  protected List<String> _interface;
  @JsonProperty("SessionParameter")
  protected List<String> sessionParameter;
  @JsonProperty("Role")
  protected List<String> role;
  @JsonProperty("CommonTemplate")
  protected List<String> commonTemplate;
  @JsonProperty("FilterCriterion")
  protected List<String> filterCriterion;
  @JsonProperty("CommonModule")
  protected List<String> commonModule;
  @JsonProperty("CommonAttribute")
  protected List<String> commonAttribute;
  @JsonProperty("ExchangePlan")
  protected List<String> exchangePlan;
  @JsonProperty("XDTOPackage")
  protected List<String> xdtoPackage;
  @JsonProperty("WebService")
  protected List<String> webService;
  @JsonProperty("HTTPService")
  protected List<String> httpService;
  @JsonProperty("WSReference")
  protected List<String> wsReference;
  @JsonProperty("EventSubscription")
  protected List<String> eventSubscription;
  @JsonProperty("ScheduledJob")
  protected List<String> scheduledJob;
  @JsonProperty("SettingsStorage")
  protected List<String> settingsStorage;
  @JsonProperty("FunctionalOption")
  protected List<String> functionalOption;
  @JsonProperty("FunctionalOptionsParameter")
  protected List<String> functionalOptionsParameter;
  @JsonProperty("DefinedType")
  protected List<String> definedType;
  @JsonProperty("CommonCommand")
  protected List<String> commonCommand;
  @JsonProperty("CommandGroup")
  protected List<String> commandGroup;
  @JsonProperty("Constant")
  protected List<String> constant;
  @JsonProperty("CommonForm")
  protected List<String> commonForm;
  @JsonProperty("Catalog")
  protected List<String> catalog;
  @JsonProperty("Document")
  protected List<String> document;
  @JsonProperty("DocumentNumerator")
  protected List<String> documentNumerator;
  @JsonProperty("Sequence")
  protected List<String> sequence;
  @JsonProperty("DocumentJournal")
  protected List<String> documentJournal;
  @JsonProperty("Enum")
  protected List<String> _enum;
  @JsonProperty("Report")
  protected List<String> report;
  @JsonProperty("DataProcessor")
  protected List<String> dataProcessor;
  @JsonProperty("InformationRegister")
  protected List<String> informationRegister;
  @JsonProperty("AccumulationRegister")
  protected List<String> accumulationRegister;
  @JsonProperty("ChartOfCharacteristicTypes")
  protected List<String> chartOfCharacteristicTypes;
  @JsonProperty("ChartOfAccounts")
  protected List<String> chartOfAccounts;
  @JsonProperty("AccountingRegister")
  protected List<String> accountingRegister;
  @JsonProperty("ChartOfCalculationTypes")
  protected List<String> chartOfCalculationTypes;
  @JsonProperty("CalculationRegister")
  protected List<String> calculationRegister;
  @JsonProperty("BusinessProcess")
  protected List<String> businessProcess;
  @JsonProperty("Task")
  protected List<String> task;
  @JsonProperty("ExternalDataSource")
  protected List<String> externalDataSource;

}
