/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.InterfaceCompatibilityMode;
import com.github._1c_syntax.bsl.mdo.support.UsePurposes;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.github._1c_syntax.bsl.types.ScriptVariant;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CF extends MDClass, ConfigurationTree, CFAccess {

  /**
   * Язык приложения по умолчанию
   */
  MdoReference getDefaultLanguage();

  /**
   * Язык, на котором ведется разработка
   */
  ScriptVariant getScriptVariant();

  /**
   * Вид интерфейса
   */
  InterfaceCompatibilityMode getInterfaceCompatibilityMode();

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
  Map<URI, ModuleType> getModulesByType();

  /**
   * Возвращает соответствие пути к модулю к нему самому
   */
  Map<URI, Module> getModulesByURI();

  /**
   * Возвращает соответствие имени общего модуля к нему самому
   */
  Map<String, CommonModule> getCommonModulesByName();

  /**
   * Возвращает соответствие ссылки на дочерний объект к нему самому
   */
  Map<MdoReference, MD> getChildrenByMdoRef();

  /**
   * Возвращает соответствие типов модулей их путям к файлам для дочернего объекта
   */
  default Map<ModuleType, List<URI>> mdoModuleTypes(MdoReference mdoReference) {
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
  default Map<ModuleType, List<URI>> mdoModuleTypes(String mdoRef) {
    return mdoModuleTypes(MdoReference.create(mdoRef));
  }

  /**
   * Возвращает соответствие пути файла модуля ссылке его владельца
   */
  Map<URI, MD> getModulesByObject();

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

  /**
   * Возвращает локализованное представление ссылки на объект метаданных с учетом используемого варианта языка
   * разработки
   *
   * @param md Объект метаданных, принадлежащий MDClasses
   * @return Строковое представление ссылки
   */
  default String getMdoRefLocal(MD md) {
    return md.getMdoRef(getScriptVariant());
  }

  @Override
  default ModuleType getModuleTypeByURI(URI uri) {
    return getModulesByType().getOrDefault(uri, ModuleType.UNKNOWN);
  }

  @Override
  default Optional<Module> getModuleByUri(URI uri) {
    return Optional.ofNullable(getModulesByURI().get(uri));
  }

  @Override
  default Optional<MD> findChild(URI uri) {
    return Optional.ofNullable(getModulesByObject().get(uri));
  }

  @Override
  default Optional<MD> findChild(MdoReference ref) {
    return Optional.ofNullable(getChildrenByMdoRef().get(ref));
  }

  @Override
  default Optional<CommonModule> findCommonModule(String name) {
    return Optional.ofNullable(getCommonModulesByName().get(name));
  }

  /**
   * Возвращает признак пустоты конфигурации
   *
   * @return Это пустая конфигурация
   */
  default boolean isEmpty() {
    return this == Configuration.EMPTY;
  }
}
