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

import com.github._1c_syntax.mdclasses.mdo.attributes.AbstractMDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import com.github._1c_syntax.mdclasses.mdo.support.AttributeKind;
import com.github._1c_syntax.mdclasses.mdo.support.MDOModule;
import com.github._1c_syntax.mdclasses.mdo.support.MDOReference;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import com.github._1c_syntax.mdclasses.mdo.support.ObjectBelonging;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Базовый класс для тестов объектов метаданных
 */
abstract class AbstractMDOTest {

  /**
   * Каталог исходников конфигурации в формате EDT
   */
  private static final String SRC_EDT = "src/test/resources/metadata/edt/src";

  /**
   * Каталог исходников расширения конфигурации в формате EDT
   */
  private static final String SRC_EXT_EDT = "src/test/resources/metadata/edt_ext/src";

  /**
   * Каталог исходников конфигурации в формате EDT для англоязычной конфигурации
   */
  private static final String SRC_EDT_EN = "src/test/resources/metadata/edt_en/src";

  /**
   * Каталог исходников конфигурации в формате Конфигуратора
   */
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";

  /**
   * Каталог исходников расширения конфигурации в формате Конфигуратора
   */
  private static final String SRC_EXT_DESIGNER = "src/test/resources/metadata/original_ext";

  /**
   * Тип тестируемого объекта метаданных
   */
  private final MDOType mdoType;

  /**
   * Ппинадлженость объектов по умолчанию
   */
  private final ObjectBelonging objectBelonging;

  AbstractMDOTest(MDOType type) {
    mdoType = type;
    objectBelonging = ObjectBelonging.OWN;
  }

  AbstractMDOTest(MDOType type, ObjectBelonging belonging) {
    mdoType = type;
    objectBelonging = belonging;
  }

  /**
   * Обязательный тест в формате EDT
   */
  @Test
  abstract void testEDT();

  /**
   * Обязательный тест в формате Конфигуратора
   */
  @Test
  abstract void testDesigner();

  /**
   * Возвращает объект метаданных по файлу описания в формате EDT
   *
   * @param partPath путь к файлу описания объекта
   * @return прочитанный объект
   */
  protected AbstractMDObjectBase getMDObjectEDT(String partPath) {
    var mdo = MDOFactory.readMDObject(Paths.get(SRC_EDT, partPath));
    assertThat(mdo).isPresent();
    return mdo.get();
  }

  /**
   * Возвращает объект метаданных по файлу описания в формате EDT для расширения
   *
   * @param partPath путь к файлу описания объекта
   * @return прочитанный объект
   */
  protected AbstractMDObjectBase getMDObjectEDTExt(String partPath) {
    var mdo = MDOFactory.readMDObject(Paths.get(SRC_EXT_EDT, partPath));
    assertThat(mdo).isPresent();
    return mdo.get();
  }

  /**
   * Возвращает объект метаданных по файлу описания в формате EDT для англоязычной конфигурации
   *
   * @param partPath путь к файлу описания объекта
   * @return прочитанный объект
   */
  protected AbstractMDObjectBase getMDObjectEDTEn(String partPath) {
    var mdo = MDOFactory.readMDObject(Paths.get(SRC_EDT_EN, partPath));
    assertThat(mdo).isPresent();
    return mdo.get();
  }

  /**
   * Возвращает объект метаданных по файлу описания в формате Конфигуратора
   *
   * @param partPath путь к файлу описания объекта
   * @return прочитанный объект
   */
  protected AbstractMDObjectBase getMDObjectDesigner(String partPath) {
    var mdo = MDOFactory.readMDObject(getMDOPathDesigner(partPath));
    assertThat(mdo).isPresent();
    return mdo.get();
  }

  /**
   * Возвращает объект метаданных по файлу описания в формате Конфигуратора
   *
   * @param partPath путь к файлу описания объекта
   * @return прочитанный объект
   */
  protected AbstractMDObjectBase getMDObjectDesignerExt(String partPath) {
    var mdo = MDOFactory.readMDObject(Paths.get(SRC_EXT_DESIGNER, partPath));
    assertThat(mdo).isPresent();
    return mdo.get();
  }

