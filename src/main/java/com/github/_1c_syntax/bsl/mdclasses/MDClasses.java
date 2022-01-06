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

import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.reader.MDOReader;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

@UtilityClass
public class MDClasses {

  private static final String EMPTY_STRING = "empty";

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
   * Создает пустую конфигурацию
   *
   * @return Пустая конфигурация
   */
  public MDClass createConfiguration() {
    var emptyBuilder = Configuration.builder();
    emptyBuilder.configurationSource(ConfigurationSource.EMPTY)
      .name(EMPTY_STRING)
      .uuid(EMPTY_STRING)
      .compatibilityMode(new CompatibilityMode())
      .configurationExtensionCompatibilityMode(new CompatibilityMode())
      .scriptVariant(ScriptVariant.RUSSIAN)
      .defaultRunMode(ApplicationRunMode.AUTO)
      .defaultLanguage(Language.DEFAULT)
      .dataLockControlMode(DataLockControlMode.AUTOMATIC)
      .objectAutonumerationMode("")
      .modalityUseMode(UseMode.DONT_USE)
      .synchronousPlatformExtensionAndAddInCallUseMode(UseMode.DONT_USE)
      .copyright(new MultiLanguageString(Map.of("", "")))
      .detailedInformation(new MultiLanguageString(Map.of("", "")))
      .briefInformation(new MultiLanguageString(Map.of("", "")))
      .children(Collections.emptyList())
      .plainChildren(Collections.emptyList())
      .modules(Collections.emptyList())
      .supportVariant(SupportVariant.NONE)
      .mdoReference(MdoReference.create(MDOType.CONFIGURATION, EMPTY_STRING, EMPTY_STRING))
    ;

    return emptyBuilder.build();
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
