/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * MDClasses is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * MDClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MDClasses.
 */
package com.github._1c_syntax.bsl.reader;

import com.github._1c_syntax.bsl.mdclasses.CF;
import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationExtension;
import com.github._1c_syntax.bsl.mdclasses.ObjectProvenance;
import com.github._1c_syntax.bsl.mdo.Attribute;
import com.github._1c_syntax.bsl.mdo.CalculationRegister;
import com.github._1c_syntax.bsl.mdo.ChartOfAccounts;
import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.Command;
import com.github._1c_syntax.bsl.mdo.CommandOwner;
import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.Enum;
import com.github._1c_syntax.bsl.mdo.ExternalDataSource;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.FormOwner;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.PredefinedDataOwner;
import com.github._1c_syntax.bsl.mdo.ReferenceObject;
import com.github._1c_syntax.bsl.mdo.Register;
import com.github._1c_syntax.bsl.mdo.TabularSection;
import com.github._1c_syntax.bsl.mdo.TabularSectionOwner;
import com.github._1c_syntax.bsl.mdo.Task;
import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.mdo.TemplateOwner;
import com.github._1c_syntax.bsl.mdo.children.EnumValue;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceCube;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceCubeDimensionTable;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceFunction;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTable;
import com.github._1c_syntax.bsl.mdo.children.PredefinedValue;
import com.github._1c_syntax.bsl.mdo.children.Recalculation;
import com.github._1c_syntax.bsl.mdo.storage.FormData;
import com.github._1c_syntax.bsl.mdo.storage.ManagedFormData;
import com.github._1c_syntax.bsl.mdo.storage.form.FormAddition;
import com.github._1c_syntax.bsl.mdo.storage.form.FormButton;
import com.github._1c_syntax.bsl.mdo.storage.form.FormContextMenu;
import com.github._1c_syntax.bsl.mdo.storage.form.FormDecoration;
import com.github._1c_syntax.bsl.mdo.storage.form.FormElement;
import com.github._1c_syntax.bsl.mdo.storage.form.FormElementOwner;
import com.github._1c_syntax.bsl.mdo.storage.form.FormEventHandler;
import com.github._1c_syntax.bsl.mdo.storage.form.FormEventHandlerOwner;
import com.github._1c_syntax.bsl.mdo.storage.form.FormExtendedTooltip;
import com.github._1c_syntax.bsl.mdo.storage.form.FormField;
import com.github._1c_syntax.bsl.mdo.storage.form.FormGroup;
import com.github._1c_syntax.bsl.mdo.storage.form.FormTable;
import com.github._1c_syntax.bsl.mdo.storage.form.FormUnknown;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.types.MdoReference;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Вспомогательный класс для выполнения объединения объектов
 */
@UtilityClass
public class MDMerger {