  /**
   * Проверяет корректность чтения базовых полей
   */
  protected void checkBaseField(AbstractMDObjectBase mdo, Class<?> clazz, String name, String uuid) {
    assertThat(mdo)
      .isInstanceOf(clazz).extracting(AbstractMDObjectBase::getName)
      .isEqualTo(name);

    assertThat(mdo.getType()).isEqualTo(mdoType);

    assertThat(mdo.getUuid()).isEqualTo(uuid);
    assertThat(mdo.getMdoReference())
      .isNotNull().extracting(MDOReference::getType)
      .isEqualTo(mdoType);
    assertThat(mdo.getMdoReference().getMdoRef())
      .isEqualTo(mdoType.getName() + "." + name);
    assertThat(mdo.getMdoReference().getMdoRefRu())
      .isEqualTo(mdoType.getNameRu() + "." + name);

    assertThat(mdo.getObjectBelonging()).isEqualTo(objectBelonging);

  }

  /**
   * Выполнение проверки дочерних форм - пусто
   */
  protected void checkForms(AbstractMDObjectBase mdo) {
    assertThat(mdo).isInstanceOf(AbstractMDObjectComplex.class);
    var mdoComplex = (AbstractMDObjectComplex) mdo;
    assertThat(mdoComplex.getForms()).isEmpty();
  }

  /**
   * Выполнение проверки дочерних форм
   */
  protected void checkForms(AbstractMDObjectBase mdo,
                            int count,
                            String... names) {
    assertThat(mdo).isInstanceOf(AbstractMDObjectComplex.class);
    var mdoComplex = (AbstractMDObjectComplex) mdo;
    var children = mdoComplex.getForms();
    assertThat(names).hasSize(count);
    assertThat(children).hasSize(count);
    assertThat(children).allMatch(AbstractMDObjectBase.class::isInstance);
    assertThat(children).allMatch(child -> List.of(names).contains(child.getName()));
    children.forEach(child -> checkChild(mdo.getMdoReference(), MDOType.FORM, ModuleType.FormModule, child));
  }

  /**
   * Выполнение проверки дочерних макетов - пусто
   */
  protected void checkTemplates(AbstractMDObjectBase mdo) {
    assertThat(mdo).isInstanceOf(AbstractMDObjectComplex.class);
    var mdoComplex = (AbstractMDObjectComplex) mdo;
    assertThat(mdoComplex.getTemplates()).isEmpty();
  }

  /**
   * Выполнение проверки дочерних макетов
   */
  protected void checkTemplates(AbstractMDObjectBase mdo, int count, String... names) {
    assertThat(mdo).isInstanceOf(AbstractMDObjectComplex.class);
    var mdoComplex = (AbstractMDObjectComplex) mdo;
    var children = mdoComplex.getTemplates();
    assertThat(names).hasSize(count);
    assertThat(children).hasSize(count);
    assertThat(children).allMatch(AbstractMDObjectBase.class::isInstance);
    assertThat(children).allMatch(child -> List.of(names).contains(child.getName()));
    children.forEach(child -> checkChild(mdo.getMdoReference(), MDOType.TEMPLATE, ModuleType.UNKNOWN, child));
  }

  /**
   * Выполнение проверки дочерних команд - пусто
   */
  protected void checkCommands(AbstractMDObjectBase mdo) {
    assertThat(mdo).isInstanceOf(AbstractMDObjectComplex.class);
    var mdoComplex = (AbstractMDObjectComplex) mdo;
    assertThat(mdoComplex.getCommands()).isEmpty();
  }

