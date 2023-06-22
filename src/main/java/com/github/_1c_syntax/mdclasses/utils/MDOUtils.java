/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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
package com.github._1c_syntax.mdclasses.utils;

import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.github._1c_syntax.mdclasses.mdo.support.MDOModule;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@UtilityClass
public class MDOUtils {
  private static final byte[] PROTECTED_FILE_HEADER = new byte[]{-1, -1, -1, 127};
  public final String TYPE_INPUT_FIELD = "InputField";
  private final Map<MDOType, Set<ModuleType>> MODULE_TYPES_FOR_MDO_TYPES = moduleTypesForMDOTypes();

  /**
   * Определяет тип исходников по корню проекта
   */
  public ConfigurationSource getConfigurationSourceByPath(Path rootPath) {
    var configurationSource = ConfigurationSource.EMPTY;
    if (rootPath != null) {
      var rootPathString = rootPath.toString();

      var rootConfiguration = new File(rootPathString, "Configuration.xml");
      if (rootConfiguration.exists()) {
        configurationSource = ConfigurationSource.DESIGNER;
      } else {
        rootConfiguration = Paths.get(rootPathString, "src", MDOType.CONFIGURATION.getName(),
          "Configuration.mdo").toFile();
        if (rootConfiguration.exists()) {
          configurationSource = ConfigurationSource.EDT;
        }
      }
    }
    return configurationSource;
  }

  /**
   * Определяет тип исходников по MDO файлу
   */
  public ConfigurationSource getConfigurationSourceByMDOPath(Path path) {
    if (path.toString().endsWith(MDOPathUtils.mdoExtension(ConfigurationSource.DESIGNER, true))) {
      return ConfigurationSource.DESIGNER;
    } else {
      return ConfigurationSource.EDT;
    }
  }

  /**
   * Возвращает соответствие типов объектов и доступных им типов модулей
   */
  public Map<MDOType, Set<ModuleType>> getModuleTypesForMdoTypes() {
    return MODULE_TYPES_FOR_MDO_TYPES;
  }

  private Map<MDOType, Set<ModuleType>> moduleTypesForMDOTypes() {
    Map<MDOType, Set<ModuleType>> result = new EnumMap<>(MDOType.class);

    for (MDOType mdoType : MDOType.values()) {
      Set<ModuleType> types = new HashSet<>();
      switch (mdoType) {
        case INTEGRATION_SERVICE:
          types.add(ModuleType.IntegrationServiceModule);
          break;
        case BOT:
          types.add(ModuleType.BotModule);
          break;
        case ACCOUNTING_REGISTER:
        case ACCUMULATION_REGISTER:
        case CALCULATION_REGISTER:
        case INFORMATION_REGISTER:
          types.add(ModuleType.ManagerModule);
          types.add(ModuleType.RecordSetModule);
          break;
        case BUSINESS_PROCESS:
        case CATALOG:
        case CHART_OF_ACCOUNTS:
        case CHART_OF_CALCULATION_TYPES:
        case CHART_OF_CHARACTERISTIC_TYPES:
        case DATA_PROCESSOR:
        case DOCUMENT:
        case EXCHANGE_PLAN:
        case REPORT:
        case TASK:
          types.add(ModuleType.ManagerModule);
          types.add(ModuleType.ObjectModule);
          break;
        case COMMAND_GROUP:
        case COMMON_ATTRIBUTE:
        case COMMON_PICTURE:
        case COMMON_TEMPLATE:
        case DEFINED_TYPE:
        case DOCUMENT_NUMERATOR:
        case EVENT_SUBSCRIPTION:
        case FUNCTIONAL_OPTION:
        case ROLE:
        case SCHEDULED_JOB:
        case SESSION_PARAMETER:
        case STYLE_ITEM:
        case STYLE:
        case SUBSYSTEM:
        case WS_REFERENCE:
        case XDTO_PACKAGE:
          break;
        case COMMON_COMMAND:
        case COMMAND:
          types.add(ModuleType.CommandModule);
          break;
        case COMMON_FORM:
        case FORM:
          types.add(ModuleType.FormModule);
          break;
        case COMMON_MODULE:
          types.add(ModuleType.CommonModule);
          break;
        case CONFIGURATION:
          types.add(ModuleType.ApplicationModule);
          types.add(ModuleType.SessionModule);
          types.add(ModuleType.ExternalConnectionModule);
          types.add(ModuleType.ManagedApplicationModule);
          types.add(ModuleType.OrdinaryApplicationModule);
          break;
        case CONSTANT:
          types.add(ModuleType.ValueManagerModule);
          break;
        case DOCUMENT_JOURNAL:
        case ENUM:
        case FILTER_CRITERION:
        case SETTINGS_STORAGE:
          types.add(ModuleType.ManagerModule);
          break;
        case HTTP_SERVICE:
          types.add(ModuleType.HTTPServiceModule);
          break;
        case SEQUENCE:
          types.add(ModuleType.RecordSetModule);
          break;
        case RECALCULATION:
          types.add(ModuleType.RecalculationModule);
          break;
        case WEB_SERVICE:
          types.add(ModuleType.WEBServiceModule);
          break;
        default:
          // non
      }

      result.put(mdoType, types);
    }

    return result;
  }

  public static Optional<MDOModule> getMdoModule(ModuleOwner mdo, ConfigurationSource configurationSource,
                                                 ModuleType moduleType, Path modulePath) {
    final var modulePathExists = modulePath.toFile().exists();
    var protectedModulePath = computeIsProtected(modulePath, modulePathExists, configurationSource);
    var isProtected = protectedModulePath.isPresent();
    if (modulePathExists) {
      return Optional.of(new MDOModule(moduleType, modulePath.toUri(), mdo, isProtected));
    } else if (isProtected) {
      return Optional.of(new MDOModule(moduleType, protectedModulePath.get().toUri(), mdo, true));
    }
    return Optional.empty();
  }

  public static Optional<Path> computeIsProtected(Path modulePath, boolean modulePathExists,
                                                  ConfigurationSource configurationSource) {
    switch (configurationSource) {
      case EDT:
        return computeIsProtectedForEDT(modulePath, modulePathExists);
      case DESIGNER:
        return computeIsProtectedForDesigner(modulePath, modulePathExists);
      default:
        break;
    }
    return Optional.empty();
  }

  public static Optional<Path> computeIsProtectedForEDT(Path modulePath, boolean modulePathExists) {
    if (modulePathExists) {
      var bytes = new byte[PROTECTED_FILE_HEADER.length];

      try (var fis = new FileInputStream(modulePath.toFile())) {
        var count = fis.read(bytes);
        if (count == PROTECTED_FILE_HEADER.length && Arrays.equals(bytes, PROTECTED_FILE_HEADER)) {
          return Optional.of(modulePath);
        }
      } catch (IOException e) {
        // ошибка чтения в данном случае неважна
      }
    }
    return Optional.empty();
  }

  public static Optional<Path> computeIsProtectedForDesigner(Path modulePath, boolean modulePathExists) {
    if (!modulePathExists) {
      final var filePath = modulePath.toFile().getPath();
      final var protectedPath = Paths.get(FilenameUtils.removeExtension(filePath) + ".bin");
      if (protectedPath.toFile().exists()) {
        return Optional.of(protectedPath);
      }
    }
    return Optional.empty();
  }
}
