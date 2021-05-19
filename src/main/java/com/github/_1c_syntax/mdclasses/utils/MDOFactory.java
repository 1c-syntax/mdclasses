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
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDLanguage;
import com.github._1c_syntax.mdclasses.mdo.children.XDTOPackageData;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormData;
import com.github._1c_syntax.mdclasses.mdo.children.template.DataCompositionSchema;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.RoleData;
import com.github._1c_syntax.mdclasses.mdo.support.ScriptVariant;
import com.github._1c_syntax.mdclasses.unmarshal.XStreamFactory;
import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Фабрика для создания MDO объекта
 */
@UtilityClass
public class MDOFactory {

  /**
   * Читает конфигурацию из корня проекта
   */
  public Optional<AbstractMDObjectBase> readMDOConfiguration(ConfigurationSource configurationSource, Path rootPath) {
    return MDOPathUtils.getMDOPath(configurationSource, rootPath,
      MDOType.CONFIGURATION, MDOType.CONFIGURATION.getName())
      .flatMap(MDOFactory::readMDObject);
  }

  /**
   * Читает объект по его файлу описания, а также его дочерние при наличии
   *
   * @param mdoPath - путь к файлу описания объекта
   * @return - прочитанный объект
   */
  public Optional<AbstractMDObjectBase> readMDObject(Path mdoPath) {
    var mdo = Optional.ofNullable(readMDO(mdoPath));
    mdo.ifPresent(AbstractMDObjectBase::supplement);
    return mdo;
  }

  /**
   * Читает объект по его файлу описания, но не выполняет чтение дочерних элементов
   *
   * @param path - путь к файлу описания объекта
   * @return - прочитанный объект
   */
  public AbstractMDObjectBase readMDO(Path path) {
    if (Files.notExists(path)) {
      return null;
    }
    var value = (AbstractMDObjectBase) XStreamFactory.fromXML(path.toFile());
    value.setPath(path);
    return value;
  }

  /**
   * Читает данные формы (FormData) в объект из файла
   *
   * @param path - путь к файлу описания объекта
   * @return - прочитанный объект
   */
  public Optional<FormData> readFormData(Path path) {
    if (Files.notExists(path)) {
      return Optional.empty();
    }
    var value = (FormData) XStreamFactory.fromXML(path.toFile());
    value.fillPlainChildren(value.getChildren());
    return Optional.of(value);
  }

  public Optional<XDTOPackageData> readXDTOPackageData(Path path) {
    if (Files.notExists(path)) {
      return Optional.empty();
    }

    return Optional.of((XDTOPackageData) XStreamFactory.fromXML(path.toFile()));
  }

  /**
   * Читает данные роли из файла Rights
   *
   * @param path - путь к файлу прав роли.
   * @return {@code Optional} POJO представление данных ролей
   */
  public static Optional<RoleData> readRoleData(Path path) {
    if (Files.notExists(path)) {
      return Optional.empty();
    }

    return Optional.ofNullable((RoleData) XStreamFactory.fromXML(path.toFile()));
  }

  /**
   * Читает макет со схемой компоновки данных
   *
   * @param path - путь к файлу макета
   * @return {@code Optional} POJO представление данных макета
   */
  public Optional<DataCompositionSchema> readDataCompositionSchema(Path path) {
    if (Files.notExists(path)) {
      return Optional.empty();
    }
    var value = (DataCompositionSchema) XStreamFactory.fromXML(path.toFile());
    value.fillPlainDataSets();
    return Optional.of(value);
  }

  /**
   * Создает объект языка если вдруг его не оказалось в данных конфигурации
   *
   * @param scriptVariant - вариант языка конфигурации
   * @return - созданный и минимально заполненный объект языка
   */
  public MDLanguage fakeLanguage(ScriptVariant scriptVariant) {
    if (scriptVariant == ScriptVariant.ENGLISH) {
      return MDLanguage.ENGLISH;
    } else {
      return MDLanguage.RUSSIAN;
    }
  }
}
