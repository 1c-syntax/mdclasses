

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurationProperties {

  @JsonProperty("Name")
  protected String name;
  @JsonProperty("Synonym")
  protected LocalStringType synonym;
  @JsonProperty("Comment")
  protected String comment;
  @JsonProperty("ObjectBelonging")
  protected ObjectBelonging objectBelonging;
  @JsonProperty("ConfigurationExtensionPurpose")
  protected ConfigurationExtensionPurpose configurationExtensionPurpose;
  @JsonProperty("NamePrefix")
  protected String namePrefix;
  @JsonProperty("ConfigurationExtensionCompatibilityMode")
  protected CompatibilityMode configurationExtensionCompatibilityMode;
  @JsonProperty("DefaultRunMode")
  protected ClientRunMode defaultRunMode;
  // fixme
//    @JsonProperty("UsePurposes")
//  protected FixedArray usePurposes;
  @JsonProperty("ScriptVariant")
  protected ScriptVariant scriptVariant;
  @JsonProperty("DefaultRoles")
  protected List<String> defaultRoles;
  @JsonProperty("Vendor")
  protected String vendor;
  @JsonProperty("Version")
  protected String version;
  @JsonProperty("UpdateCatalogAddress")
  protected String updateCatalogAddress;
  @JsonProperty("ParentConfigurations")
  protected String parentConfigurations;
  @JsonProperty("ManagedApplicationModule")
  protected String managedApplicationModule;
  @JsonProperty("SessionModule")
  protected String sessionModule;
  @JsonProperty("ExternalConnectionModule")
  protected String externalConnectionModule;
  @JsonProperty("OrdinaryApplicationModule")
  protected String ordinaryApplicationModule;
  @JsonProperty("ApplicationModule")
  protected String applicationModule;
  @JsonProperty("IncludeHelpInContents")
  protected Boolean includeHelpInContents;
  @JsonProperty("Help")
  protected String help;
  @JsonProperty("UseManagedFormInOrdinaryApplication")
  protected Boolean useManagedFormInOrdinaryApplication;
  @JsonProperty("UseOrdinaryFormInManagedApplication")
  protected Boolean useOrdinaryFormInManagedApplication;
  @JsonProperty("AdditionalFullTextSearchDictionaries")
  protected MDListType additionalFullTextSearchDictionaries;
  @JsonProperty("CommonSettingsStorage")
  protected String commonSettingsStorage;
  @JsonProperty("ReportsUserSettingsStorage")
  protected String reportsUserSettingsStorage;
  @JsonProperty("ReportsVariantsStorage")
  protected String reportsVariantsStorage;
  @JsonProperty("FormDataSettingsStorage")
  protected String formDataSettingsStorage;
  @JsonProperty("DynamicListsUserSettingsStorage")
  protected String dynamicListsUserSettingsStorage;
  @JsonProperty("Content")
  protected MDListType content;
  @JsonProperty("DefaultReportForm")
  protected String defaultReportForm;
  @JsonProperty("DefaultReportVariantForm")
  protected String defaultReportVariantForm;
  @JsonProperty("DefaultReportSettingsForm")
  protected String defaultReportSettingsForm;
  @JsonProperty("DefaultDynamicListSettingsForm")
  protected String defaultDynamicListSettingsForm;
  @JsonProperty("DefaultSearchForm")
  protected String defaultSearchForm;
  @JsonProperty("RequiredMobileApplicationPermissions")
  protected FixedMap requiredMobileApplicationPermissions;
  @JsonProperty("CommandInterface")
  protected String commandInterface;
  @JsonProperty("HomePageWorkArea")
  protected String homePageWorkArea;
  @JsonProperty("StartPageWorkingArea")
  protected String startPageWorkingArea;
  @JsonProperty("MainSectionCommandInterface")
  protected String mainSectionCommandInterface;
  @JsonProperty("MainSectionPicture")
  protected String mainSectionPicture;
  @JsonProperty("ClientApplicationInterface")
  protected String clientApplicationInterface;
  @JsonProperty("MainClientApplicationWindowMode")
  protected MainClientApplicationWindowMode mainClientApplicationWindowMode;
  @JsonProperty("DefaultInterface")
  protected String defaultInterface;
  @JsonProperty("DefaultStyle")
  protected String defaultStyle;
  @JsonProperty("DefaultLanguage")
  protected String defaultLanguage;
  @JsonProperty("BriefInformation")
  protected LocalStringType briefInformation;
  @JsonProperty("DetailedInformation")
  protected LocalStringType detailedInformation;
  @JsonProperty("Logo")
  protected String logo;
  @JsonProperty("Splash")
  protected String splash;
  @JsonProperty("Copyright")
  protected LocalStringType copyright;
  @JsonProperty("VendorInformationAddress")
  protected LocalStringType vendorInformationAddress;
  @JsonProperty("ConfigurationInformationAddress")
  protected LocalStringType configurationInformationAddress;
  @JsonProperty("DataLockControlMode")
  protected DefaultDataLockControlMode dataLockControlMode;
  @JsonProperty("ObjectAutonumerationMode")
  protected ObjectAutonumerationMode objectAutonumerationMode;
  @JsonProperty("ModalityUseMode")
  protected ModalityUseMode modalityUseMode;
  @JsonProperty("SynchronousPlatformExtensionAndAddInCallUseMode")
  protected SynchronousPlatformExtensionAndAddInCallUseMode synchronousPlatformExtensionAndAddInCallUseMode;
  @JsonProperty("SynchronousExtensionAndAddInCallUseMode")
  protected SynchronousPlatformExtensionAndAddInCallUseMode synchronousExtensionAndAddInCallUseMode;
  @JsonProperty("InterfaceCompatibilityMode")
  protected InterfaceCompatibilityMode interfaceCompatibilityMode;
  @JsonProperty("CompatibilityMode")
  protected CompatibilityMode compatibilityMode;
  @JsonProperty("DefaultConstantsForm")
  protected String defaultConstantsForm;
  @JsonProperty("DefaultConstantForm")
  protected String defaultConstantForm;

}
