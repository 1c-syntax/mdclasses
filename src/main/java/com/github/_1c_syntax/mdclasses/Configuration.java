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
package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.common.CompatibilityMode;
import com.github._1c_syntax.mdclasses.common.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBSL;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDCommonModule;
import com.github._1c_syntax.mdclasses.mdo.MDConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDLanguage;
import com.github._1c_syntax.mdclasses.mdo.MDOHasChildren;
import com.github._1c_syntax.mdclasses.mdo.MDRole;
import com.github._1c_syntax.mdclasses.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.mdclasses.mdo.support.DataLockControlMode;
import com.github._1c_syntax.mdclasses.mdo.support.LanguageContent;
import com.github._1c_syntax.mdclasses.mdo.support.MDOModule;
import com.github._1c_syntax.mdclasses.mdo.support.MDOReference;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import com.github._1c_syntax.mdclasses.mdo.support.ObjectBelonging;
import com.github._1c_syntax.mdclasses.mdo.support.ScriptVariant;
import com.github._1c_syntax.mdclasses.mdo.support.UseMode;
import com.github._1c_syntax.mdclasses.supportconf.ParseSupportData;
import com.github._1c_syntax.mdclasses.supportconf.SupportConfiguration;
import com.github._1c_syntax.mdclasses.supportconf.SupportVariant;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import io.vavr.control.Either;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Корневой класс конфигурации 1с
 */
@Data
@Slf4j
public class Configuration {

  /**
   * Имя конфигурации
   */
  private String name;
  /**
   * Уникальный идентификатор конфигурации
   */
  private String uuid;

  /**
   * Вариант исходников конфигурации
   */
  private ConfigurationSource configurationSource;
  /**
   * Режим совместимости
   */
  private CompatibilityMode compatibilityMode;
  /**
   * Режим совместимости расширений
   */
  private CompatibilityMode configurationExtensionCompatibilityMode;
  /**
   * Язык, на котором ведется разработка
   */
  private ScriptVariant scriptVariant;

  /**
   * Режим запуска приложения по умолчанию
   */
  private ApplicationRunMode defaultRunMode;
  /**
   * Язык приложения по умолчанию
   */
  private MDLanguage defaultLanguage;
  /**
   * Режим управления блокировкой данных
   */
  private DataLockControlMode dataLockControlMode;
  /**
   * Режим автонумерации объектов
   */
  private String objectAutonumerationMode;
  /**
   * Режим использования модальных окон
   */
  private UseMode modalityUseMode;
  /**
   * Режим использования синхронных вызовов
   */
  private UseMode synchronousExtensionAndAddInCallUseMode;
  /**
   * Режим использования синхронных вызовов для платформенных объектов и расширений
   */
  private UseMode synchronousPlatformExtensionAndAddInCallUseMode;

  /**
   * Использовать управляемые формы в обычном приложении
   */
  private boolean useManagedFormInOrdinaryApplication;
  /**
   * Использовать обычные формы в управляемом приложении
   */
  private boolean useOrdinaryFormInManagedApplication;

  /**
   * Информация о копирайте на разных языках
   */
  private List<LanguageContent> copyrights;

  /**
   * Детальная информация о конфигурации, на разных языках
   */
  private List<LanguageContent> detailedInformation;

  /**
   * Краткая информация о конфигурации, на разных языках
   */
  private List<LanguageContent> briefInformation;

  /**
   * Модули объектов конфигурации в связке со ссылкой на файлы
   */
  private Map<URI, ModuleType> modulesByType;
  /**
   * Модули объектов конфигурации в связке со ссылкой на файлы, сгруппированные по представлению mdoRef
   */
  private Map<String, Map<ModuleType, URI>> modulesByMDORef;
  /**
   * Объекты конфигурации в связке со ссылкой на файлы
   */
  private Map<URI, AbstractMDObjectBSL> modulesByObject;
  /**
   * Модули конфигурации
   */
  private List<MDOModule> modules;
  /**
   * Режимы поддержки в связке со ссылкой на файлы
   */
  private Map<URI, Map<SupportConfiguration, SupportVariant>> modulesBySupport;
  /**
   * Дочерние объекты конфигурации (все, включая дочерние)
   */
  private Set<AbstractMDObjectBase> children;
  /**
   * Дочерние объекты конфигурации с MDO ссылками на них
   */
  private Map<MDOReference, AbstractMDObjectBase> childrenByMdoRef;
  /**
   * Дочерние общие модули
   */
  private Map<String, MDCommonModule> commonModules;
  /**
   * Доступные языки конфигурации, ключ - код языка
   */
  private Map<String, MDLanguage> languages;
  /**
   * Корневой каталог конфигурации
   */
  private Path rootPath;
  /**
   * Роли
   */
  private List<MDRole> roles;

