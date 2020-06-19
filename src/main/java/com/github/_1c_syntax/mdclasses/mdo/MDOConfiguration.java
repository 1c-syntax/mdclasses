/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationExtensionPurpose;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import io.vavr.control.Either;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class MDOConfiguration extends MDObjectBSL {

  /**
   * Вариант языка исходного кода
   */
  private ScriptVariant scriptVariant = ScriptVariant.ENGLISH;

  /**
   * Режим совместимости конфигурации
   */
  private CompatibilityMode compatibilityMode = new CompatibilityMode();

  /**
   * Режим совместимости расширений конфигурации
   */
  private CompatibilityMode configurationExtensionCompatibilityMode = new CompatibilityMode();

  /**
   * Использование модального режима
   */
  private UseMode modalityUseMode = UseMode.USE;

  /**
   * Использование синхронных вызовов расширений и внешних компонент
   */
  private UseMode synchronousExtensionAndAddInCallUseMode = UseMode.USE;

  /**
   * Использования синхронных вызовов расширений платформы и внешних компонент
   */
  private UseMode synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;

  /**
   * Режим запуска клиента по умолчанию
   */
  private String defaultRunMode = "";

  /**
   * Язык приложения по умолчанию
   */
  private Either<String, Language> defaultLanguage;

  /**
   * Режим управления блокировками
   */
  private String dataLockControlMode = "";

  /**
   * Режим автонумерации объектов
   */
  private String objectAutonumerationMode = "";

  /**
   * Все объекты конфигурации первого уровня
   */
  @XStreamImplicit
  private List<Either<String, MDObjectBase>> children = Collections.emptyList();

  /**
   * Назначение расширения конфигурации
   */
  private ConfigurationExtensionPurpose configurationExtensionPurpose = ConfigurationExtensionPurpose.PATCH;

  /**
   * Префикс собственных объектов расширения
   */
  private String namePrefix = "";

  public MDOConfiguration(DesignerMDO designerMDO) {
    super(designerMDO);
    var designerProperties = designerMDO.getProperties();
    scriptVariant = designerProperties.getScriptVariant();
    compatibilityMode = designerProperties.getCompatibilityMode();
    configurationExtensionCompatibilityMode = designerProperties.getConfigurationExtensionCompatibilityMode();
    dataLockControlMode = designerProperties.getDataLockControlMode();
    defaultLanguage = Either.left(designerProperties.getDefaultLanguage());
    defaultRunMode = designerProperties.getDefaultRunMode();
    modalityUseMode = designerProperties.getModalityUseMode();
    objectAutonumerationMode = designerProperties.getObjectAutonumerationMode();
    synchronousExtensionAndAddInCallUseMode = designerProperties.getSynchronousExtensionAndAddInCallUseMode();
    synchronousPlatformExtensionAndAddInCallUseMode =
      designerProperties.getSynchronousPlatformExtensionAndAddInCallUseMode();
    configurationExtensionPurpose = designerProperties.getConfigurationExtensionPurpose();
    namePrefix = designerProperties.getNamePrefix();

    children = designerMDO.getChildObjects().getChildren();
  }

  @Override
  public MDOType getType() {
    return MDOType.CONFIGURATION;
  }

}
