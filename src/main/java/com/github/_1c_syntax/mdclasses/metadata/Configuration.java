/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses.metadata;

import com.github._1c_syntax.mdclasses.mdo.CommonModule;
import com.github._1c_syntax.mdclasses.mdo.MDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.MDOConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBSL;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDObjectComplex;
import com.github._1c_syntax.mdclasses.mdo.TabularSection;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOModule;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOReference;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ParseSupportData;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.SupportVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import io.vavr.control.Either;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.net.URI;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Value
@Slf4j
public class Configuration {

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
  String defaultRunMode;
  /**
   * Язык приложения по умолчанию
   */
  String defaultLanguage;
  /**
   * Режим управления блокировкой данных
   */
  String dataLockControlMode;
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
   * Модули объектов конфигурации в связке со ссылкой на файлы
   */
  Map<URI, ModuleType> modulesByType;
  /**
   * Объекты конфигурации в связке со ссылкой на файлы
   */
  Map<URI, MDObjectBase> modulesByObject;
  /**
   * Режимы поддержки в связке со ссылкой на файлы
   */
  Map<URI, Map<SupportConfiguration, SupportVariant>> modulesBySupport;
  /**
   * Дочерние объекты конфигурации (все, включая дочерние)
   */
  Set<MDObjectBase> children;
  /**
   * Дочерние объекты конфигурации с MDO ссылками на них
   */
  Map<MDOReference, MDObjectBase> childrenByMdoRef;
  /**
   * Дочерние общие модули
   */
  Map<String, CommonModule> commonModules;
  /**
   * Корневой каталог конфигурации
   */
  Path rootPath;

  private Configuration() {
    configurationSource = ConfigurationSource.EMPTY;
    children = Collections.emptySet();
    childrenByMdoRef = Collections.emptyMap();
    modulesByType = Collections.emptyMap();
    modulesBySupport = Collections.emptyMap();
    modulesByObject = Collections.emptyMap();
    commonModules = Collections.emptyMap();

    rootPath = null;
    name = "";
    uuid = "";

    compatibilityMode = new CompatibilityMode();
    configurationExtensionCompatibilityMode = new CompatibilityMode();
    scriptVariant = ScriptVariant.ENGLISH;

    defaultRunMode = "";
    defaultLanguage = "";
    dataLockControlMode = "";
    objectAutonumerationMode = "";
    modalityUseMode = UseMode.USE;
    synchronousExtensionAndAddInCallUseMode = UseMode.USE;
    synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;
  }

  private Configuration(MDOConfiguration mdoConfiguration, ConfigurationSource source, Path path) {
    configurationSource = source;
    children = getAllChildren(mdoConfiguration);
    childrenByMdoRef = new HashMap<>();
    commonModules = new CaseInsensitiveMap<>();
    children.forEach((MDObjectBase mdo) -> {
      this.childrenByMdoRef.put(mdo.getMdoReference(), mdo);
      if (mdo instanceof CommonModule) {
        this.commonModules.put(mdo.getName(), (CommonModule) mdo);
      }
    });

    rootPath = path;

    name = mdoConfiguration.getName();
    uuid = mdoConfiguration.getUuid();

    // если версии совместимости конфигурации нет, то используем версию совместимости расширения
    configurationExtensionCompatibilityMode = mdoConfiguration.getConfigurationExtensionCompatibilityMode();
    if (CompatibilityMode.compareTo(mdoConfiguration.getCompatibilityMode(), "") == 0) {
      compatibilityMode = mdoConfiguration.getConfigurationExtensionCompatibilityMode();
    } else {
      compatibilityMode = mdoConfiguration.getCompatibilityMode();
    }

    scriptVariant = mdoConfiguration.getScriptVariant();

    defaultRunMode = mdoConfiguration.getDefaultRunMode();
    defaultLanguage = mdoConfiguration.getDefaultLanguage();
    dataLockControlMode = mdoConfiguration.getDataLockControlMode();
    objectAutonumerationMode = mdoConfiguration.getObjectAutonumerationMode();
    modalityUseMode = mdoConfiguration.getModalityUseMode();
    synchronousExtensionAndAddInCallUseMode = mdoConfiguration.getSynchronousExtensionAndAddInCallUseMode();
    synchronousPlatformExtensionAndAddInCallUseMode =
      mdoConfiguration.getSynchronousPlatformExtensionAndAddInCallUseMode();

    Map<URI, ModuleType> modulesType = new HashMap<>();
    Map<URI, Map<SupportConfiguration, SupportVariant>> modulesSupport = new HashMap<>();
    Map<URI, MDObjectBase> modulesObject = new HashMap<>();
    final Map<String, Map<SupportConfiguration, SupportVariant>> supportMap = getSupportMap();

    children.forEach((MDObjectBase mdo) -> {
      var supports = supportMap.getOrDefault(mdo.getUuid(), Collections.emptyMap());

      // todo возможно надо будет добавить ссылку на mdo файл

      if (mdo instanceof MDObjectBSL) {
        computeModules(modulesType, modulesSupport, modulesObject, (MDObjectBSL) mdo, supports);
      }
    });

    modulesBySupport = modulesSupport;
    modulesByType = modulesType;
    modulesByObject = modulesObject;
  }

