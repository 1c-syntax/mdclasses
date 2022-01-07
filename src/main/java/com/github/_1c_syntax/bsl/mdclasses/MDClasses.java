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

import com.github._1c_syntax.reader.MDOReader;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
public class MDClasses {

  /**
   * Создает пустую конфигурацию
   *
   * @return Пустая конфигурация
   */
  public MDClass createConfiguration() {
    return Configuration.EMPTY;
  }

  /**
   * Создает конфигурацию или расширение по указанному пути
   *
   * @param path Путь к корню проекта
   * @return Конфигурация или расширение
   */
//  @SneakyThrows
//  public MDClass createConfiguration(Path path) {
//    var configurationSource = MDOUtils.getConfigurationSourceByPath(path);
//    var fileParentConfiguration = MDOPathUtils.getParentConfigurationsPath(configurationSource, path);
//    if (fileParentConfiguration.isPresent() && fileParentConfiguration.get().toFile().exists()) {
//      ParseSupportData.readSimple(fileParentConfiguration.get());
//    }
//
//    var mdo = MDOFactory.readMDOConfiguration(configurationSource, path);
//    if (mdo.isPresent()) {
//      var configuration = (MDClass) mdo.get().buildMDObject();
//      computeCommonAttributeLinks(configuration);
//      return configuration;
//    }
//
//    return createConfiguration();
//  }
  public MDClass createConfiguration(Path path) {
    return MDOReader.readConfiguration(path);
//
//    var configurationSource = MDOUtils.getConfigurationSourceByPath(path);
//    var fileParentConfiguration = MDOPathUtils.getParentConfigurationsPath(configurationSource, path);
//    if (fileParentConfiguration.isPresent() && fileParentConfiguration.get().toFile().exists()) {
//      ParseSupportData.readSimple(fileParentConfiguration.get());
//    }
//
//    var mdo = MDOFactory.readMDOConfiguration(configurationSource, path);
//    if (mdo.isPresent()) {
//      var configuration = (MDClass) mdo.get().buildMDObject();
//      computeCommonAttributeLinks(configuration);
//      return configuration;
//    }
//
//    return createConfiguration();
  }

  public MDClass createConfiguration(Path path, boolean skipSupport) {
    return MDOReader.readConfiguration(path, skipSupport);
  }



  /**
   * Выполняет связь общего реквизита с объектами из его состава
   *
   * @param mdc Конфигурация или расширение
   */
  private static void computeCommonAttributeLinks(MDClass mdc) {
//    if (mdc instanceof ConfigurationTree) {
//      var configuration = (ConfigurationTree) mdc;
//      configuration.getCommonAttributes()
//        .forEach(commonAttribute -> commonAttribute.getContent()
//          .forEach(mdoReference -> mdc.findChild(mdoReference)
//            .ifPresent((MDObject mdObject) -> {
//              if (mdObject instanceof AttributeOwner) {
//                ((AttributeOwner) mdObject).addCommonAttribute(commonAttribute);
//              }
//            }))
//        );
//    }
  }
}
