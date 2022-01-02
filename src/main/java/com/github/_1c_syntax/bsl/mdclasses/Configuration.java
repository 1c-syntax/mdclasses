/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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

import com.github._1c_syntax.bsl.mdo.Interface;
import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.Style;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.ApplicationUsePurpose;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static lombok.Builder.Default;

/**
 * Корневой класс конфигурации 1с
 */
@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
@NonNull
public class Configuration implements MDClass, ConfigurationTree, ModuleOwner {

  /**
   * Имя конфигурации
   */
  String name;

  /**
   * Уникальный идентификатор конфигурации
   */
  String uuid;

  /**
   * Комментарий
   */
  @Default
  String comment = "";

  /**
   * Вариант применения
   */
  @Default
  List<ApplicationUsePurpose> usePurposes = Collections.emptyList();

  /**
   * Синонимы объекта
   */
  @Default
  MultiLanguageString synonym = MultiLanguageString.EMPTY;

  /**
   * Вариант исходников конфигурации
   */
  @Default
  ConfigurationSource configurationSource = ConfigurationSource.EMPTY;

  /**
   * Режим совместимости
   */
  @Default
  CompatibilityMode compatibilityMode = new CompatibilityMode();

  /**
   * Режим совместимости расширений
   */
  @Default
  CompatibilityMode configurationExtensionCompatibilityMode = new CompatibilityMode();

  /**
   * Язык, на котором ведется разработка
   */
  @Default
  ScriptVariant scriptVariant = ScriptVariant.ENGLISH;

  /**
   * Режим запуска приложения по умолчанию
   */
  @Default
  ApplicationRunMode defaultRunMode = ApplicationRunMode.AUTO;

  /**
   * Язык приложения по умолчанию
   */
  @Default
  Language defaultLanguage = Language.DEFAULT;

  /**
   * Роли по умолчанию
   */
  @Default
  List<Role> defaultRoles = Collections.emptyList();

  /**
   * Режим управления блокировкой данных
   */
  @Default
  DataLockControlMode dataLockControlMode = DataLockControlMode.AUTOMATIC;

  /**
   * Режим автонумерации объектов
   */
  @Default
  String objectAutonumerationMode = "";

  /**
   * Режим использования модальных окон
   */
  @Default
  UseMode modalityUseMode = UseMode.USE;

  /**
   * Режим использования синхронных вызовов для платформенных объектов и расширений
   */
  @Default
  UseMode synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;

  /**
   * Использовать управляемые формы в обычном приложении
   */
  boolean useManagedFormInOrdinaryApplication;

  /**
   * Использовать обычные формы в управляемом приложении
   */
  boolean useOrdinaryFormInManagedApplication;

  /**
   * Информация о копирайте на разных языках
   */
  @Default
  MultiLanguageString copyright = MultiLanguageString.EMPTY;

  /**
   * Детальная информация о конфигурации, на разных языках
   */
  @Default
  MultiLanguageString detailedInformation = MultiLanguageString.EMPTY;

  /**
   * Краткая информация о конфигурации, на разных языках
   */
  @Default
  MultiLanguageString briefInformation = MultiLanguageString.EMPTY;

  /**
   * Дочерние объекты конфигурации
   */
  @Default
  List<MDObject> children = Collections.emptyList();

  /**
   * Дочерние объекты конфигурации (все, включая дочерние)
   */
  @Default
  List<MDObject> plainChildren = Collections.emptyList();

  /**
   * Список модулей конфигурации
   */
  @Default
  List<Module> modules = Collections.emptyList();

  /**
   * Вариант поддержки родительской конфигурации
   */
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;

  /**
   * MDO-Ссылка на объект
   */
  @Default
  MdoReference mdoReference = MdoReference.EMPTY;

  @Default
  String commonSettingsStorage = ""; // todo mdoref?
  @Default
  String reportsUserSettingsStorage = ""; // todo mdoref?
  @Default
  String reportsVariantsStorage = ""; // todo mdoref?
  @Default
  String formDataSettingsStorage = ""; // todo mdoref?
  @Default
  String dynamicListsUserSettingsStorage = ""; // todo mdoref?
  @Default
  String defaultReportForm = ""; // todo mdoref?
  @Default
  String defaultReportVariantForm = ""; // todo mdoref?
  @Default
  String defaultReportSettingsForm = ""; // todo mdoref?
  @Default
  String defaultDynamicListSettingsForm = ""; // todo mdoref?
  @Default
  String defaultSearchForm = ""; // todo mdoref?
  @Default
  String defaultConstantsForm = ""; // todo mdoref?
  @Default
  String mainClientApplicationWindowMode = ""; // todo enum?
  Interface defaultInterface;
  Style defaultStyle;
  @Default
  String interfaceCompatibilityMode= ""; // todo enum?

  @Default
  String vendor = "";
  @Default
  String version = "";
  @Default
  String updateCatalogAddress = "";

  boolean includeHelpInContents;

  @Default
  List<String> additionalFullTextSearchDictionaries = Collections.emptyList();
  @Default
  String content = "";

  @Default
  List<String> requiredMobileApplicationPermissions = Collections.emptyList();

  @Default
  String vendorInformationAddress = "";

  @Default
  String configurationInformationAddress = "";



  /**
   * Возвращает соответствие ссылок на файлы модулей их вариантам поддержки
   *
   * @return Соответствие ссылок на файлы и вариантов поддержки
   */
  public Map<URI, SupportVariant> getModulesBySupport() {
    return getAllModules().stream().collect(Collectors.toMap(Module::getUri, Module::getSupportVariant));
  }
}
