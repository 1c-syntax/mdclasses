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

import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.children.MDChildObject;
import com.github._1c_syntax.bsl.mdo.children.ObjectModule;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import com.github._1c_syntax.support_configuration.ParseSupportData;
import com.github._1c_syntax.support_configuration.SupportVariant;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class MDClasses {

  private static final Map<Object, MDObject> mdObjects = new ConcurrentHashMap<>();

  @SneakyThrows
  public MDClass createConfiguration(Path path) {
    mdObjects.clear();
    var configurationSource = MDOUtils.getConfigurationSourceByPath(path);
    if (configurationSource != ConfigurationSource.EMPTY) {
      var mdo = MDOFactory.readMDOConfiguration(configurationSource, path);
      if (mdo.isPresent()) {
        MDClass configuration = (buildMDClass(mdo.get().buildMDObject()));
        if (configuration instanceof Configuration) {
          computeSupportConfiguration((Configuration) configuration, path);
        }
        return configuration;
      }
    }
    return Configuration.builder().build();
  }

  @SneakyThrows
  // todo времянка
  public MDObject build(Object builder) {
    MDObject result;
    if (mdObjects.containsKey(builder)) {
      result = mdObjects.get(builder);
    } else {
      var mdObject = (MDObject) builder.getClass().getDeclaredMethod("build").invoke(builder);
      mdObjects.put(builder, mdObject);
      result = mdObject;
    }
    if (result instanceof ChildrenOwner) {
      ((ChildrenOwner) result).getChildren().stream()
        .filter(MDChildObject.class::isInstance)
        .map(MDChildObject.class::cast)
        .forEach(mdObject -> mdObject.setOwner(result));
    }

    if (result instanceof ModuleOwner) {
      ((ModuleOwner) result).getModules().stream()
        .filter(ObjectModule.class::isInstance)
        .map(ObjectModule.class::cast)
        .forEach(module -> module.setOwner((ModuleOwner) result));
    }

    return result;
  }

  @SneakyThrows
  // todo времянка
  public Module buildModule(Object builder) {
    return (Module) builder.getClass().getDeclaredMethod("build").invoke(builder);
  }

  @SneakyThrows
  // todo времянка
  public MDClass buildMDClass(Object builder) {
    return (MDClass) builder.getClass().getDeclaredMethod("build").invoke(builder);
  }

  /**
   * Заполняет информацию о поддержке для конфигурации
   *
   * @param configuration Конфигурация (для расширений поддержки нет)
   */
  private static void computeSupportConfiguration(Configuration configuration, Path path) {
    var fileParentConfiguration = MDOPathUtils.getParentConfigurationsPath(
      configuration.getConfigurationSource(), path);
    if (fileParentConfiguration.isPresent() && fileParentConfiguration.get().toFile().exists()) {
      var supportData = ParseSupportData.readSimple(fileParentConfiguration.get());

      configuration.setSupportVariant(supportData.getOrDefault(configuration.getUuid(), SupportVariant.NONE));

      configuration.getPlainChildren()
        .forEach(mdo -> mdo.setSupportVariant(supportData.getOrDefault(mdo.getUuid(), SupportVariant.NONE)));
    } else {
      configuration.setSupportVariant(SupportVariant.NONE);
      configuration.getPlainChildren().forEach(mdo -> mdo.setSupportVariant(SupportVariant.NONE));
    }
  }
}

// todo реализовать
//  - при сборке конфигурации и расширения добавлять ссылки на общий реквизит в список реквизитов


