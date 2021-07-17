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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.types.MDOType;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый интерфейс всех объектов метаданных
 */
public interface MDObject {
  /**
   * уникальный идентификатор объекта
   */
  String getUuid();

  /**
   * Имя объекта
   */
  String getName();

  /**
   * Синонимы объекта
   */
  MultiLanguageString getSynonyms();

  /**
   * Строка с комментарием объекта
   */
  String getComment();

  /**
   * MDO-Ссылка на объект
   */
  MdoReference getMdoReference();

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  ObjectBelonging getObjectBelonging();

  /**
   * Возвращает тип метаданных
   */
  MDOType getType();

  /**
   * Возвращает имя метаданных объекта
   */
  String getMetadataName();

  /**
   * Возвращает имя метаданных объекта на русском языке
   */
  String getMetadataNameRu();

  /**
   * Возвращает дочерние элементы объекта.
   * Реализация по умолчанию подходит для большинства случаев, но для объектов, имеющих иные дочерние
   * элементы необходимо переопределение
   */
  default List<MDObject> getChildren() {
    List<MDObject> children = new ArrayList<>();

    if (this instanceof AttributeOwner) {
      children.addAll(((AttributeOwner) this).getAttributes());
    }

    if (this instanceof TabularSectionOwner) {
      children.addAll(((TabularSectionOwner) this).getTabularSections());
    }

    if (this instanceof FormOwner) {
      children.addAll(((FormOwner) this).getForms());
    }

    if (this instanceof CommandOwner) {
      children.addAll(((CommandOwner) this).getCommands());
    }

    if (this instanceof TemplateOwner) {
      children.addAll(((TemplateOwner) this).getTemplates());
    }

    return children;
  }

// todo добавить ИНФО о поддержке, чтобы прям из объекта можно было понимать
}
