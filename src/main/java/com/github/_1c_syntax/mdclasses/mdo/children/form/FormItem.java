/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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
package com.github._1c_syntax.mdclasses.mdo.children.form;

import com.github._1c_syntax.mdclasses.mdo.support.DataPath;
import com.github._1c_syntax.mdclasses.unmarshal.converters.StringConverterIntern;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Модель элемента формы в формате EDT
 */
@Data
@ToString(of = {"id", "name"})
@NoArgsConstructor
public class FormItem {
  /**
   * Имя элемента
   */
  private String name;
  /**
   * Идентификатор элемента, в конфигураторе не отображается
   */
  private int id = -1;
  /**
   * Тип элемента. Например Label или InputField. На текущий момент в виде строки
   */
  @XStreamConverter(StringConverterIntern.class)
  private String type = "";
  /**
   * Признак видимости элемента
   */
  private boolean visible = true;
  /**
   * Признак доступности элемента
   */
  private boolean enabled = true;
  /**
   * Подчиненные элементы формы
   */
  @XStreamImplicit
  private List<FormItem> children = new ArrayList<>();
  /**
   * Путь к данным элемента формы. Путь к данным может быть пустым (пустая строка)
   */
  private DataPath dataPath = DataPath.EMPTY;

  /**
   * Обработчики событий элемента
   */
  @XStreamImplicit
  private List<FormHandlerItem> handlers = Collections.emptyList();
}
