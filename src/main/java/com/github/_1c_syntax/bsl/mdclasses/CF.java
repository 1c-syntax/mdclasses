/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.children.ObjectModule;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.UsePurposes;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface CF extends MDClass, ConfigurationTree {

  /**
   * Язык приложения по умолчанию
   */
  MdoReference getDefaultLanguage();

  /**
   * Язык, на котором ведется разработка
   */
  ScriptVariant getScriptVariant();

  /**
   * Режим совместимости
   */
  CompatibilityMode getCompatibilityMode();

  /**
   * Режим совместимости расширений
   */
  CompatibilityMode getConfigurationExtensionCompatibilityMode();

  /**
   * Режим запуска приложения по умолчанию
   */
  ApplicationRunMode getDefaultRunMode();

  /**
   * Разработчик решения
   */
  String getVendor();

  /**
   * Версия
   */
  String getVersion();

  /**
   * Назначения использования форм
   */
  List<UsePurposes> getUsePurposes();

  /**
   * Возвращает соответствие пути к модулю его типу
   */
  default Map<URI, ModuleType> getModulesByType() {
    return getAllModules().stream().collect(Collectors.toMap(Module::getUri, Module::getModuleType));
  }

  /**
   * Возвращает соответствие типов модулей их путям к файлам сгруппированные по представлению ссылки объекта-владельца
   */
  default Map<String, Map<ModuleType, URI>> modulesByMDORef() {
    return getPlainChildren().stream()
      .filter(ModuleOwner.class::isInstance)
      .map(ModuleOwner.class::cast)
      .collect(Collectors.toMap(
          (MD md) -> md.getMdoReference().getMdoRef(),
          md -> md.getModules().stream()
            .collect(Collectors.toMap(Module::getModuleType, Module::getUri))
        )
      );
  }

  /**
   * Возвращает соответствие типов модулей их путям к файлам для дочернего объекта
   */
  default Map<ModuleType, URI> mdoModuleTypes(MdoReference mdoReference) {
    var child = findChild(mdoReference);
    if (child.isPresent() && child.get() instanceof ModuleOwner moduleOwner) {
      return moduleOwner.getModuleTypes();
    } else {
      return Collections.emptyMap();
    }
  }

  /**
   * Возвращает соответствие типов модулей их путям к файлам для дочернего объекта
   */
  default Map<ModuleType, URI> mdoModuleTypes(String mdoRef) {
    return mdoModuleTypes(MdoReference.create(mdoRef));
  }

  /**
   * Возвращает соответствие пути файла модуля ссылке его владельца
   */
  default Map<URI, MdoReference> modulesByObject() {
    return getAllModules().stream().collect(Collectors.toMap(Module::getUri, (Module module) -> {
      if (module instanceof ObjectModule objectModule) {
        return objectModule.getOwner();
      } else {
        return ((CommonModule) module).getMdoReference();
      }
    }));
  }

  /**
   * Возвращает список подсистем, в состав которых входит объект метаданных
   *
   * @param md                 объект метаданных
   * @param addParentSubsystem - признак необходимости добавлять родительскую (текущую) подсистему в список,
   *                           если объект присутствует в дочерних.
   *                           Используется для кейса: раз есть в дочерней, то считаем что и ко всем родителям
   *                           тоже относится
   * @return список подсистем
   */
  default List<Subsystem> includedSubsystems(MD md, boolean addParentSubsystem) {
    return includedSubsystems(md.getMdoReference(), addParentSubsystem);
  }

  /**
   * Возвращает список подсистем, в состав которых входит ссылка
   *
   * @param mdoReference       ссылка на объект метаданных
   * @param addParentSubsystem - признак необходимости добавлять родительскую (текущую) подсистему в список,
   *                           если объект присутствует в дочерних.
   *                           Используется для кейса: раз есть в дочерней, то считаем что и ко всем родителям
   *                           тоже относится
   * @return список подсистем
   */
  default List<Subsystem> includedSubsystems(MdoReference mdoReference, boolean addParentSubsystem) {
    return getSubsystems().parallelStream()
      .flatMap(subsystem -> subsystem.included(mdoReference, addParentSubsystem).stream())
      .toList();
  }
}
