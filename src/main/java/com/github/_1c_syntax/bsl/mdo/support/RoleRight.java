/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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
package com.github._1c_syntax.bsl.mdo.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Возможные права роли
 */
@AllArgsConstructor
public enum RoleRight implements EnumWithValue {
  VIEW("View"),
  EDIT("Edit"),
  GET("Get"),
  READ("Read"),
  SET("Set"),
  INPUT_BY_STRING("InputByString"),
  UPDATE("Update"),
  USE("Use"),
  INSERT("Insert"),
  MAIN_WINDOW_MODE_NORMAL("MainWindowModeNormal"),
  MAIN_WINDOW_MODE_WORKPLACE("MainWindowModeWorkplace"),
  MAIN_WINDOW_MODE_EMBEDDED_WORKPLACE("MainWindowModeEmbeddedWorkplace"),
  MAIN_WINDOW_MODE_FULLSCREEN_WORKPLACE("MainWindowModeFullscreenWorkplace"),
  MAIN_WINDOW_MODE_KIOSK("MainWindowModeKiosk"),
  ANALYTICS_SYSTEM_CLIENT("AnalyticsSystemClient"),
  ADMINISTRATION("Administration"),
  DATA_ADMINISTRATION("DataAdministration"),
  UPDATE_DATA_BASE_CONFIGURATION("UpdateDataBaseConfiguration"),
  EXCLUSIVE_MODE("ExclusiveMode"),
  ACTIVE_USERS("ActiveUsers"),
  EVENT_LOG("EventLog"),
  THIN_CLIENT("ThinClient"),
  WEB_CLIENT("WebClient"),
  THICK_CLIENT("ThickClient"),
  EXTERNAL_CONNECTION("ExternalConnection"),
  AUTOMATION("Automation"),
  ALL_FUNCTIONS_MODE("AllFunctionsMode"),
  COLLABORATION_SYSTEM_INFO_BASE_REGISTRATION("CollaborationSystemInfoBaseRegistration"),
  SAVE_USER_DATA("SaveUserData"),
  CONFIGURATION_EXTENSIONS_ADMINISTRATION("ConfigurationExtensionsAdministration"),
  INTERACTIVE_OPEN_EXT_DATA_PROCESSORS("InteractiveOpenExtDataProcessors"),
  INTERACTIVE_OPEN_EXT_REPORTS("InteractiveOpenExtReports"),
  OUTPUT("Output"),
  DELETE("Delete"),
  POSTING("Posting"),
  UNDO_POSTING("UndoPosting"),
  INTERACTIVE_INSERT("InteractiveInsert"),
  INTERACTIVE_DELETE("InteractiveDelete"),
  INTERACTIVE_SET_DELETION_MARK("InteractiveSetDeletionMark"),
  INTERACTIVE_CLEAR_DELETION_MARK("InteractiveClearDeletionMark"),
  INTERACTIVE_DELETE_MARKED("InteractiveDeleteMarked"),
  INTERACTIVE_POSTING("InteractivePosting"),
  INTERACTIVE_POSTING_REGULAR("InteractivePostingRegular"),
  INTERACTIVE_UNDO_POSTING("InteractiveUndoPosting"),
  INTERACTIVE_CHANGE_OF_POSTED("InteractiveChangeOfPosted"),
  INTERACTIVE_DELETE_PREDEFINED_DATA("InteractiveDeletePredefinedData"),
  INTERACTIVE_SET_DELETION_MARK_PREDEFINED_DATA("InteractiveSetDeletionMarkPredefinedData"),
  INTERACTIVE_CLEAR_DELETION_MARK_PREDEFINED_DATA("InteractiveClearDeletionMarkPredefinedData"),
  INTERACTIVE_DELETE_MARKED_PREDEFINED_DATA("InteractiveDeleteMarkedPredefinedData"),
  EXECUTE("Execute"),
  READ_DATA_HISTORY("ReadDataHistory"),
  READ_DATA_HISTORY_OF_MISSING_DATA("ReadDataHistoryOfMissingData"),
  UPDATE_DATA_HISTORY("UpdateDataHistory"),
  UPDATE_DATA_HISTORY_OF_MISSING_DATA("UpdateDataHistoryOfMissingData"),
  UPDATE_DATA_HISTORY_SETTINGS("UpdateDataHistorySettings"),
  UPDATE_DATA_HISTORY_VERSION_COMMENT("UpdateDataHistoryVersionComment"),
  VIEW_DATA_HISTORY("ViewDataHistory"),
  EDIT_DATA_HISTORY_VERSION_COMMENT("EditDataHistoryVersionComment"),
  SWITCH_TO_DATA_HISTORY_VERSION("SwitchToDataHistoryVersion"),
  TOTALS_CONTROL("TotalsControl"),
  MOBILE_CLIENT("MobileClient"),
  INTERACTIVE_ACTIVATE("InteractiveActivate"),
  INTERACTIVE_EXECUTE("InteractiveExecute"),
  TECHNICAL_SPECIALIST_MODE("TechnicalSpecialistMode"),
  INTERACTIVE_START("InteractiveStart"),
  START("Start");

  @Getter
  @Accessors(fluent = true)
  private final String value;
}