  /**
   * Выполнение проверки дочерних команд
   */
  protected void checkCommands(AbstractMDObjectBase mdo, int count, String... names) {
    assertThat(mdo).isInstanceOf(AbstractMDObjectComplex.class);
    var mdoComplex = (AbstractMDObjectComplex) mdo;
    var children = mdoComplex.getCommands();
    assertThat(names).hasSize(count);
    assertThat(children).hasSize(count);
    assertThat(children).allMatch(AbstractMDObjectBase.class::isInstance);
    assertThat(children).allMatch(child -> List.of(names).contains(child.getName()));
    children.forEach(child -> checkChild(mdo.getMdoReference(), MDOType.COMMAND, ModuleType.CommandModule, child));
  }

  /**
   * Выполняет проверку дочерних элементов-реквизитов и табличных частей
   */
  protected void checkAttributes(List<AbstractMDOAttribute> children,
                                 int count,
                                 MDOReference parentMdoReference,
                                 AttributeType... types) {
    assertThat(children).hasSize(count);
    assertThat(children).allMatch(AbstractMDObjectBase.class::isInstance);
    assertThat(children)
      .allMatch(mdoAttribute -> List.of(types).contains(mdoAttribute.getAttributeType()));

    assertThat(children).allMatch(mdoAttribute -> mdoAttribute.getObjectBelonging() == objectBelonging);
    assertThat(children).allMatch(mdoAttribute -> mdoAttribute.getKind() == AttributeKind.CUSTOM);

    children.forEach(attribute -> {
      assertThat(attribute.getMdoReference())
        .isNotNull()
        .extracting(MDOReference::getType)
        .isEqualTo(MDOType.ATTRIBUTE);
      assertThat(attribute.getMdoReference().getMdoRef())
        .startsWith(parentMdoReference.getMdoRef())
        .endsWith("." + attribute.getMetadataName() + "." + attribute.getName());
      assertThat(attribute.getMdoReference().getMdoRefRu())
        .startsWith(parentMdoReference.getMdoRefRu())
        .endsWith("." + attribute.getMetadataNameRu() + "." + attribute.getName());
    });
  }

  /**
   * Выполняет проверку модулей
   */
  protected void checkModules(List<MDOModule> modules, int count, String parentPartPath, ModuleType... types) {
    assertThat(modules).hasSize(count);
    assertThat(modules)
      .allMatch(mdoModule -> List.of(types).contains(mdoModule.getModuleType()))
      .extracting(MDOModule::getUri).extracting(URI::getPath)
      .allMatch(s -> s.contains(parentPartPath));
  }

  /**
   * Проверка на невозможность наличия модулей
   */
  protected void checkNoModules(AbstractMDObjectBase mdo) {
    assertThat(mdo).isNotInstanceOf(AbstractMDObjectBSL.class);
  }

  /**
   * Проверка на невозможность наличия дочерних объектов
   */
  protected void checkNoChildren(AbstractMDObjectBase mdo) {
    assertThat(mdo).isNotInstanceOf(AbstractMDObjectComplex.class);
  }

  /**
   * Проверка корректности заполнения дочерних элементов
   */
  protected void checkChild(MDOReference parentMdoReference,
                            MDOType type,
                            ModuleType moduleType,
                            AbstractMDObjectBase child) {
    checkNoChildren(child);
    assertThat(child.getMdoReference())
      .isNotNull()
      .extracting(MDOReference::getType)
      .isEqualTo(type);
    assertThat(child.getMdoReference().getMdoRef())
      .startsWith(parentMdoReference.getMdoRef())
      .endsWith("." + type.getName() + "." + child.getName());
    assertThat(child.getMdoReference().getMdoRefRu())
      .startsWith(parentMdoReference.getMdoRefRu())
      .endsWith("." + type.getNameRu() + "." + child.getName());

    assertThat(child.getObjectBelonging()).isEqualTo(objectBelonging);

    if (child instanceof AbstractMDObjectBSL) {
      checkModules(((AbstractMDObjectBSL) child).getModules(), 1,
        type.getGroupName() + "/" + child.getName(), moduleType);
    }
  }

  protected static Path getMDOPathDesigner(String path) {
    return Paths.get(SRC_DESIGNER, path);
  }

}
