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
package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.common.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.MDConfiguration;
import com.github._1c_syntax.mdclasses.mdo.support.ConfigurationExtensionPurpose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

/**
 * Корневой класс расширения конфигурации
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class ConfigurationExtension extends Configuration {

  /**
   * Назначение расширения конфигурации
   */
  private ConfigurationExtensionPurpose configurationExtensionPurpose;

  /**
   * Префикс собственных объектов расширения
   */
  private String namePrefix;

  protected ConfigurationExtension() {
    super();
    configurationExtensionPurpose = ConfigurationExtensionPurpose.UNDEFINED;
    namePrefix = "";
  }

  protected ConfigurationExtension(MDConfiguration mdoConfiguration, ConfigurationSource source, Path path) {
    super(mdoConfiguration, source, path);

    configurationExtensionPurpose = mdoConfiguration.getConfigurationExtensionPurpose();
    namePrefix = mdoConfiguration.getNamePrefix();
  }

}
