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

import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Корневой класс конфигурации 1с
 */
@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class Configuration implements MDClass {

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
   * Дочерние объекты конфигурации (все, включая дочерние)
   */
  List<MDObject> children;

  /**
   * Дочерние объекты конфигурации (все, включая дочерние)
   */
  List<MDObject> allChildren;

  public List<Role> roles() {
    return getChildrenByType(Role.class);
  }

  public List<CommonModule> commonModules() {
    return getChildrenByType(CommonModule.class);
  }

//
//  /**
//   * Модули объектов конфигурации в связке со ссылкой на файлы
//   */
//   Map<URI, ModuleType> modulesByType;
//  /**
//   * Модули объектов конфигурации в связке со ссылкой на файлы, сгруппированные по представлению mdoRef
//   */
//   Map<String, Map<ModuleType, URI>> modulesByMDORef;
//  /**
//   * Объекты конфигурации в связке со ссылкой на файлы
//   */
//   Map<URI, MDOHasModule> modulesByObject;
//  /**
//   * Модули конфигурации
//   */
//   List<MDOModule> modules;
//  /**
//   * Режимы поддержки в связке со ссылкой на файлы
//   */
//   Map<URI, Map<SupportConfiguration, SupportVariant>> modulesBySupport;


//  /**
//   * Дочерние объекты конфигурации с MDO ссылками на них
//   */
//   Map<MDOReference, AbstractMDObjectBase> childrenByMdoRef;
//  /**
//   * Упорядоченный список объектов метаданных верхнего уровня в разрезе типов метаданных
//   */
//   Map<MDOType, List<AbstractMDObjectBase>> orderedTopMDObjects;
//  /**
//   * Дочерние общие модули
//   */
//   Map<String, MDCommonModule> commonModules;
//  /**
//   * Доступные языки конфигурации, ключ - код языка
//   */
//   Map<String, MDLanguage> languages;
//  /**
//   * Корневой каталог конфигурации
//   */
//   Path rootPath;

//
//  protected Configuration() {
//    configurationSource = ConfigurationSource.EMPTY;
//    children = Collections.emptySet();
//    childrenByMdoRef = Collections.emptyMap();
//    modulesByType = Collections.emptyMap();
//    modulesBySupport = Collections.emptyMap();
//    modulesByObject = Collections.emptyMap();
//    modules = Collections.emptyList();
//    commonModules = Collections.emptyMap();
//    languages = Collections.emptyMap();
//    modulesByMDORef = Collections.emptyMap();
//    orderedTopMDObjects = Collections.emptyMap();
//    roles = Collections.emptyList();
//    copyrights = Collections.emptyList();
//    detailedInformation = Collections.emptyList();
//    briefInformation = Collections.emptyList();
//
//    rootPath = null;
//    name = "";
//    uuid = "";
//
//    compatibilityMode = new CompatibilityMode();
//    configurationExtensionCompatibilityMode = new CompatibilityMode();
//    scriptVariant = ScriptVariant.ENGLISH;
//
//    defaultRunMode = ApplicationRunMode.MANAGED_APPLICATION;
//    defaultLanguage = MDOFactory.fakeLanguage(scriptVariant);
//    dataLockControlMode = DataLockControlMode.AUTOMATIC;
//    objectAutonumerationMode = "";
//    modalityUseMode = UseMode.USE;
//    synchronousExtensionAndAddInCallUseMode = UseMode.USE;
//    synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;
//  }
//
//  protected Configuration(MDConfiguration mdoConfiguration, ConfigurationSource source, Path path) {
//    var allChildren = getAllChildren(mdoConfiguration);
//
//    configurationSource = source;
//    children = new HashSet<>(allChildren);
//    childrenByMdoRef = new HashMap<>();
//    orderedTopMDObjects = getOrderedTopObjectsByChildren(allChildren);
//    commonModules = new CaseInsensitiveMap<>();
//    languages = new HashMap<>();
//    roles = new ArrayList<>();
//    children.forEach((AbstractMDObjectBase mdo) -> {
//      this.childrenByMdoRef.put(mdo.getMdoReference(), mdo);
//      if (mdo instanceof MDCommonModule) {
//        this.commonModules.put(mdo.getName(), (MDCommonModule) mdo);
//      } else if (mdo instanceof MDLanguage) {
//        this.languages.put(((MDLanguage) mdo).getLanguageCode(), (MDLanguage) mdo);
//      } else if (mdo instanceof MDRole) {
//        this.roles.add((MDRole) mdo);
//      }
//    });
//
//    rootPath = path;
//
//    name = mdoConfiguration.getName();
//    uuid = mdoConfiguration.getUuid();
//
//    // если версии совместимости конфигурации нет, то используем версию совместимости расширения
//    configurationExtensionCompatibilityMode = mdoConfiguration.getConfigurationExtensionCompatibilityMode();
//    if (CompatibilityMode.compareTo(mdoConfiguration.getCompatibilityMode(), "") == 0) {
//      compatibilityMode = mdoConfiguration.getConfigurationExtensionCompatibilityMode();
//    } else {
//      compatibilityMode = mdoConfiguration.getCompatibilityMode();
//    }
//
//    scriptVariant = mdoConfiguration.getScriptVariant();
//    defaultRunMode = ApplicationRunMode.getByName(mdoConfiguration.getDefaultRunMode());
//
//    if (mdoConfiguration.getDefaultLanguage().isRight()) {
//      defaultLanguage = mdoConfiguration.getDefaultLanguage().get();
//    } else {
//      defaultLanguage = MDOFactory.fakeLanguage(scriptVariant);
//    }
//
//    dataLockControlMode = mdoConfiguration.getDataLockControlMode();
//    objectAutonumerationMode = mdoConfiguration.getObjectAutonumerationMode();
//    modalityUseMode = mdoConfiguration.getModalityUseMode();
//    synchronousExtensionAndAddInCallUseMode = mdoConfiguration.getSynchronousExtensionAndAddInCallUseMode();
//    synchronousPlatformExtensionAndAddInCallUseMode =
//      mdoConfiguration.getSynchronousPlatformExtensionAndAddInCallUseMode();
//
//    useManagedFormInOrdinaryApplication = mdoConfiguration.isUseManagedFormInOrdinaryApplication();
//    useOrdinaryFormInManagedApplication = mdoConfiguration.isUseOrdinaryFormInManagedApplication();
//
//    copyrights = mdoConfiguration.getCopyrights();
//
//    briefInformation = mdoConfiguration.getBriefInformation();
//    detailedInformation = mdoConfiguration.getDetailedInformation();
//
//    Map<URI, ModuleType> modulesType = new HashMap<>();
//    Map<URI, Map<SupportConfiguration, SupportVariant>> modulesSupport = new HashMap<>();
//    Map<URI, MDOHasModule> modulesObject = new HashMap<>();
//    Map<String, Map<ModuleType, URI>> modulesMDORef = new CaseInsensitiveMap<>();
//    List<MDOModule> modulesList = new ArrayList<>();
//    final Map<String, Map<SupportConfiguration, SupportVariant>> supportMap = getSupportMap();
//
//    children.forEach((AbstractMDObjectBase mdo) -> {
//
//      var supports = supportMap.getOrDefault(mdo.getUuid(), Collections.emptyMap());
//      if (mdo instanceof MDOHasModule) {
//        computeModules(modulesType,
//          modulesSupport,
//          modulesObject,
//          modulesList,
//          modulesMDORef,
//          (MDOHasModule) mdo,
//          supports);
//      }
//    });
//
//    modulesBySupport = modulesSupport;
//    modulesByType = modulesType;
//    modulesByObject = modulesObject;
//    modules = modulesList;
//    modulesByMDORef = modulesMDORef;
//  }
//
//  /**
//   * Метод для создания пустой конфигурации
//   */
//  public static Configuration create() {
//    return new Configuration();
//  }
//
//  /**
//   * Метод для создания пустой конфигурации расширения
//   */
//  public static Configuration createExtension() {
//    return new ConfigurationExtension();
//  }
//
//  /**
//   * Метод создания конфигурации каталогу исходных файлов
//   *
//   * @param rootPath - Адрес корневого каталога конфигурации
//   */
//  public static Configuration create(Path rootPath) {
//    var configurationSource = MDOUtils.getConfigurationSourceByPath(rootPath);
//    if (configurationSource != ConfigurationSource.EMPTY) {
//      var configurationMDO = MDOFactory.readMDOConfiguration(configurationSource, rootPath);
//      if (configurationMDO.isPresent()) {
//        var mdoConfiguration = (MDConfiguration) configurationMDO.get();
//        if (mdoConfiguration.getObjectBelonging() == ObjectBelonging.ADOPTED) {
//          return new ConfigurationExtension(mdoConfiguration, configurationSource, rootPath);
//        } else {
//          return new Configuration(mdoConfiguration, configurationSource, rootPath);
//        }
//      }
//    }
//
//    return create();
//  }
//
//  public Optional<Path> getRootPath() {
//    if (rootPath == null) {
//      return Optional.empty();
//    }
//    return Optional.of(rootPath);
//  }
//
//  /**
//   * Возвращает тип модуля по ссылке на его файл
//   */
//  public ModuleType getModuleType(URI uri) {
//    return modulesByType.getOrDefault(uri, ModuleType.UNKNOWN);
//  }
//
//  /**
//   * Возвращает режим поддержки по ссылке на файл
//   */
//  public Map<SupportConfiguration, SupportVariant> getModuleSupport(URI uri) {
//    return modulesBySupport.getOrDefault(uri, new HashMap<>());
//  }
//
//  /**
//   * Возвращает общий модуль по его имени
//   *
//   * @param name - Имя модуля
//   */
//  public Optional<MDCommonModule> getCommonModule(String name) {
//    return Optional.ofNullable(commonModules.get(name));
//  }
//
//  /**
//   * Модули объектов конфигурации в связке со ссылкой на файлы по ссылке mdoRef
//   *
//   * @param mdoRef Строковая ссылка на объект
//   * @return Соответствие ссылки на файл и его тип
//   */
//  public Map<ModuleType, URI> getModulesByMDORef(String mdoRef) {
//    return modulesByMDORef.getOrDefault(mdoRef, Collections.emptyMap());
//  }
//
//  /**
//   * Модули объектов конфигурации в связке со ссылкой на файлы по ссылке mdoRef
//   *
//   * @param mdoRef Ссылка на объект
//   * @return Соответствие ссылки на файл и его тип
//   */
//  public Map<ModuleType, URI> getModulesByMDORef(MDOReference mdoRef) {
//    return getModulesByMDORef(mdoRef.getMdoRef());
//  }
//
//   Map<String, Map<SupportConfiguration, SupportVariant>> getSupportMap() {
//    var fileParentConfiguration = MDOPathUtils.getParentConfigurationsPath(configurationSource, rootPath);
//    if (fileParentConfiguration.isPresent() && fileParentConfiguration.get().toFile().exists()) {
//      var supportData = new ParseSupportData(fileParentConfiguration.get());
//      return supportData.getSupportMap();
//    }
//    return Collections.emptyMap();
//  }
//
//  // todo надо рефакторить!!!!
//   static void computeModules(Map<URI, ModuleType> modulesType,
//                                     Map<URI, Map<SupportConfiguration, SupportVariant>> modulesSupport,
//                                     Map<URI, MDOHasModule> modulesObject,
//                                     List<MDOModule> modulesList,
//                                     Map<String, Map<ModuleType, URI>> modulesMDORef, MDOHasModule mdo,
//                                     Map<SupportConfiguration, SupportVariant> supports) {
//    Map<ModuleType, URI> modulesTypesAndURIs = new EnumMap<>(ModuleType.class);
//    mdo.getModules().forEach((MDOModule module) -> {
//      var uri = module.getUri();
//      modulesType.put(uri, module.getModuleType());
//      modulesTypesAndURIs.put(module.getModuleType(), uri);
//      modulesObject.put(uri, mdo);
//      if (!supports.isEmpty()) {
//        modulesSupport.put(uri, supports);
//      }
//      modulesList.add(module);
//    });
//    modulesMDORef.put(mdo.getMdoReference().getMdoRef(), modulesTypesAndURIs);
//  }
//
//   static List<AbstractMDObjectBase> getAllChildren(MDConfiguration mdoConfiguration) {
//    List<AbstractMDObjectBase> allChildren = mdoConfiguration.getChildren().stream()
//      .filter(Either::isRight).map(Either::get)
//      .collect(Collectors.toList());
//
//    allChildren.addAll(allChildren.stream()
//      .filter(MDOHasChildren.class::isInstance)
//      .map(MDOHasChildren.class::cast)
//      .map(MDOHasChildren::getChildren)
//      .flatMap(Collection::stream)
//      .collect(Collectors.toList()));
//
//    allChildren.add(mdoConfiguration);
//    return allChildren;
//  }
//
//   static Map<MDOType, List<AbstractMDObjectBase>> getOrderedTopObjectsByChildren(
//    List<AbstractMDObjectBase> children) {
//
//    return children.stream().collect(Collectors.groupingBy(AbstractMDObjectBase::getType));
//  }


}
