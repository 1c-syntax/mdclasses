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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class XdtoPackage implements MDObject, IncludedSubsystems {

  /**
   * Имя
   */
  String name;

  /**
   * Уникальный идентификатор
   */
  String uuid;

  /**
   * Комментарий к объекту
   */
  String comment;

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  ObjectBelonging objectBelonging;

  /**
   * Тип метаданных
   */
  MDOType type;

  /**
   * Имя метаданных объекта
   */
  String metadataName;

  /**
   * Имя метаданных объекта на русском языке
   */
  String metadataNameRu;

  /**
   * Синонимы объекта
   */
  MultiLanguageString synonyms;

  /**
   * MDO-Ссылка на объект
   */
  MdoReference mdoReference;

  /**
   * Список подсистем, в состав которых входит объект
   */
  List<Subsystem> includedSubsystems;

//  /**
//   * Пространство имен xdto-пакета
//   */
//  String namespace;

//  /**
//   * Путь к файлу с данными xsd-схемы
//   */
//  private Path packageDataPath;
//
//  /**
//   * Содержимое xsd-схемы пакета
//   */
//  private XDTOPackageData data;
//
//  public XdtoPackage(DesignerMDO designerMDO) {
//    super(designerMDO);
//    namespace = designerMDO.getProperties().getNamespace();
//  }
//
//
}
