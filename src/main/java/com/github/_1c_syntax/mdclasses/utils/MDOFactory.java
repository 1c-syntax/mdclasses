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
package com.github._1c_syntax.mdclasses.utils;

import com.github._1c_syntax.mdclasses.mdo.Command;
import com.github._1c_syntax.mdclasses.mdo.Form;
import com.github._1c_syntax.mdclasses.mdo.FormData;
import com.github._1c_syntax.mdclasses.mdo.HTTPService;
import com.github._1c_syntax.mdclasses.mdo.HTTPServiceURLTemplate;
import com.github._1c_syntax.mdclasses.mdo.Language;
import com.github._1c_syntax.mdclasses.mdo.MDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.MDOConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBSL;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDObjectComplex;
import com.github._1c_syntax.mdclasses.mdo.Subsystem;
import com.github._1c_syntax.mdclasses.mdo.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.Template;
import com.github._1c_syntax.mdclasses.mdo.WEBServiceOperation;
import com.github._1c_syntax.mdclasses.mdo.WebService;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerWrapper;
import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerForm;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOModule;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOReference;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.unmarshal.XStreamFactory;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Фабрика для создания MDO объекта
 */
@UtilityClass
public class MDOFactory {

  /**
   * Читает конфигурацию из корня проекта
   */
  public Optional<MDObjectBase> readMDOConfiguration(ConfigurationSource configurationSource, Path rootPath) {
    return MDOPathUtils.getMDOPath(configurationSource, rootPath,
      MDOType.CONFIGURATION,
      MDOType.CONFIGURATION.getName())
      .flatMap((Path mdoPath) -> readMDObject(configurationSource, MDOType.CONFIGURATION, mdoPath));
  }

  /**
   * Читает объект по его файлу описания, а также его дочерние при наличии
   *
   * @param configurationSource - формат исходных файлов
   * @param type                - тип читаемого объекта
   * @param mdoPath             - путь к файлу описания объекта
   * @return - прочитанный объект
   */
  public Optional<MDObjectBase> readMDObject(ConfigurationSource configurationSource,
                                             MDOType type,
                                             Path mdoPath) {
    Optional<MDObjectBase> mdo = readMDObjectFromFile(configurationSource, type, mdoPath);
    mdo.ifPresent((MDObjectBase mdoValue) -> {
      // проставляем mdo ссылку для объекта
      if (mdoValue.getMdoReference() == null) {
        mdoValue.setMdoReference(new MDOReference(mdoValue));
      }
      // проставляем mdo ссылку для дочерних объектов
      if (mdoValue instanceof MDObjectComplex) {
        computeMdoReference((MDObjectComplex) mdoValue);
      }
      // проставляем mdo ссылку для операций
      if (mdoValue instanceof WebService) {
        ((WebService) mdoValue).getOperations().parallelStream().forEach(
          (WEBServiceOperation child) -> computeMdoReferenceForChild(mdoValue, child));
      }
      // проставляем mdo ссылку дочерних объектов http сервиса
      if (mdoValue instanceof HTTPService) {
        ((HTTPService) mdoValue).getUrlTemplates().parallelStream().forEach(
          (HTTPServiceURLTemplate child) -> {
            computeMdoReferenceForChild(mdoValue, child);
            child.getHttpServiceMethods().parallelStream().forEach(method ->
              computeMdoReferenceForChild(child, method));
          });
      }
      // загружаем дочерние подсистемы для каждой подсистемы
      if (mdoValue instanceof Subsystem) {
        computeSubsystemChildren(configurationSource, (Subsystem) mdoValue, mdoPath);
      }
      // заполняем модули объекта
      if (mdoValue instanceof MDObjectBSL) {
        computeMdoModules(configurationSource, (MDObjectBSL) mdoValue, mdoPath);
      }
      // загрузка всех объектов конфигурации
      if (mdoValue instanceof MDOConfiguration) {
        MDOPathUtils.getRootPathByConfigurationMDO(configurationSource, mdoPath)
          .ifPresent((Path rootPath) -> {
            computeAllMDObject((MDOConfiguration) mdoValue, configurationSource, rootPath);
            setDefaultConfigurationLanguage((MDOConfiguration) mdoValue);
            setIncludedSubsystems((MDOConfiguration) mdoValue);
          });
      }

      if (mdoValue instanceof MDObjectComplex) {
        ((MDObjectComplex) mdoValue).getForms().parallelStream().forEach(form -> {
          Path formDataPath = MDOPathUtils.getFormDataPath(configurationSource, mdoPath.getParent().toString(),
            mdoValue.getName(), form.getName());
          var formDataOptional = readFormData(configurationSource, formDataPath);
          formDataOptional.ifPresent(form::setData);
        });
      }

    });

    return mdo;
  }

