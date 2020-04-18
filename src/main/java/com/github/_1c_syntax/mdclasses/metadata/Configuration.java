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
import com.github._1c_syntax.mdclasses.mdo.MDOConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.SupportVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.github._1c_syntax.mdclasses.utils.Common;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

@Value
@Slf4j
public class Configuration {

  String name;
  String uuid;

  ConfigurationSource configurationSource;
  CompatibilityMode compatibilityMode;
  CompatibilityMode configurationExtensionCompatibilityMode;
  ScriptVariant scriptVariant;

  String defaultRunMode;
  String defaultLanguage;
  String dataLockControlMode;
  String objectAutonumerationMode;
  UseMode modalityUseMode;
  UseMode synchronousExtensionAndAddInCallUseMode;
  UseMode synchronousPlatformExtensionAndAddInCallUseMode;

  Map<URI, ModuleType> modulesByType;
  TreeMap<String, CommonModule> commonModules;
  Map<URI, MDObjectBase> modulesByURI;
  Map<URI, Map<SupportConfiguration, SupportVariant>> modulesBySupport;
  Set<MDObjectBase> children;
  Map<String, MDObjectBase> childrenByMdoRef;
  Path rootPath;

  private Configuration() {
    this.configurationSource = ConfigurationSource.EMPTY;
    this.children = Collections.emptySet();
    this.childrenByMdoRef = Collections.emptyMap();
    this.modulesByType = Collections.emptyMap();
    this.modulesBySupport = Collections.emptyMap();
    this.commonModules = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    this.modulesByURI = Collections.emptyMap();

    this.rootPath = null;
    this.name = "";
    this.uuid = "";

    this.compatibilityMode = new CompatibilityMode();
    this.configurationExtensionCompatibilityMode = new CompatibilityMode();
    this.scriptVariant = ScriptVariant.ENGLISH;

    this.defaultRunMode = "";
    this.defaultLanguage = "";
    this.dataLockControlMode = "";
    this.objectAutonumerationMode = "";
    this.modalityUseMode = UseMode.USE;
    this.synchronousExtensionAndAddInCallUseMode = UseMode.USE;
    this.synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;
  }

  private Configuration(MDOConfiguration configurationXml, ConfigurationSource configurationSource, Path rootPath) {
    this.configurationSource = configurationSource;
    this.children = MDOUtils.getAllChildren(configurationSource, rootPath, true);
    this.childrenByMdoRef = new HashMap<>();
    this.children.forEach(mdo -> this.childrenByMdoRef.put(mdo.getMdoRef(), mdo));

    this.rootPath = rootPath;

    this.name = configurationXml.getName();
    this.uuid = configurationXml.getUuid();

    this.compatibilityMode = configurationXml.getCompatibilityMode();
    this.configurationExtensionCompatibilityMode = configurationXml.getConfigurationExtensionCompatibilityMode();
    this.scriptVariant = configurationXml.getScriptVariant();

    this.defaultRunMode = configurationXml.getDefaultRunMode();
    this.defaultLanguage = configurationXml.getDefaultLanguage();
    this.dataLockControlMode = configurationXml.getDataLockControlMode();
    this.objectAutonumerationMode = configurationXml.getObjectAutonumerationMode();
    this.modalityUseMode = configurationXml.getModalityUseMode();
    this.synchronousExtensionAndAddInCallUseMode = configurationXml.getSynchronousExtensionAndAddInCallUseMode();
    this.synchronousPlatformExtensionAndAddInCallUseMode = configurationXml.getSynchronousPlatformExtensionAndAddInCallUseMode();

    this.modulesByType = Common.getModuleTypesByPath(this);
    this.modulesBySupport = Common.getModuleSupports(this);
    this.commonModules = Common.getCommonModules(this);
    this.modulesByURI = Common.getModulesByURI(this);

  }

  public static Configuration create() {
    return new Configuration();
  }

  public static Configuration create(Path rootPath) {
    ConfigurationSource configurationSource = MDOUtils.getConfigurationSourceByPath(rootPath);
    if (configurationSource != ConfigurationSource.EMPTY) {
      MDOConfiguration configurationXML = (MDOConfiguration) MDOUtils.getMDObject(configurationSource,
        rootPath, MDOType.CONFIGURATION, "Configuration");
      if (configurationXML != null) {
        return new Configuration(configurationXML, configurationSource, rootPath);
      }
    }

    return create();
  }

  public ModuleType getModuleType(URI uri) {
    return modulesByType.getOrDefault(uri, ModuleType.Unknown);
  }

  public Map<SupportConfiguration, SupportVariant> getModuleSupport(URI uri) {
    return modulesBySupport.getOrDefault(uri, new HashMap<>());
  }

  public Optional<CommonModule> getCommonModule(String name) {
    return Optional.ofNullable(commonModules.get(name));
  }

}
