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
package com.github._1c_syntax.mdclasses.utils;

import com.github._1c_syntax.mdclasses.common.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import com.github._1c_syntax.mdclasses.mdo.support.RightType;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@UtilityClass
public class MDOUtils {
  public final String TYPE_INPUT_FIELD = "InputField";

  private final Map<MDOType, Set<ModuleType>> MODULE_TYPES_FOR_MDO_TYPES = moduleTypesForMDOTypes();
  private final Map<MDOType, Set<RightType>> RIGHT_TYPES_FOR_MDO_TYPES = rightTypesForMDOTypes();

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
        case WEB_SERVICE:
          types.add(ModuleType.WEBServiceModule);
          break;
        case RECALCULATION:
          types.add(ModuleType.RecalculationModule);
          break;
        default:
          // non
      }

      result.put(mdoType, types);
    }

    return result;
  }

  /**
   * Возвращает соответствие типов объектов и применимых к ним прав доступа
   */
  public Map<MDOType, Set<RightType>> getRightTypesForMDOTypes() {
    return RIGHT_TYPES_FOR_MDO_TYPES;
  }

  private Map<MDOType, Set<RightType>> rightTypesForMDOTypes() {
    Map<MDOType, Set<RightType>> result = new EnumMap<>(MDOType.class);

    for (MDOType mdoType : MDOType.values()) {
      Set<RightType> types = new HashSet<>();
      switch (mdoType) {
        case CONFIGURATION:
          types.add(RightType.ADMINISTRATION);
          types.add(RightType.DATA_ADMINISTRATION);
          types.add(RightType.UPDATE_DATABASE_CONFIGURATION);
          types.add(RightType.EXCLUSIVE_MODE);
          types.add(RightType.ACTIVE_USERS);
          types.add(RightType.EVENTLOG);
          types.add(RightType.THIN_CLIENT);
          types.add(RightType.WEB_CLIENT);
          types.add(RightType.MOBILE_CLIENT);
          types.add(RightType.THICK_CLIENT);
          types.add(RightType.EXTERNAL_CONNECTION);
          types.add(RightType.AUTOMATION);
          types.add(RightType.TECHNICAL_SPECIALIST_MODE);
          types.add(RightType.ALL_FUNCTIONS_MODE); // deprecated
          types.add(RightType.COLLABORATION_SYSTEM_INFOBASE_REGISTRATION);
          types.add(RightType.MAIN_WINDOW_MODE_NORMAL);
          types.add(RightType.MAIN_WINDOW_MODE_WORKPLACE);
          types.add(RightType.MAIN_WINDOW_MODE_EMBEDDED_WORKPLACE);
          types.add(RightType.MAIN_WINDOW_MODE_FULLSCREEN_WORKPLACE);
          types.add(RightType.MAIN_WINDOW_MODE_KIOSK);
          types.add(RightType.ANALYTICS_SYSTEM_CLIENT);
          types.add(RightType.SAVE_USER_DATA);
          types.add(RightType.CONFIGURATION_EXTENSIONS_ADMINISTRATION);
          types.add(RightType.INTERACTIVE_OPEN_EXT_DATA_PROCESSORS);
          types.add(RightType.INTERACTIVE_OPEN_EXT_REPORTS);
          types.add(RightType.EXCLUSIVE_MODE_TERMINATION_AT_SESSION_START);
          types.add(RightType.OUTPUT);
          break;
        case SUBSYSTEM:
        case WS_OPERATION:
        case HTTP_SERVICE_METHOD:
          types.add(RightType.USE);
          break;
        case SESSION_PARAMETER:
          types.add(RightType.GET);
          types.add(RightType.SET);
          break;
        case COMMON_ATTRIBUTE:
        case ATTRIBUTE:
//        case TABULAR_SECTION:
//        case ACCOUNTING_FLAG:
//        case EXT_DIMENSION_ACCOUNTING_FLAG:
//        case STANDARD_TABULAR_SECTION_ATTRIBUTE:
//        case DIMENSION:
//        case RESOURCE:
//        case ADDRESSING_ATTRIBUTE:
          types.add(RightType.VIEW);
          types.add(RightType.EDIT);
          break;
        case EXCHANGE_PLAN:
          types.add(RightType.READ);
          types.add(RightType.INSERT);
          types.add(RightType.UPDATE);
          types.add(RightType.DELETE);
          types.add(RightType.VIEW);
          types.add(RightType.INTERACTIVE_INSERT);
          types.add(RightType.EDIT);
          types.add(RightType.INTERACTIVE_DELETE);
          types.add(RightType.INTERACTIVE_SET_DELETION_MARK);
          types.add(RightType.INTERACTIVE_CLEAR_DELETION_MARK);
          types.add(RightType.INTERACTIVE_DELETE_MARKED);
          types.add(RightType.INPUT_BY_STRING);
          types.add(RightType.READ_DATA_HISTORY);
          types.add(RightType.READ_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY);
          types.add(RightType.UPDATE_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY_SETTINGS);
          types.add(RightType.UPDATE_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.VIEW_DATA_HISTORY);
          types.add(RightType.EDIT_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.SWITCH_TO_DATA_HISTORY_VERSION);
          break;
        case FILTER_CRITERION:
        case COMMON_COMMAND:
        case COMMON_FORM:
        case COMMAND:
          types.add(RightType.VIEW);
          break;
        case CONSTANT:
          types.add(RightType.READ);
          types.add(RightType.UPDATE);
          types.add(RightType.VIEW);
          types.add(RightType.EDIT);
          types.add(RightType.READ_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY_SETTINGS);
          types.add(RightType.UPDATE_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.VIEW_DATA_HISTORY);
          types.add(RightType.EDIT_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.SWITCH_TO_DATA_HISTORY_VERSION);
          break;
        case CATALOG:
        case CHART_OF_CHARACTERISTIC_TYPES:
        case CHART_OF_ACCOUNTS:
        case CHART_OF_CALCULATION_TYPES:
          types.add(RightType.READ);
          types.add(RightType.INSERT);
          types.add(RightType.UPDATE);
          types.add(RightType.DELETE);
          types.add(RightType.VIEW);
          types.add(RightType.INTERACTIVE_INSERT);
          types.add(RightType.EDIT);
          types.add(RightType.INTERACTIVE_DELETE);
          types.add(RightType.INTERACTIVE_SET_DELETION_MARK);
          types.add(RightType.INTERACTIVE_CLEAR_DELETION_MARK);
          types.add(RightType.INTERACTIVE_DELETE_MARKED);
          types.add(RightType.INPUT_BY_STRING);
          types.add(RightType.INTERACTIVE_DELETE_PREDEFINED_DATA);
          types.add(RightType.INTERACTIVE_SET_DELETION_MARK_PREDEFINED_DATA);
          types.add(RightType.INTERACTIVE_CLEAR_DELETION_MARK_PREDEFINED_DATA);
          types.add(RightType.INTERACTIVE_DELETE_MARKED_PREDEFINED_DATA);
          types.add(RightType.READ_DATA_HISTORY);
          types.add(RightType.READ_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY);
          types.add(RightType.UPDATE_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY_SETTINGS);
          types.add(RightType.UPDATE_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.VIEW_DATA_HISTORY);
          types.add(RightType.EDIT_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.SWITCH_TO_DATA_HISTORY_VERSION);
          break;
        case DOCUMENT:
          types.add(RightType.READ);
          types.add(RightType.INSERT);
          types.add(RightType.UPDATE);
          types.add(RightType.DELETE);
          types.add(RightType.POSTING);
          types.add(RightType.UNDO_POSTING);
          types.add(RightType.VIEW);
          types.add(RightType.INTERACTIVE_INSERT);
          types.add(RightType.EDIT);
          types.add(RightType.INTERACTIVE_DELETE);
          types.add(RightType.INTERACTIVE_SET_DELETION_MARK);
          types.add(RightType.INTERACTIVE_CLEAR_DELETION_MARK);
          types.add(RightType.INTERACTIVE_DELETE_MARKED);
          types.add(RightType.INTERACTIVE_POSTING);
          types.add(RightType.INTERACTIVE_POSTING_REGULAR);
          types.add(RightType.INTERACTIVE_UNDO_POSTING);
          types.add(RightType.INTERACTIVE_CHANGE_POSTED);
          types.add(RightType.INTERACTIVE_CHANGE_OF_POSTED); // deprecated since?
          types.add(RightType.INPUT_BY_STRING);
          types.add(RightType.READ_DATA_HISTORY);
          types.add(RightType.READ_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY);
          types.add(RightType.UPDATE_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY_SETTINGS);
          types.add(RightType.UPDATE_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.VIEW_DATA_HISTORY);
          types.add(RightType.EDIT_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.SWITCH_TO_DATA_HISTORY_VERSION);
          break;
        case DOCUMENT_NUMERATOR:
        case RECALCULATION:
          types.add(RightType.READ);
          types.add(RightType.UPDATE);
          break;
        case DOCUMENT_JOURNAL:
//        case CUBE:
//          types.add(RightType.READ);
//          types.add(RightType.VIEW);
//          break;
        case REPORT:
        case DATA_PROCESSOR:
//        case FUNCTION:
//          types.add(RightType.USE);
//          types.add(RightType.VIEW);
//          break;
        case INFORMATION_REGISTER:
          types.add(RightType.READ);
          types.add(RightType.UPDATE);
          types.add(RightType.VIEW);
          types.add(RightType.EDIT);
          types.add(RightType.TOTALS_CONTROL);
          types.add(RightType.READ_DATA_HISTORY);
          types.add(RightType.READ_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY);
          types.add(RightType.UPDATE_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY_SETTINGS);
          types.add(RightType.UPDATE_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.VIEW_DATA_HISTORY);
          types.add(RightType.EDIT_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.SWITCH_TO_DATA_HISTORY_VERSION);
          break;
        case ACCUMULATION_REGISTER:
        case ACCOUNTING_REGISTER:
          types.add(RightType.READ);
          types.add(RightType.UPDATE);
          types.add(RightType.VIEW);
          types.add(RightType.EDIT);
          types.add(RightType.TOTALS_CONTROL);
          break;
        case CALCULATION_REGISTER:
          types.add(RightType.READ);
          types.add(RightType.UPDATE);
          types.add(RightType.VIEW);
          types.add(RightType.EDIT);
          break;
        case BUSINESS_PROCESS:
          types.add(RightType.READ);
          types.add(RightType.INSERT);
          types.add(RightType.UPDATE);
          types.add(RightType.DELETE);
          types.add(RightType.VIEW);
          types.add(RightType.INTERACTIVE_INSERT);
          types.add(RightType.EDIT);
          types.add(RightType.INTERACTIVE_DELETE);
          types.add(RightType.INTERACTIVE_SET_DELETION_MARK);
          types.add(RightType.INTERACTIVE_CLEAR_DELETION_MARK);
          types.add(RightType.INTERACTIVE_DELETE_MARKED);
          types.add(RightType.INPUT_BY_STRING);
          types.add(RightType.INTERACTIVE_ACTIVATE);
          types.add(RightType.START);
          types.add(RightType.INTERACTIVE_START);
          types.add(RightType.READ_DATA_HISTORY);
          types.add(RightType.READ_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY);
          types.add(RightType.UPDATE_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY_SETTINGS);
          types.add(RightType.UPDATE_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.VIEW_DATA_HISTORY);
          types.add(RightType.EDIT_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.SWITCH_TO_DATA_HISTORY_VERSION);
          break;
        case TASK:
          types.add(RightType.READ);
          types.add(RightType.INSERT);
          types.add(RightType.UPDATE);
          types.add(RightType.DELETE);
          types.add(RightType.VIEW);
          types.add(RightType.INTERACTIVE_INSERT);
          types.add(RightType.EDIT);
          types.add(RightType.INTERACTIVE_DELETE);
          types.add(RightType.INTERACTIVE_SET_DELETION_MARK);
          types.add(RightType.INTERACTIVE_CLEAR_DELETION_MARK);
          types.add(RightType.INTERACTIVE_DELETE_MARKED);
          types.add(RightType.INPUT_BY_STRING);
          types.add(RightType.INTERACTIVE_ACTIVATE);
          types.add(RightType.EXECUTE);
          types.add(RightType.INTERACTIVE_EXECUTE);
          types.add(RightType.READ_DATA_HISTORY);
          types.add(RightType.READ_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY);
          types.add(RightType.UPDATE_MISSING_DATA_HISTORY);
          types.add(RightType.UPDATE_DATA_HISTORY_SETTINGS);
          types.add(RightType.UPDATE_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.VIEW_DATA_HISTORY);
          types.add(RightType.EDIT_DATA_HISTORY_VERSION_COMMENT);
          types.add(RightType.SWITCH_TO_DATA_HISTORY_VERSION);
          break;
//        case EXTERNAL_DATA_SOURCE:
//          types.add(RightType.USE);
//          types.add(RightType.ADMINISTRATION);
//          types.add(RightType.STANDARD_AUTHENTICATION_CHANGE);
//          types.add(RightType.SESSION_STANDARD_AUTHENTICATION_CHANGE);
//          types.add(RightType.SESSION_OS_AUTHENTICATION_CHANGE);
//          break;
//
//        case TABLE:
//          types.add(RightType.READ);
//          types.add(RightType.INSERT);
//          types.add(RightType.UPDATE);
//          types.add(RightType.DELETE);
//          types.add(RightType.VIEW);
//          types.add(RightType.INTERACTIVE_INSERT);
//          types.add(RightType.EDIT);
//          types.add(RightType.INTERACTIVE_DELETE);
//          types.add(RightType.INPUT_BY_STRING);
//          break;

        default:
          // non
      }

      result.put(mdoType, types);
    }

    return result;
  }
}
