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
package com.github._1c_syntax.mdclasses.mdo.support;

/**
 * Возможные типы модулей объектов
 */
public enum ModuleType {

  BotModule("Module.bsl"),
  IntegrationServiceModule("Module.bsl"),
  CommandModule("CommandModule.bsl"),
  CommonModule("Module.bsl"),
  ObjectModule("ObjectModule.bsl"),
  ManagerModule("ManagerModule.bsl"),
  FormModule("Module.bsl"),
  RecordSetModule("RecordSetModule.bsl"),
  ValueManagerModule("ValueManagerModule.bsl"),
  ApplicationModule("ApplicationModule.bsl"),
  ManagedApplicationModule("ManagedApplicationModule.bsl"),
  SessionModule("SessionModule.bsl"),
  ExternalConnectionModule("ExternalConnectionModule.bsl"),
  OrdinaryApplicationModule("OrdinaryApplicationModule.bsl"),
  HTTPServiceModule("Module.bsl"),
  WEBServiceModule("Module.bsl"),
  RecalculationModule("RecordSetModule.bsl"),
  UNKNOWN(""); // для неизвестных типов модулей

  /**
   * Имя файла
   */
  private final String fileName;

  ModuleType(String fileName) {
    this.fileName = fileName;
  }

  public String getFileName() {
    return this.fileName;
  }
}
