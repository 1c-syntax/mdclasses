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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.AdditionalIndexOwner;
import com.github._1c_syntax.bsl.mdo.CommonForm;
import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.IndexOwner;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.storage.Index;
import com.github._1c_syntax.bsl.mdo.storage.PlatformIndex;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
import com.github._1c_syntax.bsl.mdo.support.InterfaceCompatibilityMode;
import com.github._1c_syntax.bsl.mdo.support.UsePurposes;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.github._1c_syntax.bsl.types.ScriptVariant;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CF extends MDClass, ConfigurationTree, CFAccess {

  /**
   * Язык приложения по умолчанию
   */
  MdoReference getDefaultLanguage();

  /**
   * Язык, на котором ведется разработка
   */
  ScriptVariant getScriptVariant();

  /**
   * Вид интерфейса
   */
  InterfaceCompatibilityMode getInterfaceCompatibilityMode();

  /**
   * Режим совместимости
   */
  CompatibilityMode getCompatibilityMode();

  /**
   * Режим совместимости расширений
   */
  CompatibilityMode getConfigurationExtensionCompatibilityMode();

  /**
   * Режим запуска приложения по умолчанию
   */
  ApplicationRunMode getDefaultRunMode();

  /**
   * Разработчик решения
   */
  String getVendor();

  /**
   * Версия
   */
  String getVersion();

  /**
   * Назначения использования форм
   */
  List<UsePurposes> getUsePurposes();

  /**
   * Ссылка на основную форму констант
   */
  MdoReference getDefaultConstantsForm();

  /**
   * Ссылка на основную форму отчёта
   */
  MdoReference getDefaultReportForm();

  /**
   * Ссылка на основную форму варианта отчёта
   */
  MdoReference getDefaultReportVariantForm();

  /**
   * Ссылка на основную форму настроек отчёта
   */
  MdoReference getDefaultReportSettingsForm();

  /**
   * Ссылка на основную форму настроек динамического списка
   */
  MdoReference getDefaultDynamicListSettingsForm();

  /**
   * Ссылка на основную форму поиска
   */
  MdoReference getDefaultSearchForm();

  /**
   * Ссылка на основную форму истории изменений истории данных
   */
  MdoReference getDefaultDataHistoryChangeHistoryForm();

  /**
   * Ссылка на основную форму данных версии истории данных
   */
  MdoReference getDefaultDataHistoryVersionDataForm();

  /**
   * Ссылка на основную форму различий версий истории данных
   */
  MdoReference getDefaultDataHistoryVersionDifferencesForm();

  /**
   * Ссылка на основную форму выбора пользователей системы взаимодействия
   */
  MdoReference getDefaultCollaborationSystemUsersChoiceForm();

  /**
   * Ссылка на дополнительную форму отчёта
   */
  MdoReference getAuxiliaryDefaultReportForm();

  /**
   * Ссылка на дополнительную форму варианта отчёта
   */
  MdoReference getAuxiliaryDefaultReportVariantForm();

  /**
   * Ссылка на дополнительную форму настроек отчёта
   */
  MdoReference getAuxiliaryDefaultReportSettingsForm();

  /**
   * Ссылка на дополнительную форму настроек динамического списка
   */
  MdoReference getAuxiliaryDefaultDynamicListSettingsForm();

  /**
   * Ссылка на дополнительную форму истории изменений истории данных
   */
  MdoReference getAuxiliaryDefaultDataHistoryChangeHistoryForm();

  /**
   * Ссылка на дополнительную форму данных версии истории данных
   */
  MdoReference getAuxiliaryDefaultDataHistoryVersionDataForm();

  /**
   * Ссылка на дополнительную форму различий версий истории данных
   */
  MdoReference getAuxiliaryDefaultDataHistoryVersionDifferencesForm();

  /**
   * Ссылка на дополнительную форму выбора пользователей системы взаимодействия
   */
  MdoReference getAuxiliaryDefaultCollaborationSystemUsersChoiceForm();

  /**
   * Соответствие видов основных форм конфигурации и ссылок на соответствующие общие формы
   *
   * @return соответствие видов форм по умолчанию
   */
  default Map<DefaultFormKind, MdoReference> getDefaultFormMap() {
    return Map.ofEntries(
      Map.entry(DefaultFormKind.CONSTANTS_FORM, getDefaultConstantsForm()),
      Map.entry(DefaultFormKind.REPORT_FORM, getDefaultReportForm()),
      Map.entry(DefaultFormKind.REPORT_VARIANT_FORM, getDefaultReportVariantForm()),
      Map.entry(DefaultFormKind.REPORT_SETTINGS_FORM, getDefaultReportSettingsForm()),
      Map.entry(DefaultFormKind.DYNAMIC_LIST_SETTINGS_FORM, getDefaultDynamicListSettingsForm()),
      Map.entry(DefaultFormKind.SEARCH_FORM, getDefaultSearchForm()),
      Map.entry(DefaultFormKind.DATA_HISTORY_CHANGE_HISTORY_FORM, getDefaultDataHistoryChangeHistoryForm()),
      Map.entry(DefaultFormKind.DATA_HISTORY_VERSION_DATA_FORM, getDefaultDataHistoryVersionDataForm()),
      Map.entry(DefaultFormKind.DATA_HISTORY_VERSION_DIFFERENCES_FORM, getDefaultDataHistoryVersionDifferencesForm()),
      Map.entry(DefaultFormKind.COLLABORATION_SYSTEM_USERS_CHOICE_FORM, getDefaultCollaborationSystemUsersChoiceForm()),
      Map.entry(DefaultFormKind.AUX_REPORT_FORM, getAuxiliaryDefaultReportForm()),
      Map.entry(DefaultFormKind.AUX_REPORT_VARIANT_FORM, getAuxiliaryDefaultReportVariantForm()),
      Map.entry(DefaultFormKind.AUX_REPORT_SETTINGS_FORM, getAuxiliaryDefaultReportSettingsForm()),
      Map.entry(DefaultFormKind.AUX_DYNAMIC_LIST_SETTINGS_FORM, getAuxiliaryDefaultDynamicListSettingsForm()),
      Map.entry(DefaultFormKind.AUX_DATA_HISTORY_CHANGE_HISTORY_FORM, getAuxiliaryDefaultDataHistoryChangeHistoryForm()),
      Map.entry(DefaultFormKind.AUX_DATA_HISTORY_VERSION_DATA_FORM, getAuxiliaryDefaultDataHistoryVersionDataForm()),
      Map.entry(DefaultFormKind.AUX_DATA_HISTORY_VERSION_DIFFERENCES_FORM, getAuxiliaryDefaultDataHistoryVersionDifferencesForm()),
      Map.entry(DefaultFormKind.AUX_COLLABORATION_SYSTEM_USERS_CHOICE_FORM, getAuxiliaryDefaultCollaborationSystemUsersChoiceForm())
    );
  }

  /**
   * Возвращает ссылку на основную форму конфигурации по типу.
   *
   * @param kind Тип основной формы
   * @return Ссылка на форму или {@link MdoReference#EMPTY}, если её нет либо тип неприменим
   */
  default MdoReference getDefaultFormLink(DefaultFormKind kind) {
    return getDefaultFormMap().getOrDefault(kind, MdoReference.EMPTY);
  }

  /**
   * Возвращает основную форму конфигурации по типу.
   * Ищет общую форму в {@link #getCommonForms()} по ссылке, полученной из {@link #getDefaultFormLink}.
   *
   * @param kind Тип основной формы
   * @return Форма или {@link Optional#empty()}, если ссылка не указана или форма не найдена в списке
   */
  default Optional<CommonForm> getDefaultForm(DefaultFormKind kind) {
    var link = getDefaultFormLink(kind);
    if (link.isEmpty()) {
      return Optional.empty();
    }
    return findCommonForm(form -> form.getMdoReference().equals(link));
  }

  /**
   * Возвращает соответствие пути к модулю его типу
   */
  Map<URI, ModuleType> getModulesByType();

  /**
   * Возвращает соответствие пути к модулю к нему самому
   */
  Map<URI, Module> getModulesByURI();

  /**
   * Возвращает соответствие имени общего модуля к нему самому
   */
  Map<String, CommonModule> getCommonModulesByName();

  /**
   * Возвращает соответствие ссылки на дочерний объект к нему самому
   */
  Map<MdoReference, MD> getChildrenByMdoRef();

  /**
   * Возвращает соответствие ссылок на объекты метаданных к списку их индексов
   */
  Map<MdoReference, List<PlatformIndex>> getIndexesByMdoRef();

  /**
   * Возвращает соответствие типов модулей их путям к файлам для дочернего объекта
   */
  default Map<ModuleType, List<URI>> mdoModuleTypes(MdoReference mdoReference) {
    var child = findChild(mdoReference);
    if (child.isPresent() && child.get() instanceof ModuleOwner moduleOwner) {
      return moduleOwner.getModuleTypes();
    } else {
      return Collections.emptyMap();
    }
  }

  /**
   * Возвращает соответствие типов модулей их путям к файлам для дочернего объекта
   */
  default Map<ModuleType, List<URI>> mdoModuleTypes(String mdoRef) {
    return mdoModuleTypes(MdoReference.create(mdoRef));
  }

  /**
   * Возвращает соответствие пути файла модуля ссылке его владельца
   */
  Map<URI, MD> getModulesByObject();

  /**
   * Возвращает список подсистем, в состав которых входит объект метаданных
   *
   * @param md                 объект метаданных
   * @param addParentSubsystem - признак необходимости добавлять родительскую (текущую) подсистему в список,
   *                           если объект присутствует в дочерних.
   *                           Используется для кейса: раз есть в дочерней, то считаем что и ко всем родителям
   *                           тоже относится
   * @return список подсистем
   */
  default List<Subsystem> includedSubsystems(MD md, boolean addParentSubsystem) {
    return includedSubsystems(md.getMdoReference(), addParentSubsystem);
  }

  /**
   * Возвращает список подсистем, в состав которых входит ссылка
   *
   * @param mdoReference       ссылка на объект метаданных
   * @param addParentSubsystem - признак необходимости добавлять родительскую (текущую) подсистему в список,
   *                           если объект присутствует в дочерних.
   *                           Используется для кейса: раз есть в дочерней, то считаем что и ко всем родителям
   *                           тоже относится
   * @return список подсистем
   */
  default List<Subsystem> includedSubsystems(MdoReference mdoReference, boolean addParentSubsystem) {
    return getSubsystems().parallelStream()
      .flatMap(subsystem -> subsystem.included(mdoReference, addParentSubsystem).stream())
      .toList();
  }

  /**
   * Возвращает локализованное представление ссылки на объект метаданных с учетом используемого варианта языка
   * разработки
   *
   * @param md Объект метаданных, принадлежащий MDClasses
   * @return Строковое представление ссылки
   */
  default String getMdoRefLocal(MD md) {
    return md.getMdoRef(getScriptVariant());
  }

  @Override
  default ModuleType getModuleTypeByURI(URI uri) {
    return getModulesByType().getOrDefault(uri, ModuleType.UNKNOWN);
  }

  @Override
  default Optional<Module> getModuleByUri(URI uri) {
    return Optional.ofNullable(getModulesByURI().get(uri));
  }

  @Override
  default Optional<MD> findChild(URI uri) {
    return Optional.ofNullable(getModulesByObject().get(uri));
  }

  @Override
  default Optional<MD> findChild(MdoReference ref) {
    return Optional.ofNullable(getChildrenByMdoRef().get(ref));
  }

  @Override
  default Optional<CommonModule> findCommonModule(String name) {
    return Optional.ofNullable(getCommonModulesByName().get(name));
  }

  /**
   * Возвращает признак пустоты конфигурации
   *
   * @return Это пустая конфигурация
   */
  default boolean isEmpty() {
    return this == Configuration.EMPTY;
  }

  /**
   * Возвращает список индексов таблицы БД объекта
   *
   * @param mdo объект метаданных
   * @return Немодифицируемый список индексов
   */
  default List<Index> getIndexes(MD mdo) {
    if (mdo instanceof IndexOwner) {
      var result = new ArrayList<Index>(getIndexesByMdoRef()
        .getOrDefault(mdo.getMdoReference(), Collections.emptyList()));
      if (mdo instanceof AdditionalIndexOwner additionalIndexOwner) {
        result.addAll(additionalIndexOwner.getAdditionalIndexes());
      }
      return Collections.unmodifiableList(result);
    }
    return Collections.emptyList();
  }

  /**
   * Возвращает список индексов таблицы БД объекта по ссылке на него
   *
   * @param mdoRef Ссылка на объект метаданных
   * @return Немодифицируемый список индексов
   */
  default List<Index> getIndexes(MdoReference mdoRef) {
    return getIndexes(getChildrenByMdoRef().get(mdoRef));
  }
}