  /**
   * Выполняет объединение конфигурации и расширения
   *
   * @param cf        Основная конфигурация
   * @param extension Добавляемое расширение
   *
   * @return Объединенная конфигурация
   */
  public static Configuration merge(Configuration cf, ConfigurationExtension extension) {
    var builder = cf.toBuilder();
    if (cf.isEmpty()) { // скопируем из первого расширения
      builder.configurationSource(extension.getConfigurationSource())
        .name("solution")
        .uuid("solution")
        .defaultLanguage(extension.getDefaultLanguage())
        .scriptVariant(extension.getScriptVariant())
        .interfaceCompatibilityMode(extension.getInterfaceCompatibilityMode())
        .compatibilityMode(extension.getCompatibilityMode())
        .defaultRunMode(extension.getDefaultRunMode())
        .usePurposes(extension.getUsePurposes())
        .defaultConstantsForm(extension.getDefaultConstantsForm())
        .defaultReportForm(extension.getDefaultReportForm())
        .defaultReportVariantForm(extension.getDefaultReportVariantForm())
        .defaultReportSettingsForm(extension.getDefaultReportSettingsForm())
        .defaultDynamicListSettingsForm(extension.getDefaultDynamicListSettingsForm())
        .defaultSearchForm(extension.getDefaultSearchForm())
        .defaultDataHistoryChangeHistoryForm(extension.getDefaultDataHistoryChangeHistoryForm())
        .defaultDataHistoryVersionDataForm(extension.getDefaultDataHistoryVersionDataForm())
        .defaultDataHistoryVersionDifferencesForm(extension.getDefaultDataHistoryVersionDifferencesForm())
        .defaultCollaborationSystemUsersChoiceForm(extension.getDefaultCollaborationSystemUsersChoiceForm())
        .auxiliaryDefaultReportForm(extension.getAuxiliaryDefaultReportForm())
        .auxiliaryDefaultReportVariantForm(extension.getAuxiliaryDefaultReportVariantForm())
        .auxiliaryDefaultReportSettingsForm(extension.getAuxiliaryDefaultReportSettingsForm())
        .auxiliaryDefaultDynamicListSettingsForm(extension.getAuxiliaryDefaultDynamicListSettingsForm())
        .auxiliaryDefaultDataHistoryChangeHistoryForm(extension.getAuxiliaryDefaultDataHistoryChangeHistoryForm())
        .auxiliaryDefaultDataHistoryVersionDataForm(extension.getAuxiliaryDefaultDataHistoryVersionDataForm())
        .auxiliaryDefaultDataHistoryVersionDifferencesForm(
          extension.getAuxiliaryDefaultDataHistoryVersionDifferencesForm())
        .auxiliaryDefaultCollaborationSystemUsersChoiceForm(
          extension.getAuxiliaryDefaultCollaborationSystemUsersChoiceForm());
    }

    List<MD> newChildren = new ArrayList<>();
    return builder
      .modules(mergeModules(cf.getModules(), extension.getModules()))

      .subsystems(mergeMDListSimple(cf, extension, CF::getSubsystems, newChildren))
      .sessionParameters(mergeMDListSimple(cf, extension, CF::getSessionParameters, newChildren))
      .roles(mergeMDListSimple(cf, extension, CF::getRoles, newChildren))
      .commonAttributes(mergeMDListSimple(cf, extension, CF::getCommonAttributes, newChildren))
      .eventSubscriptions(mergeMDListSimple(cf, extension, CF::getEventSubscriptions, newChildren))
      .scheduledJobs(mergeMDListSimple(cf, extension, CF::getScheduledJobs, newChildren))
      .functionalOptions(mergeMDListSimple(cf, extension, CF::getFunctionalOptions, newChildren))
      .functionalOptionsParameters(mergeMDListSimple(cf, extension, CF::getFunctionalOptionsParameters, newChildren))
      .definedTypes(mergeMDListSimple(cf, extension, CF::getDefinedTypes, newChildren))
      .commandGroups(mergeMDListSimple(cf, extension, CF::getCommandGroups, newChildren))
      .commonTemplates(mergeMDListSimple(cf, extension, CF::getCommonTemplates, newChildren))
      .commonPictures(mergeMDListSimple(cf, extension, CF::getCommonPictures, newChildren))
      .interfaces(mergeMDListSimple(cf, extension, CF::getInterfaces, newChildren))
      .xDTOPackages(mergeMDListSimple(cf, extension, CF::getXDTOPackages, newChildren))
      .wsReferences(mergeMDListSimple(cf, extension, CF::getWsReferences, newChildren))
      .styleItems(mergeMDListSimple(cf, extension, CF::getStyleItems, newChildren))
      .paletteColors(mergeMDListSimple(cf, extension, CF::getPaletteColors, newChildren))
      .styles(mergeMDListSimple(cf, extension, CF::getStyles, newChildren))
      .languages(mergeMDListSimple(cf, extension, CF::getLanguages, newChildren))
      .documentNumerators(mergeMDListSimple(cf, extension, CF::getDocumentNumerators, newChildren))

      .clearCommonModules().commonModules(mergeMD(cf, extension, CF::getCommonModules, newChildren))
      .clearFilterCriteria().filterCriteria(mergeMD(cf, extension, CF::getFilterCriteria, newChildren))
      .clearBots().bots(mergeMD(cf, extension, CF::getBots, newChildren))
      .clearSettingsStorages().settingsStorages(mergeMD(cf, extension, CF::getSettingsStorages, newChildren))
      .clearCommonForms().commonForms(mergeMD(cf, extension, CF::getCommonForms, newChildren))
      .clearCommonCommands().commonCommands(mergeMD(cf, extension, CF::getCommonCommands, newChildren))
      .clearWebServices().webServices(mergeMD(cf, extension, CF::getWebServices, newChildren))
      .clearWebSocketClients().webSocketClients(mergeMD(cf, extension, CF::getWebSocketClients, newChildren))
      .clearHttpServices().httpServices(mergeMD(cf, extension, CF::getHttpServices, newChildren))
      .clearIntegrationServices().integrationServices(mergeMD(cf, extension, CF::getIntegrationServices, newChildren))
      .clearExchangePlans().exchangePlans(mergeMD(cf, extension, CF::getExchangePlans, newChildren))
      .clearConstants().constants(mergeMD(cf, extension, CF::getConstants, newChildren))
      .clearCatalogs().catalogs(mergeMD(cf, extension, CF::getCatalogs, newChildren))
      .clearDocuments().documents(mergeMD(cf, extension, CF::getDocuments, newChildren))
      .clearSequences().sequences(mergeMD(cf, extension, CF::getSequences, newChildren))
      .clearDocumentJournals().documentJournals(mergeMD(cf, extension, CF::getDocumentJournals, newChildren))
      .clearEnums().enums(mergeMD(cf, extension, CF::getEnums, newChildren))
      .clearReports().reports(mergeMD(cf, extension, CF::getReports, newChildren))
      .clearDataProcessors().dataProcessors(mergeMD(cf, extension, CF::getDataProcessors, newChildren))
      .clearChartsOfCharacteristicTypes()
      .chartsOfCharacteristicTypes(mergeMD(cf, extension, CF::getChartsOfCharacteristicTypes, newChildren))
      .clearChartsOfAccounts().chartsOfAccounts(mergeMD(cf, extension, CF::getChartsOfAccounts, newChildren))

      .clearChartsOfCalculationTypes()
      .chartsOfCalculationTypes(mergeMD(cf, extension, CF::getChartsOfCalculationTypes, newChildren))

      .clearInformationRegisters()
      .informationRegisters(mergeMD(cf, extension, CF::getInformationRegisters, newChildren))
      .clearAccumulationRegisters()

      .accumulationRegisters(mergeMD(cf, extension, CF::getAccumulationRegisters, newChildren))
      .clearAccountingRegisters().accountingRegisters(mergeMD(cf, extension, CF::getAccountingRegisters, newChildren))

      .clearCalculationRegisters()
      .calculationRegisters(mergeMD(cf, extension, CF::getCalculationRegisters, newChildren))

      .clearBusinessProcesses().businessProcesses(mergeMD(cf, extension, CF::getBusinessProcesses, newChildren))
      .clearTasks().tasks(mergeMD(cf, extension, CF::getTasks, newChildren))
      .clearExternalDataSources().externalDataSources(mergeMD(cf, extension, CF::getExternalDataSources, newChildren))

      .clearChildren().children(Collections.unmodifiableList(newChildren))
      .build();
  }

