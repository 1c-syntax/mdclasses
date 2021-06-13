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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.common.CompatibilityMode;
import com.github._1c_syntax.mdclasses.common.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.mdo.support.ConfigurationExtensionPurpose;
import com.github._1c_syntax.mdclasses.mdo.support.DataLockControlMode;
import com.github._1c_syntax.mdclasses.mdo.support.LanguageContent;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ScriptVariant;
import com.github._1c_syntax.mdclasses.mdo.support.UseMode;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerContentItem;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import io.vavr.control.Either;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.CONFIGURATION,
  name = "Configuration",
  nameRu = "Конфигурация",
  groupName = "",
  groupNameRu = ""
)
public class MDConfiguration extends AbstractMDObjectBSL {

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
   * Использовать управляемые формы в обычном приложении
   */
  private boolean useManagedFormInOrdinaryApplication;

  /**
   * Информация об авторском праве, на разных языках
   */
  @XStreamImplicit(itemFieldName = "copyright")
  private List<LanguageContent> copyrights = Collections.emptyList();

  /**
   * Детальная информация о конфигурации, на разных языках
   */
  @XStreamImplicit(itemFieldName = "detailedInformation")
  private List<LanguageContent> detailedInformation = Collections.emptyList();

  /**
   * Краткая информация о конфигурации, на разных языках
   */
  @XStreamImplicit(itemFieldName = "briefInformation")
  private List<LanguageContent> briefInformation = Collections.emptyList();

  /**
   * Использовать обычные формы в управляемом приложении
   */
  private boolean useOrdinaryFormInManagedApplication;

  /**
   * Режим запуска клиента по умолчанию
   */
  private String defaultRunMode = "";

  /**
   * Язык приложения по умолчанию
   */
  private Either<String, MDLanguage> defaultLanguage = Either.right(MDOFactory.fakeLanguage(scriptVariant));

  /**
   * Режим управления блокировками
   */
  private DataLockControlMode dataLockControlMode = DataLockControlMode.AUTOMATIC;

  /**
   * Режим автонумерации объектов
   */
  private String objectAutonumerationMode = "";

  /**
   * Все объекты конфигурации первого уровня
   */
  @XStreamImplicit
  private List<Either<String, AbstractMDObjectBase>> children = Collections.emptyList();

  /**
   * Назначение расширения конфигурации
   */
  private ConfigurationExtensionPurpose configurationExtensionPurpose = ConfigurationExtensionPurpose.PATCH;

  /**
   * Префикс собственных объектов расширения
   */
  private String namePrefix = "";

  public MDConfiguration(DesignerMDO designerMDO) {
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

    useManagedFormInOrdinaryApplication = designerProperties.isUseManagedFormInOrdinaryApplication();
    useOrdinaryFormInManagedApplication = designerProperties.isUseOrdinaryFormInManagedApplication();

    copyrights = createLanguageContent(designerProperties.getCopyrights());
    briefInformation = createLanguageContent(designerProperties.getBriefInformation());
    detailedInformation = createLanguageContent(designerProperties.getDetailedInformation());

    children = designerMDO.getChildObjects().getChildren();
  }

  @Override
  public void supplement() {
    super.supplement();
    MDOPathUtils.getRootPathByConfigurationMDO(path).ifPresent(this::computeAllMDObject);

    var localChildren = getAllMDO();
    linkChildAndSubsystem(localChildren);
    linkCommonAttributesAndUsing(localChildren);
    linkExchangePlanAndMDO(localChildren);

    setDefaultConfigurationLanguage();
  }

  private static List<LanguageContent> createLanguageContent(List<DesignerContentItem> designerContentItems) {
    if (designerContentItems.isEmpty()) {
      return Collections.emptyList();
    }

    return designerContentItems.stream()
      .map(designerCopyright -> new LanguageContent(designerCopyright.getLanguage(), designerCopyright.getContent()))
      .collect(Collectors.toList());
  }

  private void setDefaultConfigurationLanguage() {
    if (defaultLanguage.isLeft()) {
      var defaultLang = defaultLanguage.getLeft();

      children.stream().filter(Either::isRight)
        .map(Either::get)
        .filter(MDLanguage.class::isInstance)
        .map(MDLanguage.class::cast)
        .filter((MDLanguage mdo) -> defaultLang.equals(mdo.getMdoReference().getMdoRef()))
        .findFirst()
        .ifPresent((MDLanguage mdo) -> setDefaultLanguage(Either.right(mdo)));
    }
  }

  private void computeAllMDObject(Path rootPath) {
    var configurationSource = MDOUtils.getConfigurationSourceByMDOPath(path);
    var localChildren =
      children.parallelStream()
        .map(child -> readChildMDO(configurationSource, rootPath, child))
        .collect(Collectors.toList());
    setChildren(localChildren);
  }

  private static Either<String, AbstractMDObjectBase> readChildMDO(ConfigurationSource configurationSource,
                                                                   Path rootPath,
                                                                   Either<String, AbstractMDObjectBase> child) {
    if (!child.isRight()) {
      var value = child.getLeft();
      var dotPosition = value.indexOf('.');
      var type = MDOType.fromValue(value.substring(0, dotPosition));
      var name = value.substring(dotPosition + 1);

      if (type.isPresent()) {
        var mdo = MDOPathUtils.getMDOPath(configurationSource, rootPath, type.get(), name)
          .flatMap(MDOFactory::readMDObject);
        return mdo.<Either<String, AbstractMDObjectBase>>map(Either::right).orElse(child);
      }
    }
    return child;
  }

  private void linkChildAndSubsystem(Map<String, AbstractMDObjectBase> allMDO) {
    children.stream()
      .filter(Either::isRight)
      .map(Either::get)
      .filter((AbstractMDObjectBase mdo) -> mdo.getType() == MDOType.SUBSYSTEM)
      .map(MDSubsystem.class::cast)
      .forEach(subsystem -> subsystem.linkToChildren(allMDO));
  }

  private void linkCommonAttributesAndUsing(Map<String, AbstractMDObjectBase> allMDO) {
    children.stream()
      .filter(Either::isRight)
      .map(Either::get)
      .filter((AbstractMDObjectBase mdo) -> mdo.getType() == MDOType.COMMON_ATTRIBUTE)
      .map(MDCommonAttribute.class::cast)
      .forEach(commonAttribute -> commonAttribute.linkUsing(allMDO));
  }

  private void linkExchangePlanAndMDO(Map<String, AbstractMDObjectBase> allMDO) {
    children.stream()
      .filter(Either::isRight)
      .map(Either::get)
      .filter((AbstractMDObjectBase mdo) -> mdo.getType() == MDOType.EXCHANGE_PLAN)
      .map(MDExchangePlan.class::cast)
      .forEach(exchangePlan -> exchangePlan.linkToMDO(allMDO));
  }

  private Map<String, AbstractMDObjectBase> getAllMDO() {
    return children.stream()
      .filter(Either::isRight)
      .map(Either::get)
      .collect(Collectors.toMap((AbstractMDObjectBase mdo)
        -> mdo.getMdoReference().getMdoRef(), (AbstractMDObjectBase mdo) -> mdo));
  }
}