  protected Configuration() {
    configurationSource = ConfigurationSource.EMPTY;
    children = Collections.emptySet();
    childrenByMdoRef = Collections.emptyMap();
    modulesByType = Collections.emptyMap();
    modulesBySupport = Collections.emptyMap();
    modulesByObject = Collections.emptyMap();
    modules = Collections.emptyList();
    commonModules = Collections.emptyMap();
    languages = Collections.emptyMap();
    modulesByMDORef = Collections.emptyMap();
    roles = Collections.emptyList();
    copyrights = Collections.emptyList();
    detailedInformation = Collections.emptyList();
    briefInformation = Collections.emptyList();

    rootPath = null;
    name = "";
    uuid = "";

    compatibilityMode = new CompatibilityMode();
    configurationExtensionCompatibilityMode = new CompatibilityMode();
    scriptVariant = ScriptVariant.ENGLISH;

    defaultRunMode = ApplicationRunMode.MANAGED_APPLICATION;
    defaultLanguage = MDOFactory.fakeLanguage(scriptVariant);
    dataLockControlMode = DataLockControlMode.AUTOMATIC;
    objectAutonumerationMode = "";
    modalityUseMode = UseMode.USE;
    synchronousExtensionAndAddInCallUseMode = UseMode.USE;
    synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;
  }

