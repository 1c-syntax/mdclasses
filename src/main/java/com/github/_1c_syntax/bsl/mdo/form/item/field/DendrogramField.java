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
package com.github._1c_syntax.bsl.mdo.form.item.field;

import com.github._1c_syntax.bsl.mdo.form.FormItemHandler;
import com.github._1c_syntax.bsl.mdo.form.item.BaseFormItem;
import com.github._1c_syntax.bsl.mdo.form.item.DataPathRelated;
import com.github._1c_syntax.bsl.mdo.form.item.EventDriven;
import com.github._1c_syntax.bsl.mdo.form.item.FieldItem;
import com.github._1c_syntax.mdclasses.mdo.support.DataPath;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
@Builder
@ToString(of = {"name", "id"})
public class DendrogramField implements FieldItem, DataPathRelated, EventDriven {
  String name;
  int id;
  List<BaseFormItem> children;
  boolean visibility;
  boolean enabled;
  DataPath dataPath;
  List<FormItemHandler> handlers;
}
