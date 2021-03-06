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
package com.github._1c_syntax.mdclasses.mdo.template;

import com.github._1c_syntax.mdclasses.metadata.additional.DataSetType;
import com.github._1c_syntax.mdclasses.metadata.additional.QuerySource;
import com.github._1c_syntax.mdclasses.unmarshal.DataSetConverter;
import com.github._1c_syntax.mdclasses.unmarshal.QuerySourceConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@XStreamConverter(DataSetConverter.class)
public class DataSet {
  /**
   * Имя набора данных
   */
  String name;
  /**
   * Тип набора данных
   */
  @XStreamAlias("xsi:type")
  DataSetType type = DataSetType.DATA_SET_QUERY;
  /**
   * Имя источника данных
   */
  String dataSource = "";
  /**
   * Подчиненные наборы данных
   */
  @XStreamAlias("item")
  @XStreamImplicit
  List<DataSet> items = new ArrayList<>();
  /**
   * Текста запроса (опционально)
   */
  @XStreamAlias("query")
  @XStreamConverter(QuerySourceConverter.class)
  QuerySource querySource = QuerySource.empty();
  /**
   * Поля набора данных
   */
  @XStreamAlias("field")
  @XStreamImplicit
  List<DataSetField> fields = new ArrayList<>();
}
