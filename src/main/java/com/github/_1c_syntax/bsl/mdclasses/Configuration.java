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

import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.support_configuration.SupportVariant;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Корневой класс конфигурации 1с
 */
@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
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
   * Вариант исходников конфигурации
   */
  ConfigurationSource configurationSource;

  /**
   * Режим совместимости
   */
  CompatibilityMode compatibilityMode;

  /**
   * Режим совместимости расширений
   */
  CompatibilityMode configurationExtensionCompatibilityMode;

  /**
   * Язык, на котором ведется разработка
   */
  ScriptVariant scriptVariant;

  /**
   * Режим запуска приложения по умолчанию
   */
  ApplicationRunMode defaultRunMode;

  /**
   * Язык приложения по умолчанию
   */
  Language defaultLanguage;

  /**
   * Режим управления блокировкой данных
   */
  DataLockControlMode dataLockControlMode;

  /**
   * Режим автонумерации объектов
   */
  String objectAutonumerationMode;

  /**
   * Режим использования модальных окон
   */
  UseMode modalityUseMode;

  /**
   * Режим использования синхронных вызовов
   */
  UseMode synchronousExtensionAndAddInCallUseMode;

  /**
   * Режим использования синхронных вызовов для платформенных объектов и расширений
   */
  UseMode synchronousPlatformExtensionAndAddInCallUseMode;

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
  MultiLanguageString copyrights;

  /**
   * Детальная информация о конфигурации, на разных языках
   */
  MultiLanguageString detailedInformation;

  /**
   * Краткая информация о конфигурации, на разных языках
   */
  MultiLanguageString briefInformation;

  /**
   * Дочерние объекты конфигурации
   */
  List<MDObject> children;

  /**
   * Дочерние объекты конфигурации (все, включая дочерние)
   */
  List<MDObject> plainChildren;

  /**
   * Список модулей конфигурации
   */
  List<Module> modules;

  /**
   * Вариант поддержки родительской конфигурации
   */
  SupportVariant supportVariant;

  /**
   * MDO-Ссылка на объект
   */
  MdoReference mdoReference;

  /**
   * Возвращает соответствие ссылок на файлы модулей их вариантам поддержки
   *
   * @return Соответствие ссылок на файлы и вариантов поддержки
   */
  public Map<URI, SupportVariant> getModulesBySupport() {
    return getAllModules().stream().collect(Collectors.toMap(Module::getUri, Module::getSupportVariant));
  }
}