  /**
   * Последовательное слияние базовой конфигурации со списком расширений. Каждое следующее расширение применяется к
   * результату предыдущего.
   *
   * @param base       Базовая конфигурация
   * @param extensions Список расширений (в порядке применения)
   *
   * @return MergeResult с конфигурацией и происхождениями
   */
  public static MergeResult mergeAll(Configuration base, List<ConfigurationExtension> extensions) {
    var provenance = new HashMap<MdoReference, ObjectProvenance>();
    initProvenance(base, base.getMdoReference(), provenance);

    var result = base;
    for (var ext : extensions) {
      initProvenance(ext, ext.getMdoReference(), provenance);
      result = merge(result, ext);
    }

    return new MergeResult(result, Collections.unmodifiableMap(provenance));
  }

  /**
   * Объединяет список модулей. Выполняется простое добавление модулей из копируемого объекта, т.к. в любом случае
   * совпадений быть не может (модуль связан с файлом на диске)
   *
   * @param source     Исходный список модулей
   * @param additional Список модулей копируемого объекта
   *
   * @return Объединенный список
   */
  private static List<Module> mergeModules(List<Module> source, List<Module> additional) {
    if (additional.isEmpty()) {
      return source;
    } else if (source.isEmpty()) {
      return additional;
    }
    var result = new ArrayList<>(source);
    result.addAll(additional);
    return Collections.unmodifiableList(result);
  }

