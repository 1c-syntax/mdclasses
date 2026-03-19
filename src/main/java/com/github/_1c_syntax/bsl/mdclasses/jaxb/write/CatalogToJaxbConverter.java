/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.Attribute;
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.children.ObjectCommand;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.support.CodeSeries;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.AttributeProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.CatalogChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.CatalogProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Command;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.CommandProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_1_data_core.AllowedLength;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_2_managed_application_logform.ChoiceDataGetModeOnInputByString;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_2_managed_application_logform.FullTextSearchOnInputByString;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_2_managed_application_logform.SearchStringModeOnInputByString;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.CatalogCodeType;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.CatalogCodesSeries;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.CatalogMainPresentation;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.CreateOnInput;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.DataHistoryUse;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.EditType;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.HierarchyType;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.PredefinedDataUpdate;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.SubordinationUse;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Преобразует модель справочника mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class CatalogToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private static final String EMPTY_REF = "";

  private static final Set<String> STANDARD_CATALOG_ATTRIBUTE_NAMES = Set.of(
    "Code", "Description", "Ref", "DeletionMark", "IsFolder", "Owner", "Parent",
    "PredefinedDataName", "Predefined"
  );

  private CatalogToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из справочника mdclasses.
   *
   * @param catalog справочник из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(Catalog catalog) {
    return toMetaDataObject(catalog, null);
  }

  /**
   * Собирает JAXB MetaDataObject из справочника mdclasses с указанием группы команд по умолчанию.
   *
   * @param catalog справочник из модели mdclasses
   * @param defaultCommandGroupRef MDO-ссылка группы команд (например, "CommandGroup.ГруппаКоманд1") для команд без группы
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(Catalog catalog, String defaultCommandGroupRef) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Catalog inner = FACTORY.createCatalog();
    inner.setUuid(catalog.getUuid() != null ? catalog.getUuid() : "");
    inner.setProperties(buildProperties(catalog));
    inner.setChildObjects(buildChildObjects(catalog, defaultCommandGroupRef));
    root.setCatalog(inner);
    return root;
  }

  private static CatalogProperties buildProperties(Catalog c) {
    CatalogProperties p = FACTORY.createCatalogProperties();
    p.setName(c.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(c.getSynonym())));
    p.setComment(c.getComment() != null ? c.getComment() : "");
    p.setObjectBelonging(JaxbWriteDefaults.objectBelongingNative());
    p.setHierarchical(false);
    p.setHierarchyType(HierarchyType.HIERARCHY_FOLDERS_AND_ITEMS);
    p.setLimitLevelCount(false);
    p.setLevelCount(BigDecimal.ONE);
    p.setFoldersOnTop(true);
    p.setUseStandardCommands(true);
    p.setOwners(buildOwners(c));
    p.setSubordinationUse(SubordinationUse.TO_FOLDERS_AND_ITEMS);
    p.setCodeLength(BigDecimal.valueOf(9));
    p.setDescriptionLength(BigDecimal.valueOf(150));
    p.setCodeType(CatalogCodeType.STRING);
    p.setCodeAllowedLength(AllowedLength.VARIABLE);
    p.setCodeSeries(codeSeriesToJaxb(c.getCodeSeries()));
    p.setCheckUnique(c.isCheckUnique());
    p.setAutonumbering(false);
    p.setDefaultPresentation(CatalogMainPresentation.AS_DESCRIPTION);
    p.setStandardAttributes(JaxbWriteDefaults.emptyStandardAttributeDescriptions());
    p.setCharacteristics(JaxbWriteDefaults.emptyCharacteristicsDescriptions());
    p.setPredefined("");
    p.setPredefinedDataUpdate(PredefinedDataUpdate.DONT_AUTO_UPDATE);
    p.setEditType(EditType.IN_LIST);
    p.setQuickChoice(false);
    p.setChoiceMode(JaxbWriteDefaults.choiceModeFromForm());
    p.setInputByString(JaxbWriteDefaults.emptyFieldList());
    p.setSearchStringModeOnInputByString(SearchStringModeOnInputByString.ANY_PART);
    p.setFullTextSearchOnInputByString(FullTextSearchOnInputByString.USE);
    p.setChoiceDataGetModeOnInputByString(ChoiceDataGetModeOnInputByString.DIRECTLY);
    p.setDefaultObjectForm(EMPTY_REF);
    p.setDefaultFolderForm(EMPTY_REF);
    p.setDefaultListForm(EMPTY_REF);
    p.setDefaultChoiceForm(EMPTY_REF);
    p.setDefaultFolderChoiceForm(EMPTY_REF);
    p.setAuxiliaryObjectForm(EMPTY_REF);
    p.setAuxiliaryFolderForm(EMPTY_REF);
    p.setAuxiliaryListForm(EMPTY_REF);
    p.setAuxiliaryChoiceForm(EMPTY_REF);
    p.setAuxiliaryFolderChoiceForm(EMPTY_REF);
    p.setObjectModule(EMPTY_REF);
    p.setManagerModule(EMPTY_REF);
    p.setIncludeHelpInContents(false);
    p.setHelp(EMPTY_REF);
    p.setBasedOn(JaxbWriteDefaults.emptyMDListType());
    p.setDataLockFields(JaxbWriteDefaults.emptyFieldList());
    p.setDataLockControlMode(JaxbWriteDefaults.defaultDataLockControlModeAutomatic());
    p.setFullTextSearch(JaxbWriteDefaults.fullTextSearchDontUse());
    p.setObjectPresentation(JaxbWriteDefaults.localStringType(""));
    p.setExtendedObjectPresentation(JaxbWriteDefaults.localStringType(""));
    p.setListPresentation(JaxbWriteDefaults.localStringType(""));
    p.setExtendedListPresentation(JaxbWriteDefaults.localStringType(""));
    p.setExplanation(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(c.getExplanation())));
    p.setCreateOnInput(CreateOnInput.DONT_USE);
    p.setChoiceHistoryOnInput(JaxbWriteDefaults.choiceHistoryOnInputAuto());
    p.setDataHistory(DataHistoryUse.DONT_USE);
    p.setUpdateDataHistoryImmediatelyAfterWrite(false);
    p.setExecuteAfterWriteDataHistoryVersionProcessing(false);
    p.setAdditionalIndexes(EMPTY_REF);
    return p;
  }

  private static com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_readable.MDListType buildOwners(Catalog c) {
    com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_readable.MDListType owners =
      new com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_readable.MDListType();
    if (c.getOwners() != null && !c.getOwners().isEmpty()) {
      for (MdoReference ref : c.getOwners()) {
        owners.getItem().add(shortNameFromMdoRef(ref != null ? ref.getMdoRef() : null));
      }
    }
    return owners;
  }

  private static CatalogCodesSeries codeSeriesToJaxb(CodeSeries cs) {
    if (cs == null) {
      return CatalogCodesSeries.WHOLE_CATALOG;
    }
    return switch (cs) {
      case WITHIN_SUBORDINATION -> CatalogCodesSeries.WITHIN_SUBORDINATION;
      case WITHIN_OWNER_SUBORDINATION -> CatalogCodesSeries.WITHIN_OWNER_SUBORDINATION;
      default -> CatalogCodesSeries.WHOLE_CATALOG;
    };
  }

  private static CatalogChildObjects buildChildObjects(Catalog c, String defaultCommandGroupRef) {
    CatalogChildObjects co = FACTORY.createCatalogChildObjects();
    addForms(co, c);
    addCommands(co, c, defaultCommandGroupRef);
    addAttributes(co, c);
    return co;
  }

  private static void addForms(CatalogChildObjects co, Catalog c) {
    if (c.getForms() == null) {
      return;
    }
    for (ObjectForm form : c.getForms()) {
      co.getForm().add(form.getName() != null ? form.getName() : "");
    }
  }

  private static void addCommands(CatalogChildObjects co, Catalog c, String defaultCommandGroupRef) {
    if (c.getCommands() == null) {
      return;
    }
    for (ObjectCommand cmd : c.getCommands()) {
      Command command = FACTORY.createCommand();
      command.setUuid(cmd.getUuid() != null ? cmd.getUuid() : "");
      CommandProperties cmdProps = FACTORY.createCommandProperties();
      cmdProps.setName(cmd.getName());
      cmdProps.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(cmd.getSynonym())));
      cmdProps.setComment(cmd.getComment() != null ? cmd.getComment() : "");
      cmdProps.setOnMainServerUnavalableBehavior(JaxbWriteDefaults.onMainServerUnavalableBehaviorAuto());
      if (defaultCommandGroupRef != null && !defaultCommandGroupRef.isEmpty()) {
        cmdProps.setGroup(defaultCommandGroupRef);
      }
      command.setProperties(cmdProps);
      co.getCommand().add(command);
    }
  }

  private static void addAttributes(CatalogChildObjects co, Catalog c) {
    if (c.getAttributes() == null) {
      return;
    }
    for (Attribute attr : c.getAttributes()) {
      if (attr.getName() == null || STANDARD_CATALOG_ATTRIBUTE_NAMES.contains(attr.getName())) {
        continue;
      }
      com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Attribute attribute = FACTORY.createAttribute();
      attribute.setUuid(attr.getUuid() != null ? attr.getUuid() : "");
      AttributeProperties attrProps = FACTORY.createAttributeProperties();
      attrProps.setName(attr.getName());
      attrProps.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(attr.getSynonym())));
      attrProps.setComment(attr.getComment() != null ? attr.getComment() : "");
      attribute.setProperties(attrProps);
      co.getAttribute().add(attribute);
    }
  }

  private static String shortNameFromMdoRef(String mdoRef) {
    if (mdoRef == null) {
      return "";
    }
    int dot = mdoRef.indexOf('.');
    return dot >= 0 ? mdoRef.substring(dot + 1) : mdoRef;
  }
}
