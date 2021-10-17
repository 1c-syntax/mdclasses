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
package com.github._1c_syntax.bsl.mdo.form.item.group;

import com.github._1c_syntax.bsl.mdo.form.FormItemHandler;
import com.github._1c_syntax.bsl.mdo.form.item.BaseFormItem;
import com.github._1c_syntax.bsl.mdo.form.item.EventDriven;
import com.github._1c_syntax.bsl.mdo.form.item.GroupItem;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.List;

/**
 * Элемент формы: Страницы
 */
@Value
@Builder
@ToString(of = {"name", "id"})
public class Pages implements GroupItem, EventDriven {
  /**
   * Имя элемента
   */
  @Builder.Default
  String name = "";
  /**
   * Идентификатор элемента
   */
  @Builder.Default
  int id = -1;
  /**
   * Дочерние элементы
   */
  @Builder.Default
  List<BaseFormItem> children = Collections.emptyList();
  /**
   * Видимость
   */
  boolean visibility;
  /**
   * Доступность
   */
  boolean enabled;
  /**
   * События элемента
   */
  @Builder.Default
  List<FormItemHandler> handlers = Collections.emptyList();
}