  /**
   * для простого объединения, не предусматривающего предварительную очистку источника и анализа дочерних
   */
  private static <T extends MD> List<T> mergeMDListSimple(Configuration cf,
                                                          ConfigurationExtension extension,
                                                          Function<CF, List<T>> getList, List<MD> newChildren) {
    var additional = getList.apply(extension);
    var source = getList.apply(cf);
    newChildren.addAll(source);
    if (source.isEmpty() || additional.isEmpty()) {
      newChildren.addAll(additional);
      return additional;
    }

    // список ссылок всех существующих объектов
    var refs = source.stream().map(MD::getMdoRef).toList();
    var result = List.copyOf(additional.stream().filter(md -> !refs.contains(md.getMdoRef())).toList());
    newChildren.addAll(result);
    return result;
  }

  /**
   * Выполняет объединение коллекций дочерних объектов. Объединение работает следующим образом - получается список
   * объектов, несовпадающих по именам - это новые, все добавляются в итоговый список - получается список объектов, по
   * которым имена совпадают. Для них создаются копии (рекурсивно). Копии включаются в итоговый список
   *
   * @param <T>         Тип дочерних объектов
   * @param <K>         Тип объединяемых объектов
   * @param source      Исходный объект
   * @param copy        Объединяемый расширение
   * @param getList     Метод получения списка дочерних
   * @param newChildren Накопительный список всех дочерних
   *
   * @return Итоговый список дочерних
   */
  private static <T extends MD, K> List<T> mergeMD(K source,
                                                   K copy,
                                                   Function<K, List<T>> getList,
                                                   List<MD> newChildren) {
    var additionalList = getList.apply(copy);
    var sourceList = getList.apply(source);
    if (sourceList.isEmpty()) {
      newChildren.addAll(additionalList);
      return additionalList;
    } else if (additionalList.isEmpty()) {
      newChildren.addAll(sourceList);
      return sourceList;
    }

    // список ссылок всех существующих объектов
    var additionalRefs = additionalList.stream()
      .collect(Collectors.toMap(MD::getMdoRef, Function.identity(), (a, b) -> b));

    // переносим сначала существующие объекты
    List<T> result = new ArrayList<>();
    sourceList.forEach((T srcMD) -> {
      var modMD = additionalRefs.get(srcMD.getMdoRef());
      if (modMD == null) { // изменений нет, просто копируем
        result.add(srcMD);
        return;
      }

      // изменения есть, создаем копию
      if (srcMD instanceof Form baseForm && modMD instanceof Form extForm) {
        result.add((T) mergeForm(baseForm, extForm));
        return;
      }

      // ModuleOwner
      var md = copyModuleOwner(srcMD, modMD);
      // ChildrenOwner
      md = copyChildrenOwner(md, modMD);

      result.add(md);
    });

    var sourceRefs = sourceList.stream().map(MD::getMdoRef).collect(Collectors.toSet());
    result.addAll(additionalList.stream()
      .filter(md -> !sourceRefs.contains(md.getMdoRef()))
      .toList());

    newChildren.addAll(result);
    return Collections.unmodifiableList(result);
  }

  @SuppressWarnings("unchecked")
  private static <T extends MD> T copyModuleOwner(T srcMD, T modMD) {
    if (srcMD instanceof ModuleOwner moduleOwner) {
      var source = moduleOwner.getModules();
      var additional = ((ModuleOwner) modMD).getModules().stream()
        .filter(MDMerger::hasModuleFile)
        .toList();
      var merge = mergeModules(source, additional);
      if (source != merge) { // список модулей изменился, создадим копию
        var builder = TransformationUtils.toBuilder(srcMD);
        if (builder == null) { // такого не бывает, но проверка нужна для спокойствия jwm
          throw new IllegalStateException("toBuilder() is not available for " + srcMD.getClass());
        }
        TransformationUtils.setValue(builder, "modules", merge);
        var computeResult = TransformationUtils.build(builder);
        assert computeResult != null; // там и не будет null
        return (T) computeResult;
      }
    }
    return srcMD;
  }

  static Form mergeForm(Form baseForm, Form extForm) {
    var modules = mergeModules(baseForm.getModules(), extForm.getModules().stream()
      .filter(MDMerger::hasModuleFile)
      .toList());
    var data = mergeFormData(baseForm.getData(), extForm.getData());
    if (modules.equals(baseForm.getModules()) && data.equals(baseForm.getData())) {
      return baseForm;
    }

    var builder = TransformationUtils.toBuilder(baseForm);
    if (builder == null) {
      throw new IllegalStateException("toBuilder() is not available for " + baseForm.getClass());
    }
    TransformationUtils.setValue(builder, "modules", modules);
    TransformationUtils.setValue(builder, "data", data);
    var computeResult = TransformationUtils.build(builder);
    if (computeResult instanceof Form form) {
      return form;
    }
    return baseForm;
  }

