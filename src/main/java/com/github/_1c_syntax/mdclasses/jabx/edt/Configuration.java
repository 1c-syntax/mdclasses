

package com.github._1c_syntax.mdclasses.jabx.edt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;

import java.util.List;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration
  extends MDObjectBase {

  protected String configurationExtensionCompatibilityMode;
  protected String defaultRunMode;
  protected String usePurposes;
  protected String scriptVariant;
  protected String defaultLanguage;
  protected String dataLockControlMode;
  protected String objectAutonumerationMode;
  protected String modalityUseMode;
  protected String synchronousPlatformExtensionAndAddInCallUseMode;
  protected String compatibilityMode;
  protected List<String> subsystems;
  protected List<String> styleItems;
  protected List<String> commonPictures;
  protected List<String> sessionParameters;
  protected List<String> roles;
  protected List<String> commonTemplates;
  protected List<String> filterCriteria;
  protected List<String> commonModules;
  protected List<String> commonAttributes;
  protected List<String> exchangePlans;
  @JsonProperty("xDTOPackages")
  protected List<String> xdtoPackages;
  protected List<String> webServices;
  protected List<String> httpServices;
  protected List<String> eventSubscriptions;
  protected List<String> scheduledJobs;
  protected List<String> settingsStorages;
  protected List<String> functionalOptions;
  protected List<String> definedTypes;
  protected List<String> commonCommands;
  protected List<String> commandGroups;
  protected List<String> constants;
  protected List<String> commonForms;
  protected List<String> catalogs;
  protected List<String> documents;
  protected List<String> documentNumerators;
  protected List<String> sequences;
  protected List<String> documentJournals;
  protected List<String> enums;
  protected List<String> reports;
  protected List<String> dataProcessors;
  protected List<String> accumulationRegisters;
  protected List<String> chartsOfCharacteristicTypes;
  protected List<String> chartsOfAccounts;
  protected List<String> accountingRegisters;
  protected List<String> chartsOfCalculationTypes;
  protected List<String> calculationRegisters;
  protected List<String> businessProcesses;
  protected List<String> tasks;
  protected List<String> informationRegisters;
  protected List<ContainedObject> containedObjects;
  @JacksonXmlElementWrapper(useWrapping = false)
  protected List<Language> languages;

}