  /**
   * Читает данные формы (FormData) в объект из файла
   *
   * @param configurationSource - формат исходных файлов
   * @param path                - путь к файлу описания объекта
   * @return - прочитанный объект
   */
  public Optional<FormData> readFormData(ConfigurationSource configurationSource, Path path) {
    if (!path.toFile().exists()) {
      return Optional.empty();
    }
    FormData formData = null;
    if (configurationSource == ConfigurationSource.EDT) {
      formData = (FormData) XStreamFactory.fromXML(path.toFile());
    } else {
      var designerForm = (DesignerForm) XStreamFactory.fromXML(path.toFile());
      formData = new FormData(designerForm);
    }
    return Optional.ofNullable(formData);
  }

  /**
   * Читает объект по его файлу описания, но не выполняет чтение дочерних элементов
   *
   * @param configurationSource - формат исходных файлов
   * @param type                - тип читаемого объекта
   * @param mdoPath             - путь к файлу описания объекта
   * @return - прочитанный объект
   */
  public Optional<MDObjectBase> readMDObjectFromFile(ConfigurationSource configurationSource,
                                                     MDOType type, Path mdoPath) {
    var mdoFile = mdoPath.toFile();
    if (!mdoFile.exists()) {
      return Optional.empty();
    }

    Optional<MDObjectBase> mdo;
    if (configurationSource == ConfigurationSource.EDT) {
      mdo = Optional.of((MDObjectBase) XStreamFactory.fromXML(mdoFile));
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      DesignerWrapper metaDataObject = (DesignerWrapper) XStreamFactory.fromXML(mdoFile);
      mdo = metaDataObject.getPropertyByType(type, mdoPath);
    } else {
      mdo = Optional.empty();
    }

    return mdo;
  }

  /**
   * Создает объект языка если вдруг его не оказалось в данных конфигурации
   *
   * @param scriptVariant - вариант языка конфигурации
   * @return - созданный и минимально заполненный объект языка
   */
  public Language fakeLanguage(ScriptVariant scriptVariant) {
    var lang = new Language();
    if (scriptVariant == ScriptVariant.ENGLISH) {
      lang.setName("English");
      lang.setLanguageCode("en");
    } else {
      lang.setName("Русский");
      lang.setLanguageCode("ru");
    }
    return lang;
  }

  private void computeAllMDObject(MDOConfiguration configuration,
                                  ConfigurationSource configurationSource,
                                  Path rootPath) {
    List<Either<String, MDObjectBase>> children =
      configuration.getChildren().parallelStream()
        .map(child -> readChildMDO(configurationSource, rootPath, child))
        .collect(Collectors.toList());

    configuration.setChildren(children);
  }

  private Either<String, MDObjectBase> readChildMDO(ConfigurationSource configurationSource,
                                                    Path rootPath,
                                                    Either<String, MDObjectBase> child) {
    if (!child.isRight()) {
      var value = child.getLeft();
      var dotPosition = value.indexOf('.');
      var type = MDOType.fromValue(value.substring(0, dotPosition));
      var name = value.substring(dotPosition + 1);

      if (type.isPresent()) {
        var mdo = MDOPathUtils.getMDOPath(configurationSource, rootPath, type.get(), name)
          .flatMap((Path pathValue) -> readMDObject(configurationSource, type.get(), pathValue));
        return mdo.<Either<String, MDObjectBase>>map(Either::right).orElse(child);
      }
    }
    return child;
  }

