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
package com.github._1c_syntax.mdclasses.mdo.children;

import com.github._1c_syntax.mdclasses.mdo.children.xdtopackage.XdtoImport;
import com.github._1c_syntax.mdclasses.mdo.children.xdtopackage.XdtoObjectType;
import com.github._1c_syntax.mdclasses.mdo.children.xdtopackage.XdtoProperty;
import com.github._1c_syntax.mdclasses.mdo.children.xdtopackage.XdtoValueType;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * Вспомогательный класс для хранения содержимого XDTO пакета
 */
@Data
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class XDTOPackageData {

  /**
   * Пространство имен пакета
   */
  @XStreamAsAttribute
  private String targetNamespace = "";

  /**
   * Список импортов пакета
   */
  @XStreamImplicit(itemFieldName = "import")
  private List<XdtoImport> imports = Collections.emptyList();

  /**
   * Список типов значений
   */
  @XStreamImplicit(itemFieldName = "valueType")
  private List<XdtoValueType> valueTypes = Collections.emptyList();

  /**
   * Список типов объектов
   */
  @XStreamImplicit(itemFieldName = "objectType")
  private List<XdtoObjectType> objectTypes = Collections.emptyList();

  /**
   * Список глобальных атрибутов
   */
  @XStreamImplicit(itemFieldName = "property")
  private List<XdtoProperty> properties = Collections.emptyList();
}
