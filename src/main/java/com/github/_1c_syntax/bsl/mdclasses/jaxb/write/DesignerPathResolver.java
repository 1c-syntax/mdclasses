/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.reader.designer.DesignerReader;
import com.github._1c_syntax.bsl.types.MDOType;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Разрешение путей к XML-файлам объектов метаданных в формате Designer.
 */
public final class DesignerPathResolver {

  private DesignerPathResolver() {
  }

  /**
   * Путь к файлу Configuration.xml в корне выгрузки.
   *
   * @param rootPath корень выгрузки (каталог, в котором лежит Configuration.xml)
   * @return путь к Configuration.xml
   */
  public static Path configurationPath(Path rootPath) {
    return rootPath.resolve(DesignerReader.CONFIGURATION_MDO_PATH);
  }

  /**
   * Путь к XML-файлу объекта. Для вложенной подсистемы задаётся путь к .xml родителя.
   *
   * @param rootPath             корень выгрузки
   * @param md                   объект метаданных
   * @param parentSubsystemPath  путь к .xml родительской подсистемы или null
   * @return путь к .xml файлу
   */
  public static Path pathForObject(Path rootPath, MD md, Path parentSubsystemPath) {
    MDOType type = md.getMdoType();
    String name = md.getName();
    if (type == MDOType.SUBSYSTEM && parentSubsystemPath != null) {
      Path parentDir = Paths.get(
        FilenameUtils.getFullPathNoEndSeparator(parentSubsystemPath.toString()),
        FilenameUtils.getBaseName(parentSubsystemPath.toString()));
      return parentDir.resolve(type.groupName()).resolve(name + ".xml");
    }
    return rootPath.resolve(type.groupName()).resolve(name + ".xml");
  }

  /**
   * Путь к XML-файлу объекта верхнего уровня.
   *
   * @param rootPath корень выгрузки
   * @param md      объект метаданных
   * @return путь к .xml файлу
   */
  public static Path pathForObject(Path rootPath, MD md) {
    return pathForObject(rootPath, md, null);
  }

  /**
   * Путь к XML-файлу формы (например Catalogs/Имя/Forms/ИмяФормы.xml).
   *
   * @param rootPath   корень выгрузки
   * @param ownerGroup группа владельца (Catalogs, Documents и т.д.)
   * @param ownerName  имя владельца
   * @param formName   имя формы
   * @return путь к .xml файлу формы
   */
  public static Path pathForForm(Path rootPath, String ownerGroup, String ownerName, String formName) {
    return rootPath.resolve(ownerGroup).resolve(ownerName).resolve("Forms").resolve(formName + ".xml");
  }
}