  private void setDefaultConfigurationLanguage(MDOConfiguration configuration) {
    if (configuration.getDefaultLanguage().isLeft()) {
      var defaultLang = configuration.getDefaultLanguage().getLeft();

      var language = configuration.getChildren().stream().filter(Either::isRight)
        .map(Either::get).filter((MDObjectBase mdo) -> defaultLang.equals(mdo.getMdoReference().getMdoRef()))
        .findFirst();
      language.ifPresent((MDObjectBase mdo) ->
        configuration.setDefaultLanguage(Either.right((Language) mdo)));
    }
  }

  private void computeMdoReference(MDObjectComplex mdoValue) {
    mdoValue.getForms().parallelStream().forEach((Form child) -> computeMdoReferenceForChild(mdoValue, child));
    mdoValue.getTemplates().parallelStream().forEach((Template child) -> computeMdoReferenceForChild(mdoValue, child));
    mdoValue.getCommands().parallelStream().forEach((Command child) -> computeMdoReferenceForChild(mdoValue, child));
    mdoValue.getAttributes().parallelStream().forEach((MDOAttribute child) -> {
      if (child.getMdoReference() == null) {
        child.setMdoReference(new MDOReference(child, mdoValue));
      }
      // для табличной части надо дополнительно по реквизитам заполнить
      if (child instanceof TabularSection) {
        ((TabularSection) child).getAttributes()
          .forEach((MDOAttribute childTabular) -> {
            if (childTabular.getMdoReference() == null) {
              childTabular.setMdoReference(new MDOReference(childTabular, child));
            }
          });
      }
    });
  }

  private void computeMdoReferenceForChild(MDObjectBase mdoValue, MDObjectBase child) {
    if (child.getMdoReference() == null) {
      child.setMdoReference(new MDOReference(child, mdoValue));
    }
  }

  private void computeSubsystemChildren(ConfigurationSource configurationSource,
                                        Subsystem subsystem, Path subsystemMDOPath) {
    var children = subsystem.getChildren();
    if (children.isEmpty()) {
      return;
    }

    var rootFolder = MDOPathUtils.getMDOTypeFolderByMDOPath(configurationSource, subsystemMDOPath);
    if (rootFolder.isEmpty()) {
      return;
    }

    List<Either<String, MDObjectBase>> newChildren = new ArrayList<>();
    var folder = Paths.get(rootFolder.get().toString(), subsystem.getName(), MDOType.SUBSYSTEM.getGroupName());
    final var startName = MDOType.SUBSYSTEM.getName() + ".";
    children.stream() // параллелизм нельзя!!!! может произойти чтение подсистем из одного фала разными потоками
      .filter(Either::isLeft)
      .filter((Either<String, MDObjectBase> child) -> child.getLeft().startsWith(startName)
        && !child.getLeft().contains("-")) // для исключения битых ссылок сразу
      .forEach((Either<String, MDObjectBase> child) -> {
        // для обработки имен Subsystem._ДемоСервисныеПодсистемы.Subsystem._ДемоПолучениеФайловИзИнтернета
        var subsystemObjectLastPosition = child.getLeft().lastIndexOf(startName);
        var subsystemName = child.getLeft().substring(subsystemObjectLastPosition + startName.length());
        MDOPathUtils.getMDOPath(configurationSource, folder, subsystemName)
          .ifPresent((Path mdoPath) -> {
            var childSubsystem = readMDObjectFromFile(configurationSource, MDOType.SUBSYSTEM, mdoPath);
            childSubsystem.ifPresent((MDObjectBase mdoValue) -> {
              if (mdoValue.getMdoReference() == null) {
                mdoValue.setMdoReference(new MDOReference(mdoValue, subsystem));
              }
              newChildren.add(Either.right(mdoValue));
              computeSubsystemChildren(configurationSource, (Subsystem) mdoValue, mdoPath);
            });
            if (childSubsystem.isEmpty()) {
              if (!child.getLeft().equals(subsystem.getMdoReference().getMdoRef())) {
                // ссылку на самого себя исключаем
                // вернем несуществующий объект обратно в набор
                newChildren.add(child);
              }
            }
          });
      });
    newChildren.addAll(children.stream()
      .filter(Either::isLeft)
      .filter((Either<String, MDObjectBase> child) -> !child.getLeft().startsWith(startName))
      .collect(Collectors.toList()));

    subsystem.setChildren(newChildren);
  }