  protected Configuration(MDConfiguration mdoConfiguration, ConfigurationSource source, Path path) {
    configurationSource = source;
    children = getAllChildren(mdoConfiguration);
    childrenByMdoRef = new HashMap<>();
    commonModules = new CaseInsensitiveMap<>();
    languages = new HashMap<>();
    roles = new ArrayList<>();
    children.forEach((AbstractMDObjectBase mdo) -> {
      this.childrenByMdoRef.put(mdo.getMdoReference(), mdo);
      if (mdo instanceof MDCommonModule) {
        this.commonModules.put(mdo.getName(), (MDCommonModule) mdo);
      } else if (mdo instanceof MDLanguage) {
        this.languages.put(((MDLanguage) mdo).getLanguageCode(), (MDLanguage) mdo);
      } else if (mdo instanceof MDRole) {
        this.roles.add((MDRole) mdo);
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
    defaultRunMode = ApplicationRunMode.getByName(mdoConfiguration.getDefaultRunMode());

    if (mdoConfiguration.getDefaultLanguage().isRight()) {
      defaultLanguage = mdoConfiguration.getDefaultLanguage().get();
    } else {
      defaultLanguage = MDOFactory.fakeLanguage(scriptVariant);
    }

    dataLockControlMode = mdoConfiguration.getDataLockControlMode();
    objectAutonumerationMode = mdoConfiguration.getObjectAutonumerationMode();
    modalityUseMode = mdoConfiguration.getModalityUseMode();
    synchronousExtensionAndAddInCallUseMode = mdoConfiguration.getSynchronousExtensionAndAddInCallUseMode();
    synchronousPlatformExtensionAndAddInCallUseMode =
      mdoConfiguration.getSynchronousPlatformExtensionAndAddInCallUseMode();

    useManagedFormInOrdinaryApplication = mdoConfiguration.isUseManagedFormInOrdinaryApplication();
    useOrdinaryFormInManagedApplication = mdoConfiguration.isUseOrdinaryFormInManagedApplication();

    copyrights = mdoConfiguration.getCopyrights();

    briefInformation = mdoConfiguration.getBriefInformation();
    detailedInformation = mdoConfiguration.getDetailedInformation();

    Map<URI, ModuleType> modulesType = new HashMap<>();
    Map<URI, Map<SupportConfiguration, SupportVariant>> modulesSupport = new HashMap<>();
    Map<URI, AbstractMDObjectBSL> modulesObject = new HashMap<>();
    Map<String, Map<ModuleType, URI>> modulesMDORef = new CaseInsensitiveMap<>();
    List<MDOModule> modulesList = new ArrayList<>();
    final Map<String, Map<SupportConfiguration, SupportVariant>> supportMap = getSupportMap();

    children.forEach((AbstractMDObjectBase mdo) -> {

      var supports = supportMap.getOrDefault(mdo.getUuid(), Collections.emptyMap());
      if (mdo instanceof AbstractMDObjectBSL) {
        computeModules(modulesType,
          modulesSupport,
          modulesObject,
          modulesList,
          modulesMDORef,
          (AbstractMDObjectBSL) mdo,
          supports);
      }
    });

    modulesBySupport = modulesSupport;
    modulesByType = modulesType;
    modulesByObject = modulesObject;
    modules = modulesList;
    modulesByMDORef = modulesMDORef;
  }

  /**
   * Метод для создания пустой конфигурации
   */
  public static Configuration create() {
    return new Configuration();
  }

  /**
   * Метод для создания пустой конфигурации расширения
   */
  public static Configuration createExtension() {
    return new ConfigurationExtension();
  }

  /**
   * Метод создания конфигурации каталогу исходных файлов
   *
   * @param rootPath - Адрес корневого каталога конфигурации
   */
  public static Configuration create(Path rootPath) {
    var configurationSource = MDOUtils.getConfigurationSourceByPath(rootPath);
    if (configurationSource != ConfigurationSource.EMPTY) {
      var configurationMDO = MDOFactory.readMDOConfiguration(configurationSource, rootPath);
      if (configurationMDO.isPresent()) {
        var mdoConfiguration = (MDConfiguration) configurationMDO.get();
        if (mdoConfiguration.getObjectBelonging() == ObjectBelonging.ADOPTED) {
          return new ConfigurationExtension(mdoConfiguration, configurationSource, rootPath);
        } else {
          return new Configuration(mdoConfiguration, configurationSource, rootPath);
        }
      }
    }

    return create();
  }

  public Optional<Path> getRootPath() {
    if (rootPath == null) {
      return Optional.empty();
    }
    return Optional.of(rootPath);
  }

  /**
   * Возвращает тип модуля по ссылке на его файл
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
  public Optional<MDCommonModule> getCommonModule(String name) {
    return Optional.ofNullable(commonModules.get(name));
  }

  /**
   * Модули объектов конфигурации в связке со ссылкой на файлы по ссылке mdoRef
   *
   * @param mdoRef Строковая ссылка на объект
   * @return Соответствие ссылки на файл и его тип
   */
  public Map<ModuleType, URI> getModulesByMDORef(String mdoRef) {
    return modulesByMDORef.getOrDefault(mdoRef, Collections.emptyMap());
  }

  /**
   * Модули объектов конфигурации в связке со ссылкой на файлы по ссылке mdoRef
   *
   * @param mdoRef Ссылка на объект
   * @return Соответствие ссылки на файл и его тип
   */
  public Map<ModuleType, URI> getModulesByMDORef(MDOReference mdoRef) {
    return getModulesByMDORef(mdoRef.getMdoRef());
  }

  private Map<String, Map<SupportConfiguration, SupportVariant>> getSupportMap() {
    var fileParentConfiguration = MDOPathUtils.getParentConfigurationsPath(configurationSource, rootPath);
    if (fileParentConfiguration.isPresent() && fileParentConfiguration.get().toFile().exists()) {
      var supportData = new ParseSupportData(fileParentConfiguration.get());
      return supportData.getSupportMap();
    }
    return Collections.emptyMap();
  }

  // todo надо рефакторить!!!!
  private static void computeModules(Map<URI, ModuleType> modulesType,
                                     Map<URI, Map<SupportConfiguration, SupportVariant>> modulesSupport,
                                     Map<URI, AbstractMDObjectBSL> modulesObject,
                                     List<MDOModule> modulesList,
                                     Map<String, Map<ModuleType, URI>> modulesMDORef, AbstractMDObjectBSL mdo,
                                     Map<SupportConfiguration, SupportVariant> supports) {
    Map<ModuleType, URI> modulesTypesAndURIs = new EnumMap<>(ModuleType.class);
    mdo.getModules().forEach((MDOModule module) -> {
      var uri = module.getUri();
      modulesType.put(uri, module.getModuleType());
      modulesTypesAndURIs.put(module.getModuleType(), uri);
      modulesObject.put(uri, mdo);
      if (!supports.isEmpty()) {
        modulesSupport.put(uri, supports);
      }
      modulesList.add(module);
    });
    modulesMDORef.put(mdo.getMdoReference().getMdoRef(), modulesTypesAndURIs);
  }

  private static Set<AbstractMDObjectBase> getAllChildren(MDConfiguration mdoConfiguration) {
    Set<AbstractMDObjectBase> allChildren = mdoConfiguration.getChildren().stream()
      .filter(Either::isRight).map(Either::get)
      .collect(Collectors.toSet());

    allChildren.addAll(allChildren.stream()
      .filter(MDOHasChildren.class::isInstance)
      .map(MDOHasChildren.class::cast)
      .map(MDOHasChildren::getChildren)
      .flatMap(Collection::stream)
      .collect(Collectors.toSet()));

    allChildren.add(mdoConfiguration);
    return allChildren;
  }

}