  private static FormData mergeFormData(FormData baseData, FormData extData) {
    if (extData.isEmpty()) {
      return baseData;
    }
    if (baseData.isEmpty()) {
      return extData;
    }
    if (!(baseData instanceof ManagedFormData baseManaged) || !(extData instanceof ManagedFormData extManaged)) {
      return baseData;
    }

    var eventHandlers = mergeEventHandlers(baseManaged.getEventHandlers(), extManaged.getEventHandlers());
    var elements = mergeFormElements(baseManaged.getElements(), extManaged);

    if (eventHandlers.equals(baseManaged.getEventHandlers()) && elements.equals(baseManaged.getElements())) {
      return baseData;
    }

    return baseManaged.toBuilder()
      .clearEventHandlers().eventHandlers(eventHandlers)
      .clearElements().elements(elements)
      .build();
  }

  private static List<FormEventHandler> mergeEventHandlers(List<FormEventHandler> baseHandlers,
                                                           List<FormEventHandler> extHandlers) {
    if (extHandlers.isEmpty()) {
      return baseHandlers;
    }
    if (baseHandlers.isEmpty()) {
      return dedupHandlersByEvent(extHandlers);
    }

    List<FormEventHandler> result = new ArrayList<>(baseHandlers);
    var changed = false;
    for (var handler : extHandlers) {
      if (containsHandlerEvent(result, handler.event()) || result.contains(handler)) {
        continue;
      }
      result.add(handler);
      changed = true;
    }
    return changed ? Collections.unmodifiableList(result) : baseHandlers;
  }

  private static List<FormEventHandler> dedupHandlersByEvent(List<FormEventHandler> handlers) {
    List<FormEventHandler> result = new ArrayList<>();
    for (var handler : handlers) {
      if (!containsHandlerEvent(result, handler.event())) {
        result.add(handler);
      }
    }
    return Collections.unmodifiableList(result);
  }

  private static boolean containsHandlerEvent(List<FormEventHandler> handlers, String event) {
    for (var handler : handlers) {
      if (handler.event().equalsIgnoreCase(event)) {
        return true;
      }
    }
    return false;
  }

  private static List<FormElement> mergeFormElements(List<FormElement> baseElements, ManagedFormData extData) {
    if (baseElements.isEmpty()) {
      return baseElements;
    }

    var extByName = indexFormElementsByName(extData);
    if (extByName.isEmpty()) {
      return baseElements;
    }

    List<FormElement> result = new ArrayList<>(baseElements.size());
    var changed = false;
    for (var element : baseElements) {
      var merged = mergeFormElement(element, extByName);
      changed = changed || !merged.equals(element);
      result.add(merged);
    }

    return changed ? Collections.unmodifiableList(result) : baseElements;
  }

  private static Map<String, List<FormElement>> indexFormElementsByName(ManagedFormData extData) {
    Map<String, List<FormElement>> extByName = new HashMap<>();
    for (var element : extData.getPlainElements()) {
      if (element.getName() != null && !element.getName().isEmpty()) {
        extByName.computeIfAbsent(element.getName().toLowerCase(Locale.ROOT), k -> new ArrayList<>()).add(element);
      }
    }
    return extByName;
  }

  private static FormElement mergeFormElement(FormElement baseElement, Map<String, List<FormElement>> extByName) {
    var mergedChildren = mergeFormElementChildren(baseElement, extByName);
    if (!(mergedChildren instanceof FormEventHandlerOwner handlerOwner)) {
      return mergedChildren;
    }

    var extElements = extByName.getOrDefault(baseElement.getName().toLowerCase(Locale.ROOT), Collections.emptyList());
    if (extElements.isEmpty()) {
      return mergedChildren;
    }

    var mergedHandlers = mergeEventHandlers(handlerOwner.getEventHandlers(), collectExtHandlers(extElements));
    if (mergedHandlers.equals(handlerOwner.getEventHandlers())) {
      return mergedChildren;
    }

    return rebuildElementWith(mergedChildren, mergedHandlers, null);
  }

