/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.Command;
import com.github._1c_syntax.bsl.mdo.CommandOwner;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.FormOwner;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.mdo.TemplateOwner;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
   * @return Объединенная конфигурация
   */
  public static Configuration merge(@NonNull Configuration cf, @NonNull ConfigurationExtension extension) {
    var builder = cf.toBuilder();
    // todo подумать о том, как контролировать, что все свойства копируются
    if (cf.isEmpty()) { // скопируем из первого расширения
      builder.configurationSource(extension.getConfigurationSource())
        .name("solution")
        .uuid("solution")
        .defaultLanguage(extension.getDefaultLanguage())
        .scriptVariant(extension.getScriptVariant())
        .interfaceCompatibilityMode(extension.getInterfaceCompatibilityMode())
        .compatibilityMode(extension.getCompatibilityMode())
        .defaultRunMode(extension.getDefaultRunMode());
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
      .clearConstants().constants(mergeMD(cf, extension, CF::getConstants, newChildren))
      .clearCatalogs().catalogs(mergeMD(cf, extension, CF::getCatalogs, newChildren))
      .clearDocuments().documents(mergeMD(cf, extension, CF::getDocuments, newChildren))
      .clearSequences().sequences(mergeMD(cf, extension, CF::getSequences, newChildren))
      .clearDocumentJournals().documentJournals(mergeMD(cf, extension, CF::getDocumentJournals, newChildren))
      .clearEnums().enums(mergeMD(cf, extension, CF::getEnums, newChildren))
      .clearReports().reports(mergeMD(cf, extension, CF::getReports, newChildren))
      .clearDataProcessors().dataProcessors(mergeMD(cf, extension, CF::getDataProcessors, newChildren))
      .clearChartsOfCharacteristicTypes().chartsOfCharacteristicTypes(mergeMD(cf, extension, CF::getChartsOfCharacteristicTypes, newChildren))
      .clearChartsOfAccounts().chartsOfAccounts(mergeMD(cf, extension, CF::getChartsOfAccounts, newChildren))
      .clearChartsOfCalculationTypes().chartsOfCalculationTypes(mergeMD(cf, extension, CF::getChartsOfCalculationTypes, newChildren))
      .clearInformationRegisters().informationRegisters(mergeMD(cf, extension, CF::getInformationRegisters, newChildren))
      .clearAccumulationRegisters().accumulationRegisters(mergeMD(cf, extension, CF::getAccumulationRegisters, newChildren))
      .clearAccountingRegisters().accountingRegisters(mergeMD(cf, extension, CF::getAccountingRegisters, newChildren))
      .clearCalculationRegisters().calculationRegisters(mergeMD(cf, extension, CF::getCalculationRegisters, newChildren))
      .clearBusinessProcesses().businessProcesses(mergeMD(cf, extension, CF::getBusinessProcesses, newChildren))
      .clearTasks().tasks(mergeMD(cf, extension, CF::getTasks, newChildren))
      .clearExternalDataSources().externalDataSources(mergeMD(cf, extension, CF::getExternalDataSources, newChildren))

      .clearChildren().children(Collections.unmodifiableList(newChildren))
      .build();
  }

  /**
   * Объединяет список модулей. Выполняется простое добавление модулей из копируемого объекта, т.к. в любом случае
   * совпадений быть не может (модуль связан с файлом на диске)
   *
   * @param source     Исходный список модулей
   * @param additional Список модулей копируемого объекта
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
   * Выполняет объединение коллекций дочерних объектов.
   * Объединение работает следующим образом
   * - получается список объектов, несовпадающих по именам - это новые, все добавляются в итоговый список
   * - получается список объектов, по которым имена совпадают. Для них создаются копии (рекурсивно). Копии включаются
   * в итоговый список
   *
   * @param <T>         Тип дочерних объектов
   * @param <K>         Тип объединяемых объектов
   * @param source      Исходный объект
   * @param copy        Объединяемый расширение
   * @param getList     Метод получения списка дочерних
   * @param newChildren Накопительный список всех дочерних
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
    var additionalRefs = additionalList.stream().collect(Collectors.toMap(MD::getMdoRef, md -> md));
    var sourceRefs = sourceList.stream().map(MD::getMdoRef).toList();

    // переносим сначала существующие объекты
    List<T> result = new ArrayList<>();
    sourceList.forEach((T srcMD) -> {
      var modMD = additionalRefs.get(srcMD.getMdoRef());
      if (modMD == null) { // изменений нет, просто копируем
        result.add(srcMD);
        return;
      }

      // изменения есть, создаем копию

      // ModuleOwner
      var md = copyModuleOwner(srcMD, modMD);
      // ChildrenOwner
      md = copyChildrenOwner(md, modMD);

      result.add(md);
    });

    result.addAll(additionalList.stream().filter(md -> !sourceRefs.contains(md.getMdoRef())).toList());
    newChildren.addAll(result);
    return Collections.unmodifiableList(result);
  }

  @SuppressWarnings("unchecked")
  private static <T extends MD> T copyModuleOwner(T srcMD, T modMD) {
    if (srcMD instanceof ModuleOwner moduleOwner) {
      var source = moduleOwner.getModules();
      var merge = mergeModules(source, ((ModuleOwner) modMD).getModules());
      if (source != merge) { // список модулей изменился, создадим копию
        var builder = TransformationUtils.toBuilder(srcMD);
        assert builder != null;
        TransformationUtils.setValue(builder, "modules", merge);
        return (T) TransformationUtils.build(builder);
      }
    }
    return srcMD;
  }

  @SuppressWarnings("unchecked")
  private static <T extends MD> T copyChildrenOwner(T srcMD, T modMD) {
    // todo доделать копирование атрибутов и других дочерних

    var result = srcMD;
    if (result instanceof ChildrenOwner childrenOwner) {
      Object builder = null;

      var newChildren = new ArrayList<>(
        childrenOwner.getChildren().stream()
          .filter(
            child -> !(child instanceof Form)
              && !(child instanceof Template)
              && !(child instanceof Command)
          ).toList()
      );

      if (srcMD instanceof FormOwner formOwner) {
        builder = copyFormOwner(formOwner, (FormOwner) modMD, builder, newChildren);
      }

      if (srcMD instanceof TemplateOwner templateOwner) {
        builder = copyTemplateOwner(templateOwner, (TemplateOwner) modMD, builder, newChildren);
      }

      if (srcMD instanceof CommandOwner commandOwner) {
        builder = copyCommandOwner(commandOwner, (CommandOwner) modMD, builder, newChildren);
      }

      if (builder != null) {
        TransformationUtils.invoke(builder, "clearChildren");
        TransformationUtils.setValue(builder, "children", newChildren);
        result = (T) TransformationUtils.build(builder);
      }
    }
    return result;
  }

  private static <K, T extends MD> Object copyChildrenList(K srcMD,
                                                           K modMD,
                                                           @Nullable Object builder,
                                                           Function<K, List<T>> getList,
                                                           String methodName,
                                                           ArrayList<MD> newChildren) {
    var oldList = getList.apply(srcMD);
    var mergeList = mergeMD(srcMD, modMD, getList, newChildren);
    if (oldList != mergeList) { // список изменился, создадим копию
      if (builder == null) {
        builder = TransformationUtils.toBuilder(srcMD);
      }
      assert builder != null;
      TransformationUtils.invoke(builder, "clear" + methodName);
      TransformationUtils.setValue(builder, methodName, mergeList);
    }
    return builder;
  }

  private static Object copyFormOwner(FormOwner srcMD,
                                      FormOwner modMD,
                                      @Nullable Object builder,
                                      ArrayList<MD> newChildren) {
    return copyChildrenList(srcMD, modMD, builder, FormOwner::getForms, "Forms", newChildren);
  }

  private static Object copyTemplateOwner(TemplateOwner srcMD,
                                          TemplateOwner modMD,
                                          @Nullable Object builder,
                                          ArrayList<MD> newChildren) {
    return copyChildrenList(srcMD, modMD, builder, TemplateOwner::getTemplates, "Templates", newChildren);
  }

  private static Object copyCommandOwner(CommandOwner srcMD,
                                         CommandOwner modMD,
                                         @Nullable Object builder,
                                         ArrayList<MD> newChildren) {
    return copyChildrenList(srcMD, modMD, builder, CommandOwner::getCommands, "Commands", newChildren);
  }
}
