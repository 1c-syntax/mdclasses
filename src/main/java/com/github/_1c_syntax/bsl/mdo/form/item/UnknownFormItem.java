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
package com.github._1c_syntax.bsl.mdo.form.item;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.List;

/**
 * Неопределенный элемент формы
 */
@Value
@Builder
@ToString(of = {"name", "id"})
public class UnknownFormItem implements BaseFormItem {
  /**
   * Имя элемента
   */
  @Builder.Default
  String name = "";
  /**
   * Идентификатор элемента
   */
  int id = -1;
  /**
   * Тип группы элемента
   */
  FormItemGroupType groupType = FormItemGroupType.UNKNOWN;
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
}