  private static FormElement mergeFormElementChildren(FormElement baseElement,
                                                      Map<String, List<FormElement>> extByName) {
    if (!(baseElement instanceof FormElementOwner owner)) {
      return baseElement;
    }

    var children = owner.getElements();
    if (children.isEmpty()) {
      return baseElement;
    }

    List<FormElement> newChildren = new ArrayList<>(children.size());
    var childChanged = false;
    for (var child : children) {
      var mergedChild = mergeFormElement(child, extByName);
      childChanged = childChanged || !mergedChild.equals(child);
      newChildren.add(mergedChild);
    }

    if (!childChanged) {
      return baseElement;
    }
    return rebuildElementWith(baseElement, null, Collections.unmodifiableList(newChildren));
  }

  private static List<FormEventHandler> collectExtHandlers(List<FormElement> extElements) {
    List<FormEventHandler> extHandlers = new ArrayList<>();
    for (var extElement : extElements) {
      if (extElement instanceof FormEventHandlerOwner extHandlerOwner) {
        extHandlers.addAll(extHandlerOwner.getEventHandlers());
      }
    }
    return extHandlers;
  }

  private static FormElement rebuildElementWith(FormElement element,
                                                @Nullable List<FormEventHandler> eventHandlers,
                                                @Nullable List<FormElement> children) {
    return switch (element) {
      case FormField formField -> formField.toBuilder()
        .clearEventHandlers().eventHandlers(eventHandlersOrDefault(eventHandlers, formField.getEventHandlers()))
        .clearElements().elements(childrenOrDefault(children, formField.getElements()))
        .build();
      case FormGroup formGroup -> formGroup.toBuilder()
        .clearEventHandlers().eventHandlers(eventHandlersOrDefault(eventHandlers, formGroup.getEventHandlers()))
        .clearElements().elements(childrenOrDefault(children, formGroup.getElements()))
        .build();
      case FormTable formTable -> formTable.toBuilder()
        .clearEventHandlers().eventHandlers(eventHandlersOrDefault(eventHandlers, formTable.getEventHandlers()))
        .clearElements().elements(childrenOrDefault(children, formTable.getElements()))
        .build();
      case FormButton formButton -> formButton.toBuilder()
        .clearEventHandlers().eventHandlers(eventHandlersOrDefault(eventHandlers, formButton.getEventHandlers()))
        .clearElements().elements(childrenOrDefault(children, formButton.getElements()))
        .build();
      case FormDecoration formDecoration -> formDecoration.toBuilder()
        .clearEventHandlers().eventHandlers(eventHandlersOrDefault(eventHandlers, formDecoration.getEventHandlers()))
        .clearElements().elements(childrenOrDefault(children, formDecoration.getElements()))
        .build();
      case FormUnknown formUnknown -> formUnknown.toBuilder()
        .clearEventHandlers().eventHandlers(eventHandlersOrDefault(eventHandlers, formUnknown.getEventHandlers()))
        .clearElements().elements(childrenOrDefault(children, formUnknown.getElements()))
        .build();
      case FormExtendedTooltip tooltip -> tooltip.toBuilder()
        .clearEventHandlers().eventHandlers(eventHandlersOrDefault(eventHandlers, tooltip.getEventHandlers()))
        .build();
      case FormAddition addition -> addition.toBuilder()
        .clearElements().elements(childrenOrDefault(children, addition.getElements()))
        .build();
      case FormContextMenu contextMenu -> contextMenu.toBuilder()
        .clearElements().elements(childrenOrDefault(children, contextMenu.getElements()))
        .build();
      default -> element;
    };
  }

  private static List<FormEventHandler> eventHandlersOrDefault(@Nullable List<FormEventHandler> eventHandlers,
                                                               List<FormEventHandler> defaults) {
    return eventHandlers == null ? defaults : eventHandlers;
  }

  private static List<FormElement> childrenOrDefault(@Nullable List<FormElement> children,
                                                     List<FormElement> defaults) {
    return children == null ? defaults : children;
  }

