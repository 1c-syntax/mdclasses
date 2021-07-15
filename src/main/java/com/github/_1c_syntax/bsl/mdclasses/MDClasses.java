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
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDConfiguration;
import com.github._1c_syntax.mdclasses.mdo.support.LanguageContent;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import io.vavr.control.Either;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@UtilityClass
public class MDClasses {

  private static final Map<Object, MDObject> mdObjects = new ConcurrentHashMap<>();

  @SneakyThrows
  public MDClass createConfiguration(Path path) {
    mdObjects.clear();
    var configurationSource = MDOUtils.getConfigurationSourceByPath(path);
    if (configurationSource != ConfigurationSource.EMPTY) {
      var configurationFromFile = MDOFactory.readMDOConfiguration(configurationSource, path);
      if (configurationFromFile.isPresent()) {
        var configurationValue = (MDConfiguration) configurationFromFile.get();

        if (configurationValue.getObjectBelonging() == ObjectBelonging.ADOPTED) {
          var configurationBuilder = ConfigurationExtension.builder()
            .configurationSource(configurationSource);
          computeConfigurationBaseFields(configurationBuilder, configurationValue);
          var children = computeChildren(configurationValue);
          configurationBuilder.children(children);
          configurationBuilder.allChildren(computeAllChildren(children));
          return configurationBuilder.build();
        } else {
          var configurationBuilder = Configuration.builder()
            .configurationSource(configurationSource);

          computeConfigurationBaseFields(configurationBuilder, configurationValue);
          var children = computeChildren(configurationValue);
          configurationBuilder.children(children);
          configurationBuilder.allChildren(computeAllChildren(children));
          return configurationBuilder.build();
        }
      }
    }
    return Configuration.builder().build();
  }

  private static List<MDObject> computeChildren(MDConfiguration configurationValue) {
    return configurationValue.getChildren().stream().filter(Either::isRight).map(Either::get)
      .map(AbstractMDObjectBase::buildMDObject)
      .filter(Objects::nonNull)
      .map(MDClasses::build)
      .collect(Collectors.toList());
  }

  private static List<MDObject> computeAllChildren(List<MDObject> children) {
    // todo
    return children;
  }

  private static void computeConfigurationBaseFields(Configuration.ConfigurationBuilder builder, MDConfiguration source) {
    builder
      .uuid(source.getUuid())
      .name(source.getName())
      .compatibilityMode(source.getCompatibilityMode())
      .configurationExtensionCompatibilityMode(source.getConfigurationExtensionCompatibilityMode())
      .scriptVariant(source.getScriptVariant())
      .defaultRunMode(ApplicationRunMode.getByName(source.getDefaultRunMode()))
      .dataLockControlMode(source.getDataLockControlMode())
      .objectAutonumerationMode(source.getObjectAutonumerationMode())
      .modalityUseMode(source.getModalityUseMode())
      .synchronousExtensionAndAddInCallUseMode(source.getSynchronousExtensionAndAddInCallUseMode())
      .synchronousPlatformExtensionAndAddInCallUseMode(source.getSynchronousPlatformExtensionAndAddInCallUseMode())
      .useManagedFormInOrdinaryApplication(source.isUseManagedFormInOrdinaryApplication())
      .useOrdinaryFormInManagedApplication(source.isUseOrdinaryFormInManagedApplication());

    if (source.getDefaultLanguage().isRight()) {
      var lang = source.getDefaultLanguage().get();
      if (lang.getBuilder() == null) {
        lang.buildMDObject();
      }
      builder.defaultLanguage((Language) build(lang.getBuilder()));
    } else {
      // todo такого не бывает
    }
    builder
      .copyrights(new MultiLanguageString(source.getCopyrights().stream()
        .collect(Collectors.toUnmodifiableMap(LanguageContent::getLanguage, LanguageContent::getContent))))
      .detailedInformation(new MultiLanguageString(source.getDetailedInformation().stream()
        .collect(Collectors.toUnmodifiableMap(LanguageContent::getLanguage, LanguageContent::getContent))))
      .briefInformation(new MultiLanguageString(source.getBriefInformation().stream()
        .collect(Collectors.toUnmodifiableMap(LanguageContent::getLanguage, LanguageContent::getContent))));
  }

  // todo копипаста
  private static void computeConfigurationBaseFields(ConfigurationExtension.ConfigurationExtensionBuilder builder,
                                                     MDConfiguration source) {
    builder
      .uuid(source.getUuid())
      .name(source.getName())
      .configurationExtensionCompatibilityMode(source.getConfigurationExtensionCompatibilityMode())
      .scriptVariant(source.getScriptVariant())
      .defaultRunMode(ApplicationRunMode.getByName(source.getDefaultRunMode()))
      .dataLockControlMode(source.getDataLockControlMode())
      .objectAutonumerationMode(source.getObjectAutonumerationMode())
      .modalityUseMode(source.getModalityUseMode())
      .synchronousExtensionAndAddInCallUseMode(source.getSynchronousExtensionAndAddInCallUseMode())
      .synchronousPlatformExtensionAndAddInCallUseMode(source.getSynchronousPlatformExtensionAndAddInCallUseMode())
      .useManagedFormInOrdinaryApplication(source.isUseManagedFormInOrdinaryApplication())
      .useOrdinaryFormInManagedApplication(source.isUseOrdinaryFormInManagedApplication())
      .configurationExtensionPurpose(source.getConfigurationExtensionPurpose())
      .namePrefix(source.getNamePrefix());

    // при отсутствии режима совместимости конфигурации в расширении используется режим
    // совместимости расширений
    if (CompatibilityMode.compareTo(source.getCompatibilityMode(), "") == 0) {
      builder.compatibilityMode(source.getConfigurationExtensionCompatibilityMode());
    } else {
      builder.compatibilityMode(source.getCompatibilityMode());
    }

    if (source.getDefaultLanguage().isRight()) {
      var lang = source.getDefaultLanguage().get();
      if (lang.getBuilder() == null) {
        lang.buildMDObject();
      }
      builder.defaultLanguage((Language) build(lang.getBuilder()));
    } else {
      // todo такого не бывает
    }
    builder
      .copyrights(new MultiLanguageString(source.getCopyrights().stream()
        .collect(Collectors.toUnmodifiableMap(LanguageContent::getLanguage, LanguageContent::getContent))))
      .detailedInformation(new MultiLanguageString(source.getDetailedInformation().stream()
        .collect(Collectors.toUnmodifiableMap(LanguageContent::getLanguage, LanguageContent::getContent))))
      .briefInformation(new MultiLanguageString(source.getBriefInformation().stream()
        .collect(Collectors.toUnmodifiableMap(LanguageContent::getLanguage, LanguageContent::getContent))));
  }

  @SneakyThrows
  // todo времянка
  public MDObject build(Object builder) {
    if (mdObjects.containsKey(builder)) {
      return mdObjects.get(builder);
    } else {
      var mdObject = (MDObject) builder.getClass().getDeclaredMethod("build").invoke(builder);
      mdObjects.put(builder, mdObject);
      return mdObject;
    }
  }

  @SneakyThrows
  // todo времянка
  public Module buildModule(Object builder) {
    return (Module) builder.getClass().getDeclaredMethod("build").invoke(builder);
  }
}
