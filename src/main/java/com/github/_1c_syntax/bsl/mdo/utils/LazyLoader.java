/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
package com.github._1c_syntax.bsl.mdo.utils;

import com.github._1c_syntax.bsl.mdclasses.ConfigurationTree;
import com.github._1c_syntax.bsl.mdo.Attribute;
import com.github._1c_syntax.bsl.mdo.AttributeOwner;
import com.github._1c_syntax.bsl.mdo.CalculationRegister;
import com.github._1c_syntax.bsl.mdo.ChartOfAccounts;
import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.CommandOwner;
import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.Enum;
import com.github._1c_syntax.bsl.mdo.ExternalDataSource;
import com.github._1c_syntax.bsl.mdo.FormOwner;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.Register;
import com.github._1c_syntax.bsl.mdo.TabularSectionOwner;
import com.github._1c_syntax.bsl.mdo.Task;
import com.github._1c_syntax.bsl.mdo.TemplateOwner;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceCube;
import com.github._1c_syntax.bsl.mdo.storage.ManagedFormData;
import com.github._1c_syntax.bsl.mdo.storage.form.FormItem;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Набор методов для ленивой загрузки данных в MD/MDC
 */
@UtilityClass
public class LazyLoader {

  /**
   * Производит расчет списка дочерних объектов исходя из типа объекта.
   *
   * @param mdo Объект, у которого есть дочерние элементы
   * @return Немодифицируемый список дочерних объектов
   */
  public List<MD> computeChildren(ChildrenOwner mdo) {
    List<MD> children = Collections.emptyList();

    if (mdo instanceof AttributeOwner attributeOwner) {
      children = addAll(children, attributeOwner.getAllAttributes());
    }

    if (mdo instanceof TabularSectionOwner tabularSectionOwner) {
      children = addAll(children, tabularSectionOwner.getTabularSections());
    }

    if (mdo instanceof CommandOwner commandOwner) {
      children = addAll(children, commandOwner.getCommands());
    }

    if (mdo instanceof TemplateOwner templateOwner) {
      children = addAll(children, templateOwner.getTemplates());
    }

    if (mdo instanceof FormOwner formOwner) {
      children = addAll(children, formOwner.getForms());
    }

    if (mdo instanceof Enum anEnum) {
      children = addAll(children, anEnum.getEnumValues());
    }

    if (mdo instanceof Task task) {
      children = addAll(children, task.getAddressingAttributes());
    }

    if (mdo instanceof CalculationRegister calculationRegister) {
      children = addAll(children, calculationRegister.getRecalculations());
    }

    if (mdo instanceof ExternalDataSource externalDataSource) {
      children = addAll(children, externalDataSource.getTables());
      children = addAll(children, externalDataSource.getCubes());
      children = addAll(children, externalDataSource.getFunctions());
    }

    if (mdo instanceof ExternalDataSourceCube externalDataSourceCube) {
      children = addAll(children, externalDataSourceCube.getDimensionTables());
    }

    return Collections.unmodifiableList(children);
  }

  /**
   * Производит расчет списка дочерних объектов исходя из типа объекта. Список включает все дочерних по иерархии вниз
   *
   * @param mdo Объект, у которого есть дочерние элементы
   * @return Немодифицируемый список дочерних объектов
   */
  public List<MD> computePlainChildren(ChildrenOwner mdo) {
    List<MD> children = addAll(Collections.emptyList(), mdo.getChildren());
    children = addAll(children, children.stream().filter(ChildrenOwner.class::isInstance)
      .map(ChildrenOwner.class::cast)
      .map(ChildrenOwner::getPlainChildren)
      .flatMap(Collection::stream)
      .toList());
    return Collections.unmodifiableList(children);
  }

  /**
   * Производит расчет списка дочерних объектов, выступающих атрибутами для хранения данных (в том числе и ТЧ)
   *
   * @param mdo Объект, у которого есть дочерние элементы-атрибуты
   * @return Немодифицируемый список
   */
  public List<MD> computeStorageFields(ChildrenOwner mdo) {
    List<MD> children = Collections.emptyList();

    if (mdo instanceof AttributeOwner attributeOwner) {
      children = addAll(children, attributeOwner.getAllAttributes());
    }

    if (mdo instanceof TabularSectionOwner tabularSectionOwner) {
      children = addAll(children, tabularSectionOwner.getTabularSections());
    }
    return Collections.unmodifiableList(children);
  }

  /**
   * Производит расчет списка дочерних объектов, выступающих атрибутами для хранения данных
   * (в том числе и ТЧ и их атрибуты)
   *
   * @param mdo Объект, у которого есть дочерние элементы-атрибуты
   * @return Немодифицируемый список
   */
  public List<MD> computePlainStorageFields(AttributeOwner mdo) {
    List<MD> children = addAll(Collections.emptyList(), mdo.getStorageFields());
    children = addAll(children, children.stream().filter(AttributeOwner.class::isInstance)
      .map(AttributeOwner.class::cast)
      .map(AttributeOwner::getPlainStorageFields)
      .flatMap(Collection::stream)
      .toList());
    return Collections.unmodifiableList(children);
  }

  /**
   * Производит расчет списка атрибутов сложных объектов
   *
   * @param mdo Объект, у которого есть дочерние элементы-атрибуты
   * @return Немодифицируемый список
   */
  public List<Attribute> computeAllAttributes(AttributeOwner mdo) {
    List<Attribute> children = Collections.emptyList();
    if (mdo instanceof Register register) {
      children = addAll(children, register.getAttributes());
      children = addAll(children, register.getResources());
      children = addAll(children, register.getDimensions());
    }

    if (mdo instanceof ChartOfAccounts chartOfAccounts) {
      children = addAll(children, chartOfAccounts.getAttributes());
      children = addAll(children, chartOfAccounts.getAccountingFlags());
      children = addAll(children, chartOfAccounts.getExtDimensionAccountingFlags());
    }

    if (mdo instanceof ExternalDataSourceCube cube) {
      children = addAll(children, cube.getResources());
      children = addAll(children, cube.getDimensions());
    }

    return Collections.unmodifiableList(children);
  }