  /**
   * Метод для создания пустой конфигурации
   */
  public static Configuration create() {
    return new Configuration();
  }

  /**
   * Метод создания конфигурации каталогу исходных файлов
   *
   * @param rootPath - Адрес корневого каталога конфигурации
   */
  public static Configuration create(Path rootPath) {
    ConfigurationSource configurationSource = MDOUtils.getConfigurationSourceByPath(rootPath);
    if (configurationSource != ConfigurationSource.EMPTY) {
      var configurationMDO = MDOFactory.readMDOConfiguration(configurationSource, rootPath);
      if (configurationMDO.isPresent()) {
        return new Configuration((MDOConfiguration) configurationMDO.get(), configurationSource, rootPath);
      }
    }

    return create();
  }

  private static Set<MDObjectBase> getAllChildren(MDOConfiguration mdoConfiguration) {
    Set<MDObjectBase> allChildren = new HashSet<>();
    mdoConfiguration.getChildren().stream().filter(Either::isRight).map(Either::get)
      .forEach((MDObjectBase mdo) -> {
        allChildren.add(mdo);
        if (mdo instanceof MDObjectComplex) {
          allChildren.addAll(((MDObjectComplex) mdo).getForms());
          allChildren.addAll(((MDObjectComplex) mdo).getCommands());
          allChildren.addAll(((MDObjectComplex) mdo).getTemplates());
          ((MDObjectComplex) mdo).getAttributes().forEach((MDOAttribute child) -> {
            allChildren.add(child);
            if (child instanceof TabularSection) {
              allChildren.addAll(((TabularSection) child).getAttributes());
            }
          });
        }
      });

    allChildren.add(mdoConfiguration);
    return allChildren;
  }

  public Optional<Path> getRootPath() {
    if (rootPath == null) {
      return Optional.empty();
    }
    return Optional.of(rootPath);
  }

  /**
   * Возвращает тип модулея по ссылке на его файл
   */
  public ModuleType getModuleType(URI uri) {
    return modulesByType.getOrDefault(uri, ModuleType.UNKNOWN);
  }

  /**
   * Возвращает режим поддержки по ссылке на файл
   */
  public Map<SupportConfiguration, SupportVariant> getModuleSupport(URI uri) {
    return modulesBySupport.getOrDefault(uri, new HashMap<>());
  }

  /**
   * Возвращает общий модуль по его имени
   *
   * @param name - Имя модуля
   */
  public Optional<CommonModule> getCommonModule(String name) {
    return Optional.ofNullable(commonModules.get(name));
  }

  private Map<String, Map<SupportConfiguration, SupportVariant>> getSupportMap() {
    var fileParentConfiguration = MDOPathUtils.getParentConfigurationsPath(configurationSource, rootPath);
    if (fileParentConfiguration.isPresent() && fileParentConfiguration.get().toFile().exists()) {
      ParseSupportData supportData = new ParseSupportData(fileParentConfiguration.get());
      return supportData.getSupportMap();
    }
    return Collections.emptyMap();
  }

  private static void computeModules(Map<URI, ModuleType> modulesType,
                                     Map<URI, Map<SupportConfiguration, SupportVariant>> modulesSupport,
                                     Map<URI, MDObjectBase> modulesObject,
                                     MDObjectBSL mdo,
                                     Map<SupportConfiguration, SupportVariant> supports) {
    mdo.getModules().forEach((MDOModule module) -> {
      var uri = module.getUri();
      modulesType.put(uri, module.getModuleType());
      modulesObject.put(uri, mdo);
      if (!supports.isEmpty()) {
        modulesSupport.put(uri, supports);
      }
    });
  }
}