  private void computeMdoModules(ConfigurationSource configurationSource, MDObjectBSL mdo, Path mdoPath) {
    MDOPathUtils.getMDOTypeFolderByMDOPath(configurationSource, mdoPath, mdo.getType())
      .ifPresent((Path folder) -> {
        setModules(mdo, configurationSource, folder);
        if (mdo instanceof MDObjectComplex) {

          MDOPathUtils.getChildrenFolder(mdo.getName(), folder, MDOType.FORM)
            .ifPresent((Path formFolder) ->
              ((MDObjectComplex) mdo).getForms().parallelStream().forEach((Form form) ->
                setModules(form, configurationSource, formFolder)));

          MDOPathUtils.getChildrenFolder(mdo.getName(), folder, MDOType.COMMAND)
            .ifPresent((Path commandFolder) ->
              ((MDObjectComplex) mdo).getCommands().parallelStream().forEach((Command command) ->
                setModules(command, configurationSource, commandFolder)));
        }
      });
  }

  private void setModules(MDObjectBSL mdo, ConfigurationSource configurationSource, Path folder) {
    List<MDOModule> modules = new ArrayList<>();

    var moduleTypes = MDOUtils.getModuleTypesForMdoTypes().getOrDefault(mdo.getType(), Collections.emptySet());
    if (!moduleTypes.isEmpty()) {
      moduleTypes.forEach((ModuleType moduleType) -> {
        var mdoName = mdo.getName();
        if (mdo.getType() == MDOType.CONFIGURATION) {
          mdoName = "";
        }
        MDOPathUtils.getModulePath(configurationSource, folder, mdoName, moduleType)
          .ifPresent((Path modulePath) -> {
            if (modulePath.toFile().exists()) {
              modules.add(new MDOModule(moduleType, modulePath.toUri()));
            }
          });
      });
    }

    mdo.setModules(modules);
  }

  private void setIncludedSubsystems(MDOConfiguration configuration) {
    Map<String, MDObjectBase> children = configuration.getChildren().stream().filter(Either::isRight)
      .map(Either::get).collect(Collectors.toMap((MDObjectBase mdo)
        -> mdo.getMdoReference().getMdoRef(), (MDObjectBase mdo) -> mdo));

    configuration.getChildren().stream().filter(Either::isRight).map(Either::get)
      .filter((MDObjectBase mdo) -> mdo.getType() == MDOType.SUBSYSTEM)
      .forEach(subsystem -> setSubsystemForChildren((Subsystem) subsystem, children));
  }

  private void setSubsystemForChildren(Subsystem subsystem, Map<String, MDObjectBase> allChildren) {
    List<Either<String, MDObjectBase>> children = new ArrayList<>();

    subsystem.getChildren().forEach((Either<String, MDObjectBase> mdoPair) -> {
      if (mdoPair.isLeft()) {
        var mdo = allChildren.get(mdoPair.getLeft());
        if (mdo != null) {
          children.add(Either.right(mdo));
          setSubsystemForChild(subsystem, allChildren, mdo);
        } else {
          children.add(mdoPair);
        }
      } else {
        children.add(mdoPair);
        var mdo = mdoPair.get();
        setSubsystemForChild(subsystem, allChildren, mdo);
      }
    });

    subsystem.setChildren(children);
  }

  private void setSubsystemForChild(Subsystem subsystem, Map<String, MDObjectBase> allChildren, MDObjectBase mdo) {
    mdo.addIncludedSubsystem(subsystem);
    if (mdo instanceof Subsystem) {
      setSubsystemForChildren((Subsystem) mdo, allChildren);
    }
  }
}