  /**
   * Производит расчет списка всех модулей объекта, включая дочерних
   *
   * @param mdo Объект, у которого модули
   * @return Немодифицируемый список
   */
  public List<Module> computeAllModules(ModuleOwner mdo) {
    var modules = new ArrayList<>(mdo.getModules());
    if (mdo instanceof ChildrenOwner childrenOwner) {
      modules.addAll(childrenOwner.getPlainChildren().stream()
        .filter(ModuleOwner.class::isInstance)
        .map(ModuleOwner.class::cast)
        .map(ModuleOwner::getModules)
        .flatMap(List::stream)
        .toList());
    }

    return Collections.unmodifiableList(modules);
  }

  /**
   * Создает соответствие URI модуля объекта к его типу.
   * Используется все модуля объекта, включая дочерних объектов.
   *
   * @param mdo объект, у которого есть модули
   * @return немодифицироруемое соответствие
   */
  public Map<URI, ModuleType> computeModulesByType(ModuleOwner mdo) {
    return mdo.getAllModules().stream()
      .collect(Collectors.toUnmodifiableMap(Module::getUri, Module::getModuleType));
  }

  /**
   * Создает соответствие URI модуля объекта к самому объекту.
   * Используется все модуля объекта, включая дочерних объектов.
   *
   * @param mdo объект, у которого есть модули
   * @return немодифицироруемое соответствие
   */
  public Map<URI, MD> computeModulesByObject(ChildrenOwner mdo) {
    Map<URI, MD> result = new HashMap<>();
    mdo.getPlainChildren().stream()
      .filter(ModuleOwner.class::isInstance)
      .map(ModuleOwner.class::cast)
      .forEach(moduleOwner -> moduleOwner.getModules()
        .forEach(module -> result.put(module.getUri(), moduleOwner))
      );

    if (mdo instanceof ModuleOwner moduleOwner) {
      moduleOwner.getModules().forEach(module -> result.put(module.getUri(), moduleOwner));
    }
    return Collections.unmodifiableMap(result);
  }

  /**
   * Производит расчет соответствия ссылки довернего объекта к нему самому
   *
   * @param childrenOwner родительский объект
   * @return Немодифицируемое соответствие
   */
  public static Map<MdoReference, MD> computeChildrenByMdoRef(ChildrenOwner childrenOwner) {
    return childrenOwner.getPlainChildren().stream()
      .collect(Collectors.toUnmodifiableMap(MD::getMdoReference, child -> child));
  }

  /**
   * Создает соответствие URI модуля объекта модулю.
   * Используется все модуля объекта, включая дочерних объектов.
   *
   * @param mdo объект, у которого есть модули
   * @return немодифицироруемое соответствие
   */
  public Map<URI, Module> computeModulesByURI(ModuleOwner mdo) {
    return mdo.getAllModules().stream()
      .collect(Collectors.toUnmodifiableMap(Module::getUri, module -> module));
  }

  /**
   * Производит расчет списка дочерних элементов формы. Список включает все дочерних по иерархии вниз
   *
   * @param formItem Элемент формы, у которого есть дочерние элементы
   * @return Немодифицируемый список дочерних объектов
   */
  public List<FormItem> computePlainFormItems(FormItem formItem) {
    List<FormItem> items = addAll(Collections.emptyList(), formItem.getItems());
    items = addAll(items, items.stream()
      .map(FormItem::getPlainItems)
      .flatMap(Collection::stream)
      .toList());
    return Collections.unmodifiableList(items);
  }

  /**
   * Производит расчет списка дочерних элементов формы. Список включает все дочерних по иерархии вниз
   *
   * @param formData Форма, у которой есть дочерние элементы
   * @return Немодифицируемый список дочерних объектов
   */
  public List<FormItem> computePlainFormItems(ManagedFormData formData) {
    List<FormItem> items = addAll(Collections.emptyList(), formData.getItems());
    items = addAll(items, items.stream()
      .map(FormItem::getPlainItems)
      .flatMap(Collection::stream)
      .toList());
    return Collections.unmodifiableList(items);
  }

  /**
   * Производит расчет соответствия имени общего модуля к нему самому
   *
   * @param cf Конфигурация или расширение
   * @return Немодифицируемое соответствие
   */
  public static Map<String, CommonModule> computeCommonModulesByName(ConfigurationTree cf) {
    Map<String, CommonModule> result = new CaseInsensitiveMap<>();
    cf.getCommonModules().forEach(commonModule -> result.put(commonModule.getName(), commonModule));
    return Collections.unmodifiableMap(result);
  }

  private <T> List<T> addAll(List<T> result, List<? extends T> source) {
    if (result.isEmpty()) {
      return Collections.unmodifiableList(source);
    } else if (source.isEmpty()) {
      return result;
    } else if (isUnmodifiableList(result)) {
      List<T> newList = new ArrayList<>(result);
      newList.addAll(source);
      return newList;
    } else {
      result.addAll(source);
      return result;
    }
  }

  public static <T> boolean isUnmodifiableList(List<T> list) {
    try {
      list.addAll(Collections.emptyList());
      return false;
    } catch (UnsupportedOperationException e) {
      return true;
    }
  }
}
