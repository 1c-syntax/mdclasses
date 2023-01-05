/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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

import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.support.ConfigurationExtensionPurpose;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.mdclasses.mdo.support.LanguageContent;
import com.github._1c_syntax.mdclasses.wrapper.DesignerContentItem;
import com.github._1c_syntax.mdclasses.wrapper.DesignerMDO;
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
  private Either<String, Language> defaultLanguage = Either.right(Language.defaultLanguage(scriptVariant));

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
    computeAllMDObject();

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
        .filter(Language.class::isInstance)
        .map(Language.class::cast)
        .filter((Language mdo) -> defaultLang.equals(mdo.getMdoReference().getMdoRef()))
        .findFirst()
        .ifPresent((Language mdo) -> setDefaultLanguage(Either.right(mdo)));
    }
  }

  private void computeAllMDObject() {
    var localChildren =
      children.parallelStream()
        .map(child -> readChildMDO(path, child))
        .collect(Collectors.toList());
    setChildren(localChildren);
  }

  private static Either<String, AbstractMDObjectBase> readChildMDO(Path path,
                                                                   Either<String, AbstractMDObjectBase> child) {
//    if (!child.isRight()) {
//      var value = child.getLeft();
//      var dotPosition = value.indexOf('.');
//      var type = MDOType.fromValue(value.substring(0, dotPosition));
//      var name = value.substring(dotPosition + 1);
//
//      if (type.isPresent()) {
//        var mdo = MDClasses.readMDObject(path, type.get().getGroupName() + "." + name);
//        if (mdo.isPresent() && mdo.get() instanceof AbstractMDObjectBase) {
//          return Either.right((AbstractMDObjectBase) mdo.get());
//        }
//        return child;
//      }
//    }
    return child;
  }

  private void linkChildAndSubsystem(Map<String, AbstractMDObjectBase> allMDO) {
//    children.stream()
//      .filter(Either::isRight)
//      .map(Either::get)
//      .filter((AbstractMDObjectBase mdo) -> mdo.getMdoType() == MDOType.SUBSYSTEM)
//      .map(Subsystem.class::cast)
//      .forEach(subsystem -> subsystem.linkToChildren(allMDO));
  }

  private void linkCommonAttributesAndUsing(Map<String, AbstractMDObjectBase> allMDO) {
//    children.stream()
//      .filter(Either::isRight)
//      .map(Either::get)
//      .filter((AbstractMDObjectBase mdo) -> mdo.getMdoType() == MDOType.COMMON_ATTRIBUTE)
//      .map(CommonAttribute.class::cast)
//      .forEach(commonAttribute -> commonAttribute.linkUsing(allMDO));
  }

  private void linkExchangePlanAndMDO(Map<String, AbstractMDObjectBase> allMDO) {
//    children.stream()
//      .filter(Either::isRight)
//      .map(Either::get)
//      .filter((AbstractMDObjectBase mdo) -> mdo.getMdoType() == MDOType.EXCHANGE_PLAN)
//      .map(ExchangePlan.class::cast)
//      .forEach(exchangePlan -> exchangePlan.linkToMDO(allMDO));
  }

  private Map<String, AbstractMDObjectBase> getAllMDO() {
    return children.stream()
      .filter(Either::isRight)
      .map(Either::get)
      .collect(Collectors.toMap((AbstractMDObjectBase mdo)
        -> mdo.getMdoReference().getMdoRef(), (AbstractMDObjectBase mdo) -> mdo));
  }

  @Override
  public MultiLanguageString getSynonym() {
    return null;
  }

  @Override
  public SupportVariant getSupportVariant() {
    return null;
  }
}