  /**
   * Отбрасывает self-заглушки CommonModule, для которых файл Module.bsl отсутствует. Такие заглушки создаются
   * конвертером с uri несуществующего файла и не должны попадать в индексы слитой конфигурации.
   */
  private static boolean hasModuleFile(Module module) {
    if (!(module instanceof CommonModule)) {
      return true;
    }
    var uri = module.getUri();
    if (uri == null) {
      return false;
    }
    try {
      return Files.isRegularFile(Path.of(uri));
    } catch (RuntimeException e) {
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  private static <T extends MD> T copyChildrenOwner(T srcMD, T modMD) {
    var result = srcMD;
    if (result instanceof ChildrenOwner childrenOwner) {
      var newChildren = new ArrayList<>(
        childrenOwner.getChildren().stream()
          .filter(child -> !isSpecialChild(child))
          .toList()
      );

      var builder = copyChildrenByType(srcMD, modMD, null, newChildren);

      if (builder != null) {
        TransformationUtils.invoke(builder, "clearChildren");
        TransformationUtils.setValue(builder, "children", newChildren);
        var computeResult = TransformationUtils.build(builder);
        assert computeResult != null; // там и не будет null
        result = (T) computeResult;
      }
    }
    return result;
  }

  /**
   * Проверяет, является ли дочерний объект «специальным» (обрабатывается отдельно, а не через общий список children).
   */
  private static boolean isSpecialChild(MD child) {
    return child instanceof Form
      || child instanceof Template
      || child instanceof Command
      || child instanceof Attribute
      || child instanceof TabularSection
      || child instanceof PredefinedValue
      || child instanceof EnumValue
      || child instanceof Recalculation
      || child instanceof ExternalDataSourceTable
      || child instanceof ExternalDataSourceCube
      || child instanceof ExternalDataSourceFunction
      || child instanceof ExternalDataSourceCubeDimensionTable;
  }

  @Nullable
  private static <T extends MD> Object copyChildrenByType(T srcMD, T modMD,
                                                          @Nullable Object builder,
                                                          ArrayList<MD> newChildren) {
    if (srcMD instanceof FormOwner formOwner) {
      builder = copyFormOwner(formOwner, (FormOwner) modMD, builder, newChildren);
    }
    if (srcMD instanceof TemplateOwner templateOwner) {
      builder = copyTemplateOwner(templateOwner, (TemplateOwner) modMD, builder, newChildren);
    }
    if (srcMD instanceof CommandOwner commandOwner) {
      builder = copyCommandOwner(commandOwner, (CommandOwner) modMD, builder, newChildren);
    }
    if (srcMD instanceof ReferenceObject referenceObject) {
      builder = copyChildrenList(referenceObject, (ReferenceObject) modMD, builder,
        ReferenceObject::getAttributes, "attributes", newChildren);
    }
    if (srcMD instanceof TabularSectionOwner tabularSectionOwner) {
      builder = copyChildrenList(tabularSectionOwner, (TabularSectionOwner) modMD, builder,
        TabularSectionOwner::getTabularSections, "tabularSections", newChildren);
    }
    if (srcMD instanceof PredefinedDataOwner predefinedDataOwner) {
      builder = copyChildrenList(predefinedDataOwner, (PredefinedDataOwner) modMD, builder,
        PredefinedDataOwner::getPredefinedValues, "predefinedValues", newChildren);
    }
    if (srcMD instanceof Register register) {
      builder = copyChildrenList(register, (Register) modMD, builder,
        Register::getAttributes, "attributes", newChildren);
      builder = copyChildrenList(register, (Register) modMD, builder,
        Register::getResources, "resources", newChildren);
      builder = copyChildrenList(register, (Register) modMD, builder,
        Register::getDimensions, "dimensions", newChildren);
    }
    if (srcMD instanceof Enum anEnum) {
      builder = copyChildrenList(anEnum, (Enum) modMD, builder,
        Enum::getEnumValues, "enumValues", newChildren);
    }
    if (srcMD instanceof Task task) {
      builder = copyChildrenList(task, (Task) modMD, builder,
        Task::getAddressingAttributes, "addressingAttributes", newChildren);
    }
    if (srcMD instanceof CalculationRegister calculationRegister) {
      builder = copyChildrenList(calculationRegister, (CalculationRegister) modMD, builder,
        CalculationRegister::getRecalculations, "recalculations", newChildren);
    }
    if (srcMD instanceof ExternalDataSource externalDataSource) {
      builder = copyChildrenList(externalDataSource, (ExternalDataSource) modMD, builder,
        ExternalDataSource::getTables, "tables", newChildren);
      builder = copyChildrenList(externalDataSource, (ExternalDataSource) modMD, builder,
        ExternalDataSource::getCubes, "cubes", newChildren);
      builder = copyChildrenList(externalDataSource, (ExternalDataSource) modMD, builder,
        ExternalDataSource::getFunctions, "functions", newChildren);
    }
    if (srcMD instanceof ExternalDataSourceCube externalDataSourceCube) {
      builder = copyChildrenList(externalDataSourceCube, (ExternalDataSourceCube) modMD, builder,
        ExternalDataSourceCube::getDimensionTables, "dimensionTables", newChildren);
      builder = copyChildrenList(externalDataSourceCube, (ExternalDataSourceCube) modMD, builder,
        ExternalDataSourceCube::getDimensions, "dimensions", newChildren);
      builder = copyChildrenList(externalDataSourceCube, (ExternalDataSourceCube) modMD, builder,
        ExternalDataSourceCube::getResources, "resources", newChildren);
    }
    if (srcMD instanceof ChartOfAccounts chartOfAccounts) {
      builder = copyChildrenList(chartOfAccounts, (ChartOfAccounts) modMD, builder,
        ChartOfAccounts::getAccountingFlags, "accountingFlags", newChildren);
      builder = copyChildrenList(chartOfAccounts, (ChartOfAccounts) modMD, builder,
        ChartOfAccounts::getExtDimensionAccountingFlags, "extDimensionAccountingFlags", newChildren);
    }
    return builder;
  }

  @Nullable
  private static <K, T extends MD> Object copyChildrenList(K srcMD,
                                                           K modMD,
                                                           @Nullable Object builder,
                                                           Function<K, List<T>> getList,
                                                           String methodName,
                                                           ArrayList<MD> newChildren) {
    var source = getList.apply(srcMD);
    var merge = mergeMD(srcMD, modMD, getList, newChildren);
    if (source != merge) { // список изменился, создадим копию
      if (builder == null) {
        builder = TransformationUtils.toBuilder(srcMD);
        if (builder == null) { // такого не бывает, но проверка нужна для спокойствия jwm
          throw new IllegalStateException("toBuilder() is not available for " + srcMD.getClass());
        }
      }
      TransformationUtils.invoke(builder, "clear" + methodName);
      TransformationUtils.setValue(builder, methodName, merge);
    }
    // возвращается либо полученный билдер, либо null, который и был изначально.
    return builder;
  }

  @Nullable
  private static Object copyFormOwner(FormOwner srcMD,
                                      FormOwner modMD,
                                      @Nullable Object builder,
                                      ArrayList<MD> newChildren) {
    return copyChildrenList(srcMD, modMD, builder, FormOwner::getForms, "Forms", newChildren);
  }

  @Nullable
  private static Object copyTemplateOwner(TemplateOwner srcMD,
                                          TemplateOwner modMD,
                                          @Nullable Object builder,
                                          ArrayList<MD> newChildren) {
    return copyChildrenList(srcMD, modMD, builder, TemplateOwner::getTemplates, "Templates", newChildren);
  }

  @Nullable
  private static Object copyCommandOwner(CommandOwner srcMD,
                                         CommandOwner modMD,
                                         @Nullable Object builder,
                                         ArrayList<MD> newChildren) {
    return copyChildrenList(srcMD, modMD, builder, CommandOwner::getCommands, "Commands", newChildren);
  }

  private static void initProvenance(CF cf, MdoReference ownerRef,
                                     Map<MdoReference, ObjectProvenance> provenance) {
    cf.getChildrenByMdoRef().forEach((MdoReference ref, MD md) -> {
      var existing = provenance.get(ref);
      if (existing != null) {
        // Объект уже есть от базы (или предыдущего расширения) —
        // текущий cf расширяет его, добавляем ownerRef в список модифицировавших
        provenance.put(ref, ObjectProvenance.builder()
          .ownerRef(existing.getOwnerRef())
          .objectBelonging(md.getObjectBelonging())
          .modifiedByExtensionRefs(existing.getModifiedByExtensionRefs())
          .modifiedByExtensionRef(ownerRef)
          .build());
      } else {
        provenance.put(ref, ObjectProvenance.builder()
          .ownerRef(ownerRef)
          .objectBelonging(md.getObjectBelonging())
          .build());
      }
    });
  }

  /**
   * Результат слияния: конфигурация и происхождения объектов.
   */
  public record MergeResult(Configuration configuration, Map<MdoReference, ObjectProvenance> provenance) {
  }

}
